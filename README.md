# Manufacturing System

This project implements a manufacturing system for furniture production. It uses the Composite and State design patterns to model components and the manufacturing process.

## Project Structure

- `src/main/java/com/manufacturing`: Source code
  - `model/`: Component and Product classes
  - `state/`: State pattern implementation
  - `process/`: Manufacturing process
  - `controller/`: Application controller
  - `io/`: CSV file reader
  - `report/`: Report generation

## Design Patterns

1. **Composite Pattern**: Components and Products
2. **State Pattern**: Manufacturing process states

## Running the Application

There are two ways to run the application:

### Using the compile_and_run.sh script

```bash
./compile_and_run.sh
```

This will compile the code with debug flags and run the application with the default data files.

### Manually compiling and running

```bash
# Compile
mkdir -p bin
javac -g -d bin -sourcepath src src/main/java/com/manufacturing/Main.java

# Run
java -cp bin main.java.com.manufacturing.Main data/components.csv data/products.csv
```

You can override the default file paths by providing different paths as command line arguments.

## Debugging

To debug the application, you can:

1. **Use an IDE**: Import the project into your preferred IDE (Eclipse, IntelliJ IDEA, VS Code) and use the built-in debugger.

2. **Use jdb (Java Debugger)**:
   ```bash
   # Compile with debug info
   javac -g -d bin -sourcepath src src/main/java/com/manufacturing/Main.java
   
   # Run jdb
   jdb -classpath bin main.java.com.manufacturing.Main data/components.csv data/products.csv
   ```

3. **Add print statements**: Modify the code to add more print statements for debugging.

## Key Places to Check When Debugging

1. **Stock management**: 
   - `src/main/java/com/manufacturing/model/Component.java` - check `reduceStock` and `increaseStock` methods
   - `src/main/java/com/manufacturing/model/Product.java` - check `checkStock` and `consumeComponents` methods

2. **State transitions**:
   - `src/main/java/com/manufacturing/state/WaitingForStockState.java` - initial state checking stock
   - `src/main/java/com/manufacturing/state/InManufacturingState.java` - handles manufacturing outcomes
   - `src/main/java/com/manufacturing/state/CompletedState.java` - successful completion
   - `src/main/java/com/manufacturing/state/FailedState.java` - failed manufacturing

3. **Manufacturing process**:
   - `src/main/java/com/manufacturing/process/ManufacturingProcess.java` - coordinates the state transitions

4. **Manufacturing controller**:
   - `src/main/java/com/manufacturing/controller/ManufacturingController.java` - orchestrates the overall process