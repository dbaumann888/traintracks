package traintracks.api;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FlavorTest {
    @Test
    public void testMatchesTwoRegular() {
        // Arrange

        // Act
        boolean matched = Flavor.LIME.matches(Flavor.LEMON);

        // Assert
        assertTrue("expected no match", !matched);
    }

    @Test
    public void testMatchesTwoRainbow() {
        // Arrange

        // Act
        boolean matched = Flavor.RAINBOW.matches(Flavor.RAINBOW);

        // Assert
        assertTrue("expected to match", matched);
    }

    @Test
    public void testMatchesRegularRainbow() {
        // Arrange

        // Act
        boolean matched = Flavor.LIME.matches(Flavor.RAINBOW);

        // Assert
        assertTrue("expected to match", matched);
    }

    @Test
    public void testMatchesTwoRainbowRegular() {
        // Arrange

        // Act
        boolean matched = Flavor.RAINBOW.matches(Flavor.LEMON);

        // Assert
        assertTrue("expected to match", matched);
    }

}
