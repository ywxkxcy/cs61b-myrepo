import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {

    CharacterComparator offByN = new OffByN(5);

    @Test
    public void testequalChars1() {
        assertTrue(offByN.equalChars('a', 'f'));
    }

    @Test
    public void testequalChars2() {
        assertFalse(offByN.equalChars('h', 'f'));
    }
}
