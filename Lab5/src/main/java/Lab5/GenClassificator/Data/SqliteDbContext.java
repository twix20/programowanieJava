package Lab5.GenClassificator.Data;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Consumer;

import Lab5.GenClassificator.Entities.Examined;
import Lab5.GenClassificator.Entities.Flagella;
import Lab5.GenClassificator.Entities.Toughness;

public class SqliteDbContext {
	
	private String dbPath;
	private String connectionString;
	private Connection connection;
	
	
	public SqliteDbContext(String dbPath) throws IOException, SQLException, URISyntaxException {

		this.dbPath = dbPath;
		this.connectionString = String.format("jdbc:sqlite:%s", this.dbPath);
	}
	
	public void connect() throws SQLException {
		if(connection != null && !connection.isClosed()) return;
		
		connection = DriverManager.getConnection(this.connectionString);
	}
	
	public void dispose() throws SQLException {
		if(connection == null) return;
		if(connection.isClosed()) return;
		
		connection.close();
	}
	
	public void runInTransaction(ThrowingConsumer<Connection> c) throws SQLException {
		
		try {
			connect();
			connection.setAutoCommit(false); //transaction block start
			
			c.accept(connection);
			
			connection.commit(); //transaction block end
		}
		catch(SQLException ex) {
			connection.rollback();
			ex.printStackTrace();
		}
		finally {
			dispose();
		}
		
	}
	
	public String getConnectionString() {
		return connectionString;
	}
	
	public String getDbPath() {
		return this.dbPath;
	}
}
