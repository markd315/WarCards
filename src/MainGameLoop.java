import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;


public class MainGameLoop {
	private final static boolean playWithJokers = false; // I always played this game with Jokers being able to take down Aces but no other card. I love that variant so I've included it as a feature.
	static Stack<Card> p1Deck = new Stack<Card>(); // Open to package for unit testing.
	static Stack<Card> p2Deck = new Stack<Card>();
	private static LinkedList<Card> p1Graveyard = new LinkedList<Card>();// I selected LinkedLists for the Graveyard for one-step shuffling: I perform random access+deletions on the graveyard to shuffle a graveyard back into a stack.
	private static LinkedList<Card> p2Graveyard = new LinkedList<Card>();

	public static void main(String[] args) {
		// Fill decks.
		LinkedList<Card> temp = new LinkedList<Card>();
		for(int i = 0; i < 4; i++) {
			// for each suit
			for(int pow = 2; pow < 15; pow++) { // for each level 2-14
				if(i == 0) {
					temp.add(new Card(Card.Suit.HEARTS, pow));
				}
				if(i == 1) {
					temp.add(new Card(Card.Suit.CLUBS, pow));
				}
				if(i == 2) {
					temp.add(new Card(Card.Suit.SPADES, pow));
				}
				if(i == 3) {
					temp.add(new Card(Card.Suit.DIAMONDS, pow));
				}
			}
		}
		if(playWithJokers) {
			for(int i = 0; i < 2; i++) {
				temp.add(new Card(Card.Suit.JOKER, 1));
			}
		}
		Stack<Card> shuffled = shuffle(temp); // Perform the initial shuffling.
		for(int i = 0; i < 26; i++) {
			// Pop 26 off
			p1Deck.push(shuffled.pop());
		}
		for(int i = 0; i < 26; i++) {
			// Pop 26 off
			p2Deck.push(shuffled.pop());
		}
		if(playWithJokers) { // We need two additional cards.
			p1Deck.push(shuffled.pop());
			p2Deck.push(shuffled.pop());
		}
		// Setup done.
		System.out.println(p1Deck.size() + p1Graveyard.size());
		System.out.println(p2Deck.size() + p2Graveyard.size());
		gameLoop();
	}

	static void gameLoop() {
		while(p1Deck.size()+ p1Graveyard.size() > 0 && p2Deck.size() + p2Graveyard.size() > 0) { // Main Game Loop
			Card p1Draw = p1Deck.pop(); // Pop cards
			Card p2Draw = p2Deck.pop();
			System.out.println("p1 draws " + p1Draw.toString());
			System.out.println("p2 draws " + p2Draw.toString());
			int result = compareDraw(p1Draw, p2Draw, false);
			if(result == 0) {
				warBounty.push(p1Draw); // The two tied cards are added to the bounty.
				warBounty.push(p2Draw);
				unlessOfCourse(); // War were declared. https://www.youtube.com/watch?v=TS3kiRYcDAo
			}
			checkForShuffleOrEnd(); // Check for shuffle before every new round of draws.
		}
	}

	private static Stack<Card> warBounty = new Stack<Card>();

	static void unlessOfCourse() { // Triggers a possibly recursive war and handles result.
		if(warWereDeclared() == 1) {
			while(!warBounty.empty()) {
				Card c = warBounty.pop();
				p1Graveyard.push(c);
				System.out.println("p1 wins " + c.toString()); // Not the game, he wins the specified card.
			}
		}
		else {
			// p2 wins
			while(!warBounty.empty()) {
				Card c = warBounty.pop();
				p2Graveyard.push(c);
				System.out.println("p2 wins " + c.toString()); // Not the game, he wins the specified card.
			}
		}
	}

	static int warWereDeclared() { // Returns the winner of the war as an integer.
		checkForShuffleOrEnd(); // We have to check for shuffle before every draw.
		warBounty.push(p1Deck.pop()); // Amasses the bounty.
		warBounty.push(p2Deck.pop());
		System.out.println("Each player adds a card in bounty.");
		checkForShuffleOrEnd(); // We have to check for shuffle before every draw.
		Card p1Draw = p1Deck.pop(); // Pop cards
		Card p2Draw = p2Deck.pop();
		System.out.println("p1 draws " + p1Draw.toString());
		System.out.println("p2 draws " + p2Draw.toString());
		warBounty.push(p1Draw); // Takes from loser or grants to winner.
		warBounty.push(p2Draw);
		int result = compareDraw(p1Draw, p2Draw, true);
		if(result == 1) { // Compare to opponent's card
			System.out.println("p1 wins the war!");
			return 1;
		}
		if(result == 2) { // Compare to opponent's card
			System.out.println("p2 wins the war!");
			return 2;
		}
		else {
			System.out.println("It's another level of war!");
			return warWereDeclared(); // recursive call
		}
	}

	static int compareDraw(Card p1Draw, Card p2Draw, boolean isWar) {
		if(p1Draw.getValue() == 1 && p2Draw.getValue() == 14) {
			System.out.println("p1 takes down an Ace!");
			if(!isWar){
			p1Graveyard.add(p1Draw); // Takes from loser or grants to winner.
			p1Graveyard.add(p2Draw);
			}
			return 1;
		}
		else if(p2Draw.getValue() == 1 && p1Draw.getValue() == 14) {
			System.out.println("p2 takes down an Ace!");
			if(!isWar){
				p2Graveyard.add(p1Draw); // Takes from loser or grants to winner.
				p2Graveyard.add(p2Draw);
				}
			return 2;
		}
		else if(p1Draw.getValue() > p2Draw.getValue()) { // Compare to opponent's card, account for Ace Takedown in joker games.
			System.out.println("p1 wins the draw!");
			if(!isWar){
				p1Graveyard.add(p1Draw); // Takes from loser or grants to winner.
				p1Graveyard.add(p2Draw);
				}
			return 1;
		}
		else if(p2Draw.getValue() > p1Draw.getValue()) { // Compare to opponent's card
			System.out.println("p2 wins the draw!");
			if(!isWar){
				p2Graveyard.add(p1Draw); // Takes from loser or grants to winner.
				p2Graveyard.add(p2Draw);
				}
			return 2;
		}
		else
			return 0;
	}

	static void checkForShuffleOrEnd() {
		if(p1Deck.size() < 1) { // Checks for shuffles.
			System.out.println("Player 1 is drawn out.");
			if(p1Graveyard.size() < 1) { // Checks for game end.
				System.out.println("Player 2 wins!");
				System.exit(0);
			}
			else {
				p1Deck = shuffle(p1Graveyard);
				p1Graveyard = new LinkedList<Card>();
			}
		}
		if(p2Deck.size() < 1) { // Checks for shuffles.
			System.out.println("Player 2 is drawn out.");
			if(p2Graveyard.size() < 1) { // Checks for game end.
				System.out.println("Player 1 wins!");
				System.exit(0);
			}
			else {
				p2Deck = shuffle(p2Graveyard);
				p2Graveyard = new LinkedList<Card>();
			}
		}
	}

	// TODO playernames
	static Stack<Card> shuffle(LinkedList<Card> graveyard) {
		System.out.println("Pausing to shuffle " + graveyard.size() + " cards...");
		Stack<Card> shuffled = new Stack<Card>();
		Random RNG = new Random();
		while(graveyard.size() > 0) {
			// select a card
			int index = RNG.nextInt(graveyard.size());
			Card selected = graveyard.remove(index);
			shuffled.push(selected);
		}
		return shuffled;
	}
}
