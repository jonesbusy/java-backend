package cloud.delaye.backend.entities;

import cloud.delaye.backend.data.IEntity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * An event received by the backend
 *
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
@Entity
public class Event implements IEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * RAW data of the event
	 */
	@Column(columnDefinition="JSON NOT NULL")
	private String raw;

	/**
	 * The server timestamp
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition="DATETIME(3) NOT NULL")
	private Date serverTimestamp;

	//<editor-fold defaultstate="collapsed" desc="Constructors">
	public Event() {
		serverTimestamp = new Date();
	}
	
	public Event(String raw) {
		this.raw = raw;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="Getters & Setters">
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getRaw() {
		return raw;
	}

	public void setRaw(String raw) {
		this.raw = raw;
	}
	
	public Date getServerTimestamp() {
		return serverTimestamp;
	}

	public void setServerTimestamp(Date serverTimestamp) {
		this.serverTimestamp = serverTimestamp;
	}	
	
	//</editor-fold>	

}
