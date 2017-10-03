package traintracks.api;

public enum TurnType {
    BUILD_LINE,
    DRAW_TRAIN_CARD,
    DRAW_TICKETS;

    public String toString() {
        return name().toLowerCase();
    }
}
