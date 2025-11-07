package cloud.delaye.backend.api.mapper;

import cloud.delaye.backend.validation.ApiErrorResponse;
import cloud.delaye.backend.validation.ApiErrorsException;
import jakarta.ws.rs.ext.Provider;

/**
 * Maps API errors exceptions to an API response. The API error response enclosed in the exception
 * determines the HTTP status code and is used as the response body.
 *
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
@Provider
public class ApiErrorsExceptionMapper extends AbstractApiExceptionMapper<ApiErrorsException> {

	/**
	 * Default constructor.
	 */
	public ApiErrorsExceptionMapper() {
	}


	@Override
	protected ApiErrorResponse toApiErrorResponse(ApiErrorsException exception) {
			return exception.getErrorResponse();
	}
}
