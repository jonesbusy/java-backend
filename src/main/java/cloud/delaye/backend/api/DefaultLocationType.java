package cloud.delaye.backend.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allow defining a default location type directly configured on the
 * ExceptionMapper class
 *
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DefaultLocationType {

	String value();

	/**
	 * Class constants
	 */
	public static class LocationTypes {

		public static final String JSON_LOCATION_TYPE = "json";

		public static final String HEADER_LOCATION_TYPE = "header";
	}
}
