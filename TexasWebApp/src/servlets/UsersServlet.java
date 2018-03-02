package servlets;

import Games.GameController;
import com.google.gson.Gson;
import users.User;
import users.UserManager;
import users.Users;
import utility.ServletUtils;
import utility.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(
        name = "UsersServlet",
        urlPatterns = {"/pages/gamesManager/userManager"}
)
public class UsersServlet extends HttpServlet {

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        UserManager usersManager = ServletUtils.getUserManager(getServletContext());
        String username = SessionUtils.getUsername(request);
        if (username == null) {
            response.sendRedirect("index.html");
        }

        String action = request.getParameter("action");
        switch(action) {
            case "users":
                try {
                    this.usersAction(request, response, usersManager);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "getUser":
                try {
                    this.getUserAction(request, response, usersManager);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case "gameUsers":
                try{
                    this.getGameUsersAction(request,response);
                } catch (Exception e){
                    e.printStackTrace();
                }
        }
    }

    private void getGameUsersAction(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("application/json");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        GameController gameFromSession = request.getSession().getAttribute("game");
        out.println(gson.toJson(new Users(gameFromSession.getUsers());
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void usersAction(HttpServletRequest request, HttpServletResponse response, UserManager usersManager) throws IOException {
        response.setContentType("application/json");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        out.println(gson.toJson(new Users(usersManager.getLoggedInUsers())));
    }

    private void getUserAction(HttpServletRequest request, HttpServletResponse response, UserManager usersManager) throws IOException {
        response.setContentType("application/json");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        String userName = SessionUtils.getUsername(request);
        User user = usersManager.getUser(userName);
        boolean isComputer = user.isComputer();
        out.println(gson.toJson(new User( userName, isComputer)));
    }
}
