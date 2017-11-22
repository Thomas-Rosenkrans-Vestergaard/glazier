/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tvestergaard.glazier.servelets.administrator;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tvestergaard.glazier.AdministratorHelper;
import tvestergaard.glazier.MessageHelper;
import tvestergaard.glazier.database.DefaultMysqlSource;
import tvestergaard.glazier.database.frames.FrameDAO;
import tvestergaard.glazier.database.frames.MysqlFrameDAO;
import tvestergaard.glazier.database.glass.GlassDAO;
import tvestergaard.glazier.database.glass.GlassReference;
import tvestergaard.glazier.database.glass.MysqlGlassDAO;
import tvestergaard.glazier.database.glass.UnknownGlassReferenceException;
import tvestergaard.glazier.database.orders.MysqlOrderDAO;
import tvestergaard.glazier.database.orders.OrderDAO;
import tvestergaard.glazier.database.orders.OrderReference;
import tvestergaard.glazier.database.orders.UnknownOrderReferenceException;

/**
 *
 * @author Thomas
 */
@WebServlet(name = "OrdersServlet", urlPatterns = {"/administrator/orders"})
public class OrdersServlet extends HttpServlet {

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
        MysqlDataSource source = DefaultMysqlSource.getSource();

        if (queryId == null) {
            request.setAttribute("title", "Orders");
            OrderDAO orderDAO = new MysqlOrderDAO(DefaultMysqlSource.getSource());
            request.setAttribute("orders", orderDAO.getOrders());
            request.getRequestDispatcher("/WEB-INF/administrator/orders-template.jsp").forward(request, response);
            return;
        }

        try {
            int id = Integer.parseInt(queryId);

            GlassDAO glassDAO = new MysqlGlassDAO(source);
            request.setAttribute("glasses", glassDAO.getGlasses());

            FrameDAO frameDAO = new MysqlFrameDAO(source);
            request.setAttribute("frames", frameDAO.getFrames());

            OrderDAO orderDAO = new MysqlOrderDAO(source);
            request.setAttribute("order", orderDAO.getOrder(OrderReference.of(id)));
            request.getRequestDispatcher("/WEB-INF/administrator/order-template.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            messageHelper.addMessage("Incorrectly formatted order id.");
            response.sendRedirect("orders");
        } catch (UnknownOrderReferenceException e) {
            messageHelper.addMessage("Unknown order id.");
            response.sendRedirect("orders");
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
