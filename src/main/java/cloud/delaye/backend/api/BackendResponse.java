package cloud.delaye.backend.api;

import cloud.delaye.backend.enums.EApiHttpStatusCodes;
import cloud.delaye.backend.validation.ApiErrorResponse;
import java.util.Map;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * API backend response builder to create a JAX-RS response.
 * 
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
public class BackendResponse {

	/**
	 * The JAX-RS response building
	 */
	private final Response.ResponseBuilder jerseyResponseBuilder;

	/**
	 * Constructs an HTTP 200 OK response with no body. Call {@code configure} with an
	 * {@link EApiHttpStatusCodes} to change the status code.
	 */
	public BackendResponse() {
		jerseyResponseBuilder = Response.ok();
	}
	
	public BackendResponse(Response.ResponseBuilder builder) {
		jerseyResponseBuilder = builder;
	}

	/**
	 * Constructs an HTTP 200 OK response with the specified entity as the body. Call
	 * {@code configure} with an {@link EApiHttpStatusCodes} to change the status code.
	 *
	 * @param entity the entity to serialize
	 */
	public BackendResponse(Object entity) {
		jerseyResponseBuilder = Response.ok(entity);
	}

	/**
	 * Constructs a response with the specified response error as the body and
	 * call the configure method on the error with this.
	 *
	 * @param error the error to serialize
	 */
	public BackendResponse(ApiErrorResponse error) {
		jerseyResponseBuilder = Response.serverError();
		this.configure(error);
	}

	public BackendResponse entity(Object entity) {
		jerseyResponseBuilder.entity(entity);
		return this;
	}

	public BackendResponse header(String name, Object value) {
		jerseyResponseBuilder.header(name, value);
		return this;
	}
	
	public BackendResponse headers(Map<String, String> headers) {
		headers.entrySet().forEach(header -> jerseyResponseBuilder.header(header.getKey(), header.getValue()));
		return this;
	}

	/**
	 * Configures the response with a config object.
	 *
	 * @param config the configuration implementation
	 * @return the updated builder
	 */
	public BackendResponse configure(IApiResponseConfig config) {
		config.configure(jerseyResponseBuilder);
		return this;
	}

	public final BackendResponse configure(ApiErrorResponse apiErrorResponse) {
		jerseyResponseBuilder.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).status(apiErrorResponse.getHttpStatusCode());
		jerseyResponseBuilder.entity(apiErrorResponse);
		return this;
	}
	/**
	 * Builds the JAX-RS response
	 *
	 * @return a JAX-RS response
	 */
	public Response build() {
		return jerseyResponseBuilder.build();
	}
}
