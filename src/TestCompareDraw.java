import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;


public class TestCompareDraw {
	@Test
	public void test() throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		Card p1 =new Card(Card.Suit.CLUBS, 7);
		Card p2 =new Card(Card.Suit.CLUBS, 8);
		assertEquals(MainGameLoop.compareDraw(p1, p2, false), 2); //P2 should win this draw.

		p1 =new Card(Card.Suit.CLUBS, 14);
		p2 =new Card(Card.Suit.JOKER, 1);
		assertEquals(MainGameLoop.compareDraw(p1, p2, false), 2); //P2 should win this draw (jokers beat aces)

		p1 =new Card(Card.Suit.CLUBS, 7);
		p2 =new Card(Card.Suit.CLUBS, 7);
		assertEquals(MainGameLoop.compareDraw(p1, p2, false), 0); //Should go to war.

		p1 =new Card(Card.Suit.SPADES, 11);
		p2 =new Card(Card.Suit.DIAMONDS, 4);
		assertEquals(MainGameLoop.compareDraw(p1, p2, false), 1); //P1 should win this draw.
	}
}
