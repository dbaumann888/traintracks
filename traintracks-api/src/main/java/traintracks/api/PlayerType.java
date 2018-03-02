package traintracks.api;

public enum PlayerType {
    HUMAN,
    AI,
    CYBORG;

    public String toString() {
        return name().toLowerCase();
    }
}
