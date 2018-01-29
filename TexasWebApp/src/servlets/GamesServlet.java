package servlets;

import com.google.gson.Gson;
import utility.ServletUtils;

import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Scanner;

public class GamesServlet extends HttpServlet {

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        String action = request.getParameter("action");
        switch (action){
            case "loadGame":
                try {
                    this.loadGameAction(request, response);
                } catch (ScriptException e) {
                    e.printStackTrace();
                }
        }

    }

    private void loadGameAction(HttpServletRequest request, HttpServletResponse response)
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
