package traintracks.server.engine.game;

import traintracks.api.CompletedRoute;
import traintracks.api.Player;
import traintracks.api.Route;

public class TTCompletedRoute implements CompletedRoute {
    private Route route;
    private Player player;

    public TTCompletedRoute(Route route, Player player) {
        this.route = route;
        this.player = player;
    }

    public Route getRoute() { return this.route; }
    public Player getPlayer() { return this.player; }
}
