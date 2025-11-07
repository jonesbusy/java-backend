package cloud.delaye.backend.validation;

/**
 * Interface for error codes
 */
public interface IErrorCode {
    
    /**
     * Get the error code
     * @return the error code
     */
    int getCode();
    
    /**
     * Get the default HTTP status code
     * @return the default HTTP status code
     */
    int getDefaultHttpStatusCode();
}
