package cloud.delaye.backend.data;

import java.util.List;
import java.util.Map;
import cloud.delaye.backend.entities.Event;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 * Data access layer for {@link Event}
 */
public class EventDao extends AbstractDao<Event> {

	//<editor-fold defaultstate="collapsed" desc="Constructor">
	public EventDao(EntityManager entityManager) {
		super(Event.class, entityManager);
	}
	//</editor-fold>
	
	public List<Event> find(Map<String, String> properties, int offset, int limit) {
		TypedQuery<Event> typedQuery = buildFindTypedQuery(properties);
		typedQuery.setFirstResult(offset);
		typedQuery.setMaxResults(limit);
		return typedQuery.getResultList();
	}
	
	public long count(Map<String, String> properties) {
		TypedQuery<Long> typedQuery = buildCountTypedQuery(properties);
		return typedQuery.getSingleResult();		
	}
	
	/**
	 * Builds up a typed query for counting the Events matching the query
	 * parameters criteria.
	 *
	 * @param properties The search properties
	 * @return the built query
	 */
	private TypedQuery<Long> buildCountTypedQuery(Map<String, String> properties) {

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
		Root<Event> from = criteriaQuery.from(Event.class);

		Predicate predicate = buildPredicate(cb, from, properties);
		criteriaQuery.where(predicate);

		CriteriaQuery<Long> select = criteriaQuery.select(cb.count(from));
		return createCountQuery(select);
	}

	
	/**
	 * Builds up a typed query for retrieving the Events matching the
	 * query parameters criteria.
	 *
	 * @param properties Properties
	 * @return the built query
	 */
	private TypedQuery<Event> buildFindTypedQuery(Map<String, String> properties) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Event> criteriaQuery = cb.createQuery(Event.class);
		Root<Event> from = criteriaQuery.from(Event.class);

		Predicate predicate = buildPredicate(cb, from, properties);
		criteriaQuery.where(predicate);

		CriteriaQuery<Event> select = criteriaQuery.select(from);

		return createQuery(select);
	}
	
	/**
	 * Build a predicate with all the conditions needed in the where clause.
	 *
	 * @param cb The criteria builder
	 * @param from The root type
	 * @param properties The search properties
	 * @return The built predicate
	 */
	private Predicate buildPredicate(CriteriaBuilder cb, Root<Event> from,  Map<String, String> properties) {
		
		Predicate predicate = cb.conjunction();
		
		if (!properties.isEmpty()) {
			
			for (Map.Entry<String, String> entry : properties.entrySet()) {
				
				// Build JSON expression
				Expression<String> expression = cb.function("JSON_UNQUOTE", String.class, 
						cb.function("JSON_EXTRACT", String.class, from.<String>get("raw"), cb.literal("$.\"" + entry.getKey() + "\"")));
				predicate = cb.and(predicate, cb.equal(expression, entry.getValue()));

			}
		}

		return predicate;
	}	

}
