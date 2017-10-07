package traintracks.api;

import java.util.UUID;

public interface Route {
    UUID getId();
    Station getStationA();
    Station getStationB();
    int getLength();
    RouteType getType();
    Flavor getFlavor();
    int getNumEngines();
}
