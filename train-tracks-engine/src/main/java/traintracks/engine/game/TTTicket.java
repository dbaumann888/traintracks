package traintracks.engine.game;

import traintracks.api.Station;
import traintracks.api.Ticket;

import java.util.UUID;

public class TTTicket implements Ticket {
    private UUID id;
    private Station stationA;
    private Station stationB;
    private int score;

    public TTTicket(Station stationA, Station stationB, int score) {
        this.id = UUID.randomUUID();
        this.stationA = stationA;
        this.stationB = stationB;
        this.score = score;
    }

    public UUID getId() { return this.id; }
    public Station getStationA() { return this.stationA; }
    public Station getStationB() { return this.stationB; }
    public int getScore() { return this.score; };
}
