package servlets;

import Games.GamesManager;
import com.google.gson.Gson;
import users.LoginStatus;
import utility.ServletUtils;
import utility.SessionUtils;
import users.User;
import users.UserManager;

import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

@WebServlet(
        name = "LoginServlet",
        urlPatterns = {"/pages/signup/login"}
)

public class LoginServlet extends HttpServlet{

    private final String SIGN_UP_URL = "/pages/signup/signup.html";
    private final String LOGIN_ERROR_URL = "/pages/loginError/loginError.html";
    private final String GAMES_MANAGER_URL = "/pages/gamesManager/gamesManager.html";

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
        UserManager usersManager = ServletUtils.getUserManager(getServletContext());
        String action = request.getParameter("action");
        switch (action){
            case "login":
                try {
                    this.loginAction(request, response);
                } catch (ScriptException e) {
                    e.printStackTrace();
                }
                break;
            case "status":
                try {
                    this.statusAction(request, response, usersManager);
                } catch (ScriptException e) {
                    e.printStackTrace();
                }
                break;

        }



    }

    private void loginAction(HttpServletRequest request, HttpServletResponse response)
            throws ScriptException, IOException {
        response.setContentType("application/json");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        String usernameFromSession = SessionUtils.getUsername(request);
        if (usernameFromSession == null) {
            //user is not logged in yet
            String usernameFromParameter = request.getParameter("userName");
            if (usernameFromParameter == null) {
                //no username in session and no username in parameter -
                //redirect back to the index page
                //this return an HTTP code back to the browser telling it to load
                response.sendRedirect(SIGN_UP_URL); //Need To Add Error message

            } else {
                //normalize the username value
                usernameFromParameter = usernameFromParameter.trim();
                String userTypeFromParameter = request.getParameter("userType");
                boolean isComputer = Objects.equals(userTypeFromParameter, "true");
                userTypeFromParameter = userTypeFromParameter.trim();
                if (userManager.isUserExists(usernameFromParameter)) {
//                    String errorMessage = "Username " + usernameFromParameter + " already exists. Please enter a different username.";
//                    request.setAttribute("username_error", errorMessage);
//                    getServletContext().getRequestDispatcher(LOGIN_ERROR_URL).forward(request, response);
                    out.println(gson.toJson(new LoginStatus(false, "User name is allready taken.")));
                } else {
                    //add the new user to the users list
                    //boolean isComputer = false;
                    //if (userTypeFromParameter == "Computer Player"){isComputer = true;}
                    userManager.addUser(new User(usernameFromParameter, isComputer));
                    //set the username in a session so it will be available on each request
                    //the true parameter means that if a session object does not exists yet
                    //create a new one
                    request.getSession(true).setAttribute("username", usernameFromParameter);

                    //redirect the request to the game manager - in order to actually change the URL
//                    System.out.println("On login, request URI is: " + request.getRequestURI());
//                    response.sendRedirect(GAMES_MANAGER_URL);
                    out.println(gson.toJson(new LoginStatus(true, null)));

                }
            }
        }
        else {
            //user is already logged in
            //response.sendRedirect(GAMES_MANAGER_URL);
            out.println(gson.toJson(new LoginStatus(true, "User is allready logged in.")));
        }
    }

    private void statusAction(HttpServletRequest request, HttpServletResponse response, UserManager usersManager)
            throws ScriptException, IOException {
        response.setContentType("application/json");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        if(SessionUtils.hasSession(request) && SessionUtils.isLoggedIn(request.getSession())) {
            String userName = SessionUtils.getUsername(request);
            boolean isComputer = SessionUtils.isComputer(request.getSession(false));
            User user = usersManager.getUser(userName);
            out.println(gson.toJson(new LoginStatus(true, (String)null, userName, isComputer, user.getInGameNumber())));
        } else {
            out.println(gson.toJson(new LoginStatus(false)));
        }

    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
