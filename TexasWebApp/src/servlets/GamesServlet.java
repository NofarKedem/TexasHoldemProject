package servlets;

import Games.GameController;
import Games.GamesManager;
import Games.Games;
import com.google.gson.Gson;
import utility.ServletUtils;
import utility.SessionUtils;

import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Scanner;


@WebServlet(
        name = "GamesServlet",
        urlPatterns = {"/pages/gamesManager/upload"}
)
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class GamesServlet extends HttpServlet {

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
            case "loadGame":
                try {
                    this.loadGameAction(request, response, gamesManager);
                } catch (ScriptException e) {
                    e.printStackTrace();
                }
                break;
            case "gameList":
                try {
                    this.gameListAction(request, response, gamesManager);
                } catch (ScriptException e) {
                    e.printStackTrace();
                }
        }

    }

    private void gameListAction(HttpServletRequest request, HttpServletResponse response, GamesManager gamesManager)
            throws ScriptException, IOException {
        response.setContentType("application/json");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        Games games = new Games(gamesManager.getLobbyGameList());
        out.println(gson.toJson(games));

    }

    private void loadGameAction(HttpServletRequest request, HttpServletResponse response, GamesManager gamesManager)
            throws IOException, ServletException, ScriptException {
        String gameCreator = request.getParameter("creator");
        Collection<Part> parts = request.getParts();
        String xmlTitle = null;
        StringBuilder xmlContent = new StringBuilder();


        for (Part part : parts) {
            //to write the content of the file to an actual file in the system (will be created at c:\samplefile)
            part.write("samplefile");
            String contentDisposition = part.getHeader("Content-Disposition");
            //to write the content of the file to a string
            xmlContent.append(readFromInputStream(part.getInputStream()));
            for (String cd : contentDisposition.split(";")) {
                if (cd.trim().startsWith("filename")) {
                    xmlTitle = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                }
            }
        }

        response.setContentType("application/json");
        String errorMsg = null;
        try {
//            if(gamesManager.isGameExists(xmlTitle)){
//                errorMsg = "";
//            }
            GameController game = new GameController(xmlTitle,gameCreator);
            game.loadXmlFile(xmlContent.toString());
            gamesManager.addGame(game);


            response.sendRedirect("gamesManager.html?isLoaded=true&error=" + errorMsg + "");
        } catch (Exception e) {
            errorMsg = e.getMessage();
            response.sendRedirect("gamesManager.html?isLoaded=false&error=" + errorMsg + "");
        }


    }


    private String readFromInputStream(InputStream inputStream) {
        return new Scanner(inputStream).useDelimiter("\\Z").next();
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

}
