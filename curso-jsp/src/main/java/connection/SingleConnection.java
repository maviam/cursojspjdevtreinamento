package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnection {

	private static Connection con = null;
	private static String bd = "jdbc:postgresql://localhost:5432/cursojspbd?autoReconnect=true";
	private static String user = "postgres";
	private static String password = "Ramsirc!1972";
	
	// Garante que executamos a conexão ao chamar a classe mesmo sem instanciá-la
	static { connectionDB(); }
	
	// Class constructor
	public SingleConnection() { connectionDB(); }
	
	// Getter
	public static Connection getConnection() { return con; }
	
	
	// Other methods
	private static void connectionDB() {
		try {
			if (con == null) {
				// Carregar o driver de conexão à base de dados
				Class.forName("org.postgresql.Driver"); 
				con = DriverManager.getConnection(bd,user,password);
				// Não quero que a conexão guarde as alterações automaticamente
				con.setAutoCommit(false);
				// System.out.println("Conexão estabelecida com sucesso!");
			}
		} catch (Exception e) {
			// Apresenta o erro ocorrido na conexão
			e.printStackTrace();
		}
	}
}
