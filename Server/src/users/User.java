package users;


public class User {
    private String name;
    private boolean isComputer;
    private int id=0;

    public User(String name, boolean isComputer) {
        this.name = name;
        this.isComputer = isComputer;
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
}
