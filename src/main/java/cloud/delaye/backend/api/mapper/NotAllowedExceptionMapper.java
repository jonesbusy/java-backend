package cloud.delaye.backend.api.mapper;


import io.probedock.jee.validation.ApiErrorResponse;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.ext.Provider;

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
