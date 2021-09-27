package filters;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import connection.SingleConnection;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/* It intercepts all requests that come from the project or mapping. 
 * In this specific case, intercept all requests done from the pages
 * put inside the main ("principal") folder
*/
@WebFilter(urlPatterns = {"/principal/*"}) 
public class FilterAutenticacao implements Filter {

	private static Connection con;
	
    public FilterAutenticacao() {}

    // Terminates processes when server is stopped
    // Example: Terminate the connection to the database
	public void destroy() {
		// Terminate the connection to the database
		try { 
			con.close(); 
		} catch (SQLException e) { 
			e.printStackTrace(); 
		}
	}

	// Intercept all requests and send responses to each request
	// Everything that is executed in the system pass through this method
	// At this specific case executed inside the "principal" folder
	// Example1: Validation of authentication
	// Example2: Apply commit and rollback to the transactions at the database
	// Example3: Apply redirects
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			String utilizadorAtivo = (String) session.getAttribute("utilizadorAtivo");
			String urlAutenticar = req.getServletPath(); /* Url that is been accessed */
		
			/* Validates if the login occured with success else redirects to the page index.jsp */
			if ((utilizadorAtivo == null || (utilizadorAtivo != null && utilizadorAtivo.isEmpty()))
			&& !(urlAutenticar.contains("/principal/ServletLogin"))) {
				RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?url=" + urlAutenticar);
				request.setAttribute("msg","Por favor, realize o login!");
				rd.forward(request,response);
				return; // Stop the execution and returns to the system login
			}
			else { // Login successfully
				// Pass the request along the filter chain
				// In other words, open the requested page
				chain.doFilter(request, response);
			}
			
			// If everything occurs well ...
			con.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			// Executes a redirect to the page erro.jsp
			RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
			request.setAttribute("msg",e.getMessage());
			rd.forward(request, response);
			
			try {
				con.rollback(); // Reset all steps of the transaction
			} catch (SQLException e1) {
				e1.printStackTrace();
			} 
		}
	}

	// Start all processes or recurses when the server "start" the project
	// Example: Start the connection to the database
	public void init(FilterConfig fConfig) throws ServletException {
		// Start the connection to the database
		con = SingleConnection.getConnection();
	}

}
