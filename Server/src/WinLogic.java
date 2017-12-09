
import com.rundef.poker.EquityCalculator;
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


    public Map<Integer,String> findTheWinner(List<Card> communityCardsRef, List<Player> playersRef) throws Exception {
        Map<Integer,String> theWinners = new HashMap<>();
        mCalculator.setBoardFromString(setCommunityCardsAsString(communityCardsRef));

        mCalculator.addHand(com.rundef.poker.Hand.fromString("3S5S"));
        mCalculator.addHand(com.rundef.poker.Hand.fromString("3D5D"));
        mCalculator.calculate();

        List<Integer> winnerList =  mCalculator.getWinningHands();
        int originNumOfPlayer=1;
        int indexInHands=0;
        int indexInWinnerList=0;
        for(Player player : playersRef) {
            if (player.getQuit() == false) //לא פרשת
            {
                if(indexInHands== winnerList.get(indexInWinnerList))
                {
                    theWinners.put(originNumOfPlayer, mCalculator.getHandRanking(indexInHands).getRank().toString());
                    indexInWinnerList++;
                }
                indexInHands++;
            }
            originNumOfPlayer++;


        }

        return theWinners;
    }


    public Map<Integer,String> setTechniqWinnersForCompPlayer(List<Player> handPlayers) {
        Map<Integer,String> theWinners = new HashMap<>();
        int originNumOfPlayer=1;
        for(Player player : handPlayers){
            if(player.getType() != 'H' && !player.getQuit()){
                theWinners.put(originNumOfPlayer,"Technical victory");
            }
            originNumOfPlayer++;
        }
        return theWinners;
    }

    public Map<Integer,String> setTechniqWinnersForHumanPlayer(List<Player> handPlayers) {
        Map<Integer,String> theWinners = new HashMap<>();
        int originNumOfPlayer=1;
        for(Player player : handPlayers){
            if(player.getType() == 'H'){
                theWinners.put(originNumOfPlayer,"Technical victory");
            }
            originNumOfPlayer++;
        }
        return theWinners;
    }

}
