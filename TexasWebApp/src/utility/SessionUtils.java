package utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {

    public static String getUsername (HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object sessionAttribute = session != null ? session.getAttribute("username") : null;
        return sessionAttribute != null ? sessionAttribute.toString() : null;
    }

    public static void clearSession (HttpServletRequest request) {
        request.getSession().invalidate();
    }

    public static boolean hasSession(HttpServletRequest request) {
        return request.getSession(false) != null;
    }

    public static boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("userName") != null;
    }

    public static void loginUser(HttpSession session, String userName, boolean isComputer) {
        session.setAttribute("userName", userName);
        session.setAttribute("isComputer", Boolean.valueOf(isComputer));
    }

    public static void logoutUser(HttpSession session) {
        session.invalidate();
    }

    public static boolean isComputer(HttpSession session) {
        return ((Boolean)session.getAttribute("isComputer")).booleanValue();
    }

}
