package cloud.delaye.backend.api;

import cloud.delaye.backend.api.mapper.ApiErrorsExceptionMapper;
import cloud.delaye.backend.api.mapper.CatchAllExceptionMapper;
import cloud.delaye.backend.api.mapper.EOFExceptionMapper;
import cloud.delaye.backend.api.mapper.JsonMappingExceptionMapper;
import cloud.delaye.backend.api.mapper.JsonParseExceptionMapper;
import cloud.delaye.backend.api.mapper.NotAcceptableExceptionMapper;
import cloud.delaye.backend.api.mapper.NotAllowedExceptionMapper;
import cloud.delaye.backend.api.mapper.NotFoundExceptionMapper;
import cloud.delaye.backend.api.mapper.NotSupportedExceptionMapper;
import cloud.delaye.backend.api.mapper.UnrecognizedPropertyExceptionMapper;
import cloud.delaye.backend.resource.EventResource;
import cloud.delaye.backend.enums.EApiErrorCodes;
import cloud.delaye.backend.json.JsonObjectMapper;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;
import java.util.HashSet;
import java.util.Set;
import jakarta.ws.rs.core.Application;

/**
 * Root JAX-RS application for the /api path.
 * 
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
public class ApiRestApplication extends Application {
	
	@Override
	public Set<Object> getSingletons() {
		final Set<Object> singletons = new HashSet<>(4);
		singletons.add(new ResteasyJackson2Provider());
		singletons.add(new JsonObjectMapper());
		return singletons;
	}

	@Override
	public Set<Class<?>> getClasses() {
		
		Set<Class<?>> classes = new HashSet<>();
		
		// Add resources
		classes.add(ApiErrorsExceptionMapper.class);
		
		// Application resources
		classes.add(EventResource.class);
		
		// ExceptionMappers
		for (MapperMappingDefinition mmd : retrieveMappersConfiguration()) {
			MapperMapping.addMapping(mmd);
			classes.add(mmd.getMapper());
		}
		
		return classes;
		
	}

	/**
	 * List of default mapper mapping
	 * @return Array of mapper mapping definition
	 */
	public static MapperMappingDefinition[] retrieveMappersConfiguration() {
		return new MapperMappingDefinition[]{
			new MapperMappingDefinition(CatchAllExceptionMapper.class, EApiErrorCodes.SERVER_UNEXPECTED),
			new MapperMappingDefinition(EOFExceptionMapper.class, EApiErrorCodes.REQUEST_END_OF_INPUT),
			new MapperMappingDefinition(JsonMappingExceptionMapper.class, EApiErrorCodes.REQUEST_BAD_JSON_VALUE_TYPE),
			new MapperMappingDefinition(NotAllowedExceptionMapper.class, EApiErrorCodes.REQUEST_RESOURCE_NOT_FOUND),
			new MapperMappingDefinition(JsonParseExceptionMapper.class, EApiErrorCodes.REQUEST_INVALID_JSON),
			new MapperMappingDefinition(NotAcceptableExceptionMapper.class, EApiErrorCodes.REQUEST_UNACCEPTABLE_MEDIA_TYPE),
			new MapperMappingDefinition(NotSupportedExceptionMapper.class, EApiErrorCodes.REQUEST_UNSUPPORTED_MEDIA_TYPE),
			new MapperMappingDefinition(NotFoundExceptionMapper.class, EApiErrorCodes.REQUEST_RESOURCE_NOT_FOUND),
			new MapperMappingDefinition(UnrecognizedPropertyExceptionMapper.class, EApiErrorCodes.REQUEST_UNKNOWN_JSON_PROPERTY),
		};
	}	

}
