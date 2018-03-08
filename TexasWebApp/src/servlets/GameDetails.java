package servlets;


import GameLogic.Card;
import GameLogic.GameDetailsInfo;
import GameLogic.PlayerInfo;
import GameLogic.movesInfo;

import GameLogic.*;

import Games.GameController;
import Games.GamesManager;
import XMLobject.Player;
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
                case "ifStartGame":
                    try {
                        this.ifStartGameAction(request, response, gamesManager);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    }break;
                case "StartGame":
                    try {
                        this.StartGameAction(request, response, gamesManager);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    }break;

                case "displayMoveButtonAccordingToMyTurn":
                    try {
                        this.displayMoveButtonAccordingToMyTurn(request, response, gamesManager);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    }break;
                case "getPlayerCards":
                    try {
                        this.getPlayerCardsAction(request, response, gamesManager);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    }break;
                case "getAllPlayersCards":
                    try {
                        this.getAllPlayersCardsAction(request, response, gamesManager);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    }break;
                case "getComunityCardsAction":
                    try {
                        this.getComunityCardsAction(request, response, gamesManager);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    }break;
                case "buttonActionClicked":
                    try {
                        this.buttonActionClickedAction(request, response, gamesManager);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    }break;
                case "readyClicked":
                    try {
                        this.readyClickedAction(request, response, gamesManager);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    }break;

                case "checkIfHandEnd":
                    try {
                        this.checkIfHandEndAction(request, response, gamesManager);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    }break;

                case "ifNewHand":
                    try {
                        this.ifNewHandAction(request, response, gamesManager);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    }break;

                case "quitClicked":
                    try {
                        this.quitClickedAction(request, response, gamesManager);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    }break;

                case "ChipsCliked":
                    try {
                        this.ChipsClikedAction(request, response, gamesManager);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    }break;
                case "refreshNewHand":
                    try {
                        this.refreshNewHandAction(request, response, gamesManager);
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
    private void ifStartGameAction(HttpServletRequest request, HttpServletResponse response, GamesManager gamesManager)
            throws ScriptException, IOException {

        response.setContentType("application/json");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();

        GameController game = (GameController)request.getSession().getAttribute("game");
        if(game.getIsActive())
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
        response.setContentType("application/json");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        List<PlayerInfo> testGame = game.getAllPlayerInfo();
        out.println(gson.toJson(/*game.getAllPlayerInfo()*/testGame));
    }

    private void displayMoveButtonAccordingToMyTurn(HttpServletRequest request, HttpServletResponse response, GamesManager gamesManager)
            throws ScriptException, IOException {
        GameController game = (GameController)request.getSession().getAttribute("game");
        User user = (User)request.getSession().getAttribute("user");
        movesInfo moveInfo = game.getMovesInfo();
        if(game.getGameEngine().getCurrPlayer() == game.getGameEngine().getPlayerIndexByName(user.getName()))
        {
            if(user.isComputer())
            {
                moveInfo.setAllFalse();
                game.gameMoveComputer();
            }
            moveInfo.setIfMyTurn(true);
        }
        else
            moveInfo.setAllFalse();
        response.setContentType("application/json");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        out.println(gson.toJson(moveInfo));

    }

    private void getPlayerCardsAction(HttpServletRequest request, HttpServletResponse response, GamesManager gamesManager)
            throws ScriptException, IOException {
        response.setContentType("application/json");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        GameController game = (GameController)request.getSession().getAttribute("game");
        User user = (User)request.getSession().getAttribute("user");
        int playerIndex = game.getGameEngine().getPlayerIndexByName(user.getName());
        PlayerInfo playerInfo = game.getGameEngine().getPlayerInfo(playerIndex);
        List<String> cardsAsString =  playerInfo.getPlayerCardsAsString();
        cardsAsString.add(user.getName()); // adding name of the card holder for client identification
        out.println(gson.toJson(cardsAsString));

    }

    private void getAllPlayersCardsAction(HttpServletRequest request, HttpServletResponse response, GamesManager gamesManager)
            throws ScriptException, IOException {
        response.setContentType("application/json");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        GameController game = (GameController)request.getSession().getAttribute("game");
        List <PlayerInfo> playersInfo = game.getGameEngine().getAllPlayerInfo();
        List<String> cardsAsString =  new ArrayList<>();
        for(PlayerInfo player : playersInfo){
            cardsAsString.add(player.getName());
            cardsAsString.addAll(player.getPlayerCardsAsString());
        }

        out.println(gson.toJson(cardsAsString));

    }

    private void getComunityCardsAction(HttpServletRequest request, HttpServletResponse response, GamesManager gamesManager)
            throws ScriptException, IOException {
        response.setContentType("application/json");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        GameController game = (GameController)request.getSession().getAttribute("game");

        List<Card> communityCards = game.getGameEngine().getCommunityCards();
        List<String> communityCardsAsString = new ArrayList<>();
        for (Card card:communityCards) {
            communityCardsAsString.add(card.toString());
        }
        out.println(gson.toJson(communityCardsAsString));

    }


    private void buttonActionClickedAction(HttpServletRequest request, HttpServletResponse response, GamesManager gamesManager)
            throws ScriptException, IOException {
        GameController game = (GameController)request.getSession().getAttribute("game");
        String numOfMove = request.getParameter("move");
        game.setMove(numOfMove);
        String amount = request.getParameter("amount");
        GameMoveStatus gameMoveStatus = game.getGameEngine().getGameMoveStatus(amount);
        if(gameMoveStatus.getIsValidAmount())
        {

            game.gameMoveHuman(numOfMove,Integer.parseInt(amount));
        }
        response.setContentType("application/json");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        out.println(gson.toJson(gameMoveStatus));
    }

    private void readyClickedAction(HttpServletRequest request, HttpServletResponse response, GamesManager gamesManager)
            throws ScriptException, IOException {
        GameController game = (GameController)request.getSession().getAttribute("game");
        boolean flag =  game.userClickedReady();
        List<Boolean> flags = new ArrayList<>();
        flags.add(true);
        flags.add(flag);
        response.setContentType("application/json");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        out.println(gson.toJson(flags));
    }

    private void refreshNewHandAction(HttpServletRequest request, HttpServletResponse response, GamesManager gamesManager)
            throws ScriptException, IOException {
        GameController game = (GameController)request.getSession().getAttribute("game");
        boolean flag =  game.refreshNewHand();
        response.setContentType("application/json");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        out.println(gson.toJson(flag));
    }


    private void checkIfHandEndAction(HttpServletRequest request, HttpServletResponse response, GamesManager gamesManager)
            throws ScriptException, IOException {
        GameController game = (GameController)request.getSession().getAttribute("game");

        response.setContentType("application/json");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        //game.clearWinResults();
        HandResult handResult = new HandResult(game.isEndHand(),game.isAllHandsEnd(),game.getWinResults());
        out.println(gson.toJson(handResult));
    }


    private void ifNewHandAction(HttpServletRequest request, HttpServletResponse response, GamesManager gamesManager)
            throws ScriptException, IOException {
        GameController game = (GameController)request.getSession().getAttribute("game");

        response.setContentType("application/json");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        HandResult handResult = new HandResult();
        handResult.setNewHand(!game.isEndHand());
        handResult.setEnoughPlayer(game.enoughPlayerInTheGame());
        out.println(gson.toJson( handResult));
    }

    private void quitClickedAction(HttpServletRequest request, HttpServletResponse response, GamesManager gamesManager)
            throws ScriptException, IOException {
        GameController game = (GameController)request.getSession().getAttribute("game");
        User user = (User)request.getSession().getAttribute("user");
        response.setContentType("application/json");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();

        game.quitClicked(user);
        out.println(gson.toJson( true));


    }


    private void ChipsClikedAction(HttpServletRequest request, HttpServletResponse response, GamesManager gamesManager)
            throws ScriptException, IOException {
        GameController game = (GameController)request.getSession().getAttribute("game");
        User user = (User)request.getSession().getAttribute("user");
        game.ChipsCliked(user);

        response.setContentType("application/json");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        out.println(gson.toJson( true));
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

