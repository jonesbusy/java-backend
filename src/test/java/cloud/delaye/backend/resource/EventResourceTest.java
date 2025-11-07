package cloud.delaye.backend.resource;

import cloud.delaye.backend.json.JsonUtility;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Basic test for JSON utility functionality used by EventResource
 * Tests the JSON parsing without requiring database connectivity
 * 
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
public class EventResourceTest {
	
	@Test
	public void testJsonParsingWithValidJson() {
		// Test valid JSON parsing
		String validJson = "{\"temperature\": \"25.5\", \"humidity\": \"60\"}";
		
		Map<String, String> result = JsonUtility.getObjectMap(validJson, String.class);
		
		assertNotNull(result);
		assertEquals("25.5", result.get("temperature"));
		assertEquals("60", result.get("humidity"));
	}
	
	@Test
	public void testJsonParsingWithEmptyJson() {
		// Test empty JSON
		String emptyJson = "{}";
		
		Map<String, String> result = JsonUtility.getObjectMap(emptyJson, String.class);
		
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}
	
	@Test
	public void testJsonParsingWithComplexJson() {
		// Test more complex JSON
		String complexJson = "{\"device\": \"sensor1\", \"location\": \"room1\", \"value\": \"100\"}";
		
		Map<String, String> result = JsonUtility.getObjectMap(complexJson, String.class);
		
		assertNotNull(result);
		assertEquals("sensor1", result.get("device"));
		assertEquals("room1", result.get("location"));
		assertEquals("100", result.get("value"));
		assertEquals(3, result.size());
	}
	
	@Test
	public void testJsonValidation() {
		// Test JSON validation
		assertTrue(JsonUtility.isValidJson("{\"key\": \"value\"}"));
		assertTrue(JsonUtility.isValidJson("{}"));
		assertFalse(JsonUtility.isValidJson("not valid json"));
		// Note: empty string might be considered valid by some JSON parsers
		// so we test with clearly invalid JSON instead
		assertFalse(JsonUtility.isValidJson("{invalid}"));
	}
}
