package tvestergaard.glazier.servelets.administrator;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.io.IOException;
import java.math.BigDecimal;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tvestergaard.glazier.AdministratorHelper;
import tvestergaard.glazier.MessageHelper;
import tvestergaard.glazier.database.DefaultMysqlSource;
import tvestergaard.glazier.database.glass.Glass;
import tvestergaard.glazier.database.glass.GlassBuilder;
import tvestergaard.glazier.database.glass.GlassReference;
import tvestergaard.glazier.database.glass.UnknownGlassReferenceException;
import tvestergaard.glazier.database.glass.GlassDAO;
import tvestergaard.glazier.database.glass.MysqlGlassDAO;

@WebServlet(name = "GlassServlet", urlPatterns = {"/administrator/glasses"})
public class GlassesServlet extends HttpServlet {

    /**
     * The {@link MysqlDataSource} used to save changes made by the
     * administrator.
     */
    private MysqlDataSource source = new DefaultMysqlSource();

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

        AdministratorHelper administratorHelper = new AdministratorHelper(request, response);
        if (!administratorHelper.isAdmin()) {
            administratorHelper.redirect();
            return;
        }

        MessageHelper messageHelper = new MessageHelper(request);

        String action = request.getParameter("action");
        if (action != null && action.equals("create")) {
            handleCreateGet(request, response);
            return;
        }

        String queryId = request.getParameter("id");

        if (queryId == null) {
            GlassDAO glassDAO = new MysqlGlassDAO(source);
            request.setAttribute("glasses", glassDAO.getGlasses());

            request.setAttribute("title", "Glasses");
            request.getRequestDispatcher("/WEB-INF/administrator/glasses-template.jsp").forward(request, response);
            return;
        }

        try {
            int id = Integer.parseInt(queryId);
            GlassDAO glassDAO = new MysqlGlassDAO(source);
            request.setAttribute("glass", glassDAO.getGlass(GlassReference.of(id)));
            request.getRequestDispatcher("/WEB-INF/administrator/glass-template.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            messageHelper.addMessage("Incorrectly formatted glass id.");
            response.sendRedirect("glasses");
        } catch (UnknownGlassReferenceException e) {
            messageHelper.addMessage("Unknown glass id.");
            response.sendRedirect("glasses");
        }
    }

