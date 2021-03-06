package servlets;

import Games.LoadGameStatus;
import Games.GameController;
import Games.GamesManager;
import users.User;
import users.UserManager;
import utility.ServletUtils;
import utility.SessionUtils;
import com.google.gson.Gson;

import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.String;
import java.lang.Object;

import static jdk.nashorn.internal.objects.NativeString.substring;

@WebServlet(
        name = "GetStartedServlet",
        urlPatterns = {"/pages/gamesManager/getStarted"}
)
public class GetStartedServlet extends HttpServlet {

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        GamesManager gamesManager = ServletUtils.getGamesManager(getServletContext());
        String username = SessionUtils.getUsername(request);
        if (username == null) {
            response.sendRedirect("index.html");
        }

        String action = request.getParameter("action");
        switch (action){

            case "createGameDialog":
                try {
                    this.GameDialogAction(request, response, gamesManager);
                } catch (ScriptException e) {
                    e.printStackTrace();
                } break;


        }

    }
    private void GameDialogAction(HttpServletRequest request, HttpServletResponse response, GamesManager gamesManager)
            throws ScriptException, IOException {
        response.setContentType("application/json");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        String nameOfGame = request.getParameter("nameGame");
        GameController game = gamesManager.getGame(nameOfGame);

        String username = SessionUtils.getUsername(request);
        UserManager usersManager = ServletUtils.getUserManager(getServletContext());
        User user = usersManager.getUser(username);

        if(game.isUserAllreadyExsist(user.getName()))
        {
            out.println(gson.toJson(false));

        }
        else {
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("game", game);

            game.addUser(user);
            out.println(gson.toJson(true));
        }

    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
