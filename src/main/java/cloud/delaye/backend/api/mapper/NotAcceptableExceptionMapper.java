package cloud.delaye.backend.api.mapper;


import cloud.delaye.backend.api.DefaultLocationType;
import cloud.delaye.backend.api.DefaultLocationType.LocationTypes;
import cloud.delaye.backend.validation.ApiErrorResponse;
import jakarta.ws.rs.NotAcceptableException;
import jakarta.ws.rs.ext.Provider;


/**
 * Maps Jersey not acceptable exceptions to an HTTP 406 Not Acceptable API response.
 *
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
@Provider
@DefaultLocationType(LocationTypes.HEADER_LOCATION_TYPE)
public class NotAcceptableExceptionMapper extends AbstractApiExceptionMapper<NotAcceptableException> {

	@Override
	protected ApiErrorResponse toApiErrorResponse(NotAcceptableException exception) {
		return new ApiErrorResponse(
			"The requested media type is not available.", 
			getCode(), 
			getLocationType(), 
			"Accept"
			);
	}
}
