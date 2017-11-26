package cloud.delaye.backend.api.mapper;


import io.probedock.jee.validation.ApiErrorResponse;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ext.Provider;

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
