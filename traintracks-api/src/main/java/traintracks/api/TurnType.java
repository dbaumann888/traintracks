package traintracks.api;

public enum TurnType {
    BUILD_ROUTE,
    DRAW_TRAIN_CAR,
    DRAW_TICKETS,
    BUILD_STATION,
    DISCARD_PENDING_TICKETS;

    public String toString() {
        return name().toLowerCase();
    }
}
