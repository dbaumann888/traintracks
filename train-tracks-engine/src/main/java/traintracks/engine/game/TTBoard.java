package traintracks.engine.game;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import traintracks.api.*;
import traintracks.engine.common.SetupReader;

import java.util.List;
import java.util.UUID;

public class TTBoard implements Board {
    private final UUID id;
    private final String name;
    private final List<Station> stations;
    private final List<Route> routes;
    private final BoardState boardState;
    private final Deck<Car> cars;
    private final Deck<Ticket> tickets;
    private final RouteScoring routeScores;

    public TTBoard(String name, List<Station> stations, List<Route> routes, BoardState gameState,
                   Deck<Car> cars, Deck<Ticket> tickets, RouteScoring routeScores)
    {
        this.id = UUID.randomUUID();
        this.name = name;
        this.stations = stations;
        this.routes = routes;
        this.boardState = gameState;
        this.cars = cars;
        this.tickets = tickets;
        this.routeScores = routeScores;
    }

    public static TTBoard getTTBoard(TTSetup setup) {
        SetupReader reader = new SetupReader(setup.getPath());
        String jsonBoard = reader.read(TTBoard.class);
        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject) parser.parse(jsonBoard);
        String name = obj.get("name").getAsString();
        // TODO get the other parts out of the json obj
        //         JsonArray constraintsArray = null;
        List<Station> stations = null;
        List<Route> routes = null;
        BoardState boardState = null;
        Deck<Car> cars = null;
        Deck<Ticket> tickets = null;
        RouteScoring routeScoring = null;
        return new TTBoard(name, stations, routes, boardState, cars, tickets, routeScoring);
    }

    public UUID getId() { return this.id; }
    public String getName() { return this.name; }
    public List<Station> getStations() {
        return this.stations;
    }
    public List<Route> getRoutes() { return this.routes; }
    public BoardState getBoardState() { return this.boardState; }
    public Deck<Car> getCarDeck() { return this.cars; }
    public Deck<Ticket> getTicketDeck() {
        return this.tickets;
    }
    public RouteScoring getRouteScoring() { return this.routeScores; }
}
