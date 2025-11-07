package cloud.delaye.backend.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * API error response wrapper
 */
public class ApiErrorResponse {
    
    private int httpStatusCode;
    private int errorCode;
    private String message;
    private List<ApiError> errors = new ArrayList<>();
    
    public ApiErrorResponse() {
    }
    
    public ApiErrorResponse(String message, IErrorCode errorCode) {
        this.message = message;
        this.errorCode = errorCode.getCode();
        this.httpStatusCode = errorCode.getDefaultHttpStatusCode();
    }
    
    public ApiErrorResponse(String message, IErrorCode errorCode, IErrorLocationType locationType, String location) {
        this.message = message;
        this.errorCode = errorCode.getCode();
        this.httpStatusCode = errorCode.getDefaultHttpStatusCode();
        ApiError error = new ApiError();
        error.setLocation(location);
        error.setLocationType(locationType.getLocationType());
        error.setCode(errorCode.getCode());
        error.setMessage(message);
        this.errors.add(error);
    }
    
    public ApiErrorResponse(int httpStatusCode, int errorCode, String message) {
        this.httpStatusCode = httpStatusCode;
        this.errorCode = errorCode;
        this.message = message;
    }
    
    public int getHttpStatusCode() {
        return httpStatusCode;
    }
    
    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }
    
    public int getErrorCode() {
        return errorCode;
    }
    
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public List<ApiError> getErrors() {
        return errors;
    }
    
    public void setErrors(List<ApiError> errors) {
        this.errors = errors;
    }
    
    public void addError(ApiError error) {
        this.errors.add(error);
    }
    
    public static class ApiError {
        private String location;
        private String locationType;
        private String message;
        private int code;
        
        public ApiError() {
        }
        
        public ApiError(String location, String locationType, String message, int code) {
            this.location = location;
            this.locationType = locationType;
            this.message = message;
            this.code = code;
        }
        
        public String getLocation() {
            return location;
        }
        
        public void setLocation(String location) {
            this.location = location;
        }
        
        public String getLocationType() {
            return locationType;
        }
        
        public void setLocationType(String locationType) {
            this.locationType = locationType;
        }
        
        public String getMessage() {
            return message;
        }
        
        public void setMessage(String message) {
            this.message = message;
        }
        
        public int getCode() {
            return code;
        }
        
        public void setCode(int code) {
            this.code = code;
        }
    }
}
