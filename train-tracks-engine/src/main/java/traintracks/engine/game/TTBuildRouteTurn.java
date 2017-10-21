package traintracks.engine.game;

import traintracks.api.BuildRouteTurn;
import traintracks.api.Flavor;
import traintracks.api.Player;
import traintracks.api.Route;
import traintracks.api.TurnType;

public class TTBuildRouteTurn extends TTTurn implements BuildRouteTurn {
    private Route route;
    private Flavor flavor;

    public TTBuildRouteTurn(Player player, Route route, Flavor flavor) {
        super(player, TurnType.BUILD_LINE);
        this.route = route;
        this.flavor = flavor;
    }

    public Route getRoute() { return this.route; }
    public Flavor getFlavor() { return this.flavor; }
}
