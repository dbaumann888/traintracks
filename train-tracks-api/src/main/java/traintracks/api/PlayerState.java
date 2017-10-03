package traintracks.api;

import java.util.List;

public interface PlayerState {
    int getScore();
    List<CarCard> getCarCards();
    List<Ticket> getTickets();
    List<Ticket> getPendingTickets();
}
