package servlets;

import java.io.Serializable;
import java.sql.SQLException;

import dao.DAOUserRepository;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class ServletGenericUtil extends HttpServlet implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private DAOUserRepository userDB = new DAOUserRepository();
	
	// This method returns the user authenticated in the session
	// In other words, returns the user that do the login
	public Long getUtilizadorAtivo(HttpServletRequest request) throws SQLException {
		HttpSession session = request.getSession();
		String utilizadorAtivo = (String) session.getAttribute("utilizadorAtivo");
		return userDB.searchAuthenticatedUserDataForm(utilizadorAtivo).getId();
	}

}
