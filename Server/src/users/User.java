package users;


public class User {
    private String name;
    private boolean isComputer;
    private int id=0;
    private boolean isMyTurn;
    private boolean duplicate;
    public User(String name, boolean isComputer) {
        this.name = name;
        this.isComputer = isComputer;
        isMyTurn=false;
        this.duplicate = false;
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

    public void setDuplicate(boolean duplicate) {
        this.duplicate = duplicate;
    }

    public boolean isDuplicate() {
        return duplicate;
    }
}
