package traintracks.engine.game;

import traintracks.api.Car;
import traintracks.api.CompletedRoute;
import traintracks.api.Player;
import traintracks.api.PlayerScore;
import traintracks.api.PlayerState;
import traintracks.api.Route;
import traintracks.api.RouteScoring;
import traintracks.api.Ticket;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class TTPlayerState implements PlayerState {
    private PlayerScore score;
    private int carriageCount;
    private List<Ticket> tickets;
    private List<Car> cars;
    private boolean mustDrawSecondCar;
    private List<Ticket> pendingTickets;
    private int pendingTicketsMustKeepCount;

    public TTPlayerState(Player player, RouteScoring routeScoring) {
        this.score = new TTPlayerScore(player, routeScoring);
        this.carriageCount = 0;
        this.cars = new ArrayList<>();
        this.tickets = new ArrayList<>();
        this.pendingTickets = new ArrayList<>();
        this.pendingTicketsMustKeepCount = 0;
        this.mustDrawSecondCar = false;
    }

    public PlayerScore getScore() { return this.score; }
    public int getCarriageCount() { return this.carriageCount; }
    public void setCarriageCount(int carriageCount) { this.carriageCount = carriageCount; }
    public List<Car> getCars() { return this.cars; }
    public boolean mustDrawSecondCar() { return this.mustDrawSecondCar; }
    public void setMustDrawSecondCar(boolean mustDrawSecondCar) { this.mustDrawSecondCar = mustDrawSecondCar; }
    public List<Ticket> getTickets() { return this.tickets; }
    public boolean hasPendingTickets() { return this.pendingTickets.size() > 0; }
    public List<Ticket> getPendingTickets() { return this.pendingTickets; }
    public int getPendingTicketsMustKeepCount() { return this.pendingTicketsMustKeepCount; }
    public void addPendingTickets(List<Ticket> tickets, int mustKeepCount) {
        this.pendingTickets.addAll(tickets);
        this.pendingTicketsMustKeepCount = mustKeepCount;
    }
    public void discardPendingTicket(Ticket ticket) {
        if (this.pendingTicketsMustKeepCount >= this.pendingTickets.size()) {
            throw new InvalidParameterException("Can't discard any more tickets");
        }
        this.pendingTickets.remove(ticket);
    }
    public void keepPendingTickets(List<CompletedRoute> allCompletedRoutes) {
        this.tickets.addAll(this.pendingTickets);
        this.pendingTickets.clear();
        updateScore(allCompletedRoutes);
    }

    public void updateScore(List<CompletedRoute> allCompletedRoutes) {
        this.score.updateScore(allCompletedRoutes, this.tickets, null);
    }

    public String toString() { return "" + this.getScore(); }
}
