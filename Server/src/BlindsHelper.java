public class BlindsHelper {


    public static int calcTotalRounds(int totalHands){
        int totalRounds = totalHands / Utils.numOfPlayers;
        return totalRounds;
    }

    public static int calcMaxBig(int totalRounds, int addition,int max_total_rounds,int startingBig){
        int min = 0;
        if (totalRounds*addition < max_total_rounds*addition){
            min = totalRounds*addition;
        }
        else{
            min = max_total_rounds*addition;
        }
        int maxBig = min + startingBig;
        return maxBig;
    }



}
