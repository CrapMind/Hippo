import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HippodromeTest {


    @Mock
    Hippodrome hippodrome;
    @Mock
    List<Horse> horses;

    @Test
    void firstParameterIsNull() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", e.getMessage());
    }
    @Test
    void firstParameterIsEmptyList() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
        assertEquals("Horses cannot be empty.", e.getMessage());
    }
    @Test
    void getHorsesListContainsSameList() {
        var horses = new ArrayList<Horse>();
        for (int i = 0; i < 30; i++) {
            horses.add(mock(Horse.class));
        }

        hippodrome = new Hippodrome(horses);

        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    void invokeMoveOnAllHorses() {
        horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
                horses.add(mock(Horse.class));
            }

        new Hippodrome(horses).move();

        horses.forEach(verify(Horse::move));
    }
    @Test
    void getMostBigDistance() {
        var horses = new ArrayList<Horse>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("Horse " + i, 100 + i, 1000 + i));
        }
        hippodrome = new Hippodrome(horses);

        assertSame(hippodrome.getWinner(), hippodrome.getHorses().stream().max(Comparator.comparing(Horse::getDistance)).get());
    }
}
