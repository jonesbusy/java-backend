package cloud.delaye.backend.api.mapper;


import cloud.delaye.backend.validation.ApiErrorResponse;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.ext.Provider;

/**
 * Maps Jersey not allowed exceptions to an HTTP 405 Not Allowed API response.
 *
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
@Provider
public class NotAllowedExceptionMapper extends AbstractApiExceptionMapper<NotAllowedException> {

	@Override
	protected ApiErrorResponse toApiErrorResponse(NotAllowedException exception) {
		return new ApiErrorResponse(
			"This resource does not support this HTTP verb.", 
			getCode()
		);
	}
}
