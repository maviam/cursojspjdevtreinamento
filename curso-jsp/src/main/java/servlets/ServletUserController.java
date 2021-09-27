package servlets;

import java.io.IOException;
import java.util.List;

import org.apache.tomcat.jakartaee.commons.compress.utils.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOUserRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import models.ModelLogin;

@MultipartConfig
@WebServlet("/servlets.ServletUserController")
public class ServletUserController extends ServletGenericUtil {
	
	private static final long serialVersionUID = 1L;
	
	// Object used to execute all DAO methods of a user (create, save, delete)
	private DAOUserRepository userDB = new DAOUserRepository();
	
    public ServletUserController() { }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String msg = "";
		
		try {
			String acao = request.getParameter("acao");
		
			if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("eliminar")) {
				String idUser = request.getParameter("id");
				if (idUser == null || idUser == "") {
					msg = "Nenhum utilizador selecionado!";
				}
				else {
					msg = "Utilizador com id " + idUser + " eliminado com sucesso!";
					userDB.deleteUser(Long.parseLong(idUser));
				}
				
				// The next two lines will permit that the data in the table still visible
				List<ModelLogin> usersList = userDB.listUsers(super.getUtilizadorAtivo(request));
				request.setAttribute("listarutilizadores",usersList);
				
				// Show a message indicates that the save action occurs successfully or not
				request.setAttribute("msg",msg);
				// Redirect to the same page and hold the information
				RequestDispatcher rd = request.getRequestDispatcher("principal/cadastro-utilizador.jsp");
				rd.forward(request,response);
			}
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("eliminarajax")) {
				String idUser = request.getParameter("id");
				if (idUser == null || idUser == "") {
					msg = "Nenhum utilizador selecionado!";
				}
				else {
					msg = "Utilizador eliminado com sucesso!";
					userDB.deleteUser(Long.parseLong(idUser));
				}
				// Show a message indicates that the save action occurs successfully or not
				response.getWriter().write(msg);
			}
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("pesquisarutilizadorajax")) {
				String resultSearch = request.getParameter("nameToSearch");
				List<ModelLogin> usersJsonFoundList = userDB.searchUsers(resultSearch,super.getUtilizadorAtivo(request));
				
				// Json use to show data
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(usersJsonFoundList);
				response.getWriter().write(json);
			}
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("editarutilizador")) {
				String id = request.getParameter("id");
				ModelLogin userFound = userDB.fillDataFormById(id,super.getUtilizadorAtivo(request));
				
				// The next two lines will permit that the data in the table still visible
				List<ModelLogin> usersList = userDB.listUsers(super.getUtilizadorAtivo(request));
				request.setAttribute("listarutilizadores",usersList);
				
				// Attribute 'dados' represents the information save in userFound to be use
				// in the value attribute of the form that save user's information
				request.setAttribute("dados",userFound);
			
				// Redirect to the same page and hold the information
				request.getRequestDispatcher("principal/cadastro-utilizador.jsp").forward(request,response);
			}
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarutilizadores")) {
				List<ModelLogin> usersList = userDB.listUsers(super.getUtilizadorAtivo(request));
				
				request.setAttribute("msg","Lista de utilizadores carregada com sucesso!");
				
				// Attribute 'listarutilizadores' represents the information save in usersList to be use
				// to show all users in the database
				request.setAttribute("listarutilizadores",usersList);
			
				// Redirect to the same page and hold the information
				RequestDispatcher rd = request.getRequestDispatcher("principal/cadastro-utilizador.jsp");
				rd.forward(request,response);
			}
			else {
				// The next two lines will permit that the data in the table still visible
				List<ModelLogin> usersList = userDB.listUsers(super.getUtilizadorAtivo(request));
				request.setAttribute("listarutilizadores",usersList);
				
				// Redirect to the same page and hold the information
				RequestDispatcher rd = request.getRequestDispatcher("principal/cadastro-utilizador.jsp");
				rd.forward(request,response);
			}	
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
			request.setAttribute("msg",e.getMessage());
			rd.forward(request,response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String msg = "";
			
			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String perfil = request.getParameter("perfil");
			String sexo = request.getParameter("sexo");
			
			ModelLogin mdlLgn = new ModelLogin();
		
			mdlLgn.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			mdlLgn.setNome(nome);
			mdlLgn.setEmail(email);
			mdlLgn.setUsername(username);
			mdlLgn.setPassword(password);
			mdlLgn.setPerfil(perfil);
			mdlLgn.setSexo(sexo);
			
			// To accepted the image load
			if (ServletFileUpload.isMultipartContent(request)) {
				Part photo = request.getPart("filephoto"); // Put the path of the photo inside the object photo
				
				if (photo.getSize() > 0) {
					byte[] conversor = IOUtils.toByteArray(photo.getInputStream()); // Convert the image to byte
					String pathToPhoto = "data:image/" + photo.getContentType().split("\\/")[1] + ";base64," + new Base64().encodeBase64String(conversor); // Convert to a String in basis64
				
					mdlLgn.setPhotoUser(pathToPhoto);
					mdlLgn.setExtensionPhotoUser(photo.getContentType().split("\\/")[1]);
				}
			}
		
			// If the username exist in the database and the id is null ..
			if (userDB.usernameExists(mdlLgn.getUsername()) && mdlLgn.getId() == null) {
				msg = "Escolha outro username pois este já foi escolhido!";
			}
			else {
				if (mdlLgn.isNewUser()) {
					msg = "Utilizador inserido com sucesso!";
				}
				else {
					msg = "Dados do utilizador atualizados com sucesso!";
				}
				// Save user data in the database
				mdlLgn = userDB.saveUser(mdlLgn,super.getUtilizadorAtivo(request));
			}
			
			// The next two lines will permit that the data in the table still visible
			List<ModelLogin> usersList = userDB.listUsers(super.getUtilizadorAtivo(request));
			request.setAttribute("listarutilizadores",usersList);
			
			// Show a message indicates that the save action occurs successfully or not
			request.setAttribute("msg",msg);
			
			// Attribute 'dados' represents the information save in mdlLgn to be use
			// in the value attribute of the form that save user's information
			request.setAttribute("dados",mdlLgn);
		
			// Redirect to the same page and hold the information
			RequestDispatcher rd = request.getRequestDispatcher("principal/cadastro-utilizador.jsp");
			rd.forward(request,response);
		} catch (Exception e) {
			e.printStackTrace();
			// Execute a redirect to the page erro.jsp
			RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
			request.setAttribute("msg",e.getMessage());
			rd.forward(request, response);
		}
	}

}
