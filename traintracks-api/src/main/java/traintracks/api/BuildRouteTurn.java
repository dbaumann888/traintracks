package traintracks.api;

import java.util.List;

public interface BuildRouteTurn extends Turn {
    Route getRoute();
    List<Car> getCars();
}
