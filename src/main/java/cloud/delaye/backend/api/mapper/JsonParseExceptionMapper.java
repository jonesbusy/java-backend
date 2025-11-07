package cloud.delaye.backend.api.mapper;


import cloud.delaye.backend.api.DefaultLocationType;
import com.fasterxml.jackson.core.JsonParseException;
import cloud.delaye.backend.validation.ApiErrorResponse;
import jakarta.ws.rs.ext.Provider;

/**
 * Maps JSON parsing exceptions to an HTTP 400 Bad Request API response.
 *
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
@Provider
@DefaultLocationType(DefaultLocationType.LocationTypes.JSON_LOCATION_TYPE)
public class JsonParseExceptionMapper extends AbstractJsonExceptionMapper<JsonParseException> {

	/**
	 * Default constructor.
	 */
	public JsonParseExceptionMapper() {
	}


	@Override
	protected ApiErrorResponse toApiErrorResponse(JsonParseException jpe) {
		return new ApiErrorResponse(buildMessage(jpe), 
			getCode(),
			getLocationType(),
			""
		);
	}

	private String buildMessage(JsonParseException jpe) {
		return "JSON parsing error due to invalid syntax" + getLocationDetails(jpe) + ".";
	}
}
