package traintracks.api;

public enum RouteType {
    STANDARD,
    FERRY,
    TUNNEL;

    public String toString() {
        return name().toLowerCase();
    }
}
