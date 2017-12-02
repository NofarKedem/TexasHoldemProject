
import com.rundef.poker.EquityCalculator;
import com.rundef.poker.*;

import java.lang.reflect.Array;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WinLogic {

    private EquityCalculator mCalculator = new EquityCalculator();


    private String setCommunityCardsAsString(List<Card> communityCardsRef){
        String cards = "";
        for (Card card:communityCardsRef) {
            cards = cards + card.toString() + ",";
        }
        return cards;
    }

    public void updateTheWinnerWithCashBox(){

    }

    public void devideTheCashBoxWhenTeko(){

    }

    public void showPlayersCards(){

    }

    public Map<Integer,String> findTheWinner(List<Card> communityCardsRef, List<Player> playersRef) throws Exception {
        Map<Integer,String> theWinners = new HashMap<>();
        mCalculator.setBoardFromString(setCommunityCardsAsString(communityCardsRef));
        for(Player player : playersRef)
        {
            String cardStr = player.getCard()[0].toString() + player.getCard()[1].toString();
            mCalculator.addHand(com.rundef.poker.Hand.fromString(cardStr));

        }

        mCalculator.calculate();
       for(int i=0;i<playersRef.size();i++)
       {
           if(mCalculator.getHandEquity(i).toString().equals("100 %") || mCalculator.getHandEquity(i).toString().equals("50 %"))
           {
               theWinners.put(i, mCalculator.getHandRanking(i).getRank().toString());
           }

       }
        return theWinners;
    }


}