    /**
     * Serves the page where the administrator can create new {@link Glass}es.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void handleCreateGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("title", "Create glass");
        request.getRequestDispatcher("/WEB-INF/administrator/glass-create-template.jsp").forward(request, response);
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

        AdministratorHelper administratorHelper = new AdministratorHelper(request, response);
        if (!administratorHelper.isAdmin()) {
            administratorHelper.redirect();
            return;
        }

        MessageHelper messageHelper = new MessageHelper(request);

        String action = request.getParameter("action");

        if (action.equals("delete")) {
            handleDeletePost(request, response, messageHelper);
            return;
        }

        if (action.equals("update")) {
            handleUpdatePost(request, response, messageHelper);
            return;
        }

        if (action.equals("create")) {
            handleCreatePost(request, response, messageHelper);
            return;
        }

        messageHelper.addMessage("Unknown action when posting to /glass.");
        response.sendRedirect("glasses");
    }

    /**
     * Deletes a {@link Glass} after pressing the <code>DELETE</code> button.
     *
     * @param request servlet request
     * @param response servlet response
     * @param messageHelper Instance of {@link MessageHelper} allowing the
     * method to pass messages to the user.
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void handleDeletePost(HttpServletRequest request, HttpServletResponse response, MessageHelper messageHelper)
            throws ServletException, IOException {
        String queryID = request.getParameter("id");

        if (queryID == null) {
            messageHelper.addMessage("Missing glass id when attempting to delete glass. No glass was deleted.");
            response.sendRedirect("glasses");
            return;
        }

        try {
            int id = Integer.parseInt(queryID);
            GlassDAO glassDAO = new MysqlGlassDAO(source);
            glassDAO.deleteGlass(GlassReference.of(id));
            messageHelper.addMessage("The glass was successfully deleted.");
        } catch (NumberFormatException e) {
            messageHelper.addMessage("Malformed id when attempting to delete glass. No glass was deleted.");
        } catch (UnknownGlassReferenceException e) {
            messageHelper.addMessage("Unknown id when attempting to delete glass. No glass was deleted.");
        } finally {
            response.sendRedirect("glasses");
        }
    }

    /**
     * Updates a {@link Glass} after pressing the <code>UPDATE</code> button.
     *
     * @param request servlet request
     * @param response servlet response
     * @param messageHelper Instance of {@link MessageHelper} allowing the
     * method to pass messages to the user.
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void handleUpdatePost(HttpServletRequest request, HttpServletResponse response, MessageHelper messageHelper)
            throws ServletException, IOException {
        String queryID = request.getParameter("id");

        if (queryID == null) {
            messageHelper.addMessage("Missing glass id when attempting to update glass. No glass was updated.");
            response.sendRedirect("glasses");
            return;
        }

        try {
            int id = Integer.parseInt(queryID);
            GlassDAO glassDAO = new MysqlGlassDAO(source);

            GlassReference reference = GlassReference.of(id);
            Glass glass = glassDAO.getGlass(reference);

            boolean errors = false;

            if (!glass.setName(request.getParameter("name"))) {
                messageHelper.addMessage("Malformed name");
                errors = true;
            }

            if (!glass.setDescription(request.getParameter("description"))) {
                messageHelper.addMessage("Malformed description.");
                errors = true;
            }

            try {
                BigDecimal decimal = new BigDecimal(request.getParameter("price_per_square_meter"));
                if (!glass.setPricePerSquareMeter(decimal)) {
                    messageHelper.addMessage("Invalid price.");
                    errors = true;
                }
            } catch (NumberFormatException | NullPointerException e) {
                messageHelper.addMessage("Malformed price.");
                errors = true;
            }

            if (errors) {
                response.sendRedirect("glasses?id=" + id);
                return;
            }

            glassDAO.updateGlass(glass);
            messageHelper.addMessage("The glass was successfully updated.");
            response.sendRedirect("glasses?id=" + id);
        } catch (NumberFormatException e) {
            messageHelper.addMessage("Malformed id when attempting to update glass. No glass was updated.");
            response.sendRedirect("glasses");
        } catch (UnknownGlassReferenceException e) {
            messageHelper.addMessage("Unknown id when attempting to delete glass. No glass was deleted.");
            response.sendRedirect("glasses");
        }
    }

    /**
     * Creates a {@link Glass} after pressing the <code>CREATE</code> button.
     *
     * @param request servlet request
     * @param response servlet response
     * @param messageHelper Instance of {@link MessageHelper} allowing the
     * method to pass messages to the user.
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void handleCreatePost(HttpServletRequest request, HttpServletResponse response, MessageHelper messageHelper)
            throws ServletException, IOException {

        try {

            GlassBuilder glassBuilder = new GlassBuilder();
            GlassDAO glassDAO = new MysqlGlassDAO(source);

            boolean errors = false;

            if (!glassBuilder.setName(request.getParameter("name"))) {
                messageHelper.addMessage("Malformed name.");
                errors = true;
            }

            if (!glassBuilder.setDescription(request.getParameter("description"))) {
                messageHelper.addMessage("Malformed description.");
                errors = true;
            }

            try {
                BigDecimal decimal = new BigDecimal(request.getParameter("price_per_square_meter"));
                if (!glassBuilder.setPricePerSquareMeter(decimal)) {
                    messageHelper.addMessage("Invalid price.");
                    errors = true;
                }
            } catch (NumberFormatException | NullPointerException e) {
                messageHelper.addMessage("Malformed price.");
                errors = true;
            }

            if (errors) {
                response.sendRedirect("orders?action=create");
                return;
            }

            Glass glass = glassDAO.insertGlass(glassBuilder);
            messageHelper.addMessage("The glass was successfully created.");
            response.sendRedirect("glasses?id=" + glass.getID());
        } catch (IllegalStateException e) {
            messageHelper.addMessage("Error while attempting to insert glass. No glass was created.");
            response.sendRedirect("glasses");
        }
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
