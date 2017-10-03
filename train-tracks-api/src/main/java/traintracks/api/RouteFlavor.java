package traintracks.api;

public enum RouteFlavor {
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

    RouteFlavor(boolean isWild) { this.isWild = isWild; }

    public boolean isWild() { return isWild; }

    public boolean matches(RouteFlavor flavorB) {
        return isWild() || flavorB.isWild() || equals(flavorB);
    }

    public String toString() {
        return name().toLowerCase();
    }
}
