package cloud.delaye.backend.json;

import javax.ejb.ApplicationException;

/**
 *
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
@ApplicationException(rollback = true)
public class JsonException extends RuntimeException {

	public JsonException() {
	}

	public JsonException(String message) {
		super(message);
	}

	public JsonException(String message, Throwable cause) {
		super(message, cause);
	}

	public JsonException(Throwable cause) {
		super(cause);
	}

}
	

