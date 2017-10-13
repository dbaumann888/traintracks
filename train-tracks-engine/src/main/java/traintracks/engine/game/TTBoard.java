package traintracks.engine.game;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import traintracks.api.*;
import traintracks.engine.common.SetupReader;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TTBoard implements Board {
    private final UUID id;
    private final String name;
    private final Map<String, Station> stations;
    private final RouteMap routeMap;
    private final BoardState boardState;
    private final Deck<Car> cars;
    private final Deck<Ticket> tickets;
    private final RouteScoring routeScores;

    private TTBoard(String name, Map<String, Station> stations, RouteMap routeMap, BoardState gameState,
                   Deck<Car> cars, Deck<Ticket> tickets, RouteScoring routeScores)
    {
        this.id = UUID.randomUUID();
        this.name = name;
        this.stations = stations;
        this.routeMap = routeMap;
        this.boardState = gameState;
        this.cars = cars;
        this.tickets = tickets;
        this.routeScores = routeScores;
    }

    public static Board getTTBoard(TTSetup setup) {
        SetupReader reader = new SetupReader(setup.getPath());
        String jsonBoard = reader.read(TTBoard.class);
        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject) parser.parse(jsonBoard);
        String boardName = obj.get("name").getAsString();

        Map<String, Station> stations = new HashMap<>();
        JsonArray jStations = obj.getAsJsonArray("stations");
        for (JsonElement jStation : jStations) {
            JsonObject jStationO = jStation.getAsJsonObject();
            String stationName = jStationO.get("name").getAsString();
            JsonObject jCoordinates = jStationO.get("coordinates").getAsJsonObject();
            Point coordinates = new Point(jCoordinates.get("x").getAsInt(), jCoordinates.get("y").getAsInt());
            Station station = new TTStation(stationName, coordinates);
            stations.put(stationName, station);
        }

        List<Route> routes = new ArrayList<>();
        JsonArray jRoutes = obj.getAsJsonArray("routes");
        for (JsonElement jRoute : jRoutes) {
            JsonObject jRouteO = jRoute.getAsJsonObject();
            Station stationA = stations.get(jRouteO.get("stationA").getAsString());
            Station stationB = stations.get(jRouteO.get("stationB").getAsString());
            RouteType type = RouteType.valueOf(jRouteO.get("type").getAsString().toUpperCase());
            int length = jRouteO.get("length").getAsInt();
            Flavor flavor = Flavor.valueOf(jRouteO.get("flavor").getAsString().toUpperCase());
            int numEngines = jRouteO.get("numEngines").getAsInt();
            Route route = new TTRoute(stationA, stationB, type, length, flavor, numEngines);
            routes.add(route);
        }
        RouteMap routeMap = new TTRouteMap(routes);

        BoardState boardState = null;
        Deck<Car> cars = null;
        Deck<Ticket> tickets = null;
        RouteScoring routeScoring = null;
        return new TTBoard(boardName, stations, routeMap, boardState, cars, tickets, routeScoring);
    }

    public UUID getId() { return this.id; }
    public String getName() { return this.name; }
    public Map<String, Station> getStations() { return this.stations; }
    public RouteMap getRouteMap() { return this.routeMap; }
    public BoardState getBoardState() { return this.boardState; }
    public Deck<Car> getCarDeck() { return this.cars; }
    public Deck<Ticket> getTicketDeck() {
        return this.tickets;
    }
    public RouteScoring getRouteScoring() { return this.routeScores; }
}
