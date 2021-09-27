package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.SingleConnection;
import models.ModelLogin;

public class DAOLoginRepository {
	
	// Object that represents the connection to the database
	private Connection con = SingleConnection.getConnection();
	
	public boolean validarAutenticacao(ModelLogin mdlgn) throws SQLException {
		String sql = "SELECT * FROM dbmodellogin where username = ? and password = ?";
		PreparedStatement pstm = con.prepareStatement(sql);
		
		pstm.setString(1,mdlgn.getUsername());
		pstm.setString(2,mdlgn.getPassword());
		
		ResultSet resultado = pstm.executeQuery();
		
		if (resultado.next()) {
			return true; // Authenticated
		}
		
		return false; // Not authenticated
	}
	
}
