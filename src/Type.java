public enum Type {
    RAW_MATERIAL("raw material"),
    HARDWARE("hardware"),
    PAINT("paint");

    private final String label;

    Type(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Type fromLabel(String label) {
        for (Type type : Type.values()) {
            if (type.label.equalsIgnoreCase(label)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with label " + label);
    }    

    @Override
    public String toString() {
        return label;
    }
}
