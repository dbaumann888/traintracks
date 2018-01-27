package traintracks.api;

import java.util.UUID;

public interface Route {
    UUID getId();
    Station getStationA();
    Station getStationB();
    RouteType getType();
    int getLength();
    Flavor getFlavor();
    int getNumEngines();
    boolean connectsTo(Route nextRoute);
}
