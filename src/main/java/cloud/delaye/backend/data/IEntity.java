package cloud.delaye.backend.data;

import java.io.Serializable;

/**
 * Base interface for all entities.
 * 
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
public interface IEntity extends Serializable {
	
	/**
	 * Get the ID of the entity.
	 * 
	 * @return The id of the entity. 
	 */
	public Long getId();
	
	/**
	 * Set the ID of the entity
	 * 
	 * @param id The id of the entity.
	 */
	public void setId(Long id);
	
}
