package traintracks.api;

import java.util.List;

public interface PlayerState {
    PlayerScore getScore();
    int getCarriageCount();
    void setCarriageCount(int carriageCount);
    List<Ticket> getTickets();
    List<Car> getCars();
    boolean mustDrawSecondCar();
    void setMustDrawSecondCar(boolean mustDrawSecondCar);
    boolean hasPendingTickets();
    List<Ticket> getPendingTickets();
    int getPendingTicketsMustKeepCount();
    void addPendingTickets(List<Ticket> tickets, int mustKeepCount);
    void discardPendingTicket(Ticket ticket);
    void keepPendingTickets(List<CompletedRoute> allCompletedRoutes);
    void updateScore(List<CompletedRoute> allCompletedRoutes);
}
