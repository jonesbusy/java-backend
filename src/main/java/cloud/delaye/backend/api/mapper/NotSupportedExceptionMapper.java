package cloud.delaye.backend.api.mapper;


import cloud.delaye.backend.api.DefaultLocationType;
import cloud.delaye.backend.api.DefaultLocationType.LocationTypes;
import cloud.delaye.backend.validation.ApiErrorResponse;
import jakarta.ws.rs.NotSupportedException;
import jakarta.ws.rs.ext.Provider;

/**
 * Maps Jersey not supported exceptions to an HTTP 415 Unsupported Media Type API response.
 *
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
@Provider
@DefaultLocationType(LocationTypes.HEADER_LOCATION_TYPE)
public class NotSupportedExceptionMapper extends AbstractApiExceptionMapper<NotSupportedException> {

	@Override
	protected ApiErrorResponse toApiErrorResponse(NotSupportedException exception) {
		return new ApiErrorResponse(
			"The media type of the request body is unsupported.", 
			getCode(), 
			getLocationType(), 
			"Content-Type"
		);
	}
}
