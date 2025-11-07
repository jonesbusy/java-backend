package cloud.delaye.backend.data;

import java.util.List;
import jakarta.persistence.EntityManager;

/**
 * Base interface for all entity DAO
 * 
 * @param <T> The class of the managed entity
 * 
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
public interface IDao<T extends IEntity> {
	
	/**
	 * Find the entity by its id
	 * 
	 * @param id The entity to find
	 * @return The entity found, null otherwise
	 */
	T findById(Long id);

	/**
	 * Find the entity by its id
	 * 
	 * @param id The entity to find
	 * @return The entity found, null otherwise
	 */
	T findById(String id);
    
	/**
	 * Retrieve all the entities.
	 * 
	 * @return The list of entities
	 */
	List<T> findAll();

	/**
	 * Returns the number of total rows
	 * 
	 * @return Number of total entities in the database.
	 */
	Long countAll();
	
	/**
	 * Retrieve all the entities in the specified range.
	 * 
	 * @param offset The starting index
	 * @param limit The limit to get
	 * @return The list of entities
	 */
	List<T> findAll(int offset, int limit);

	/**
	 * Return a new empty entity that is not persisted
	 * 
	 * @return A new empty entity
	 */
	T create();

	/**
	 * Save an entity not already persisted. If the entity is already 
	 * persisted, the entity will be updated.
	 * 
	 * @param entity The entity to create
	 */
	void persist(T entity);
	
	/**
	 * Merge an entity
	 * 
	 * @param entity The merged entity
	 * @return The merged entity
	 */
	T merge(T entity);

	/**
	 * Put the entity in the persistence context if the entity 
	 * is not in the persistence context
	 * 
	 * @param entity The entity to attach
	 * @return The attached entity
	 */
	T attach(T entity);
	
	/**
	 * Delete the entity
	 * 
	 * @param entity The entity to delete
	 */
	void remove(T entity);

	/**
	 * Delete the entity by its id
	 * 
	 * @param id The id of the entity to remove
	 */
	void removeById(Long id);

	/**
	 * Reload the state of an entity
	 * 
	 * @param entity The entity to reload
	 */
	void refresh(T entity);

	/**
	 * In some cases, it is required to manually flush the persistence
	 * context to be sure the underlying database layer receive the
	 * SQL operations in the right order.
	 * 
	 * @see EntityManager#flush() 
	 */
	void flush();
	
	/**
	 * Determine whether the specified entity is managed by the persistence
	 * context or detached.
	 * 
	 * @param entity The entity to check
	 * @return <code>true</code> if managed, otherwise <code>false</code>
	 */
	boolean contains(T entity);

	/**
	 * Helper method to check if an entity is already persisted or not.
	 * 
	 * @param entity The entity for which the state would be checked
	 * @return true if persisted, false otherwise.
	 */
	boolean isPersisted(T entity);
	
}
