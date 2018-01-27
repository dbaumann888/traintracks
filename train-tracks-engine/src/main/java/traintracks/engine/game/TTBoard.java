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
    private final int playerCarriageCount;
    private final Map<String, Station> stations;
    private final RouteMap routeMap;
    private final Deck<Car> carDeck;
    private final Deck<Ticket> ticketDeck;
    private final RouteScoring routeScores;

    private TTBoard(String name, int playerCarriageCount, Map<String, Station> stations, RouteMap routeMap,
                    Deck<Car> carDeck, Deck<Ticket> ticketDeck, RouteScoring routeScores)
    {
        this.id = UUID.randomUUID();
        this.name = name;
        this.playerCarriageCount = playerCarriageCount;
        this.stations = stations;
        this.routeMap = routeMap;
        this.carDeck = carDeck;
        this.ticketDeck = ticketDeck;
        this.routeScores = routeScores;
    }

    public static Board getTTBoard(TTSetup setup) {
        SetupReader reader = new SetupReader(setup.getPath());
        String jsonBoard = reader.read(TTBoard.class);
        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject) parser.parse(jsonBoard);
        String boardName = obj.get("name").getAsString();
        int carriageCount = obj.get("carriageCount").getAsInt();

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

        List<Ticket> tickets = new ArrayList<>();
        JsonArray jTickets = obj.getAsJsonArray("ticketDeck");
        for (JsonElement jTicket : jTickets) {
            JsonObject jTicketO = jTicket.getAsJsonObject();
            Station stationA = stations.get(jTicketO.get("stationA").getAsString());
            Station stationB = stations.get(jTicketO.get("stationB").getAsString());
            int score = jTicketO.get("score").getAsInt();
            Ticket ticket = new TTTicket(stationA, stationB, score);
            tickets.add(ticket);
        }
        Deck<Ticket> ticketDeck = new TTDeck<>(tickets);

        List<Car> cars = new ArrayList<>();
        JsonArray jCars = obj.getAsJsonArray("carDeck");
        for (JsonElement jCar : jCars) {
            JsonObject jCarO = jCar.getAsJsonObject();
            Flavor flavor = Flavor.valueOf(jCarO.get("flavor").getAsString().toUpperCase());
            int count = jCarO.get("count").getAsInt();
            for (int i = 0; i < count; ++i) {
                Car car = new TTCar(flavor);
                cars.add(car);
            }
        }
        Deck<Car> carDeck = new TTDeck<>(cars);

        int longestRouteScoring = obj.get("longestRouteScoring").getAsInt();

        Map<Integer, Integer> lengthToScoreMap = new HashMap<>();
        JsonArray jRouteScores = obj.getAsJsonArray("routeScoring");
        for (JsonElement jRouteScore : jRouteScores) {
            JsonObject jRouteScoreO = jRouteScore.getAsJsonObject();
            int length = jRouteScoreO.get("length").getAsInt();
            int score = jRouteScoreO.get("score").getAsInt();
            lengthToScoreMap.put(length, score);
        }
        RouteScoring routeScoring = new TTRouteScoring(longestRouteScoring, lengthToScoreMap);

        return new TTBoard(boardName, carriageCount, stations, routeMap, carDeck, ticketDeck, routeScoring);
    }

    public UUID getId() { return this.id; }
    public String getName() { return this.name; }
    public int getPlayerCarriageCount() { return this.playerCarriageCount; }
    public Map<String, Station> getStations() { return this.stations; }
    public RouteMap getRouteMap() { return this.routeMap; }
    public Deck<Car> getCarDeck() { return this.carDeck; }
    public Deck<Ticket> getTicketDeck() {
        return this.ticketDeck;
    }
    public RouteScoring getRouteScoring() { return this.routeScores; }
}
