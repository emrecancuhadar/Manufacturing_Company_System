package enums;

public enum FailureReason {
    SYSTEM_ERROR("System error"),
    DAMAGED_COMPONENT("Damaged component"),
    STOCK_SHORTAGE("Stock shortage");

    private final String label;

    FailureReason(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}