package servlets;

import GameLogic.GameDetailsInfo;
import Games.GameController;
import Games.GamesManager;
import com.google.gson.Gson;
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
                    }

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

