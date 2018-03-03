package users;

import java.util.*;
import java.util.stream.Collectors;

public class UserManager {

    private HashMap<String, User> loggedInUsers = new HashMap();


    public boolean isUserExists(String userName) {
        return this.loggedInUsers.containsKey(userName);
    }

    public void addUser(User newUser) {
        this.loggedInUsers.put(newUser.getName(), newUser);
    }

    public void removeUser(String newUserName) {
        this.loggedInUsers.remove(newUserName);
    }

    public List<User> getLoggedInUsers() {
        return (List)this.loggedInUsers.entrySet().stream().map((entry) -> {
            return (User)entry.getValue();
        }).collect(Collectors.toList());
    }


/*
    public void userJoinGame(String userName, int gameId) {
        User user = (User)this.loggedInUsers.get(userName);
        user.setInGameNumber(gameId);
    }

    public void userLeaveGame(String userName) {
        User user = (User)this.loggedInUsers.get(userName);
        if(user != null) {
            user.setInGameNumber(-1);
        }

    }
*/
    public User getUser(String userName) {
        return (User)this.loggedInUsers.get(userName);
    }




}
