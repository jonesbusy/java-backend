package cloud.delaye.backend.validation;

/**
 * Exception for API errors
 */
public class ApiErrorsException extends RuntimeException {
    
    private final ApiErrorResponse errorResponse;
    
    public ApiErrorsException(ApiErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.errorResponse = errorResponse;
    }
    
    public ApiErrorsException(String message, ApiErrorResponse errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }
    
    public ApiErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
