package traintracks.api;

import java.util.UUID;

public interface Ticket {
    UUID getId();
    Station getStationA();
    Station getStationB();
}
