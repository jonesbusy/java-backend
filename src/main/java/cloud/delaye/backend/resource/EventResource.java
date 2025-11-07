package cloud.delaye.backend.resource;

import cloud.delaye.backend.Main;
import cloud.delaye.backend.api.pagination.PaginationData;
import cloud.delaye.backend.api.pagination.PaginationHelper;
import cloud.delaye.backend.api.pagination.PaginationQueryParam;
import cloud.delaye.backend.data.EventDao;
import cloud.delaye.backend.entities.Event;
import cloud.delaye.backend.enums.EApiErrorCodes;
import cloud.delaye.backend.validation.ApiErrorResponse;
import cloud.delaye.backend.validation.ApiErrorsException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.URLCodec;
import cloud.delaye.backend.api.BackendResponse;
import cloud.delaye.backend.entities.EventTo;
import cloud.delaye.backend.json.JsonUtility;
import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.transaction.TransactionSynchronizationRegistry;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * API REST resource for Events
 * 
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
@Path("events")
@Stateless
public class EventResource {
	
	//<editor-fold defaultstate="collapsed" desc="Logger">
	private static final Logger LOG = LoggerFactory.getLogger(EventResource.class);
	//</editor-fold>
	
	//<editor-fold defaultstate="collapsed" desc="Dao">
	private final EventDao eventDao = new EventDao(Main.getEntityManagerFactory().createEntityManager());
	//</editor-fold>
	
	// Standard query param to ignore when querying properties
	private static final String[] STANDARD_QUERY_PARAMS
		= new String[]{
			PaginationQueryParam.PAGE_PARAM,
			PaginationQueryParam.PAGE_SIZE,
		};
	
	private final PaginationHelper paginationHelper = new PaginationHelper();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(String rawEvent) throws ApiErrorsException, IOException, DecoderException {
		
		// Reserved key
		if(JsonUtility.getObjectMap(rawEvent, String.class).containsKey("id")) {
			throwApiError("Unable to create event with 'id' field.", EApiErrorCodes.REQUEST_UNKNOWN_JSON_PROPERTY);
		}
		
		eventDao.startTransaction();
		Event event = eventDao.create();
		event.setRaw(rawEvent);
		eventDao.persist(event);
		eventDao.commitTransaction();
		
		EventTo to = transfer(event);
		String response = JsonUtility.getJson(to);
				
		// Return raw JSON data of events
		return new BackendResponse(Response.status(Response.Status.CREATED).entity(response)).build();
	}	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveAll(@BeanParam PaginationQueryParam pagination,
								@Context UriInfo uriInfo) throws ApiErrorsException, IOException, DecoderException {
				
		Map<String, String> properties = filterSearchProperties(uriInfo, STANDARD_QUERY_PARAMS);
		
		PaginationData paginationData = paginationHelper.buildPagination(pagination, eventDao.count(properties));
		
		List<Event> events = eventDao.find(properties, paginationData.getOffset(), paginationData.getPageSize());
		
		// Return raw JSON data of events
		return new BackendResponse(Response.status(Response.Status.OK).entity(events.stream()
				.map(this::transfer).collect(Collectors.toList())))
							.configure(paginationData).build();
	}
	
		
	protected void throwApiError(String message, EApiErrorCodes code) throws ApiErrorsException {
		throw new ApiErrorsException(new ApiErrorResponse(message, code));
	}
	
	/**
	 * Transfer an event to a JSON transfer object
	 * @param event The event
	 * @return The transfer object
	 */
	protected EventTo transfer(Event event) {
		EventTo to = new EventTo();
		to.setId(event.getId());
		to.setMap(JsonUtility.getObjectMap(event.getRaw(), String.class));
		return to;
	}
	
	/**
	 * Filter query param properties based on URI info and array of standard query params. 
	 * 
	 * @param uriInfo URI info
	 * @param standardQueryParam Standard query params to ignore
	 * @return Filtered properties
	 * @throws DecoderException If unable to decode a query param
	 */
	protected Map<String, String> filterSearchProperties(UriInfo uriInfo, String[] standardQueryParam) throws DecoderException {
		
		MultivaluedMap<String, String> uriParameters = uriInfo.getQueryParameters();
		Map<String, String> properties = new HashMap<>();
		for (Map.Entry<String, List<String>> params : uriParameters.entrySet()) {
			
			// Take first parameters
			String value = uriParameters.getFirst(params.getKey());
			if (!Arrays.asList(standardQueryParam).contains(params.getKey())) {
				properties.put(params.getKey(), new String(URLCodec.decodeUrl(value.getBytes(StandardCharsets.UTF_8))));
			}
			
		}
		return properties;
	}
	
}
