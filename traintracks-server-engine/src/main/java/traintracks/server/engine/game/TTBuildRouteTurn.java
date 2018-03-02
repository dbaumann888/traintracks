package traintracks.server.engine.game;

import traintracks.api.BuildRouteTurn;
import traintracks.api.Car;
import traintracks.api.Player;
import traintracks.api.Route;
import traintracks.api.TurnType;

import java.util.List;

public class TTBuildRouteTurn extends TTTurn implements BuildRouteTurn {
    private Route route;
    private List<Car> cars;

    public TTBuildRouteTurn(Player player, Route route, List<Car> cars) {
        super(player, TurnType.BUILD_ROUTE);
        this.route = route;
        this.cars = cars;
    }

    public Route getRoute() { return this.route; }
    public List<Car> getCars() { return this.cars; }
}
