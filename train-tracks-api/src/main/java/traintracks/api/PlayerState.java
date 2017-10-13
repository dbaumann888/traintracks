package traintracks.api;

import java.util.List;

public interface PlayerState {
    int getScore();
    List<Car> getCars();
    List<Ticket> getTickets();
    List<Ticket> getPendingTickets();
}
