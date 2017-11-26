package cloud.delaye.backend.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * Configuration of Jackson serialization and deserialization.
 *
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JsonObjectMapper implements ContextResolver<ObjectMapper> {

	private final ObjectMapper jacksonObjectMapper;

	public JsonObjectMapper() {

		jacksonObjectMapper = new ObjectMapper();

		// do not serialize null values
		jacksonObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		
		// Default date format with miliseconds
		String format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
		TimeZone timezone = TimeZone.getTimeZone("UTC");

		DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(timezone);
		jacksonObjectMapper.setDateFormat(dateFormat);

		/*
		 * Do not wrap objects in a single-property JSON object with the root
		 * name. The result is:
		 *
		 * { name: 'my app' }
		 *
		 * Instead of:
		 *
		 * { application: { name: 'my app' } }
		 */
		jacksonObjectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		
		/**
		 * As far as we go for creation of a TO per view representation, we do not want to accept
		 * unknown properties anymore. Not accepting unknown values with the "each to per view"
		 * means that you cannot write something like:
		 *
		 * { "key": null }
		 *
		 * if the TO has not the attribute key.
		 */
		jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		
	}

	@Override
	public ObjectMapper getContext(Class<?> clazz) {		
		return jacksonObjectMapper;
	}
	
}
