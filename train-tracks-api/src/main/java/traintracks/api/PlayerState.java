package traintracks.api;

import java.util.List;

public interface PlayerState {
    int getScore();
    int getCarriageCount();
    void setCarriageCount(int carriageCount);
    List<Car> getCars();
    List<Ticket> getTickets();
    List<Ticket> getPendingTickets();
    void addPendingTicket(Ticket ticket);
}
