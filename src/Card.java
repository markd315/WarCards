public class Card implements Comparable<Card> {
	public enum Suit {
		SPADES, HEARTS, CLUBS, DIAMONDS, JOKER
	};

	private Suit suit;
	private int value;

	public Card(Suit suit, int value) { // Creates a random Card.
		this.suit = suit;
		this.value = value;
		if (suit==Suit.JOKER && value !=1)
			throw new IllegalArgumentException("Jokers must carry value 1");
		if (suit!=Suit.JOKER && value ==1)
			throw new IllegalArgumentException("Cards with value==1 must carry Joker suit.");
		if(value>14 || value <1)
			throw new IllegalArgumentException("Unsuitable value, needs range of 1-14.");
	}

	public int getValue() {
		return this.value;
	}

	public Suit getSuit() {
		return this.suit;
	}

	public boolean equals(Card comp) {
		if(comp.value == this.value)
			return true;
		return false;
	}
	@Override
	public int compareTo(Card o) {
		if(this.equals(o))
			return 0;
		int result = this.value - o.value;
		if(result == 14-1 || result == 1-14) {
			result = -1*result;
		}
		return result;
	}

	@Override
	public String toString() {
		String name = "the ";
		if(value == 1)
			return name += "Joker"; // Need name overrides for face cards.
		if(value == 11) {
			name += "Jack"; // Need name overrides for face cards.
		}
		if(value == 12) {
			name += "Queen";
		}
		if(value == 13) {
			name += "King";
		}
		if(value == 14) {
			name += "Ace";
		}
		if(value < 11) {
			name += value; // Numerical value complete.
		}
		return name += " of " + suit;
	}
}
