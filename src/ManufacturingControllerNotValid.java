public class ManufacturingControllerNotValid extends Exception {
  public ManufacturingControllerNotValid() {
    super("ManufacturingContoller object is not valid.");
  }

  public ManufacturingControllerNotValid(String msg) {
    super(msg);
  }

}
