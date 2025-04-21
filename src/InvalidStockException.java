/**
 * This exception is thrown when the Stock object is not valid
 */
public class InvalidStockException extends Exception {
    public InvalidStockException(String message) {
        super(message);
    }
}