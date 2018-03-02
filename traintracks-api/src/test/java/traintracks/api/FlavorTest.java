package traintracks.api;

import junit.framework.TestCase;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class FlavorTest extends TestCase {
    @Test
    public void testMatchesDifferentRegularColors() {
        // Arrange

        // Act
        boolean matched = Flavor.LIME.matches(Flavor.LEMON);

        // Assert
        assertFalse("expected no match", matched);
    }

    @Test
    public void testMatchesSameRegularColors() {
        // Arrange

        // Act
        boolean matched = Flavor.BLUEBERRY.matches(Flavor.BLUEBERRY);

        // Assert
        assertTrue("expected a match", matched);
    }

    @Test
    public void testMatchesRainbowRainbow() {
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
    public void testMatchesRainbowRegular() {
        // Arrange

        // Act
        boolean matched = Flavor.RAINBOW.matches(Flavor.LEMON);

        // Assert
        assertTrue("expected to match", matched);
    }

}
