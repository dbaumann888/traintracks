package traintracks.engine.game;

import traintracks.api.Car;
import traintracks.api.PlayerState;
import traintracks.api.Ticket;

import java.util.ArrayList;
import java.util.List;

public class TTPlayerState implements PlayerState {
    private int score;
    private int carriageCount;
    private List<Car> cars;
    private List<Ticket> tickets;
    private List<Ticket> pendingTickets;

    public TTPlayerState() {
        this.score = 0;
        this.carriageCount = 0;
        this.cars = new ArrayList<>();
        this.tickets = new ArrayList<>();
        this.pendingTickets = new ArrayList<>();
    }

    public int getScore() { return this.score; }
    public int getCarriageCount() { return this.carriageCount; }
    public void setCarriageCount(int carriageCount) { this.carriageCount = carriageCount; }
    public List<Car> getCars() { return this.cars; }
    public List<Ticket> getTickets() { return this.tickets; }
    public List<Ticket> getPendingTickets() { return this.pendingTickets; }
    public void addPendingTicket(Ticket ticket) { this.pendingTickets.add(ticket); };
    public String toString() { return "" + this.getScore(); }
}
