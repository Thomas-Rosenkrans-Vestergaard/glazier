/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tvestergaard.glazier.servelets.administrator;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tvestergaard.glazier.AdministratorHelper;
import tvestergaard.glazier.MessageHelper;
import tvestergaard.glazier.database.DefaultMysqlSource;
import tvestergaard.glazier.database.glass.Glass;
import tvestergaard.glazier.database.glass.GlassDAO;
import tvestergaard.glazier.database.glass.GlassReference;
import tvestergaard.glazier.database.glass.MysqlGlassDAO;
import tvestergaard.glazier.database.glass.UnknownGlassReferenceException;
import tvestergaard.glazier.database.glass.GlassDAO;
import tvestergaard.glazier.database.glass.MysqlGlassDAO;

/**
 *
 * @author Thomas
 */
@WebServlet(name = "GlassServlet", urlPatterns = {"/administrator/glasses"})
public class GlassesServlet extends HttpServlet {

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

        String queryId = request.getParameter("id");

        if (queryId == null) {
            MysqlDataSource source = DefaultMysqlSource.getSource();
            GlassDAO glassDAO = new MysqlGlassDAO(source);
            request.setAttribute("glasses", glassDAO.getGlasses());

            request.setAttribute("title", "Glasses");
            request.getRequestDispatcher("/WEB-INF/administrator/glasses-template.jsp").forward(request, response);
            return;
        }

        try {
            int id = Integer.parseInt(queryId);
            MysqlDataSource source = DefaultMysqlSource.getSource();
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
            handleDelete(request, response, messageHelper);
            return;
        }

        if (action.equals("update")) {
            handleUpdate(request, response, messageHelper);
            return;
        }

        messageHelper.addMessage("Unknown action when posting to /glass.");
        response.sendRedirect("glasses");
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response, MessageHelper messageHelper)
            throws ServletException, IOException {
        String queryID = request.getParameter("id");

        if (queryID == null) {
            messageHelper.addMessage("Missing glass id when attempting to delete glass. No glass was deleted.");
            response.sendRedirect("glasses");
            return;
        }

        try {
            int id = Integer.parseInt(queryID);
            MysqlDataSource source = DefaultMysqlSource.getSource();
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

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response, MessageHelper messageHelper)
            throws ServletException, IOException {
        String queryID = request.getParameter("id");

        if (queryID == null) {
            messageHelper.addMessage("Missing glass id when attempting to update glass. No glass was updated.");
            response.sendRedirect("glasses");
            return;
        }

        try {
            int id = Integer.parseInt(queryID);
            MysqlDataSource source = DefaultMysqlSource.getSource();
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

            if (!glass.setPricePerSquareMeter(request.getParameter("price_per_sm"))) {
                messageHelper.addMessage("Malformed price per meter.");
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
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
