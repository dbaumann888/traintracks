package traintracks.engine.game;

import traintracks.api.Station;

import java.awt.*;
import java.util.UUID;

/**
 * Station (aka city, example: Seattle)
 */
public class TTStation implements Station {

    private final UUID id;
    private final String name;
    private final Point coordinates;

    public TTStation(String name, Point coordinates) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.coordinates = coordinates;
    }

    public UUID getId() { return this.getId(); }
    public String getName() { return this.getName(); }
    public Point getCoordinates() { return this.coordinates; }
}
