package traintracks.api;

import java.util.Map;
import java.util.UUID;

public interface Board {
    UUID getId();
    String getName();
    int getPlayerCarriageCount();
    Map<String, Station> getStations();
    RouteMap getRouteMap();
    Deck<Car> getCarDeck(); // TODO make a copy of static cards for here
    Deck<Ticket> getTicketDeck(); // TODO make a copy of static cards for here
    RouteScoring getRouteScoring();
}
