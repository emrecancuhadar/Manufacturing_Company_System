public enum QuantityUnit 
{
    SQUARE_METERS("m²"),
    PIECES("pieces"),
    BOXES("boxes");

    private final String displayName;

    QuantityUnit(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
