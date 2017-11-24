package tvestergaard.glazier;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Provides helpful methods for dealing with the adiministrative pages.
 *
 * @author Thomas
 */
public class AdministratorHelper {

    /**
     * The {@link HttpServletRequest}.
     */
    private final HttpServletRequest request;

    /**
     * The {@link HttpServletResponse}.
     */
    private final HttpServletResponse response;

    /**
     * The user {@link HttpSession}.
     */
    private final HttpSession session;

    /**
     * The {@link MessageHelper} to send messages to the user with.
     */
    private final MessageHelper messageHelper;

    /**
     * Creates a new {@link AdministratorHelper}.
     *
     * @param request The {@link HttpServletRequest}.
     * @param response The {@link HttpServletResponse}.
     */
    public AdministratorHelper(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
        this.messageHelper = new MessageHelper(request);
    }

    /**
     * Checks if the request comes from an administrator.
     *
     * @return True if the request came from an administrator, false otherwise.
     */
    public boolean isAdmin() {
        return session.getAttribute("administrator") != null;
    }

    /**
     * Redirects the user to the login page.
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void redirect() throws ServletException, IOException {
        messageHelper.addMessage("You must be an administrator to access that page!");
        request.getRequestDispatcher("login").forward(request, response);
    }
}
