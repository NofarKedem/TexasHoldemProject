public class Utils {

    public static int numOfPlayers;

    public enum RoundResult{
        NOTHINGHAPPEN("1"), ENDGAME("2"),CLOSEROUND("3"),HUMANFOLD("4"),ALLCOMPUTERFOLD("5");

        private String value;
        RoundResult(String str){this.value = str;}
        public String toString(){return value;}
    }

}
