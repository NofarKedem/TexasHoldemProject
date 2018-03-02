package Games;


import java.util.*;

public class GamesManager {
    private HashMap<String, GameController> openGames = new HashMap();
    private Object lobbyGameList;

    public boolean isGameExists(String gameName) {
        return this.openGames.containsKey(gameName);
    }

    public void addGame(GameController newGame) {
        this.openGames.put(newGame.getName(), newGame);
    }

    public void removeGame(String gameName) {
        this.openGames.remove(gameName);
    }

    public GameController getGame(String key) {
        GameController game = openGames.get(key);
        return game;
                //(GameController)this.openGames.get(Integer.valueOf(key));
    }

    public List<GameController> getLobbyGameList() {
        List<GameController> GamesList = new ArrayList<>();
        for(GameController value : openGames.values()){
            GamesList.add(value);
        }
        return GamesList;

    }
}
