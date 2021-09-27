package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnection;
import models.ModelLogin;

public class DAOUserRepository {
	
	private Connection con = SingleConnection.getConnection();
	
	public DAOUserRepository() { };
	
	public ModelLogin saveUser(ModelLogin mdlLgn, Long userAuthenticated) throws SQLException {
		
		if (mdlLgn.isNewUser()) { // Insert a new user
			String sql = "INSERT INTO dbmodellogin(nome,email,username,password,userid,perfil,sexo) "
							+ "VALUES (?,?,?,?,?,?,?)";
			PreparedStatement pstm = con.prepareStatement(sql);
			
			pstm.setString(1,mdlLgn.getNome());
			pstm.setString(2,mdlLgn.getEmail());
			pstm.setString(3,mdlLgn.getUsername());
			pstm.setString(4,mdlLgn.getPassword());
			pstm.setLong(5,userAuthenticated);
			pstm.setString(6,mdlLgn.getPerfil());
			pstm.setString(7,mdlLgn.getSexo());
			
			pstm.execute();
			
			// Save all steps from the current transaction
			con.commit();
			
			if (mdlLgn.getPhotoUser() != null && !mdlLgn.getPhotoUser().isEmpty()) {
				sql = "UPDATE dbmodellogin SET photouser = ?, extensionphotouser = ? WHERE username = ?";
				pstm = con.prepareStatement(sql);
				
				pstm.setString(1,mdlLgn.getPhotoUser());
				pstm.setString(2,mdlLgn.getExtensionPhotoUser());
				pstm.setString(3,mdlLgn.getUsername());
				
				pstm.execute();
				
				con.commit();
			}
		}
		else { // Update an user already insert in the database
			String sql = "UPDATE dbmodellogin SET nome = ?, email = ?, "
							+ "username = ?, password = ?, perfil = ?, sexo = ? "
							+ "WHERE id = " + mdlLgn.getId() + ";";
			PreparedStatement pstm = con.prepareStatement(sql);
			
			pstm.setString(1,mdlLgn.getNome());
			pstm.setString(2,mdlLgn.getEmail());
			pstm.setString(3,mdlLgn.getUsername());
			pstm.setString(4,mdlLgn.getPassword());
			pstm.setString(5,mdlLgn.getPerfil());
			pstm.setString(6,mdlLgn.getSexo());
			
			pstm.executeUpdate();
			
			// Save all steps from the current transaction
			con.commit();
			
			if (mdlLgn.getPhotoUser() != null && !mdlLgn.getPhotoUser().isEmpty()) {
				sql = "UPDATE dbmodellogin SET photouser = ?, extensionphotouser = ? WHERE id = ?";
				pstm = con.prepareStatement(sql);
				
				pstm.setString(1,mdlLgn.getPhotoUser());
				pstm.setString(2,mdlLgn.getExtensionPhotoUser());
				pstm.setLong(3,mdlLgn.getId());
				
				pstm.executeUpdate();
				
				con.commit();
			}
		}
		
		// Returns the values entered for the form with the id filled in
		return this.fillDataForm(mdlLgn.getUsername(),userAuthenticated);
	}
	
	public void deleteUser(Long id) throws Exception {
		String sql = "DELETE FROM dbmodellogin WHERE id = " + id + " AND useradmin IS false";
		PreparedStatement pstm = con.prepareStatement(sql);
		
		pstm.execute();
		
		con.commit();
	}
	
	public ModelLogin searchUserDataForm(String username) throws SQLException {
		ModelLogin mdlLgn = new ModelLogin();
		
		String sql = "SELECT * FROM dbmodellogin "
					 + "WHERE upper(username) = upper('" + username + "')" 
					 + " AND useradmin IS false";
		PreparedStatement pstm = con.prepareStatement(sql);

		ResultSet userFound = pstm.executeQuery();
		
		if (userFound.next()) { // If exists an user with the username passed ...
			mdlLgn.setId(userFound.getLong("id"));
			mdlLgn.setNome(userFound.getString("nome"));
			mdlLgn.setEmail(userFound.getString("email"));
			mdlLgn.setUsername(userFound.getString("username"));
			mdlLgn.setPassword(userFound.getString("password"));
			mdlLgn.setUserAdmin(userFound.getBoolean("useradmin"));
			mdlLgn.setPerfil(userFound.getString("perfil"));
			mdlLgn.setSexo(userFound.getString("sexo"));
			mdlLgn.setPhotoUser(userFound.getString("photouser"));
		}
		
		return mdlLgn;
	}
	
	public ModelLogin searchAuthenticatedUserDataForm(String username) throws SQLException {
		ModelLogin mdlLgn = new ModelLogin();
		
		String sql = "SELECT * FROM dbmodellogin "
					 + "WHERE upper(username) = upper('" + username + "')";
		PreparedStatement pstm = con.prepareStatement(sql);

		ResultSet userFound = pstm.executeQuery();
		
		if (userFound.next()) { // If exists an user with the username passed ...
			mdlLgn.setId(userFound.getLong("id"));
			mdlLgn.setNome(userFound.getString("nome"));
			mdlLgn.setEmail(userFound.getString("email"));
			mdlLgn.setUsername(userFound.getString("username"));
			mdlLgn.setPassword(userFound.getString("password"));
			mdlLgn.setUserAdmin(userFound.getBoolean("useradmin"));
			mdlLgn.setPerfil(userFound.getString("perfil"));
			mdlLgn.setSexo(userFound.getString("sexo"));
			mdlLgn.setPhotoUser(userFound.getString("photouser"));
		}
		
		return mdlLgn;
	}
	
