package Lab5.GenClassificator.Data;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import Lab5.GenClassificator.Entities.Examined;
import Lab5.GenClassificator.Entities.Flagella;
import Lab5.GenClassificator.Entities.Toughness;

public class SqliteDbContext {
	private String connectionString;
	
	private ConnectionSource connection;
	
	public SqliteDbContext(String connectionString) throws IOException, SQLException, URISyntaxException {
		this.connectionString = connectionString;
		
		ensureDatabaseExist();
	}
	
	public void connect() throws SQLException {
		if(connection != null) return;

		connection = new JdbcConnectionSource(String.format("jdbc:sqlite:%s", getConnectionString()));
	}
	
	public void dispose() throws SQLException, IOException {
		if(connection == null) return;

		connection.close();
	}
	
	public ConnectionSource getConnection() {
		return connection;
	}
	
	private void ensureDatabaseExist() throws IOException, SQLException, URISyntaxException {
		
		File f = new File(connectionString);
		if(f.exists()) {
			System.out.println(String.format("Database %s already exists", connectionString));
			return;
		}
		
		String seedSql = new String(Files.readAllBytes(Paths.get(SqliteDbContext.class.getResource("db_seed.sql").toURI()).toAbsolutePath()));
		
		connect();

		TableUtils.createTable(connection, Examined.class);
		TableUtils.createTable(connection, Flagella.class);
		TableUtils.createTable(connection, Toughness.class);
		
		// instantiate the dao to run sql
        Dao<Examined, String> dao = DaoManager.createDao(connection, Examined.class);
        dao.executeRawNoArgs(seedSql);

		dispose();
	}
	
	public String getConnectionString() {
		return connectionString;
	}
}
