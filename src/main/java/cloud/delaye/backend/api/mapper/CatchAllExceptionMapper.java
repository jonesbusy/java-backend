package cloud.delaye.backend.api.mapper;

import cloud.delaye.backend.validation.ApiErrorResponse;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Maps any exception not caught by a more specific mapper to an HTTP 500 Internal Server Error API
 * response. Also makes an ERROR log of the exception so it can be decided whether to add a more
 * specific mapper.
 *
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
@Provider
public class CatchAllExceptionMapper extends AbstractApiExceptionMapper<Exception> {

	/**
	 * Logger for this class.
	 */
	private Logger log = LoggerFactory.getLogger(CatchAllExceptionMapper.class);
	
	/**
	 * Default constructor.
	 */
	public CatchAllExceptionMapper() {
	}
	
	@Override
	public ApiErrorResponse toApiErrorResponse(Exception exception) {
		log.error("Something went wrong...", exception);
		return new ApiErrorResponse("The request could not be completed due to a server error.", getCode());
	}
}
