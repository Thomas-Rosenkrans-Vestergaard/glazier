/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tvestergaard.glazier.servelets;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tvestergaard.glazier.MessageHelper;
import tvestergaard.glazier.database.DefaultMysqlSource;
import tvestergaard.glazier.database.frames.FrameDAO;
import tvestergaard.glazier.database.frames.MysqlFrameDAO;
import tvestergaard.glazier.database.glass.GlassDAO;
import tvestergaard.glazier.database.glass.MysqlGlassDAO;

/**
 *
 * @author Thomas
 */
@WebServlet(name = "CalculateServelet", urlPatterns = {"/calculate-price"})
public class CalculatePriceServlet extends HttpServlet {

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

        if (request.getParameter("submit") != null) {
            handleSubmit(request, response);
            return;
        }

        request.setAttribute("title", "Glazier");
        MysqlDataSource source = new DefaultMysqlSource();
        GlassDAO glassDAO = new MysqlGlassDAO(source);
        FrameDAO frameDAO = new MysqlFrameDAO(source);
        request.setAttribute("glasses", glassDAO.getGlasses());
        request.setAttribute("frames", frameDAO.getFrames());
        request.getRequestDispatcher("WEB-INF/calculate-price-template.jsp").forward(request, response);
    }

    private void handleSubmit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String messurement = request.getParameter("messurement");
        String width = request.getParameter("width");
        String height = request.getParameter("height");
        String glass = request.getParameter("glass");
        String frame = request.getParameter("frame");

        MessageHelper messageHandler = new MessageHelper(request);
        boolean errors = false;

        if (messurement == null || width == null || height == null | glass == null || frame == null) {
            messageHandler.addMessage("Incomplete form data.");
            errors = true;
        } 
        
        if(errors == false){

            if (!messurement.equals("mm") && !messurement.equals("cm") && !messurement.equals("m")) {
                messageHandler.addMessage("Invalid messurement");
                errors = true;
            }

            if (!validInteger(width)) {
                messageHandler.addMessage("Invalid width.");
                errors = true;
            }

        }

        request.getRequestDispatcher("WEB-INF/calculate-price-template.jsp").forward(request, response);
    }
    
    private boolean validInteger(String integer){
        return true;
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
        doGet(request, response);
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
