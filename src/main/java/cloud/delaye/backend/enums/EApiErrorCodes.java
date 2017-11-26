package cloud.delaye.backend.enums;

import io.probedock.jee.validation.IErrorCode;

/**
 * List of error code supported by the backend
 * 
 * @author Valentin Delaye (jonesbusy@gmail.com)
 */
 public enum EApiErrorCodes implements IErrorCode {
	
	/**
	 * Request was interrupted or body is empty.
	 */
	REQUEST_END_OF_INPUT(1000, EApiHttpStatusCodes.BAD_REQUEST),
	
	/**
	 * JSON request body is syntactically invalid.
	 */
	REQUEST_INVALID_JSON(1001, EApiHttpStatusCodes.BAD_REQUEST),
	
	/**
	 * JSON request body contains an unknown property.
	 */
	REQUEST_UNKNOWN_JSON_PROPERTY(1002, EApiHttpStatusCodes.BAD_REQUEST),
	
	/**
	 * JSON request body is of the wrong type or contains a property of the wrong type.
	 */
	REQUEST_BAD_JSON_VALUE_TYPE(1003, EApiHttpStatusCodes.BAD_REQUEST),
	
	/**
	 * The resource identified by the request URI does not exist.
	 */
	REQUEST_RESOURCE_NOT_FOUND(1004, EApiHttpStatusCodes.NOT_FOUND),
	
	/**
	 * The resource does not support the HTTP verb of the request.
	 */
	REQUEST_METHOD_NOT_ALLOWED(1005, EApiHttpStatusCodes.METHOD_NOT_ALLOWED),
	
	/**
	 * The resource cannot respond with the media type requested in the Accept header.
	 */
	REQUEST_UNACCEPTABLE_MEDIA_TYPE(1006, EApiHttpStatusCodes.NOT_ACCEPTABLE),
	
	/**
	 * The resource cannot consume the media type specified in the Content-Type header.
	 */
	REQUEST_UNSUPPORTED_MEDIA_TYPE(1007, EApiHttpStatusCodes.UNSUPPORTED_MEDIA_TYPE),
	
	/**
	 * An integer is smaller or greater than its documented range constraint.
	 */
	GENERIC_VALUE_OUT_OF_BOUNDS(2000, EApiHttpStatusCodes.UNPROCESSABLE_ENTITY),
	
	/**
	 * An unexpected server error occurred.
	 */
	SERVER_UNEXPECTED(4000, EApiHttpStatusCodes.INTERNAL_SERVER_ERROR);
	
	
	//<editor-fold defaultstate="collapsed" desc="Implementation">
	private final int code;
	private final EApiHttpStatusCodes defaultHttpStatusCode;
	
	private EApiErrorCodes(int code, EApiHttpStatusCodes defaultHttpStatusCode) {
		this.code = code;
		this.defaultHttpStatusCode = defaultHttpStatusCode;
	}
	
	@Override
	public int getDefaultHttpStatusCode() {
		return defaultHttpStatusCode.getCode();
	}
	
	@Override
	public int getCode() {
		return code;
	}
	//</editor-fold>
	
	public static EApiErrorCodes fromCode(int code) {
		for(EApiErrorCodes error : EApiErrorCodes.values()) {
			if(error.getCode() == code) {
				return error;
			}
		}
		return SERVER_UNEXPECTED;
	}
	
}
