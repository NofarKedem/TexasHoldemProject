package GameLogic;

public class WinnersDetails {
    String name;
    String cardCombination;
    String prize;

    public WinnersDetails(String name, String cardCombination, String prize){
        this.name = name;
        this.cardCombination = cardCombination;
        this.prize = prize;
    }

    public String getCardCombination() {
        return cardCombination;
    }

    public String getName() {
        return name;
    }

    public String getPrize() {
        return prize;
    }
}
