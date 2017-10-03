package traintracks.engine;

import traintracks.api.Station;

import java.util.UUID;

public class TTStation implements Station {

    private final UUID id;
    private final String name;

    public TTStation(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public UUID getId() { return this.getId(); }
    public String getName() { return this.getName(); }
}
