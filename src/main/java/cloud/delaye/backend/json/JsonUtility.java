package cloud.delaye.backend.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.ws.rs.ext.ContextResolver;

/**
 * Utility class to manual deserialize JSON.
 * 
 * Uses the {@link JsonObjectMapper} to manage the object mapping
 * 
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
public class JsonUtility {
	
	/**
	 * The default JSON object mapper
	 */
	private static final JsonObjectMapper DEFAULT_JSON_OBJECT_MAPPER = new JsonObjectMapper();
	

	/**
	 * Return if the given content is valid JSON
	 * @param content The content
	 * @return True or false
	 */
	public static boolean isValidJson(String content) {
		try {
			DEFAULT_JSON_OBJECT_MAPPER.getContext(null).readTree(content);
			return true;
		}
		catch(IOException e) {
			return false;
		}
	}
	
	/**
	 * Get a list of transfer object based on JSON content
	 * @param <T> The type of the object on the list
	 * @param content The JSON content
	 * @param clazz The class of this object
	 * @return List of transfer object
	 * @throws JsonException In case of serialization error
	 */
	public static <T> List<T> getTransferObjectList(String content, Class<T> clazz) throws JsonException {
		return getTransferObjectList(content, clazz, DEFAULT_JSON_OBJECT_MAPPER);
	}
	
	/**
	 * Get a list of transfer object based on JSON content
	 * @param <T> The type of the object on the list
	 * @param content The JSON content
	 * @param objectMapper The object mapper
	 * @param type The class type
	 * @return List of transfer object
	 * @throws JsonException In case of serialization error
	 */
	public static <T> List<T> getTransferObjectList(String content, Class<T> type, ContextResolver<ObjectMapper> objectMapper) throws JsonException {
		ObjectMapper mapper = objectMapper.getContext(type);
		try {
			return mapper.readValue(content, mapper.getTypeFactory().constructCollectionType(List.class, type));
		}
		catch(IOException e) {
			throw new JsonException(e);
		}
	}
	
	
	/**
	 * Get a transfer object based on JSON content
	 * @param <T> The type of the object
	 * @param content The JSON content
	 * @param clazz The class of the generic param
	 * @return The transfer object
	 * @throws JsonException In case of serialization error
	 */
	public static <T> T getTransferObject(String content, Class<T> clazz) throws JsonException {
		return getTransferObject(content, clazz, DEFAULT_JSON_OBJECT_MAPPER);
	}
	
	/**
	 * Get a generic map based on JSON content
	 * @param <T> The type of the map key
	 * @param <S> The type of the map value
	 * @param content The JSON content
	 * @param keyClass The class of the map key
	 * @param valueClass The class of the map value
	 * @return The map
	 * @throws JsonException In case of serialization error
	 */
	public static <T, S> Map<T, S> getMap(String content, Class<T> keyClass, Class<S> valueClass) throws JsonException {
		ObjectMapper mapper = DEFAULT_JSON_OBJECT_MAPPER.getContext(null);
		try {
			return mapper.readValue(content, mapper.getTypeFactory().constructMapType(Map.class, keyClass, valueClass));
		}
		catch(IOException e) {
			throw new JsonException(e);
		}
	}
	
	/**
	 * Get a generic map based on JSON content
	 * @param <T> The type of the map key
	 * @param <S> The type of the map value
	 * @param content The JSON content
	 * @param keyClass The class of the map key
	 * @return The map
	 * @throws JsonException In case of serialization error
	 */
	public static <T, S> Map<T, S> getObjectMap(String content, Class<T> keyClass) throws JsonException {
		ObjectMapper mapper = DEFAULT_JSON_OBJECT_MAPPER.getContext(null);
		try {
			return mapper.readValue(content, mapper.getTypeFactory().constructMapType(Map.class, keyClass, Object.class));
		}
		catch(IOException e) {
			throw new JsonException(e);
		}
	}
	
	/**
	 * Get a properties map based on JSON content
	 * @param content The JSON content
	 * @return The map
	 * @throws JsonException In case of serialization error
	 */
	public static Map<String, String> getProperties(String content) throws JsonException {
		return getMap(content, String.class, String.class);
	}
	
	/**
	 * Get a transfer object based on JSON content
	 * @param <T> The type of the object
	 * @param content The JSON content
	 * @param clazz The class of the generic param
	 * @param objectMapper The object mapper
	 * @return The transfer object
	 * @throws JsonException In case of serialization error
	 */
	public static <T> T getTransferObject(String content, Class<T> clazz, ContextResolver<ObjectMapper> objectMapper) throws JsonException {
		ObjectMapper mapper = objectMapper.getContext(null);
		try {
			return mapper.readValue(content, clazz);
		}
		catch(IOException e) {
			throw new JsonException(e);
		}
	}
	
	/**
	 * Get JSON string for a transfer object
	 * @param <T> The type of the object
	 * @param object The Object
	 * @return The JSON string
	 * @throws JsonException In case of serialization error
	 */
	public static <T> String getJson(T object) throws JsonException {
		return getJson(object, DEFAULT_JSON_OBJECT_MAPPER);
	}
	
	/**
	 * Get JSON string for a transfer object
	 * @param <T> The type of the object
	 * @param object The Object
	 * @param objectMapper The object mapper
	 * @return The JSON string
	 * @throws JsonException In case of serialization error
	 */
	public static <T> String getJson(T object, ContextResolver<ObjectMapper> objectMapper) throws JsonException {
		ObjectMapper mapper = objectMapper.getContext(null);
		try {
			return mapper.writeValueAsString(object);
		}
		catch(IOException e) {
			throw new JsonException(e);
		}
	}
	
	/**
	 * Get JSON string for a list of transfer object
	 * @param <T> The type of the object
	 * @param object The list of object to serialize
	 * @return The list of object
	 * @throws JsonException In case of serialization error
	 */
	public static <T> String getJsonFromList(List<T> object) throws JsonException {
		return getJsonFromList(object, DEFAULT_JSON_OBJECT_MAPPER);
	}
	
	/**
	 * Get JSON string for a list of transfer object
	 * @param <T> The type of the object
	 * @param object The list of object to serialize
	 * @param objectMapper The object mapper
	 * @return The list of object
	 * @throws JsonException In case of serialization error
	 */
	public static <T> String getJsonFromList(List<T> object, ContextResolver<ObjectMapper> objectMapper) throws JsonException {
		ObjectMapper mapper = objectMapper.getContext(null);
		try {
			return mapper.writeValueAsString(object);
		}
		catch(IOException e) {
			throw new JsonException(e);
		}
	}
	
	
}
