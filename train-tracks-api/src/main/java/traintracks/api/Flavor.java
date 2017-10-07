package traintracks.api;

public enum Flavor {
    LIME(false),
    LEMON(false),
    ORANGE(false),
    CHERRY(false),
    STRAWBERRY(false),
    BLUEBERRY(false),
    VANILLA(false),
    LICORICE(false),
    RAINBOW(true);

    private final boolean isWild;

    Flavor(boolean isWild) { this.isWild = isWild; }

    public boolean isWild() { return isWild; }

    public boolean matches(Flavor flavorB) {
        return isWild() || flavorB.isWild() || equals(flavorB);
    }

    public String toString() {
        return name().toLowerCase();
    }
}
