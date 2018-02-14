package GameLogic;

import GameLogic.BlindsHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {
    private static final List<Card> deck = new ArrayList<Card>();
    private int cardUsed = 0;

    static {
        for (Card.Suit suit : Card.Suit.values()){
            for (Card.Rank rank : Card.Rank.values()){
                deck.add(new Card(rank,suit));
            }
        }
    }


    public int cardsLeft(){
        return deck.size() - cardUsed;
    }

    public void InitCardInDeck(){
        for (int i = 0; i < deck.size();i++) {
            Card currCard = deck.get(i);
            if (currCard.isUsed()) {
                currCard.setUsed(false);
            }
        }
    }

    public Card[] drawFromDeck(int numOfCards){
        Random generator = new Random();
        Card[] temp = new Card[numOfCards];
        for(int i=0; i < numOfCards;i++) {
            int index = 0;
            do {
                index = generator.nextInt(52);
            } while (deck.get(index).isUsed() == true);
            temp[i] = deck.get(index);
            deck.get(index).setUsed(true);
            cardUsed++;
        }
        return temp;
    }
}
