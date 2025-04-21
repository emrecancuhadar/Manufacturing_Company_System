package exception;
/**
 * This exception is thrown when the ManufacturingProcess object is not valid
 */
public class ManufacturingProcessNotValidException extends Exception {
  public ManufacturingProcessNotValidException() {
    super("ManufacturingProcess object is not valid.");
  }

  public ManufacturingProcessNotValidException(String msg) {
    super(msg);
  }
  
}
