package traintracks.engine.game;

import traintracks.api.Route;
import traintracks.api.Flavor;
import traintracks.api.RouteType;
import traintracks.api.Station;

import java.util.UUID;

public class TTRoute implements Route {
    private UUID id;
    private Station stationA;
    private Station stationB;
    private int length;
    private RouteType type;
    private Flavor flavor;
    private int numEngines;

    public TTRoute(Station stationA, Station stationB, int length,
                   RouteType type, Flavor flavor, int numEngines)
    {
        this.id = UUID.randomUUID();
        this.stationA = stationA;
        this.stationB = stationB;
        this.length = length;
        this.type = type;
        this.flavor = flavor;
        this.numEngines = numEngines;
    }

    public UUID getId() { return this.id; }
    public Station getStationA() { return this.stationA; }
    public Station getStationB() { return this.stationA; }
    public int getLength() { return this.length; };
    public RouteType getType() { return this.type; };
    public Flavor getFlavor() { return this.flavor; };
    public int getNumEngines() { return this.numEngines; };
}
