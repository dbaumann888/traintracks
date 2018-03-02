package traintracks.api;

import java.awt.*;
import java.util.UUID;

public interface Player {
    UUID getId();
    String getName();
    Color getColor();
    PlayerType getType();
    PlayerState getState();
}
