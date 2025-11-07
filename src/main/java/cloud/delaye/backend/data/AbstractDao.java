
package cloud.delaye.backend.data;

import java.util.List;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

/**
 * DAO that support user transaction to define the transaction boundaries
 * on environment without container management (Java SE)
 * 
 * @author Valentin Delaye (jonesbusy@gmail.com)
 * 
 * @param <T> Type of entity
 */
public abstract class AbstractDao<T extends IEntity> implements IDao<T> {

	/**
	 * The entity manager
	 */
	private EntityManager em;
	
	/**
	 * Current transaction
	 */
	private EntityTransaction transaction;
	
	/**
	 * The entity type managed by the DAO.
	 */
	private final Class<T> entityClass;
	
	/**
	 * Constructor for the DAO.
	 * @param clazz the entity class
	 * @param entityManager the entity manager
	 */
	public AbstractDao(Class<T> clazz, EntityManager entityManager) {
		em = entityManager;
		entityClass = clazz;
	}
	//</editor-fold>

	/**
	 * Find an entity by ID.
	 * 
	 * @param id ID of the entity
	 * 
	 * @return The entity or null if not found. 
	 */
	@Override
	public T findById(Long id) {
		return em.find(entityClass, id);
	}

	/**
	 * Find an entity by ID.
	 * 
	 * @param id ID of the entity
	 * 
	 * @return The entity or null if not found. 
	 */
	@Override
	public T findById(String id) {
		return em.find(entityClass, Long.parseLong(id));
	}

	/**
	 * Find all entities.
	 * 
	 * @return List of entity 
	 */
	@Override
	public List<T> findAll() {
		TypedQuery<T> query = em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass);
		return query.getResultList();
	}
	
	@Override
	public List<T> findAll(int offset, int limit) {
		TypedQuery<T> query = em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass);
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	/**
	 * Count all entities.
	 * 
	 * @return The number of entities.
	 */
	@Override
	public Long countAll() {
		TypedQuery<Long> query = em.createQuery("SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e", Long.class);
		return query.getSingleResult();
	}

	/**
	 * Create a new entity.
	 * 
	 * @return A new entity.
	 */
	@Override
	public T create() {
		try {
			return entityClass.getDeclaredConstructor().newInstance();
		}
		catch (ReflectiveOperationException ex) {
			return null;
		}
	}

	/**
	 * Returns if an entity is persisted or not.
	 * 
	 * @param entity The entity
	 * @return True if the entity is persisted, false otherwise
	 */
	@Override
	public boolean isPersisted(T entity) {
		return entity.getId() != null && entity.getId() > 0;
	}

	/**
	 * Attach the entity to the persistence context,
	 * 
	 * @param entity Thee entity to attach
	 * @return The entity.
	 */
	@Override
	public T attach(T entity) {
		if (!em.contains(entity) && isPersisted(entity)) {
			return (T) em.merge(entity);
		}

		return entity;
	}

	@Override
	public void remove(T entity) {
		em.remove(attach(entity));
	}

	@Override
	public void removeById(Long id) {
		remove(findById(id));
	}

	/**
	 * Refresh the entity.
	 * @param entity the entity to refresh
	 */
	public void refresh(Entity entity) {
		em.refresh(entity);
	}

	@Override
	public void flush() {
		em.flush();
	}

	@Override
	public void persist(T entity) {
		em.persist(entity);
	}

	@Override
	public T merge(T entity) {
		return em.merge(entity);
	}
	
	@Override
	public void refresh(T entity) {
		em.refresh(entity);
	}

	@Override
	public boolean contains(T entity) {
		return em.contains(entity);
	}
	
	/**
	 * Create a typed named query in the persistent context
	 * 
	 * @param name The name of the query to create
	 * @return The query created
	 */
	protected TypedQuery<T> createNamedTypedQuery(String name) {
		return em.createNamedQuery(name, entityClass);
	}
	
	/**
	 * Create a typed named query specialized for count
	 * 
	 * @param name The name of the query to create
	 * @return The typed query created
	 */
	protected TypedQuery<Long> createCountQuery(String name) {
		return em.createNamedQuery(name, Long.class);
	}
	
	/**
	 * Create a typed named query specialized for delete
	 * 
	 * @param name The name of the query to create
	 * @return The typed query created
	 */
	protected TypedQuery<Integer> createDeleteQuery(String name) {
		return em.createNamedQuery(name, Integer.class);
	}
	
	/**
	 * Create a named query in the persistent context
     *
	 * @param name The name of the query to create
	 * @param resultClass The result class
	 * @return The query created
	 */
	protected Query createNamedNativeQuery(String name, Class resultClass) {
		return em.createNativeQuery(name, resultClass);
	}
	
	/**
	 * Execute and return a single result
	 *
	 * @param query The typed query to execute
	 * @return The entity returned by the query, null if no result
	 * @throws NonUniqueResultException More than one result found
	 */
	protected T getSingleResult(TypedQuery<T> query) throws NonUniqueResultException {
		try {
			return query.getSingleResult();
		}
		catch (NoResultException nre) {
			return null;
		}	
	}

	/**
	/**
	 * Create a query from a query string
	 *
	 * @param queryString The query string to get the query object
	 * @return The query object
	 */
	protected Query createQuery(String queryString) {
		return em.createQuery(queryString);
	}
	
	/**
	 * @return Retrieve the criteria builder from the entity manager
	 */
	protected CriteriaBuilder getCriteriaBuilder() {
		return em.getCriteriaBuilder();
	}
	
	/**
	 * Create a typed query from a criteria query
	 * 
	 * @param criteriaQuery The criteria query
	 * @return The created typed query
	 */
	protected TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
		return em.createQuery(criteriaQuery);
	}
	
	/**
	 * Create a criteria query specialized for the count query types
	 * 
	 * @param criteriaQuery The criteria query
	 * @return The typed query created
	 */
	protected TypedQuery<Long> createCountQuery(CriteriaQuery<Long> criteriaQuery) {
		return em.createQuery(criteriaQuery);
	}
	
	/**
	 * Set the transaction entity manager to use
	 * @param em The entity manager
	 */
	protected final void setTransactionalEntityManager(EntityManager em) {
		this.em = em;
	}
	
	public void startTransaction() {
		transaction = em.getTransaction();
		if(!transaction.isActive()) {
			transaction.begin();
		}
	}
	
	public void endTransaction() {
		checkTransaction();
		transaction.commit();
	}
	
	public void commitTransaction() {
		checkTransaction();
		transaction.commit();
	}
	
	public void rollbackTransaction() {
		checkTransaction();
		transaction.rollback();
	}
	
	public void closeEntityManager() {
		if(em != null) {
			em.close();
		}
	}
	
	public void closeEntityManagerIfNeeded() {
		if(!isEntityManagerClosed()) {
			closeEntityManager();
		}
	}
	
	public boolean isEntityManagerClosed() {
		if(em == null) {
			return true;
		}
		else {
			return !em.isOpen();
		}
	}
	
	private void checkTransaction() throws IllegalArgumentException {
		if(transaction == null || !transaction.isActive()) {
			throw new IllegalArgumentException("No transaction is currently active.");
		}
	}
	
}
