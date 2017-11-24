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
import tvestergaard.glazier.database.orders.OrderReference;
import tvestergaard.glazier.database.orders.UnknownOrderReferenceException;

@WebServlet(name = "OrdersServlet", urlPatterns = {"/administrator/orders"})
public class OrdersServlet extends HttpServlet {

    /**
     * The {@link MysqlDataSource} used to save changes made by the
     * administrator.
     */
    private final MysqlDataSource source = new DefaultMysqlSource();

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
            handleCreateGet(request, response, messageHelper);
            return;
        }

        String queryId = request.getParameter("id");

        if (queryId == null) {
            request.setAttribute("title", "Orders");
            OrderDAO orderDAO = new MysqlOrderDAO(source);
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
     * Serves the page where the administrator can create new {@link Order}s.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void handleCreateGet(HttpServletRequest request, HttpServletResponse response, MessageHelper messageHelper)
            throws ServletException, IOException {
        request.setAttribute("title", "Create order");
        MysqlDataSource source = new DefaultMysqlSource();
        GlassDAO glassDAO = new MysqlGlassDAO(source);
        FrameDAO frameDAO = new MysqlFrameDAO(source);
        request.setAttribute("glasses", glassDAO.getGlasses());
        request.setAttribute("frames", frameDAO.getFrames());
        request.getRequestDispatcher("/WEB-INF/administrator/order-create-template.jsp").forward(request, response);
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

        messageHelper.addMessage("Unknown action when posting to /orders.");
        response.sendRedirect("glasses");
    }

    /**
     * Deletes a {@link Order} after pressing the <code>DELETE</code> button.
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
            messageHelper.addMessage("Missing order id when attempting to delete order. No order was deleted.");
            response.sendRedirect("orders");
            return;
        }

        try {
            int id = Integer.parseInt(queryID);
            OrderDAO orderDAO = new MysqlOrderDAO(source);
            orderDAO.deleteOrder(OrderReference.of(id));
            messageHelper.addMessage("The order was successfully deleted.");
        } catch (NumberFormatException e) {
            messageHelper.addMessage("Malformed id when attempting to delete order. No order was deleted.");
        } catch (UnknownOrderReferenceException e) {
            messageHelper.addMessage("Unknown id when attempting to delete order. No order was deleted.");
        } finally {
            response.sendRedirect("orders");
        }
    }

    /**
     * Updates a {@link Order} after pressing the <code>UPDATE</code> button.
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
            messageHelper.addMessage("Missing order id when attempting to update order. No order was updated.");
            response.sendRedirect("orders");
            return;
        }

        try {
            int id = Integer.parseInt(queryID);
            OrderDAO orderDAO = new MysqlOrderDAO(source);

            OrderReference reference = OrderReference.of(id);
            Order order = orderDAO.getOrder(reference);

            boolean errors = false;

            FrameDAO frameDAO = new MysqlFrameDAO(source);
            try {
                int frameId = Integer.parseInt(request.getParameter("frame"));
                if (!order.setFrame(frameDAO.getFrame(FrameReference.of(frameId)))) {
                    messageHelper.addMessage("Incorrect frame.");
                    errors = true;
                }
            } catch (NumberFormatException e) {
                messageHelper.addMessage("Malformed frame id.");
                errors = true;
            } catch (UnknownFrameReferenceException e) {
                messageHelper.addMessage("Unknown frame id.");
                errors = true;
            }

            GlassDAO glassDAO = new MysqlGlassDAO(source);
            try {
                int glassId = Integer.parseInt(request.getParameter("glass"));
                if (!order.setGlass(glassDAO.getGlass(GlassReference.of(glassId)))) {
                    messageHelper.addMessage("Incorrect glass.");
                    errors = true;
                }
            } catch (NumberFormatException e) {
                messageHelper.addMessage("Malformed glass id.");
                errors = true;
            } catch (UnknownGlassReferenceException e) {
                messageHelper.addMessage("Unknown glass id.");
                errors = true;
            }

            try {
                int width = Integer.parseInt(request.getParameter("width"));
                if (!order.setWidthMillimeters(width)) {
                    messageHelper.addMessage("Invalid width.");
                    errors = true;
                }
            } catch (NumberFormatException e) {
                messageHelper.addMessage("Malformed width");
                errors = true;
            }

            try {
                int height = Integer.parseInt(request.getParameter("height"));
                if (!order.setHeightMillimeters(height)) {
                    messageHelper.addMessage("Invalid height.");
                    errors = true;
                }
            } catch (NumberFormatException e) {
                messageHelper.addMessage("Malformed height");
                errors = true;
            }

            if (!order.setCustomerName(request.getParameter("customer_name"))) {
                messageHelper.addMessage("Invalid customer name.");
                errors = true;
            }

            if (!order.setCustomerAddress(request.getParameter("customer_address"))) {
                messageHelper.addMessage("Invalid customer address.");
                errors = true;
            }

            if (!order.setCustomerZip(request.getParameter("customer_zip"))) {
                messageHelper.addMessage("Invalid customer zip.");
                errors = true;
            }

            if (!order.setCustomerCity(request.getParameter("customer_city"))) {
                messageHelper.addMessage("Invalid customer city.");
                errors = true;
            }

            if (!order.setCustomerEmail(request.getParameter("customer_email"))) {
                messageHelper.addMessage("Invalid customer email.");
                errors = true;
            }

            if (!order.setCustomerPhone(request.getParameter("customer_phone"))) {
                messageHelper.addMessage("Invalid customer phone.");
                errors = true;
            }

            if (errors) {
                response.sendRedirect("orders?id=" + id);
                return;
            }

            orderDAO.updateOrder(order);
            messageHelper.addMessage("The order was successfully updated.");
            response.sendRedirect("orders?id=" + id);
        } catch (NumberFormatException e) {
            messageHelper.addMessage("Malformed id when attempting to update order. No order was updated.");
            response.sendRedirect("orders");
        } catch (UnknownOrderReferenceException e) {
            messageHelper.addMessage("Unknown id when attempting to delete order. No order was deleted.");
            response.sendRedirect("orders");
        }
    }

    private void handleCreatePost(HttpServletRequest request, HttpServletResponse response, MessageHelper messageHelper)
            throws ServletException, IOException {

        try {

            OrderBuilder orderBuilder = new OrderBuilder();
            OrderDAO orderDAO = new MysqlOrderDAO(source);
            FrameDAO frameDAO = new MysqlFrameDAO(source);
            GlassDAO glassDAO = new MysqlGlassDAO(source);

            boolean errors = false;

            String messurement = request.getParameter("messurement");
            if (messurement == null || (!messurement.equals("m") && !messurement.equals("cm") && !messurement.equals("mm"))) {
                messageHelper.addMessage("Malformed messurement.");
                errors = true;
            }

            try {
                if (!orderBuilder.setWidthMillimeters(Integer.parseInt(request.getParameter("width")))) {
                    messageHelper.addMessage("Invalid width.");
                    errors = true;
                }
            } catch (NumberFormatException e) {
                messageHelper.addMessage("Malformed width");
                errors = true;
            }

            try {
                if (!orderBuilder.setHeightMillimeters(Integer.parseInt(request.getParameter("height")))) {
                    messageHelper.addMessage("Invalid height.");
                    errors = true;
                }
            } catch (NumberFormatException e) {
                messageHelper.addMessage("Malformed height");
                errors = true;
            }

            try {
                int frameID = Integer.parseInt(request.getParameter("frame"));
                if (!orderBuilder.setFrame(frameDAO.getFrame(FrameReference.of(frameID)))) {
                    messageHelper.addMessage("Incorrect frame.");
                    errors = true;
                }
            } catch (NumberFormatException e) {
                messageHelper.addMessage("Malformed frame id.");
                errors = true;
            }

            try {
                int glassID = Integer.parseInt(request.getParameter("glass"));
                if (!orderBuilder.setGlass(glassDAO.getGlass(GlassReference.of(glassID)))) {
                    messageHelper.addMessage("Incorrect glass.");
                    errors = true;
                }
            } catch (NumberFormatException e) {
                messageHelper.addMessage("Malformed glass id.");
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
                response.sendRedirect("orders?action=create");
                return;
            }

            int width = orderBuilder.getWidthMillimeters();
            int height = orderBuilder.getHeightMillimeters();

            if (messurement.equals("m")) {
                orderBuilder.setWidthMillimeters(width * 1000);
                orderBuilder.setHeightMillimeters(height * 1000);
            }

            if (messurement.equals("cm")) {
                orderBuilder.setWidthMillimeters(width * 10);
                orderBuilder.setHeightMillimeters(height * 10);
            }

            Order order = orderDAO.insertOrder(orderBuilder);
            messageHelper.addMessage("The order was successfully created.");
            response.sendRedirect("orders?id=" + order.getID());
        } catch (UnknownFrameReferenceException e) {
            messageHelper.addMessage("Unknown frame id when attempting to create order. No order was created.");
            response.sendRedirect("orders");
        } catch (UnknownGlassReferenceException e) {
            messageHelper.addMessage("Unknown glass id when attempting to create order. No order was created.");
            response.sendRedirect("orders");
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
