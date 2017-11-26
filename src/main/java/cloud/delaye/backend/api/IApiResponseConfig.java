package cloud.delaye.backend.api;

import javax.ws.rs.core.Response;

/**
 * Configuration object for an {@link BackendResponse} builder. Implementations will be passed a
 * builder's internal JAX-RS response builder to configure.
 *
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
public interface IApiResponseConfig {

	/**
	 * Configures a Jersey response.
	 *
	 * @param builder the JAX-RS response builder
	 */
	void configure(Response.ResponseBuilder builder);
	
}
