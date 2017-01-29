import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.Test;


public class TestWar {
	@Test
	public void test() {
		System.out.println("Test case 1");
		MainGameLoop.p1Deck =  new Stack<Card>();
		MainGameLoop.p1Deck.push(new Card(Card.Suit.CLUBS, 8)); //Lower in deck
		MainGameLoop.p1Deck.push(new Card(Card.Suit.CLUBS, 7));//Lower in deck
		MainGameLoop.p1Deck.push(new Card(Card.Suit.SPADES, 14));//P1 War Card
		MainGameLoop.p1Deck.push(new Card(Card.Suit.HEARTS, 4));//Bounty


		MainGameLoop.p2Deck =  new Stack<Card>();
		MainGameLoop.p2Deck.push(new Card(Card.Suit.DIAMONDS, 2)); //Lower in deck
		MainGameLoop.p2Deck.push(new Card(Card.Suit.CLUBS, 3));//Lower in deck
		MainGameLoop.p2Deck.push(new Card(Card.Suit.SPADES, 6));//P2 War Card
		MainGameLoop.p2Deck.push(new Card(Card.Suit.HEARTS, 9));//Bounty

		//In this test a war has already been triggered by two tying cards and the next cards are the bounty.
		assertEquals(MainGameLoop.warWereDeclared(), 1); //Ace beats six.

		System.out.println("Test case 2: triple war");
		MainGameLoop.p1Deck.push(new Card(Card.Suit.CLUBS, 12)); //Lower in deck
		MainGameLoop.p1Deck.push(new Card(Card.Suit.CLUBS, 7));//Lower in deck
		MainGameLoop.p1Deck.push(new Card(Card.Suit.JOKER, 1));//Final P1 War Card
		MainGameLoop.p1Deck.push(new Card(Card.Suit.HEARTS, 4));//Bounty
		MainGameLoop.p1Deck.push(new Card(Card.Suit.CLUBS, 8)); //Second P1 war card
		MainGameLoop.p1Deck.push(new Card(Card.Suit.CLUBS, 7));//Bounty
		MainGameLoop.p1Deck.push(new Card(Card.Suit.DIAMONDS, 6));//P1 War Card
		MainGameLoop.p1Deck.push(new Card(Card.Suit.HEARTS, 4));//Bounty

		//End of P2 deck.
		MainGameLoop.p2Deck.push(new Card(Card.Suit.HEARTS, 14));//Final P2 War Card
		MainGameLoop.p2Deck.push(new Card(Card.Suit.SPADES, 9));//Bounty
		MainGameLoop.p2Deck.push(new Card(Card.Suit.SPADES, 8)); //Second P2 war card
		MainGameLoop.p2Deck.push(new Card(Card.Suit.CLUBS, 7));//Bounty
		MainGameLoop.p2Deck.push(new Card(Card.Suit.SPADES, 6));//P2 War Card
		MainGameLoop.p2Deck.push(new Card(Card.Suit.DIAMONDS, 7));//Bounty

		assertEquals(MainGameLoop.warWereDeclared(), 1); //Joker beats Ace.
	}
}
