package traintracks.engine;

import traintracks.api.*;

import java.util.List;
import java.util.UUID;

public class TTBoard implements Board {
    private final UUID id;
    private final String name;
    private final List<Station> stations;
    private final List<Route> routes;
    private final GameState gameState;
    private final CarDeck cars;
    private final TicketDeck tickets;
    private final RouteScoring routeScores;

    public TTBoard(String name, List<Station> stations, List<Route> routes, GameState gameState,
                   CarDeck cars, TicketDeck tickets, RouteScoring routeScores)
    {
        this.id = UUID.randomUUID();
        this.name = name;
        this.stations = stations;
        this.routes = routes;
        this.gameState = gameState;
        this.cars = cars;
        this.tickets = tickets;
        this.routeScores = routeScores;
    }

    public UUID getId() { return this.id; }
    public String getName() { return this.name; }
    public List<Station> getStations() {
        return this.stations;
    }
    public List<Route> getRoutes() { return this.routes; }
    public GameState getGameState() { return this.gameState; }
    public CarDeck getCarDeck() { return this.cars; }
    public TicketDeck getTicketDeck() {
        return this.tickets;
    }
    public RouteScoring getRouteScoring() { return this.routeScores; }
}
