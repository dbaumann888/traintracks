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
    private RouteType type;
    private int length;
    private Flavor flavor;
    private int numEngines;

    public TTRoute(Station stationA, Station stationB, RouteType type,
                   int length, Flavor flavor, int numEngines)
    {
        this.id = UUID.randomUUID();
        this.stationA = stationA;
        this.stationB = stationB;
        this.type = type;
        this.length = length;
        this.flavor = flavor;
        this.numEngines = numEngines;
    }

    public UUID getId() { return this.id; }
    public Station getStationA() { return this.stationA; }
    public Station getStationB() { return this.stationA; }
    public RouteType getType() { return this.type; };
    public int getLength() { return this.length; };
    public Flavor getFlavor() { return this.flavor; };
    public int getNumEngines() { return this.numEngines; };
    public String toString() { return getStationA().getName() + "-->" + getStationB().getName(); }
}
