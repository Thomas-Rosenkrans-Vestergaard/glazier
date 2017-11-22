/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tvestergaard.glazier.servelets.administrator;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tvestergaard.glazier.MessageHelper;

/**
 *
 * @author Thomas
 */
@WebServlet(name = "LoginServelet", urlPatterns = {"/administrator/login"})
public class LoginServelet extends HttpServlet {

    private String correctUsername = "administrator";
    private String correctPassword = "1234";

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
        new MessageHelper(request);
        request.setAttribute("title", "Administrator login");
        request.getRequestDispatcher("/WEB-INF/administrator/login-template.jsp").forward(request, response);
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
        String submit = request.getParameter("submit");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();

        if (submit == null || username == null || username.isEmpty() || password == null || password.isEmpty()) {
            withError(request, response, "Incomplete form data.");
            return;
        }

        if (!username.equals(correctUsername) || !password.equals(correctPassword)) {
            withError(request, response, "Incorrect username or password.");
            return;
        }

        session.setAttribute("administrator", true);
        response.sendRedirect("index");
    }

    private void withError(HttpServletRequest request, HttpServletResponse response, String error)
            throws ServletException, IOException {

        MessageHelper messageHandler = new MessageHelper(request);
        messageHandler.addMessage(error);
        response.sendRedirect("login");
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
