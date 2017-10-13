package traintracks.api;

import java.util.Map;
import java.util.UUID;

public interface Board {
    UUID getId();
    String getName();
    Map<String, Station> getStations();
    RouteMap getRouteMap();
    BoardState getBoardState();
    Deck<Car> getCarDeck();
    Deck<Ticket> getTicketDeck();
    RouteScoring getRouteScoring();
}
