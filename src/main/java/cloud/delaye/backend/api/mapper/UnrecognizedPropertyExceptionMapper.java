package cloud.delaye.backend.api.mapper;

import cloud.delaye.backend.api.DefaultLocationType;
import cloud.delaye.backend.api.DefaultLocationType.LocationTypes;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import io.probedock.jee.validation.ApiErrorResponse;
import javax.ws.rs.ext.Provider;

/**
 * Maps JSON unrecognized property exceptions to an HTTP 400 Bad Request API response.
 *
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
@Provider
@DefaultLocationType(LocationTypes.JSON_LOCATION_TYPE)
public class UnrecognizedPropertyExceptionMapper extends AbstractJsonExceptionMapper<UnrecognizedPropertyException> {

	@Override
	protected ApiErrorResponse toApiErrorResponse(UnrecognizedPropertyException upe) {
		return new ApiErrorResponse(
			buildMessage(upe), 
			getCode(),
			getLocationType(),
			upe.getPropertyName()
		);
	}

	/**
	 * Builds an error message indicating which property is unknown.
	 *
	 * @param upe the cause
	 * @return a message indicating the position (line and column) of the unknown property in the
	 * JSON document
	 */
	private String buildMessage(UnrecognizedPropertyException upe) {
		return "JSON parsing error due to unknown JSON object property "
				+ upe.getPropertyName()+ getLocationDetails(upe) + ".";
	}
}
