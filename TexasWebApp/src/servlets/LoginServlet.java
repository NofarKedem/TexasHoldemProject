package servlets;

import utility.ServletUtils;
import utility.SessionUtils;
import users.User;
import users.UserManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet{

    private final String SIGN_UP_URL = "/pages/signup/singup.html";
    private final String LOGIN_ERROR_URL = "/pages/loginError/loginError.html";
    private final String GAMES_MANAGER_URL = "/pages/gamesManager/gamesManager.html";

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String usernameFromSession = SessionUtils.getUsername(request);
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        if (usernameFromSession == null) {
            //user is not logged in yet
            String usernameFromParameter = request.getParameter("username");
            if (usernameFromParameter == null) {
                //no username in session and no username in parameter -
                //redirect back to the index page
                //this return an HTTP code back to the browser telling it to load
                response.sendRedirect(SIGN_UP_URL); //Need To Add Error message
            } else {
                //normalize the username value
                usernameFromParameter = usernameFromParameter.trim();
                String userTypeFromParameter = request.getParameter("userType");
                userTypeFromParameter = userTypeFromParameter.trim();
                if (userManager.isUserExists(usernameFromParameter)) {
                    String errorMessage = "Username " + usernameFromParameter + " already exists. Please enter a different username.";
                    request.setAttribute("username_error", errorMessage);
                    getServletContext().getRequestDispatcher(LOGIN_ERROR_URL).forward(request, response);
                } else {
                    //add the new user to the users list
                    boolean isComputer = false;
                    if (userTypeFromParameter == "Computer Player"){isComputer = true;}
                    userManager.addUser(new User(usernameFromParameter, isComputer));
                    //set the username in a session so it will be available on each request
                    //the true parameter means that if a session object does not exists yet
                    //create a new one
                    request.getSession(true).setAttribute("username", usernameFromParameter);

                    //redirect the request to the game manager - in order to actually change the URL
                    System.out.println("On login, request URI is: " + request.getRequestURI());
                    response.sendRedirect(GAMES_MANAGER_URL);
                }
            }
        }
        else {
            //user is already logged in
            response.sendRedirect(GAMES_MANAGER_URL);
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
