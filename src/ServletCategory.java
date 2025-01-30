import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/categories")
public class ServletCategory extends HttpServlet {

	
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	            throws ServletException, IOException {
	        response.setContentType("text/html");
	        response.setCharacterEncoding("UTF-8");
	        try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
	        try (Connection conn = DatabaseConnection.getConnection()) {
	            String sql = "SELECT * FROM categories";
	            PreparedStatement stmt = conn.prepareStatement(sql);
	            ResultSet rs = stmt.executeQuery();
	            PrintWriter out = response.getWriter();
	            out.print("<html><body>");

	            while (rs.next()) {
	                int categoryId = rs.getInt("category_id");
	                String categoryName = rs.getString("category_name");

	                // Créer le lien HTML pour chaque catégorie
	                out.print("<p><a href=\"http://localhost:8080/ton-projet/categories/" + categoryId + "\">" + categoryName + "</a></p>");
	            }
	            out.print("</body></html>");
	            out.flush();
	           
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    
	}
}

