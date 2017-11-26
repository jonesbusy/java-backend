package cloud.delaye.backend.enums;

import javax.ws.rs.core.Response;

/**
 * Standard HTTP status
 *
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
public enum EApiHttpStatusCodes {

	/**
	 * Request successful.
	 */
	OK(200),
	
	/**
	 * The resource was successfully created.
	 */
	CREATED(201),
	
	/**
	 * The request was accepted but was not processed yet.
	 */
	ACCEPTED(202),
	
	/**
	 * The resource was successfully deleted.
	 */
	NO_CONTENT(204),
	
	/**
	 * Format or syntax error.
	 */
	BAD_REQUEST(400),
	
	/**
	 * No authorization was provided.
	 */
	UNAUTHORIZED(401),
	
	/**
	 * User is forbidden to access a resource.
	 */
	FORBIDDEN(403),
	
	/**
	 * The resource doesn't exist (or is not accessible due to scoping constraints).
	 */
	NOT_FOUND(404),
	
	/**
	 * The method specified in the Request-Line is not allowed for the resource identified by the Request-URI.
	 */
	METHOD_NOT_ALLOWED(405),
	
	/**
	 * The resource identified by the request is only capable of generating response entities which have content characteristics not acceptable according to the accept headers sent in the request.
	 */
	NOT_ACCEPTABLE(406),
	
	/**
	 * The server is refusing to service the request because the entity of the request is in a format not supported by the requested resource for the requested method.
	 */
	UNSUPPORTED_MEDIA_TYPE(415),
	
	/**
	 * Indicates a semantic/business error rather than a parsing error.
	 */
	UNPROCESSABLE_ENTITY(422),
	
	/**
	 * Unexpected errors.
	 */
	INTERNAL_SERVER_ERROR(500),
	
	/**
	 * A provider used by the server is currently in maintenance.
	 */
	GATEWAY_ERROR(502),
	
	/**
	 * Service unavailable (maintenance).
	 */
	SERVICE_UNAVAILABLE(503);

	private final int code;

	EApiHttpStatusCodes(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	/**
	 * Configure the JAX-RS response with the status code
	 * @param builder The builder
	 */
	public void configure(Response.ResponseBuilder builder) {
		builder.status(code);
	}
}
