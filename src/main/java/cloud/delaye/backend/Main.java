package cloud.delaye.backend;

import cloud.delaye.backend.api.ApiRestApplication;
import com.sun.net.httpserver.HttpServer;
import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.slf4j.bridge.SLF4JBridgeHandler;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.core.Application;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main entry class
 * 
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
public class Main {

	//<editor-fold defaultstate="collapsed" desc="Logger">
	public final Logger LOG;
	//</editor-fold>
	
	/**
	 * Executor service
	 */
	private final ExecutorService service = Executors.newFixedThreadPool(20);

	/**
	 * Entity manager factory
	 */
	private static EntityManagerFactory entityManagerFactory;
	
	/**
	 * Current HTTP server
	 */
	private HttpServer server;

	/**
	 * Default HTTP port
	 */
	private static final int HTTP_PORT = 48080;

	public Main() {
		LOG = LoggerFactory.getLogger(Main.class);
	}

	public void start() throws Exception {

		// Configure logger to redirect everything to logback
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();
		
		LOG.info("Starting IOT Java backend Please be patient...");
		
		// The entity manager factory with optional schema generation
		String schemaGeneration = System.getProperty("delaye.cloud.backend.schema.generation", "none");
		if (!"none".equals(schemaGeneration)) {
			LOG.info("Schema generation enabled: {}", schemaGeneration);
			java.util.Map<String, String> properties = new java.util.HashMap<>();
			properties.put("eclipselink.ddl-generation", schemaGeneration);
			properties.put("eclipselink.ddl-generation.output-mode", "database");
			
			// Override database connection properties from system properties if provided
			String dbHost = System.getProperty("delaye.cloud.backend.db.host");
			String dbPort = System.getProperty("delaye.cloud.backend.db.port", "3306");
			String dbName = System.getProperty("delaye.cloud.backend.db.name", "JAVA-BACKEND");
			if (dbHost != null) {
				String jdbcUrl = "jdbc:mariadb://" + dbHost + ":" + dbPort + "/" + dbName + "?autoReconnectForPools=true&autoCommit=true";
				LOG.info("Using database URL: {}", jdbcUrl);
				properties.put("jakarta.persistence.jdbc.url", jdbcUrl);
			}
			
			entityManagerFactory = Persistence.createEntityManagerFactory("PU", properties);
			// Trigger schema generation by creating and closing an EntityManager
			jakarta.persistence.EntityManager em = entityManagerFactory.createEntityManager();
			em.close();
			LOG.info("Schema generation completed");
		} else {
			entityManagerFactory = Persistence.createEntityManagerFactory("PU");
		}

		// Start the HTTP containers
		startHttpContainer(ApiRestApplication.class, HTTP_PORT);
		
		LOG.info("Successfully started IOT backend");
		LOG.info("REST API available at 'http://127.0.0.1:" + HTTP_PORT + "/api'");

	}


	public void stop() throws Exception {

		LOG.debug("Stopping the deamon...");

		// At the end, stop the executor service (gracefully)
		if (service != null) {
			LOG.debug("Try to shutdown Executor service...");
			service.shutdown();
			service.awaitTermination(5, TimeUnit.SECONDS);
		}
		
		// Stop the HTTP server
		if(server != null) {
			LOG.debug("Try to shutdown HTTP server...");
			server.stop(10);
		}

		LOG.debug("Executor service shutdown. Please wait until tall tasks complete....");

	}

	public static void main(String[] args) throws Exception {

		// Main application
		final Main application = new Main();

		try {
			application.start();
		}
		catch (Exception e) {
			application.LOG.error("Unable to start backend", e);
			application.stop();
		}
		
	}

	public Main(Logger log) {
		this.LOG = log;
	}

	public static EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}
	
	/**
	 * Start container for an HTTP server
	 * @param application The JAX-RS application application
	 * @param port The port to run the container
	 */
    public final void startHttpContainer(Class<? extends Application> application, int port) {
				
		// Build an start the undertow server
		UndertowJaxrsServer server = new UndertowJaxrsServer();
		Undertow.Builder builder = Undertow.builder()
				   .addHttpListener(port, "0.0.0.0")
				   .setWorkerThreads(200)
				   .setIoThreads(4);
		
		server.start(builder);
		
		// Deploy the application
		DeploymentInfo deploymentInfo = server.undertowDeployment(application)
				.setClassLoader(application.getClassLoader())
                .setContextPath("/api")
				.setDeploymentName("IOT")
				.addListeners(Servlets.listener(org.jboss.weld.environment.servlet.Listener.class));
		
		server.deploy(deploymentInfo);
		
    }

}
