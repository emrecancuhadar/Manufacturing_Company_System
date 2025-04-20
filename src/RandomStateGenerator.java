import java.util.Random;

public class RandomStateGenerator {
  
  public static ManufacturingState generateRandomState() {
    int randInt = new Random().nextInt(1, 4);

    ManufacturingState randState = null;

    switch (randInt) {
      case 1:
        randState = new CompletedState();
        break;
      case 2:
        randState = new FailedState(FailureReason.SYSTEM_ERROR);
        break;
      case 3:
        randState = new FailedState(FailureReason.DAMAGED_COMPONENT);
        break;
    }

    return randState;
  }
}
