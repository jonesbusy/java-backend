package cloud.delaye.backend.enums;

import cloud.delaye.backend.validation.IErrorLocationType;



/**
 * Defines the location types for the Api Errors.
 *
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
public enum EApiErrorLocationType implements IErrorLocationType {

	JSON("json"), 
	QUERY_PARAM("queryParam"),
	PATH_PARAM("pathParam"),
	FORM_PARAM("formParam"),
	HEADER("header");
	
	private final String locationType;

	private EApiErrorLocationType(String locationType) {
		this.locationType = locationType;
	}

	@Override
	public String getLocationType() {
		return locationType;
	}
	
	public static EApiErrorLocationType fromLocationType(String value) {
		for(EApiErrorLocationType type : EApiErrorLocationType.values()) {
			if(type.getLocationType().equals(value)) {
				return type;
			}
		}
		return null;
	}
		
}
