package traintracks.api;

import java.util.List;

public interface GameState {
    Player getActivePlayer();
    List<CompletedRoute> getCompletedRoutes();
    List<CarCard> getCarCardDrawPile();
    List<CarCard> getOpenCards();
    List<Ticket> getTicketDrawPile();
}
