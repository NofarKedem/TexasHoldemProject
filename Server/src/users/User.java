package users;


public class User {
    private String name;
    private boolean isComputer;
    private int id=0;
    private boolean isMyTurn;

    public User(String name, boolean isComputer) {
        this.name = name;
        this.isComputer = isComputer;
        isMyTurn=false;
    }


    public String getName() {
        return this.name;
    }

    public boolean isComputer() {
        return this.isComputer;
    }

    public int getId()
    {
         return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public void setIsMyTurn(boolean ifMyTurn) {
        this.isMyTurn = ifMyTurn;
    }

    public boolean isIfMyTurn() {
        return isMyTurn;
    }
}
