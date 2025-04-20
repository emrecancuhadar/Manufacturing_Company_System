public class ManufacturingProcessNotValid extends Exception {
  public ManufacturingProcessNotValid() {
    super("ManufacturingProcess object is not valid.");
  }

  public ManufacturingProcessNotValid(String msg) {
    super(msg);
  }
  
}
