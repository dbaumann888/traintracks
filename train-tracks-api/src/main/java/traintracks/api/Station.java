package traintracks.api;

import java.awt.*;
import java.util.UUID;

public interface Station {
    UUID getId();
    String getName();
    Point getCoordinates();
}
