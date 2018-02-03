package utility;

import Games.GamesManager;
import users.UserManager;
import javax.servlet.ServletContext;



public class ServletUtils {

    private static final String USER_MANAGER_ATTRIBUTE_NAME = "userManager";
    private static final String GAMES_MANAGER_ATTRIBUTE_NAME = "gamesManager";

    public static UserManager getUserManager(ServletContext servletContext) {
        if (servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME) == null) {
            servletContext.setAttribute(USER_MANAGER_ATTRIBUTE_NAME, new UserManager());
        }
        return (UserManager) servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME);
    }


    public static GamesManager getGamesManager(ServletContext servletContext) {
        if(servletContext.getAttribute(GAMES_MANAGER_ATTRIBUTE_NAME) == null){
            servletContext.setAttribute(GAMES_MANAGER_ATTRIBUTE_NAME,new GamesManager());
        }
        return (GamesManager)servletContext.getAttribute(GAMES_MANAGER_ATTRIBUTE_NAME);
    }
}
