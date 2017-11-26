package cloud.delaye.backend.api.mapper;

import cloud.delaye.backend.api.MapperMapping;
import io.probedock.jee.validation.ApiErrorResponse;
import io.probedock.jee.validation.IErrorCode;
import io.probedock.jee.validation.IErrorLocationType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import cloud.delaye.backend.api.BackendResponse;

/**
 * Abstract mapper that responds with a JSON API error response.
 *
 * @author Valentin Delaye (jonesbusy@gmail.com)
 * @param <E> Type of exception
 */
public abstract class AbstractApiExceptionMapper<E extends Exception> implements ExceptionMapper<E> {

	@Override
	public Response toResponse(E exception) {

		final BackendResponse response = new BackendResponse();

		// get error response from subclass
		response.configure(toApiErrorResponse(exception));

		// enrich the response
		enrich(response);
		
		return response.build();
	}

	protected void enrich(BackendResponse apiResponse) {
		// do nothing to avoid forcing to implement that method
	}
	
	/**
	 * @return Error code of this ExceptionMapper
	 */
	protected IErrorCode getCode() {
		return MapperMapping.getMapping(this).getCode();
	}
	
	/**
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
