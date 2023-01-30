package skypro.socks.constants;

public enum Operation {
    LESSTHAN("lessThan"), MORETHAN("moreThan"), EQUALS("equals");
    private final String operation;

    Operation(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return this.operation;
    }
}
