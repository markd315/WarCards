import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.junit.Test;


public class TestShuffle { //Specification: When we shuffle a Deck and then sort the original and the shuffled versions, we should get back the same deck, minus perhaps some suit irregularities.
	@Test
	public void test() { //IMPORTANT: This assertion implementation may fail for cases where there are multiple suits of a value to be sorted, and this behavior is irrelevant to proper program functioning.
		LinkedList<Card> toShuffle = new LinkedList<Card>();
		LinkedList<Card> original;
		toShuffle.add(new Card(Card.Suit.CLUBS, 5));
		toShuffle.add(new Card(Card.Suit.HEARTS, 8));
		toShuffle.add(new Card(Card.Suit.DIAMONDS, 2));
		original = (LinkedList<Card>) toShuffle.clone();
		Stack<Card> result = MainGameLoop.shuffle(toShuffle);
		//IMPORTANT: This assertion implementation may fail for cases where there are multiple suits of a value to be sorted, and this behavior is irrelevant to proper program functioning.
		assertEquals(sort(original), sort(result)); //Should still get back the same elements when we sort them.


		toShuffle=new LinkedList<Card>();
		toShuffle.add(new Card(Card.Suit.CLUBS, 9));
		toShuffle.add(new Card(Card.Suit.HEARTS, 14));
		toShuffle.add(new Card(Card.Suit.SPADES, 4));
		original = (LinkedList<Card>) toShuffle.clone();
		result = MainGameLoop.shuffle(toShuffle);
		assertEquals(sort(original), sort(result)); //Should still get back the same elements when we sort them.
	}

	private List<Card> sort(Stack<Card> sortMe) {
		List<Card> l =  new ArrayList<Card>(sortMe);
		Collections.sort(l);
		return l;
	}

	private List<Card> sort(LinkedList<Card> sortMe) {
		List<Card> l =  new ArrayList<Card>(sortMe);
		Collections.sort(l);
		return l;
	}
}
