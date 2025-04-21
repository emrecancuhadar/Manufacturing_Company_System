package exception;
/**
 * This exception is thrown when the ComponentNode object is not valid
 */
public class InvalidComponentNodeException extends Exception {
    public InvalidComponentNodeException() {
        super();
    }
    public InvalidComponentNodeException(String message) {
        super(message);
    }
}
