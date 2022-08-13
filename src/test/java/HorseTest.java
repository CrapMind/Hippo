import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
public class HorseTest {

    @Mock
    private Horse horse;

    @Test
    void firstParameterIsNull() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1));
        assertEquals("Name cannot be null.", e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "", "\n", "\t"})
    void firstParameterIsBlank(String param) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(param, 1));
        assertEquals("Name cannot be blank.", e.getMessage());
    }

    @Test
    void secondParameterIsNegative() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse("Horse", -1));
        assertEquals("Speed cannot be negative.", e.getMessage());
    }

    @Test
    void thirdParameterIsNegative() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse("Horse", 2, -1));
        assertEquals("Distance cannot be negative.", e.getMessage());
    }

    @Test
    void getNameReturnRealName() {
        horse = new Horse("Horse", 1);
        assertEquals("Horse", horse.getName());
    }
    @Test
    void getSpeedReturnRealSpeed() {
        horse = new Horse("Horse", 117);
        assertEquals(117, horse.getSpeed());
    }
    @Test
    void getDistanceReturnRealDistance() {
        horse = new Horse("Horse", 1);
        assertEquals(0, horse.getDistance());
        horse = new Horse("Horse", 1, 1024);
        assertEquals(1024, horse.getDistance());
    }
    @Test
    void moveMethodInvokeAnother() {
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            horse = new Horse("Horse", 117, 1024);
            horse.move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {112, 1896, 132, 2, 296.45})
    void moveGetRandomDoubleDistance(double random) {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            horse = new Horse("someHorse", 117, 1024);

            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);
            horse.move();
            assertEquals(1024 + 117 * random, horse.getDistance());
        }
    }


}
