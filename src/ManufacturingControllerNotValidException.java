/**
 * This exception is thrown when the ManufacturingController object is not valid
 */
public class ManufacturingControllerNotValidException extends Exception {
  public ManufacturingControllerNotValidException() {
    super("ManufacturingContoller object is not valid.");
  }

  public ManufacturingControllerNotValidException(String msg) {
    super(msg);
  }

}
