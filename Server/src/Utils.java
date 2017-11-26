public class Utils {

    public enum RoundResult{
        NOTHINGHAPPEN("1"), ENDGAME("2"),CLOSEROUND("3");

        private String value;
        RoundResult(String str){this.value = str;}
        public String toString(){return value;}
    }
}
