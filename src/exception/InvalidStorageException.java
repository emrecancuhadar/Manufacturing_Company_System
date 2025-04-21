package exception;
/**
 * This exception is thrown when the Storage object is not valid
 */
public class InvalidStorageException extends Exception {
    public InvalidStorageException() {
        super();
    }
    public InvalidStorageException(String message) {
        super(message);
    }
}