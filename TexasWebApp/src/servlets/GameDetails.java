package servlets;

import GameLogic.*;
import Games.GameController;
import Games.GamesManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import users.User;
import users.UserManager;
import utility.ServletUtils;
import utility.SessionUtils;

import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


@WebServlet(
        name = "GameDetailsServlet",
        urlPatterns = {"/pages/OneGame/OneGameDetails"}
)

public class GameDetails  extends HttpServlet {

        private void processRequest(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            response.setContentType("application/json");
            GamesManager gamesManager = ServletUtils.getGamesManager(getServletContext());
            String username = SessionUtils.getUsername(request);
            if (username == null) {
                response.sendRedirect("index.html");
            }

            String action = request.getParameter("action");
            switch (action) {

                case "GameDetails":
                    try {
                        this.GameDetailsAction(request, response, gamesManager);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    }break;
                case "ifThereAreEnoughUser":
                    try {
                        this.ifThereAreEnoughUserAction(request, response, gamesManager);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    }break;
                case "StartGame":
                    try {
                        this.StartGameAction(request, response, gamesManager);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    }break;

                case "checkIfMyTurn":
                    try {
                        this.checkIfMyTurnAction(request, response, gamesManager);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    }break;
                case "buttonActionClicked":
                    try {
                        this.buttonActionClickedAction(request, response, gamesManager);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    }break;
            }

        }

        private void GameDetailsAction(HttpServletRequest request, HttpServletResponse response, GamesManager gamesManager)
                throws ScriptException, IOException {
            response.setContentType("application/json");
            Gson gson = new Gson();
            PrintWriter out = response.getWriter();

            GameController game = (GameController)request.getSession().getAttribute("game");
            GameDetailsInfo gameDetailsInfo = game.getGameEngine().getGameDetailsInfo();
            out.println(gson.toJson(gameDetailsInfo));
        }
    private void ifThereAreEnoughUserAction(HttpServletRequest request, HttpServletResponse response, GamesManager gamesManager)
            throws ScriptException, IOException {

        response.setContentType("application/json");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();

        GameController game = (GameController)request.getSession().getAttribute("game");
        if(game.getnumOfPlayers() == game.getnumOfSubscribers())
        {
            out.println(gson.toJson(true));
        }
        else
        {
            out.println(gson.toJson(false));
        }
    }
    private void StartGameAction(HttpServletRequest request, HttpServletResponse response, GamesManager gamesManager)
            throws ScriptException, IOException {
        GameController game = (GameController)request.getSession().getAttribute("game");
        game.startGame();
        response.setContentType("application/json");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
       // List<PlayerInfo> playerInfoList = game.getAllPlayerInfo();
       // Type listType = new TypeToken<List<PlayerInfo>>() {}.getType();
        out.println(gson.toJson(game.getAllPlayerInfo()));
        //out.println(gson.toJson(new PlayerInfo(game.getGameEngine().getPlayerInfo(0))));
    }

    private void checkIfMyTurnAction(HttpServletRequest request, HttpServletResponse response, GamesManager gamesManager)
            throws ScriptException, IOException {
        GameController game = (GameController)request.getSession().getAttribute("game");
        User user = (User)request.getSession().getAttribute("user");
        movesInfo moveInfo = game.getMovesInfo();
        if(game.getGameEngine().getCurrPlayer() != game.getGameEngine().getPlayerIndexByName(user.getName()))
            moveInfo.setAllFalse();
        response.setContentType("application/json");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        out.println(gson.toJson(moveInfo));

    }

    private void buttonActionClickedAction(HttpServletRequest request, HttpServletResponse response, GamesManager gamesManager)
            throws ScriptException, IOException {
        GameController game = (GameController)request.getSession().getAttribute("game");
        String amount = request.getParameter("amount");
        GameEngine gameEngine = game.getGameEngine();
        GameMoveStatus gameMoveStatus = gameEngine.getGameMoveStatus(amount);
        String numOfMove = request.getParameter("move");
        game.getGameEngine().gameMove(numOfMove,Integer.parseInt(amount));

        response.setContentType("application/json");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        out.println(gson.toJson(gameMoveStatus));
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

