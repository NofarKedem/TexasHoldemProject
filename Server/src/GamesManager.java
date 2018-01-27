import users.User;

import java.util.HashMap;

public class GamesManager {
    private HashMap<String, GameController> openGames = new HashMap();

    public boolean isGameExists(String gameName) {
        return this.openGames.containsKey(gameName);
    }

    public void addGame(GameController newGame) {
        this.openGames.put(newGame.getName(), newGame);
    }

    public void removeGame(String gameName) {
        this.openGames.remove(gameName);
    }
}
