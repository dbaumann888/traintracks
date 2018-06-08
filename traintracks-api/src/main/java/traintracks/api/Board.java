package traintracks.api;

import java.util.Map;
import java.util.UUID;

public interface Board {
    UUID getId();
    String getName();
    BoardState getBoardState();
    int getPlayerCarriageCount();
    Map<String, Station> getStations();
    RouteMap getRouteMap();
    Deck<Car> getOriginalCarDeck();
    Deck<Ticket> getOriginalTicketDeck();
    RouteScoring getRouteScoring();
}
