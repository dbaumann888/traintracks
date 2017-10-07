package traintracks.api;

import java.util.List;
import java.util.UUID;

public interface Board {
    UUID getId();
    String getName();
    List<Station> getStations();
    List<Route> getRoutes();
    BoardState getBoardState();
    Deck<Car> getCarDeck();
    Deck<Ticket> getTicketDeck();
    RouteScoring getRouteScoring();
}
