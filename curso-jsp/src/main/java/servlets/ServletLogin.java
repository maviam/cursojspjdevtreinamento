package servlets;

import java.io.IOException;

import dao.DAOLoginRepository;
import dao.DAOUserRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.ModelLogin;
import jakarta.servlet.annotation.WebServlet;

/*  
 * URL mapping passed by the action method by the form of index.html
 * or by a redirection like http://localhost/curso-jsp/principal/principal.jsp
 */
@WebServlet(urlPatterns = {"/principal/ServletLogin","/servlets.ServletLogin"})
public class ServletLogin extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	// Object used to test if the login occurs successfully
	private DAOLoginRepository loginDB = new DAOLoginRepository();
	// Object used to represents an user that receive all methods 
	private DAOUserRepository userDB = new DAOUserRepository();
	
	// Constructor of the class
    public ServletLogin() { }

    /* Receive data via URL through parameters */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		
		if (acao.equalsIgnoreCase("logout")) {
			request.getSession().invalidate(); // Close the session
			RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		else {
			doPost(request,response);
		}
	}

	/* Receive data via URL through form data  */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// "username", "pass", "url" were id's from form controls
		String username = request.getParameter("username"); 
		String password = request.getParameter("pass");
		String url = request.getParameter("url");
		
		// Use of "try" because it will be need to access the database
		try {
			// if the user input an username and a password ...
			if (username != null && !username.isEmpty() && password != null && !password.isEmpty() ) {
				// Create an object of class ModelLogin that represents the table "dbmodellogin" from database
				ModelLogin mdlLgn = new ModelLogin();
				
				mdlLgn.setUsername(username);
				mdlLgn.setPassword(password);
				
				// Test if the login occurs successfully
				if (loginDB.validarAutenticacao(mdlLgn)) {
					mdlLgn = userDB.searchAuthenticatedUserDataForm(username);
					// Open a new session to the authenticated user ...
					// Define an attribute to represents the "username" of the login
					request.getSession().setAttribute("utilizadorAtivo", mdlLgn.getUsername());
					
					// Define an attribute that indicates if the user is an admin or not
					request.getSession().setAttribute("perfil", mdlLgn.getPerfil());
					// If the user access directly the page index.jsp ...
					// In other words, the URL have the value "null"
					if (url == null || url.equals("null")) {
						url = "/principal/principal.jsp";
					}
					
					// Redirect to the page set in the url variable
					RequestDispatcher rdp = request.getRequestDispatcher(url);
					rdp.forward(request, response);
				}
				else {
					// If the login failed, redirects to the page index.jsp and show the message presents below
					RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
					request.setAttribute("msg","Utilizador e/ou password inválido(s)!");
					rd.forward(request, response);
				}
			}
			else { // if the user failed to input an username or/and a password ...
				// executes a redirect to the page index.jsp
				RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
				request.setAttribute("msg","Preencha os campos relativos ao utilizador e a password");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// Execute a redirect to the page erro.jsp
			RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
			request.setAttribute("msg",e.getMessage());
			rd.forward(request, response);
		}
	}

}
