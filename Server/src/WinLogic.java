
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


    public Map<Integer,String> findTheWinner(List<Card> communityCardsRef, List<PokerPlayer> playersRef) throws Exception {
        Map<Integer,String> theWinners = new HashMap<>();
        mCalculator.setBoardFromString(setCommunityCardsAsString(communityCardsRef));
        for(PokerPlayer player : playersRef)
            {
                if(!player.getQuit()) {
                   String cardStr = player.getCard()[0].toString() + player.getCard()[1].toString();
                   mCalculator.addHand(com.rundef.poker.Hand.fromString(cardStr));
                 }
            }
        mCalculator.calculate();

        List<Integer> winnerList =  mCalculator.getWinningHands();
        int originNumOfPlayer=1;
        int indexInHands=0;
        int indexInWinnerList=0;
        for(PokerPlayer player : playersRef) {
            if(indexInWinnerList >= winnerList.size())
                break;
                if (player.getQuit() == false) //לא פרשת
            {
                if (indexInHands == winnerList.get(indexInWinnerList)) {
                    theWinners.put(originNumOfPlayer, mCalculator.getHandRanking(indexInHands).getRank().toString());
                    indexInWinnerList++;
                }
                indexInHands++;
            }
            originNumOfPlayer++;


        }

        return theWinners;
    }


    public Map<Integer,String> setTechniqWinnersForCompPlayer(List<PokerPlayer> handPlayers) {
        Map<Integer,String> theWinners = new HashMap<>();
        int originNumOfPlayer=1;
        for(PokerPlayer player : handPlayers){
            if(player.getType() != 'H' && !player.getQuit()){
                theWinners.put(originNumOfPlayer,"Technical victory");
            }
            originNumOfPlayer++;
        }
        return theWinners;
    }

    public Map<Integer,String> setTechniqWinnersForHumanPlayer(List<PokerPlayer> handPlayers) {
        Map<Integer,String> theWinners = new HashMap<>();
        int originNumOfPlayer=1;
        for(PokerPlayer player : handPlayers){
            if(player.getType() == 'H'){
                theWinners.put(originNumOfPlayer,"Technical victory");
            }
            originNumOfPlayer++;
        }
        return theWinners;
    }

}
