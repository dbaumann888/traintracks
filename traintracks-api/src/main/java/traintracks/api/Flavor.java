package traintracks.api;

public enum Flavor {
    LIME('m', false),
    LEMON('n', false),
    ORANGE('o', false),
    CHERRY('c', false),
    STRAWBERRY('s', false),
    BLUEBERRY('b', false),
    VANILLA('v', false),
    LICORICE('i', false),
    RAINBOW('r', true);

    private final boolean isWild;
    private final char symbol;

    Flavor(char symbol, boolean isWild) {
        this.symbol = symbol;
        this.isWild = isWild;
    }

    public char getSymbol() { return this.symbol; }
    public boolean isWild() { return this.isWild; }

    public boolean matches(Flavor flavorB) {
        return isWild() || flavorB.isWild() || equals(flavorB);
    }

    public String toString() {
        return name().toLowerCase();
    }
}
