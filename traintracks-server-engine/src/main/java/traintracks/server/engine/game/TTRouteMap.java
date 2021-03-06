package traintracks.server.engine.game;

import traintracks.api.Route;
import traintracks.api.RouteMap;

import java.util.List;

public class TTRouteMap implements RouteMap {
    private List<Route> routes;

    public TTRouteMap(List<Route> routes) {
        this.routes = routes;
    }

    public List<Route> getRoutes() { return this.routes; }
    public String toString() { return "route map with " + getRoutes().size() + " routes"; }
}
