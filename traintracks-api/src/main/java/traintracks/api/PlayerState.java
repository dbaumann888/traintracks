package traintracks.api;

import java.util.List;
import java.util.UUID;

public interface PlayerState {
    UUID getId();
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
    void keepPendingTickets(Player player, List<CompletedRoute> allCompletedRoutes);
    void updateScore(Player player, List<CompletedRoute> allCompletedRoutes);
}
