package traintracks.engine.game;

import traintracks.api.Route;
import traintracks.api.Station;
import traintracks.api.Ticket;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public int getScore() { return this.score; }

    public boolean isCompleted(List<Route> routes) {
        Set<Station> visitedStations = new HashSet<>();
        Set<Station> recentStations = new HashSet<>();
        recentStations.add(this.stationA);

        while (!recentStations.isEmpty()) {
            Set<Station> nextStations = new HashSet<>();
            for (Station station : recentStations) {
                visitedStations.add(station);
                nextStations.addAll(routes.stream().filter(route -> (route.getStationA() == station) && !visitedStations.contains(route.getStationB())).map(route -> route.getStationB()).collect(Collectors.toSet()));
                nextStations.addAll(routes.stream().filter(route -> (route.getStationB() == station) && !visitedStations.contains(route.getStationA())).map(route -> route.getStationA()).collect(Collectors.toSet()));
                if (nextStations.contains(this.stationB)) {
                    return true;
                }
            }
            recentStations = nextStations;
        }

        return false;
    }

    public String toString() { return getStationA().getName() + "-" + getScore() + "->" + getStationB().getName(); }
}
