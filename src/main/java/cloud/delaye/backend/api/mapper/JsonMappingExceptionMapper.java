package cloud.delaye.backend.api.mapper;

import cloud.delaye.backend.api.DefaultLocationType;
import cloud.delaye.backend.api.DefaultLocationType.LocationTypes;
import com.fasterxml.jackson.databind.JsonMappingException;
import cloud.delaye.backend.validation.ApiErrorResponse;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.Provider;

/**
 * Maps JSON mapping exceptions (type check errors) to an HTTP 400 Bad Request API response.
 *
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
@DefaultLocationType(LocationTypes.JSON_LOCATION_TYPE)
public class JsonMappingExceptionMapper extends AbstractJsonExceptionMapper<JsonMappingException> {

	@Override
	protected ApiErrorResponse toApiErrorResponse(JsonMappingException jme) {
		return new ApiErrorResponse(
			buildMessage(jme), 
			getCode(), 
			getLocationType(), 
			""
		);
	}

	private String buildMessage(JsonMappingException jme) {
		return "JSON parsing error due to invalid JSON value type" + getLocationDetails(jme) + ".";
	}
}
