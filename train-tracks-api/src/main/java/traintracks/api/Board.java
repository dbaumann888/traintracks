package traintracks.api;

import java.util.List;
import java.util.UUID;

public interface Board {
    UUID getId();
    String getName();
    List<Station> getStations();
    List<Route> getRoutes();
    GameState getGameState();
    CarDeck getCarDeck();
    TicketDeck getTicketDeck();
    RouteScoring getRouteScoring();
}
