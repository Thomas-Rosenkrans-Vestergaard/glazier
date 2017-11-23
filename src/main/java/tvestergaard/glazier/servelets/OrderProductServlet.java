/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tvestergaard.glazier.servelets;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tvestergaard.glazier.MessageHelper;
import tvestergaard.glazier.database.DefaultMysqlSource;
import tvestergaard.glazier.database.frames.FrameDAO;
import tvestergaard.glazier.database.frames.FrameReference;
import tvestergaard.glazier.database.frames.MysqlFrameDAO;
import tvestergaard.glazier.database.frames.UnknownFrameReferenceException;
import tvestergaard.glazier.database.glass.GlassDAO;
import tvestergaard.glazier.database.glass.GlassReference;
import tvestergaard.glazier.database.glass.MysqlGlassDAO;
import tvestergaard.glazier.database.glass.UnknownGlassReferenceException;
import tvestergaard.glazier.database.orders.MysqlOrderDAO;
import tvestergaard.glazier.database.orders.Order;
import tvestergaard.glazier.database.orders.OrderBuilder;
import tvestergaard.glazier.database.orders.OrderDAO;

/**
 *
 * @author Thomas
 */
@WebServlet(name = "OrderProductServlet", urlPatterns = {"/order-product"})
public class OrderProductServlet extends HttpServlet {

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

        MessageHelper messageHelper = new MessageHelper(request);

        if (request.getParameter("submit") != null) {

            boolean errors = false;

            String messurement = request.getParameter("messurement");
            if (messurement == null) {
                messageHelper.addMessage("Invalid messurement.");
                errors = true;
            }

            String width = request.getParameter("width");
            if (width == null) {
                messageHelper.addMessage("Invalid width.");
                errors = true;
            }

            String height = request.getParameter("height");
            if (height == null) {
                messageHelper.addMessage("Invalid height.");
                errors = true;
            }

            int frame = 0;
            try {
                frame = Integer.parseInt(request.getParameter("frame"));
            } catch (NumberFormatException e) {
                messageHelper.addMessage("Invalid frame.");
                errors = true;
            }

            int glass = 0;
            try {
                glass = Integer.parseInt(request.getParameter("glass"));
            } catch (NumberFormatException e) {
                messageHelper.addMessage("Invalid glass.");
                errors = true;
            }

            if (errors == false) {
                request.setAttribute("fillMessurement", messurement);
                request.setAttribute("fillWidth", width);
                request.setAttribute("fillHeight", height);
                request.setAttribute("fillFrame", frame);
                request.setAttribute("fillGlass", glass);
            }
        }
        
        MysqlDataSource source = new DefaultMysqlSource();
        GlassDAO glassDAO = new MysqlGlassDAO(source);
        FrameDAO frameDAO = new MysqlFrameDAO(source);
        request.setAttribute("glasses", glassDAO.getGlasses());
        request.setAttribute("frames", frameDAO.getFrames());

        response.setContentType("text/html;charset=UTF-8");
        request.setAttribute("title", "Order products");
        request.getRequestDispatcher("WEB-INF/order-product-template.jsp").forward(request, response);
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

        MessageHelper messageHelper = new MessageHelper(request);
        
        try {
           

            OrderBuilder orderBuilder = new OrderBuilder();

            MysqlDataSource source = DefaultMysqlSource.getSource();
            OrderDAO orderDAO = new MysqlOrderDAO(source);
            
            boolean errors = false;

            try {
                int frameId = Integer.parseInt(request.getParameter("frame"));
                if (!orderBuilder.setFrame(FrameReference.of(frameId))) {
                    messageHelper.addMessage("Incorrect frame.");
                    errors = true;
                }
            } catch (NumberFormatException e) {
                messageHelper.addMessage("Malformed frame id.");
                errors = true;
            }

            try {
                int glassId = Integer.parseInt(request.getParameter("glass"));
                if (!orderBuilder.setGlass(GlassReference.of(glassId))) {
                    messageHelper.addMessage("Incorrect glass.");
                    errors = true;
                }
            } catch (NumberFormatException e) {
                messageHelper.addMessage("Malformed glass id.");
                errors = true;
            }

            try {
                int width = Integer.parseInt(request.getParameter("width"));
                if (!orderBuilder.setWidthMillimeters(width)) {
                    messageHelper.addMessage("Invalid width.");
                    errors = true;
                }
            } catch (NumberFormatException e) {
                messageHelper.addMessage("Malformed width");
                errors = true;
            }

            try {
                int height = Integer.parseInt(request.getParameter("height"));
                if (!orderBuilder.setHeightMillimeters(height)) {
                    messageHelper.addMessage("Invalid height.");
                    errors = true;
                }
            } catch (NumberFormatException e) {
                messageHelper.addMessage("Malformed height");
                errors = true;
            }

            if (!orderBuilder.setCustomerName(request.getParameter("customer_name"))) {
                messageHelper.addMessage("Invalid customer name.");
                errors = true;
            }

            if (!orderBuilder.setCustomerAddress(request.getParameter("customer_address"))) {
                messageHelper.addMessage("Invalid customer address.");
                errors = true;
            }

            if (!orderBuilder.setCustomerZip(request.getParameter("customer_zip"))) {
                messageHelper.addMessage("Invalid customer zip.");
                errors = true;
            }

            if (!orderBuilder.setCustomerCity(request.getParameter("customer_city"))) {
                messageHelper.addMessage("Invalid customer city.");
                errors = true;
            }

            if (!orderBuilder.setCustomerEmail(request.getParameter("customer_email"))) {
                messageHelper.addMessage("Invalid customer email.");
                errors = true;
            }

            if (!orderBuilder.setCustomerPhone(request.getParameter("customer_phone"))) {
                messageHelper.addMessage("Invalid customer phone.");
                errors = true;
            }

            if (errors) {
                response.sendRedirect("order-product");
                return;
            }

            Order order = orderDAO.insertOrder(orderBuilder);
            messageHelper.addMessage("Order with id " + order.getID() + " was successfully created.");
            response.sendRedirect("index");
        } catch (UnknownFrameReferenceException e) {
            messageHelper.addMessage("Unknown frame id when attempting to create order. No order was created.");
            response.sendRedirect("order-product");
        } catch (UnknownGlassReferenceException e) {
            messageHelper.addMessage("Unknown glass id when attempting to create order. No order was created.");
            response.sendRedirect("order-product");
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
