package GameLogic;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Card {

    public enum Rank{
        DEUCE("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"), TEN("T"),
        JACK("J"), QUEEN("Q"), KING("K"), ACE("A");

        private String value;
        Rank(String str){this.value = str;}
        public String toString(){return value;}
    }

    public enum Suit{
        CLUBS("C"), DIAMONDS("D"), HEARTS("H"), SPADES("S");

        private String value;
        Suit(String str){this.value = str;}
        public String toString(){return value;}
    }

    private final Rank rank;
    private final Suit suit;
    private boolean isUsed;
    Image imagecard;

    public Card()
    {
        rank = null;
        suit = null;
        isUsed = false;

    }

    public Card(Rank rank, Suit suit){
        this.rank = rank;
        this.suit = suit;
        this.isUsed = false;

        //this line was relevant for JAVAFX and cause exception in web app
        //imagecard = new Image( rank.value + suit.value + ".png"); //שרשור של השם הכרטיס + png
    }

    public String toString(){
        return rank.toString() + suit.toString();
    }

    public boolean isUsed(){
        return this.isUsed;
    }

    public void setUsed(boolean val){
        this.isUsed = val;
    }

    public Image getImagecard()
    {
        return imagecard;
    }


}
