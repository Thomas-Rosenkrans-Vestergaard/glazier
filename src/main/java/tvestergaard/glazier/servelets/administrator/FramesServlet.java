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
import tvestergaard.glazier.database.frames.Frame;
import tvestergaard.glazier.database.frames.FrameBuilder;
import tvestergaard.glazier.database.frames.FrameDAO;
import tvestergaard.glazier.database.frames.FrameReference;
import tvestergaard.glazier.database.frames.MysqlFrameDAO;
import tvestergaard.glazier.database.frames.UnknownFrameReferenceException;

@WebServlet(name = "FramesServlet", urlPatterns = {"/administrator/frames"})
public class FramesServlet extends HttpServlet {

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
            FrameDAO frameDAO = new MysqlFrameDAO(source);
            request.setAttribute("frames", frameDAO.getFrames());

            request.setAttribute("title", "Frames");
            request.getRequestDispatcher("/WEB-INF/administrator/frames-template.jsp").forward(request, response);
            return;
        }

        try {
            int id = Integer.parseInt(queryId);
            FrameDAO frameDAO = new MysqlFrameDAO(source);
            request.setAttribute("frame", frameDAO.getFrame(FrameReference.of(id)));
            request.getRequestDispatcher("/WEB-INF/administrator/frame-template.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            messageHelper.addMessage("Incorrectly formatted frame id.");
            response.sendRedirect("frames");
        } catch (UnknownFrameReferenceException e) {
            messageHelper.addMessage("Unknown frame id.");
            response.sendRedirect("frames");
        }
    }

    /**
     * Serves the page where the administrator can create new {@link Frame}s.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void handleCreateGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("title", "Create frame");
        request.getRequestDispatcher("/WEB-INF/administrator/frame-create-template.jsp").forward(request, response);
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

        messageHelper.addMessage("Unknown action when posting to /frame.");
        response.sendRedirect("frames");
    }

    /**
     * Deletes a {@link Frame} after pressing the <code>DELETE</code> button.
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
            messageHelper.addMessage("Missing frame id when attempting to delete frame. No frame was deleted.");
            response.sendRedirect("frames");
            return;
        }

        try {
            int id = Integer.parseInt(queryID);
            FrameDAO frameDAO = new MysqlFrameDAO(source);
            frameDAO.deleteFrame(FrameReference.of(id));
            messageHelper.addMessage("The frame was successfully deleted.");
        } catch (NumberFormatException e) {
            messageHelper.addMessage("Malformed id when attempting to delete frame. No frame was deleted.");
        } catch (UnknownFrameReferenceException e) {
            messageHelper.addMessage("Unknown id when attempting to delete frame. No frame was deleted.");
        } finally {
            response.sendRedirect("frames");
        }
    }

    /**
     * Updates a {@link Frame} after pressing the <code>UPDATE</code> button.
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
            messageHelper.addMessage("Missing frame id when attempting to update frame. No frame was updated.");
            response.sendRedirect("frames");
            return;
        }

        try {
            int id = Integer.parseInt(queryID);
            FrameDAO frameDAO = new MysqlFrameDAO(source);

            FrameReference reference = FrameReference.of(id);
            Frame frame = frameDAO.getFrame(reference);

            boolean errors = false;

            if (!frame.setName(request.getParameter("name"))) {
                messageHelper.addMessage("Malformed name");
                errors = true;
            }

            if (!frame.setDescription(request.getParameter("description"))) {
                messageHelper.addMessage("Malformed description.");
                errors = true;
            }

            try {
                BigDecimal decimal = new BigDecimal(request.getParameter("price_per_meter"));
                if (!frame.setPricePerMeter(decimal)) {
                    messageHelper.addMessage("Invalid price.");
                    errors = true;
                }
            } catch (NumberFormatException | NullPointerException e) {
                messageHelper.addMessage("Malformed price.");
                errors = true;
            }

            if (errors) {
                response.sendRedirect("frames?id=" + id);
                return;
            }

            frameDAO.updateFrame(frame);
            messageHelper.addMessage("The frame was successfully updated.");
            response.sendRedirect("frames?id=" + id);
        } catch (NumberFormatException e) {
            messageHelper.addMessage("Malformed id when attempting to update frame. No frame was updated.");
            response.sendRedirect("frames");
        } catch (UnknownFrameReferenceException e) {
            messageHelper.addMessage("Unknown id when attempting to delete frame. No frame was deleted.");
            response.sendRedirect("frames");
        }
    }

    /**
     * Creates a {@link Frame} after pressing the <code>CREATE</code> button.
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

            FrameBuilder frameBuilder = new FrameBuilder();

            FrameDAO frameDAO = new MysqlFrameDAO(source);

            boolean errors = false;

            if (!frameBuilder.setName(request.getParameter("name"))) {
                messageHelper.addMessage("Malformed name.");
                errors = true;
            }

            if (!frameBuilder.setDescription(request.getParameter("description"))) {
                messageHelper.addMessage("Malformed description.");
                errors = true;
            }

            try {
                BigDecimal decimal = new BigDecimal(request.getParameter("price_per_meter"));
                if (!frameBuilder.setPricePerMeter(decimal)) {
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

            Frame frame = frameDAO.insertFrame(frameBuilder);
            messageHelper.addMessage("The frame was successfully created.");
            response.sendRedirect("frames?id=" + frame.getID());
        } catch (IllegalStateException e) {
            messageHelper.addMessage("Error while attempting to insert frame. No frame was created.");
            response.sendRedirect("frames");
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
