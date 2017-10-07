package traintracks.api;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RouteFlavorTest {
    @Test
    public void testMatchesTwoRegular() {
        // Arrange

        // Act
        boolean matched = RouteFlavor.LIME.matches(RouteFlavor.LEMON);

        // Assert
        assertTrue("expected no match", !matched);
    }

    @Test
    public void testMatchesTwoRainbow() {
        // Arrange

        // Act
        boolean matched = RouteFlavor.RAINBOW.matches(RouteFlavor.RAINBOW);

        // Assert
        assertTrue("expected to match", matched);
    }

    @Test
    public void testMatchesRegularRainbow() {
        // Arrange

        // Act
        boolean matched = RouteFlavor.LIME.matches(RouteFlavor.RAINBOW);

        // Assert
        assertTrue("expected to match", matched);
    }

    @Test
    public void testMatchesTwoRainbowRegular() {
        // Arrange

        // Act
        boolean matched = RouteFlavor.RAINBOW.matches(RouteFlavor.LEMON);

        // Assert
        assertTrue("expected to match", matched);
    }

}
