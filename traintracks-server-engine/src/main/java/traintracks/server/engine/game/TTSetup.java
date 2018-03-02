package traintracks.server.engine.game;

public enum TTSetup {
    NORTH_AMERICA("setups/north_america_setup.json"),
    EUROPE("setups/europe_setup.json");

    String path;

    TTSetup(String path) {
        this.path = path;
    }

    public String getPath() { return path; }
}
