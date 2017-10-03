package traintracks.api;

import java.util.UUID;

public interface Turn {
    UUID getId();
    Player getPlayer();
    TurnType getType();
}
