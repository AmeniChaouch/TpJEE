import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/categories/*")
public class ServletProduct extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Extraire l'ID de la catégorie de l'URL
        String pathInfo = request.getPathInfo(); // Donne "/id"
        if (pathInfo != null && pathInfo.length() > 1) {
            String categoryId = pathInfo.substring(1); // Enlève le "/" pour obtenir "id"
            
            // Utiliser categoryId pour interroger la base de données
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "SELECT * FROM product WHERE category_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, Integer.parseInt(categoryId));
                ResultSet rs = stmt.executeQuery();

                response.setContentType("text/html");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.print("<html><body>");

                while (rs.next()) {
                    String productName = rs.getString("product_name");
                    out.print("<p>" + productName + "</p>");
                }
                out.print("</body></html>");
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de catégorie manquant");
        }
    }
}