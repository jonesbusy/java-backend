package cloud.delaye.backend.enums;

import cloud.delaye.backend.validation.IErrorLocationType;



/**
 * Defines the location types for the Api Errors.
 *
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
public enum EApiErrorLocationType implements IErrorLocationType {

	/** JSON location type. */
	JSON("json"), 
	/** Query parameter location type. */
	QUERY_PARAM("queryParam"),
	/** Path parameter location type. */
	PATH_PARAM("pathParam"),
	/** Form parameter location type. */
	FORM_PARAM("formParam"),
	/** Header location type. */
	HEADER("header");
	
	/**
	 * The location type string.
	 */
	private final String locationType;

	/**
	 * Constructor for location types.
	 * @param locationType the location type string
	 */
	private EApiErrorLocationType(String locationType) {
		this.locationType = locationType;
	}

	@Override
	public String getLocationType() {
		return locationType;
	}
	
	/**
	 * Find a location type by its string value.
	 * @param value the value to search for
	 * @return the location type or null if not found
	 */
	public static EApiErrorLocationType fromLocationType(String value) {
		for(EApiErrorLocationType type : EApiErrorLocationType.values()) {
			if(type.getLocationType().equals(value)) {
				return type;
			}
		}
		return null;
	}
		
}