 	public ModelLogin fillDataForm(String username,Long userAuthenticated) throws SQLException {
		ModelLogin mdlLgn = new ModelLogin();
		
		String sql = "SELECT * FROM dbmodellogin "
					 + "WHERE upper(username) = upper('" + username + "')" 
					 + " AND useradmin IS false AND userid = " + userAuthenticated;
		PreparedStatement pstm = con.prepareStatement(sql);

		ResultSet userFound = pstm.executeQuery();
		
		if (userFound.next()) { // If exists an user with the username passed ...
			mdlLgn.setId(userFound.getLong("id"));
			mdlLgn.setNome(userFound.getString("nome"));
			mdlLgn.setEmail(userFound.getString("email"));
			mdlLgn.setUsername(userFound.getString("username"));
			mdlLgn.setPassword(userFound.getString("password"));
			mdlLgn.setPerfil(userFound.getString("perfil"));
			mdlLgn.setSexo(userFound.getString("sexo"));
			mdlLgn.setPhotoUser(userFound.getString("photouser"));
		}
		
		return mdlLgn;
	}
 	
 	public ModelLogin fillDataFormById(String id, Long userAuthenticated) throws SQLException {
		ModelLogin mdlLgn = new ModelLogin();
		
		String sql = "SELECT * FROM dbmodellogin "
					 + "WHERE id = " + Long.parseLong(id) + " AND useradmin IS false AND userid = " + userAuthenticated;
		PreparedStatement pstm = con.prepareStatement(sql);

		ResultSet userFound = pstm.executeQuery();
		
		if (userFound.next()) { // If exists an user with the id passed ...
			mdlLgn.setId(userFound.getLong("id"));
			mdlLgn.setNome(userFound.getString("nome"));
			mdlLgn.setEmail(userFound.getString("email"));
			mdlLgn.setUsername(userFound.getString("username"));
			mdlLgn.setPassword(userFound.getString("password"));
			mdlLgn.setPerfil(userFound.getString("perfil"));
			mdlLgn.setSexo(userFound.getString("sexo"));
			mdlLgn.setPhotoUser(userFound.getString("photouser"));
			mdlLgn.setExtensionPhotoUser(userFound.getString("extensionphotouser"));
		}
		
		return mdlLgn;
	}
 	
 	public List<ModelLogin> searchUsers(String userToSearch,Long userAuthenticated) throws Exception {
 		
 		List<ModelLogin> usersList = new ArrayList<ModelLogin>();
 		String sql = "SELECT * FROM dbmodellogin "
 						+ "WHERE upper(nome) LIKE CONCAT('%',upper(?),'%') "
 						+ "AND useradmin IS false AND userid = ?";
 		PreparedStatement pstm = con.prepareStatement(sql);
 		pstm.setString(1,userToSearch);
 		pstm.setLong(2,userAuthenticated);
 		
 		ResultSet usersFound = pstm.executeQuery();
 		
 		while (usersFound.next()) {
 			ModelLogin mdlLgn = new ModelLogin();
 			mdlLgn.setId(usersFound.getLong("id"));
 			mdlLgn.setNome(usersFound.getString("nome"));
 			mdlLgn.setEmail(usersFound.getString("email"));
 			mdlLgn.setUsername(usersFound.getString("username"));
 			mdlLgn.setPerfil(usersFound.getString("perfil"));
 			mdlLgn.setSexo(usersFound.getString("sexo"));
 			usersList.add(mdlLgn);
 		}
 		
 		return usersList;
 	}
 	
 	public List<ModelLogin> listUsers(Long userAuthenticated) throws Exception {
 		
 		List<ModelLogin> usersList = new ArrayList<ModelLogin>();
 		String sql = "SELECT * FROM dbmodellogin "
 						+ "WHERE useradmin IS false AND userid = ?";
 		PreparedStatement pstm = con.prepareStatement(sql);
 		pstm.setLong(1,userAuthenticated);
 		
 		ResultSet usersFound = pstm.executeQuery();
 		
 		while (usersFound.next()) {
 			ModelLogin mdlLgn = new ModelLogin();
 			mdlLgn.setId(usersFound.getLong("id"));
 			mdlLgn.setNome(usersFound.getString("nome"));
 			mdlLgn.setEmail(usersFound.getString("email"));
 			mdlLgn.setUsername(usersFound.getString("username"));
 			mdlLgn.setPassword(usersFound.getString("password"));
 			mdlLgn.setPerfil(usersFound.getString("perfil"));
 			mdlLgn.setSexo(usersFound.getString("sexo"));
 			usersList.add(mdlLgn);
 		}
 		
 		return usersList;
 	}
	
	public boolean usernameExists(String username) throws Exception {

		String sql = "SELECT COUNT(1) > 0 AS existUsername FROM dbmodellogin "
					 + "WHERE upper(username) = upper('" + username + "')";
		PreparedStatement pstm = con.prepareStatement(sql);

		ResultSet usernameFound = pstm.executeQuery();
		
		if (usernameFound.next()) { 
			return usernameFound.getBoolean("existUsername");
		}
		
		return false;
	}
}
