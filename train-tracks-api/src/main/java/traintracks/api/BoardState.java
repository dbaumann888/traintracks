package traintracks.api;

import java.util.List;

public interface BoardState {
    Player getActivePlayer();
    List<CompletedRoute> getCompletedRoutes();
    List<Car> getCarCardDrawPile();
    List<Car> getOpenCards();
    List<Ticket> getTicketDrawPile();
}
