package coins.state;

import org.junit.jupiter.api.Test;
import java.util.BitSet;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class CoinsTest {

    private final Coins state1 = new Coins(7, 3); // the original initial state

    private final Coins state2; // the goal state
    {
        BitSet bs = new BitSet(7);
        bs.set(0, 7);
        state2 = new Coins(7, 3, bs);
    }

    @Test
    void isGoalTestTrue() {
        assertTrue(state2.isGoal());
    }

    @Test
    void isGoalTestFalse() {
        assertFalse(state1.isGoal());
    }

    @Test
    void canFlipTestLengthGreaterNAndCardinalityEqualsM() {
        Coins c = state1.clone();
        BitSet bs = new BitSet(c.getN() + 1);
        bs.set(0, c.getM() - 1);
        bs.set(c.getN());
        assertFalse(c.canFlip(bs));
    }

    @Test
    void canFlipTestLengthGreaterNAndCardinalityNotEqualsM() {
        Coins c = state1.clone();
        BitSet bs = new BitSet(c.getN() + 1);
        bs.set(c.getN());
        assertFalse(c.canFlip(bs));
    }

    @Test
    void canFlipTestLengthLessOrEqualWithNAndCardinalityNotEqualsM() {
        Coins c = state1.clone();
        BitSet bs = new BitSet(c.getN());
        assertFalse(c.canFlip(bs));
    }

    @Test
    void canFlipTestLengthLessOrEqualWithNAndCardinalityEqualsM() {
        Coins c = state1.clone();
        BitSet bs = new BitSet(c.getN());
        bs.set(0, c.getM());
        assertTrue(c.canFlip(bs));
    }

    @Test
    void flipTest() {
        Coins c1 = state1.clone();
        BitSet bs1 = new BitSet(c1.getN());
        bs1.set(0, c1.getN());
        c1.flip(bs1);
        assertEquals(bs1, c1.getCoins());
    }

    @Test
    void generateFlipsTestMIsZero() {
        assertThrows(IllegalArgumentException.class, () -> Coins.generateFlips(7, 0));
    }

    @Test
    void generateFlipsTestNIsZero() {
        assertThrows(IllegalArgumentException.class, () -> Coins.generateFlips(0, 3));
    }

    @Test
    void generateFlipsTestNIsLessThanM() {
        assertThrows(IllegalArgumentException.class, () -> Coins.generateFlips(3, 7));
    }

    @Test
    void getFlipsTestTrue() {
        assertTrue(state1.getFlips().size() != 0);
    }

    @Test
    void getFlipsTestFalse() {
        assertFalse(state1.getFlips().size() == 0);
    }

    @Test
    void equalsTestDifferentN() {
        BitSet bs1 = new BitSet(3);
        bs1.set(0, 3);
        Coins c1 = new Coins(3, 1, bs1);
        Coins c2 = new Coins(4, 1, bs1);
        assertFalse(c1.equals(c2));
    }

    @Test
    void equalsTestDifferentM() {
        BitSet bs1 = new BitSet(3);
        bs1.set(0, 3);
        Coins c1 = new Coins(3, 1, bs1);
        Coins c2 = new Coins(3, 2, bs1);
        assertFalse(c1.equals(c2));
    }

    @Test
    void equalsTestDifferentNAndM() {
        BitSet bs1 = new BitSet(3);
        bs1.set(0, 3);
        Coins c1 = new Coins(3, 1, bs1);
        Coins c2 = new Coins(4, 2, bs1);
        assertFalse(c1.equals(c2));
    }

    @Test
    void equalsTestDifferentCoins() {
        BitSet bs1 = new BitSet(3);
        bs1.set(0, 3);
        Coins c1 = new Coins(3, 1, bs1);
        Coins c2 = new Coins(3, 1, new BitSet(3));
        assertFalse(c1.equals(c2));
    }

    @Test
    void equalsTestDifferentNAndCoins() {
        BitSet bs1 = new BitSet(3);
        bs1.set(0, 3);
        Coins c1 = new Coins(3, 1, bs1);
        Coins c2 = new Coins(4, 1, new BitSet(3));
        assertFalse(c1.equals(c2));
    }

    @Test
    void equalsTestDifferentMAndCoins() {
        BitSet bs1 = new BitSet(3);
        bs1.set(0, 3);
        Coins c1 = new Coins(3, 1, bs1);
        Coins c2 = new Coins(3, 2, new BitSet(3));
        assertFalse(c1.equals(c2));
    }

    @Test
    void equalsTestDifferentNAndNAndCoins() {
        BitSet bs1 = new BitSet(3);
        bs1.set(0, 3);
        Coins c1 = new Coins(3, 1, bs1);
        Coins c2 = new Coins(4, 2, new BitSet(3));
        assertFalse(c1.equals(c2));
    }

    @Test
    void equalsTestNotInstanceOfCoin() {
        BitSet bs1 = new BitSet(3);
        bs1.set(0, 3);
        Coins c1 = new Coins(3, 1, bs1);
        assertFalse(c1.equals(new Object()));
    }

    @Test
    void equalsTestSelf() {
        BitSet bs1 = new BitSet(3);
        bs1.set(0, 3);
        Coins c1 = new Coins(3, 1, bs1);
        assertTrue(c1.equals(c1));
    }

    @Test
    void equalsTestSameNAndMAndCoins() {
        BitSet bs1 = new BitSet(3);
        bs1.set(0, 3);
        Coins c1 = new Coins(3, 1, bs1);
        Coins c2 = new Coins(3, 1, bs1);
        assertTrue(c1.equals(c2));
    }

    @Test
    void equalsTestDifferent() {
        BitSet bs1 = new BitSet(3);
        bs1.set(0, 3);
        Coins c1 = new Coins(3, 1, bs1);
        Coins c5 = new Coins(3, 1, new BitSet(3));
        assertFalse(c1.equals(c5));
    }

    @Test
    void hashCodeTestTrue() {
        assertTrue(state1.hashCode() == Objects.hash(state1.getN(), state1.getM(), state1.getCoins()));
    }

    @Test
    void hashCodeTestFalse() {
        assertFalse(state1.hashCode() == state2.hashCode());
    }

    @Test
    void toStringTestNoneFlipped() {
        assertEquals(state1.toString(), "O|O|O|O|O|O|O");
    }

    @Test
    void toStringTestAllFlipped() {
        assertEquals(state2.toString(), "1|1|1|1|1|1|1");
    }

    @Test
    void checkArgumentsTest() {
        BitSet bs1 = new BitSet(2);
        bs1.set(1);
        assertThrows(IllegalArgumentException.class, () -> new Coins(1, 1, bs1));
    }
}