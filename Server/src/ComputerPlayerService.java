import java.util.*;

public class ComputerPlayerService {
    private Random generator;
    private List<Round.GameMoves> values = new ArrayList<>();



   public int generateAmount(int PlayerChips, int cashBoxAmount, int currBet){
       //need to fix the 'pot' definition once it will be clear
       int maxLimit;
       if(PlayerChips < cashBoxAmount){
           maxLimit = PlayerChips;
       }
       else{maxLimit = cashBoxAmount;}
       maxLimit -=currBet;
       Random amountGenerator = new Random();
       int amount;
       do {
           amount = amountGenerator.nextInt(maxLimit);
          // amount = Math.round(amount/5)*5;
       } while (amount < 0);
       return amount;
   }

    public Round.GameMoves generateMove(Round.GameMoves lastPlayerMove, boolean isBetOn){

       Round.GameMoves randomMove;
/*
       switch (lastPlayerMove){
           case NONE: //no one played before me - I'm the first to talk
               return Round.GameMoves.BET;
           case FOLD:
               if(isBetOn){
                       Collections.addAll(values, Round.GameMoves.CALL, Round.GameMoves.RAISE, Round.GameMoves.FOLD);}
               else {
                   Collections.addAll(values, Round.GameMoves.CALL, Round.GameMoves.RAISE, Round.GameMoves.FOLD,
                           Round.GameMoves.BET, Round.GameMoves.CHECK);
               }
               randomMove = randomGameMove();
               values.clear();
               return randomMove;
           case BET:
               Collections.addAll(values, Round.GameMoves.CALL, Round.GameMoves.RAISE, Round.GameMoves.FOLD);
               randomMove = randomGameMove();
               values.clear();
               return randomMove;
           case RAISE:
               Collections.addAll(values, Round.GameMoves.CALL, Round.GameMoves.RAISE, Round.GameMoves.FOLD);
               randomMove = randomGameMove();
               values.clear();
               return randomMove;
           case CALL:
               Collections.addAll(values, Round.GameMoves.CALL, Round.GameMoves.RAISE, Round.GameMoves.FOLD);
               randomMove = randomGameMove();
               values.clear();
               return randomMove;
           case CHECK:
               Collections.addAll(values, Round.GameMoves.CALL, Round.GameMoves.RAISE, Round.GameMoves.FOLD,
                       Round.GameMoves.BET, Round.GameMoves.CHECK);
               randomMove = randomGameMove();
               values.clear();
               return randomMove;
       }
*/
       //return randomGameMove();
        return randomMove=Round.GameMoves.FOLD;
    }

    private Round.GameMoves randomGameMove(){
        Round.GameMoves computerGameMove;

        int size = values.size();
        generator = new Random();
        computerGameMove = values.get(generator.nextInt(size));
        return computerGameMove;
    }

}
