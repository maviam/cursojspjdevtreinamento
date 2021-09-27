package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnection {

	private static Connection con = null;
	private static String bd = "jdbc:postgresql://localhost:5432/cursojspbd?autoReconnect=true";
	private static String user = "postgres";
	private static String password = "Ramsirc!1972";
	
	// Garante que executamos a conex�o ao chamar a classe mesmo sem instanci�-la
	static { connectionDB(); }
	
	// Class constructor
	public SingleConnection() { connectionDB(); }
	
	// Getter
	public static Connection getConnection() { return con; }
	
	
	// Other methods
	private static void connectionDB() {
		try {
			if (con == null) {
				// Carregar o driver de conex�o � base de dados
				Class.forName("org.postgresql.Driver"); 
				con = DriverManager.getConnection(bd,user,password);
				// N�o quero que a conex�o guarde as altera��es automaticamente
				con.setAutoCommit(false);
				// System.out.println("Conex�o estabelecida com sucesso!");
			}
		} catch (Exception e) {
			// Apresenta o erro ocorrido na conex�o
			e.printStackTrace();
		}
	}
}
