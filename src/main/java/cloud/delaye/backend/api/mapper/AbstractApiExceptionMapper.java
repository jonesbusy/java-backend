package cloud.delaye.backend.api.mapper;

import cloud.delaye.backend.api.MapperMapping;
import cloud.delaye.backend.validation.ApiErrorResponse;
import cloud.delaye.backend.validation.IErrorCode;
import cloud.delaye.backend.validation.IErrorLocationType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import cloud.delaye.backend.api.BackendResponse;

/**
 * Abstract mapper that responds with a JSON API error response.
 *
 * @author Valentin Delaye (jonesbusy@gmail.com)
 * @param <E> Type of exception
 */
public abstract class AbstractApiExceptionMapper<E extends Exception> implements ExceptionMapper<E> {

	/**
	 * Default constructor.
	 */
	public AbstractApiExceptionMapper() {
	}

	@Override
	public Response toResponse(E exception) {

		final BackendResponse response = new BackendResponse();

		// get error response from subclass
		response.configure(toApiErrorResponse(exception));

		// enrich the response
		enrich(response);
		
		return response.build();
	}

	/**
	 * Enrich the response with additional information.
	 * Override this method to add custom behavior.
	 * @param apiResponse the response to enrich
	 */
	protected void enrich(BackendResponse apiResponse) {
		// do nothing to avoid forcing to implement that method
	}
	
	/**
	 * Get the error code for this exception mapper.
	 * @return Error code of this ExceptionMapper
	 */
	protected IErrorCode getCode() {
		return MapperMapping.getMapping(this).getCode();
	}
	
	/**
	 * Get the location type for this exception mapper.
	 * @return Location type of this ExceptionMapper
	 */
	protected IErrorLocationType getLocationType() {
		return MapperMapping.getMapping(this).getLocationType();
	}
	
	/**
	 * Returns the API error response that will be used as the response body.
	 *
	 * @param exception the exception to map
	 * @return an API error response
	 */
	protected abstract ApiErrorResponse toApiErrorResponse(E exception);
}
