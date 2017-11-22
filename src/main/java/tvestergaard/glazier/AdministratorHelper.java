package tvestergaard.glazier;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdministratorHelper {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private MessageHelper messageHelper;

    public AdministratorHelper(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
        this.messageHelper = new MessageHelper(request);
    }

    public boolean isAdmin() {
        return session.getAttribute("administrator") != null;
    }

    public void redirect() throws ServletException, IOException {
        messageHelper.addMessage("You must be an administrator to access that page!");
        request.getRequestDispatcher("login").forward(request, response);
    }
}
