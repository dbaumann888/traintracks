package traintracks.api;

import java.util.List;

public interface PlayerState {
    int getScore();
    int getCarriageCount();
    void setCarriageCount(int carriageCount);
    List<Ticket> getTickets();
    List<Car> getCars();
    boolean mustDrawSecondCar();
    void setMustDrawSecondCar(boolean mustDrawSecondCar);
    boolean hasPendingTickets();
    List<Ticket> getPendingTickets();
    void addPendingTicket(Ticket ticket);
    void discardPendingTicket(Ticket ticket);
    void keepPendingTickets();
}
