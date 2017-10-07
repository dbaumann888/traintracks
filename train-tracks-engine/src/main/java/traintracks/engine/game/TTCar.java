package traintracks.engine.game;

import traintracks.api.Car;
import traintracks.api.Flavor;

import java.util.UUID;

public class TTCar implements Car {
    private UUID id;
    private Flavor flavor;

    public TTCar(Flavor flavor) {
        this.id = UUID.randomUUID();
        this.flavor = flavor;
    }

    public UUID getId() { return this.id; }
    public Flavor getFlavor() { return this.flavor; }
}
