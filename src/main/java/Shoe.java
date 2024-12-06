import java.util.Collections;

// TODO: Implement the Shoe class in this file
public class Shoe extends CardCollection{
    public Shoe(int decks){
        super();
        if (decks != 6 && decks != 8){
            throw new CardException("Invalid number of decks");
        }
        else {
            int i, j, k;
            Card.Rank[] rankList = {Card.Rank.ACE, Card.Rank.TWO, Card.Rank.THREE, Card.Rank.FOUR, Card.Rank.FIVE,
                                    Card.Rank.SIX, Card.Rank.SEVEN, Card.Rank.EIGHT, Card.Rank.NINE, Card.Rank.TEN, Card.Rank.JACK,
                                    Card.Rank.QUEEN, Card.Rank.KING, };
            Card.Suit[] suitList = {Card.Suit.CLUBS, Card.Suit.DIAMONDS, Card.Suit.HEARTS, Card.Suit.SPADES};
            for (k = 0; k < decks; k++) {
                for (i = 0; i < suitList.length; i++) {
                    for (j = 0; j < rankList.length; j++) {
                        cards.add(new BaccaratCard(rankList[j], suitList[i]));
                    }
                }
            }
        }

    }
    public void shuffle(){
        Collections.shuffle(cards);
    }
    public Card deal() {
        if (cards.isEmpty()) {
            throw new CardException("Shoe is empty");
        } else {
            Card temp = cards.getFirst();
            cards.removeFirst();
            return temp;
        }
    }
}