package tvestergaard.glazier.servelets;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.io.IOException;
import java.math.BigDecimal;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tvestergaard.glazier.IllegalDimensionsException;
import tvestergaard.glazier.MessageHelper;
import tvestergaard.glazier.PriceCalculator;
import tvestergaard.glazier.database.DefaultMysqlSource;
import tvestergaard.glazier.database.frames.Frame;
import tvestergaard.glazier.database.frames.FrameDAO;
import tvestergaard.glazier.database.frames.FrameReference;
import tvestergaard.glazier.database.frames.MysqlFrameDAO;
import tvestergaard.glazier.database.frames.UnknownFrameReferenceException;
import tvestergaard.glazier.database.glass.Glass;
import tvestergaard.glazier.database.glass.GlassDAO;
import tvestergaard.glazier.database.glass.GlassReference;
import tvestergaard.glazier.database.glass.MysqlGlassDAO;
import tvestergaard.glazier.database.glass.UnknownGlassReferenceException;

@WebServlet(name = "CalculateServelet", urlPatterns = {"/calculate-price"})
public class CalculatePriceServlet extends HttpServlet {

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

        if (request.getParameter("submit") != null) {
            handleSubmit(request, response);
            return;
        }

        request.setAttribute("title", "Calculate product price");
        MysqlDataSource source = new DefaultMysqlSource();
        GlassDAO glassDAO = new MysqlGlassDAO(source);
        FrameDAO frameDAO = new MysqlFrameDAO(source);
        request.setAttribute("glasses", glassDAO.getGlasses());
        request.setAttribute("frames", frameDAO.getFrames());
        request.getRequestDispatcher("/WEB-INF/calculate-price-template.jsp").forward(request, response);
    }

    /**
     * Handles the calculate price form submit.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void handleSubmit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        MessageHelper messageHelper = new MessageHelper(request);

        boolean errors = false;

        String messurement = request.getParameter("messurement");
        if (!messurement.equals("mm") && !messurement.equals("cm") && !messurement.equals("m")) {
            messageHelper.addMessage("Invalid messurement.");
            errors = true;
        }

        int width = 0;
        try {
            width = Integer.parseInt(request.getParameter("width"));
            if (width <= 0) {
                messageHelper.addMessage("Width must be positive.");
                errors = true;
            }
        } catch (NumberFormatException e) {
            messageHelper.addMessage("Malformed width.");
            errors = true;
        }

        int height = 0;
        try {
            height = Integer.parseInt(request.getParameter("height"));
            if (height <= 0) {
                messageHelper.addMessage("Height must be positive.");
                errors = true;
            }
        } catch (NumberFormatException | NullPointerException e) {
            messageHelper.addMessage("Malformed height.");
            errors = true;
        }

        int glassID = 0;
        try {
            glassID = Integer.parseInt(request.getParameter("glass"));
        } catch (NumberFormatException e) {
            messageHelper.addMessage("Malformed glass.");
            errors = true;
        }

        int frameID = 0;
        try {
            frameID = Integer.parseInt(request.getParameter("frame"));
        } catch (NumberFormatException e) {
            messageHelper.addMessage("Malformed frame.");
            errors = true;
        }

        if (errors) {
            response.sendRedirect("calculate-price");
            return;
        }

        Frame frame = null;
        try {
            FrameDAO frameDAO = new MysqlFrameDAO(source);
            frame = frameDAO.getFrame(FrameReference.of(frameID));
        } catch (UnknownFrameReferenceException e) {
            messageHelper.addMessage("Unknown frame id.");
            errors = true;
        }

        Glass glass = null;
        try {
            GlassDAO glassDAO = new MysqlGlassDAO(source);
            glass = glassDAO.getGlass(GlassReference.of(glassID));
        } catch (UnknownGlassReferenceException e) {
            messageHelper.addMessage("Unknown glass id.");
            errors = true;
        }

        PriceCalculator calculator = new PriceCalculator();

        int widthMillimeters = width;
        int heightMillimeters = height;

        if (messurement.equals("cm")) {
            widthMillimeters *= 10;
            heightMillimeters *= 10;
        }

        if (messurement.equals("m")) {
            widthMillimeters *= 1000;
            heightMillimeters *= 1000;
        }

        try {

            request.setAttribute("result", calculator.calculatePrice(frame, glass, BigDecimal.valueOf(widthMillimeters), BigDecimal.valueOf(heightMillimeters)).setScale(2).toString());
            request.setAttribute("messurement", messurement);
            request.setAttribute("width", width);
            request.setAttribute("height", height);
            request.setAttribute("glass", glassID);
            request.setAttribute("frame", frameID);
            request.setAttribute("title", "Calculate product price result.");
            request.getRequestDispatcher("WEB-INF/calculate-price-result-template.jsp").forward(request, response);

        } catch (IllegalDimensionsException e) {
            messageHelper.addMessage("Illegal dimensions.");
            response.sendRedirect("calculate-price");
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
