package traintracks.api;

import java.util.List;
import java.util.UUID;

public interface Ticket {
    UUID getId();
    Station getStationA();
    Station getStationB();
    int getScore();
    boolean isCompleted(List<Route> routes);
}
