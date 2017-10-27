package traintracks.engine.game;

import traintracks.api.Car;
import traintracks.api.PlayerState;
import traintracks.api.Ticket;

import java.util.ArrayList;
import java.util.List;

public class TTPlayerState implements PlayerState {
    private int score;
    private int carriageCount;
    private List<Ticket> tickets;
    private List<Car> cars;
    private boolean mustDrawSecondCar;
    private List<Ticket> pendingTickets;

    public TTPlayerState() {
        this.score = 0;
        this.carriageCount = 0;
        this.cars = new ArrayList<>();
        this.tickets = new ArrayList<>();
        this.pendingTickets = new ArrayList<>();
        this.mustDrawSecondCar = false;
    }

    public int getScore() { return this.score; }
    public int getCarriageCount() { return this.carriageCount; }
    public void setCarriageCount(int carriageCount) { this.carriageCount = carriageCount; }
    public List<Car> getCars() { return this.cars; }
    public boolean mustDrawSecondCar() { return this.mustDrawSecondCar; }
    public void setMustDrawSecondCar(boolean mustDrawSecondCar) { this.mustDrawSecondCar = mustDrawSecondCar; }
    public List<Ticket> getTickets() { return this.tickets; }
    public boolean hasPendingTickets() { return this.pendingTickets.size() > 0; }
    public List<Ticket> getPendingTickets() { return this.pendingTickets; }
    public void addPendingTicket(Ticket ticket) { this.pendingTickets.add(ticket); }
    public void discardPendingTicket(Ticket ticket) { this.pendingTickets.remove(ticket); }
    public void keepPendingTickets() {
        this.tickets.addAll(this.pendingTickets);
        this.pendingTickets.clear();
    }
    public String toString() { return "" + this.getScore(); }
}
