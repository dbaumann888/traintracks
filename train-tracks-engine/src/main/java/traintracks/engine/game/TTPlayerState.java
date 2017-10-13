package traintracks.engine.game;

import traintracks.api.Car;
import traintracks.api.PlayerState;
import traintracks.api.Ticket;

import java.util.ArrayList;
import java.util.List;

public class TTPlayerState implements PlayerState {
    private int score;
    private List<Car> cars;
    private List<Ticket> tickets;
    private List<Ticket> pendingTickets;

    public TTPlayerState() {
        this.score = 0;
        this.cars = new ArrayList<>();
        this.tickets = new ArrayList<>();
        this.pendingTickets = new ArrayList<>();
    }

    public int getScore() { return 0; }
    public List<Car> getCars() { return this.cars; }
    public List<Ticket> getTickets() { return this.tickets; }
    public List<Ticket> getPendingTickets() { return this.pendingTickets; }
}