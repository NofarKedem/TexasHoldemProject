package servlets;


import Games.GameController;
import com.google.gson.Gson;
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
        name = "OneGameServlet",
        urlPatterns = {"/pages/OneGame/OneGame"}
)
public class OneGameServlet extends HttpServlet {

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        String username = SessionUtils.getUsername(request);
        if (username == null) {
            response.sendRedirect("index.html");
        }

        String action = request.getParameter("action");
        switch(action) {

            case "gameUsers":
                try{
                    this.getGameUsersAction(request,response);
                } catch (Exception e){
                    e.printStackTrace();
                }
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


    private void getGameUsersAction(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("application/json");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        GameController gameFromSession = (GameController)request.getSession().getAttribute("game");
        Users gameUsers = new Users(gameFromSession.getUsers());
        out.println(gson.toJson(gameUsers));
    }

}
