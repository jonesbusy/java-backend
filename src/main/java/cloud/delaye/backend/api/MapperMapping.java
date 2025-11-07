package cloud.delaye.backend.api;

import cloud.delaye.backend.api.mapper.AbstractApiExceptionMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Keep the mappings between the ExceptionMapper class and the ErrorCode
 * 
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
public class MapperMapping {
	
	/**
	 * Private constructor to prevent instantiation.
	 */
	private MapperMapping() {
	}
	
	/**
	 * Mapping configuration
	 */
	private static final Map<Class<? extends AbstractApiExceptionMapper>, MapperMappingDefinition> MAPPING = new HashMap<>();
	
	/**
	 * Add a new mapping definition to the configuration
	 * 
	 * @param mappingDefinition The mapping definition to add
	 */
	public static void addMapping(MapperMappingDefinition mappingDefinition) {
		MAPPING.put(mappingDefinition.getMapper(), mappingDefinition);
	}
	
	/**
	 * Retrieve a specific mapping for an ExceptionMapper
	 * 
	 * @param exceptionClass The class of the ExceptionMapper
	 * @return The mapper found, otherwise null is returned
	 */
	public static MapperMappingDefinition getMapping(AbstractApiExceptionMapper exceptionClass) {
		return MAPPING.get(exceptionClass.getClass());
	}
	
	/**
	 * Retrieve the list of mappings.
	 * @return Retrieve the list of mappings
	 */
	public static Set<Class<? extends AbstractApiExceptionMapper>> getExceptionMapperClasses() {
		return MAPPING.keySet();
	}
}
