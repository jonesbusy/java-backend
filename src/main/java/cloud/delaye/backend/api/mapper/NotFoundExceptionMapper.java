package cloud.delaye.backend.api.mapper;


import cloud.delaye.backend.validation.ApiErrorResponse;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.ext.Provider;

/**
 * Maps Jersey not found exceptions to an HTTP 404 Not Found API response.
 *
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
@Provider
public class NotFoundExceptionMapper extends AbstractApiExceptionMapper<NotFoundException> {

	@Override
	protected ApiErrorResponse toApiErrorResponse(NotFoundException exception) {
		return new ApiErrorResponse(
			"Resource not found.", 
			getCode()
		);
	}
}
