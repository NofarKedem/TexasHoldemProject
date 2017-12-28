public class BlindsHelper {

    private int small;
    private int big;
    private int startingBig;
    private int totalRounds;
    private int totalHands;
    private int maxBig;
    private int addition;
    private int countDilerRounds;
    private int max_total_rounds;

    BlindsHelper(int small,int big,int totalHands,int addition,int max_total_rounds){
        this.small = small;
        this.big = big;
        this.startingBig = big;
        this.totalHands = totalHands;
        this.addition = addition;
        this.max_total_rounds = max_total_rounds;
        this.countDilerRounds = 0;
        calcMaxBig();
    }

    private void calcTotalRounds(){
        totalRounds = totalHands / Utils.numOfPlayers;
    }
    public void calcMaxBig(){
        int min = 0;
        calcTotalRounds();
        if (totalRounds*addition < max_total_rounds*addition){
            min = totalRounds*addition;
        }
        else{
            min = max_total_rounds*addition;
        }
        maxBig = min + startingBig;
    }



    public void blindsUpdate(int ancorDindex, int dillerIndex){
        if(dillerIndex == ancorDindex){
            countDilerRounds++;
            if (countDilerRounds <= max_total_rounds){
                if(big + addition <= maxBig){
                    small = small + addition;
                    big = big + addition;
                }
            }
        }
    }


    public int getSmall(){
        return small;
    }

    public int getBig(){
        return big;
    }


}
