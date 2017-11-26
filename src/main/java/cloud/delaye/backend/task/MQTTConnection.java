package cloud.delaye.backend.task;

import java.net.URISyntaxException;
import javax.jms.JMSException;
import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.Listener;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import cloud.delaye.backend.Main;
import cloud.delaye.backend.data.EventDao;
import cloud.delaye.backend.entities.Event;
import cloud.delaye.backend.json.JsonUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to maintain a JMS connection to the broker.
 * 
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
public final class MQTTConnection {

	//<editor-fold defaultstate="collapsed" desc="Logger">
	private static final Logger LOG = LoggerFactory.getLogger(MQTTConnection.class);
	//</editor-fold>

	private static String url;
	private static String username;
	private static String password;
	
	private static MQTTConnection INSTANCE;

	/**
	 * callback connection
	 */
	private CallbackConnection connection;
	
	/**
	 * To maintain the status of the connection
	 */
	private boolean connected;
	
	//<editor-fold defaultstate="collapsed" desc="Constructors">
	private MQTTConnection() {
		
	}
	//</editor-fold>

	/**
	 * Return if the JMS connection is successfully connected
	 * @return True or false
	 */
	public boolean isConnected() {
		return connected;
	}
	
	public synchronized void initialize() throws JMSException, URISyntaxException {
		
		MQTT mqtt = new MQTT();
		mqtt.setHost(url);
		mqtt.setUserName(username);
		mqtt.setPassword(password);
		mqtt.setKeepAlive((short)5);
		mqtt.setReconnectDelay(5000);
		mqtt.setReconnectBackOffMultiplier(1);
		

		if (connection != null || connected) {
			LOG.warn("MQTT connection is already initialized. Nothing to do.");
			return;
		}

		connection = mqtt.callbackConnection();
		
		connection.listener(new Listener() {
			@Override
			public void onConnected() {
				LOG.info("Successfully connected to MQTT broker");
				connected = true;
			}

			@Override
			public void onDisconnected() {
				LOG.error("Was disconnected from MQTT broker");
				connected = false;
			}

			@Override
			public void onPublish(UTF8Buffer utf8, Buffer buffer, Runnable ack) {
				
				String message = UTF8Buffer.decode(buffer);
				LOG.info("Received message from  topic '" + UTF8Buffer.decode(utf8) + "' message='" + message + "'");
				
				if(JsonUtility.isValidJson(message)) {
					
					EventDao dao = new EventDao(Main.getEntityManagerFactory().createEntityManager());
					dao.startTransaction();
					dao.persist(new Event(message));
					dao.commitTransaction();
				}
				
				ack.run();
			}

			@Override
			public void onFailure(Throwable e) {
				LOG.error("Connection failure", e);
				connected = false;	
			}
		});
		
		connection.connect(new Callback<Void>() {
			@Override
			public void onSuccess(Void arg0) {
				// Do nothing
			}

			@Override
			public void onFailure(Throwable arg0) {
				// Do nothing
			}
		});
				
		Topic[] topics = {new Topic("iot", QoS.EXACTLY_ONCE)};
		connection.subscribe(topics, new Callback<byte[]>() {
			@Override
			public void onSuccess(byte[] t) {
				LOG.info("Successfully suscribed");
			}

			@Override
			public void onFailure(Throwable thrwbl) {
				LOG.error("Unable to suscribe");
			}
		});

	}

	/**
	 * Publish a message 
	 * @param message The message
	 * @param destination The destination queue
	 */
	public void publish(final String message, final String destination) {
		// TODO : Implement publish	
	}

	/**
	 * Configure the JMS connection
	 * @param url The URL to connect
	 * @param username The username
	 * @param password The password
	 */
	public static void configure(String url, String username, String password) {
		MQTTConnection.url = url;
		MQTTConnection.username = username;
		MQTTConnection.password = password;
	}

	/**
	 * Get the singleton instance
	 * @return The instance of the JMS connection
	 */
	public static MQTTConnection getInstance() {

		if (INSTANCE == null) {
			synchronized (MQTTConnection.class) {
				// Avoid race condition
				if (INSTANCE == null) {
					INSTANCE = new MQTTConnection();
				}
			}
		}

		return INSTANCE;
	}

	//<editor-fold defaultstate="collapsed" desc="Getters">
	public String getUrl() {
		return url;
	}
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	//</editor-fold>

}
