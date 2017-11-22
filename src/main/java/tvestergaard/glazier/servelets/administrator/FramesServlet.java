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
import tvestergaard.glazier.database.frames.Frame;
import tvestergaard.glazier.database.frames.FrameDAO;
import tvestergaard.glazier.database.frames.FrameReference;
import tvestergaard.glazier.database.frames.MysqlFrameDAO;
import tvestergaard.glazier.database.frames.UnknownFrameReferenceException;

/**
 *
 * @author Thomas
 */
@WebServlet(name = "FramesServlet", urlPatterns = {"/administrator/frames"})
public class FramesServlet extends HttpServlet {

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
            FrameDAO frameDAO = new MysqlFrameDAO(source);
            request.setAttribute("frames", frameDAO.getFrames());

            request.setAttribute("title", "Frames");
            request.getRequestDispatcher("/WEB-INF/administrator/frames-template.jsp").forward(request, response);
            return;
        }

        try {
            int id = Integer.parseInt(queryId);
            MysqlDataSource source = DefaultMysqlSource.getSource();
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

        messageHelper.addMessage("Unknown action when posting to /frame.");
        response.sendRedirect("frames");
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response, MessageHelper messageHelper)
            throws ServletException, IOException {
        String queryID = request.getParameter("id");

        if (queryID == null) {
            messageHelper.addMessage("Missing frame id when attempting to delete frame. No frame was deleted.");
            response.sendRedirect("frames");
            return;
        }

        try {
            int id = Integer.parseInt(queryID);
            MysqlDataSource source = DefaultMysqlSource.getSource();
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

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response, MessageHelper messageHelper)
            throws ServletException, IOException {
        String queryID = request.getParameter("id");

        if (queryID == null) {
            messageHelper.addMessage("Missing frame id when attempting to update frame. No frame was updated.");
            response.sendRedirect("frames");
            return;
        }

        try {
            int id = Integer.parseInt(queryID);
            MysqlDataSource source = DefaultMysqlSource.getSource();
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
            
            if(!frame.setPricePerMeter(request.getParameter("price_per_m"))){
                messageHelper.addMessage("Malformed price per meter.");
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
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
