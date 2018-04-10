package Lab5.GenClassificator.Data;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableUtils;

import Lab5.GenClassificator.Entities.Examined;
import Lab5.GenClassificator.Entities.Flagella;
import Lab5.GenClassificator.Entities.Toughness;

public class SqliteDbContext {
	private String connectionString;
	private ConnectionSource connectionSource;
	
	public Dao<Flagella, Object> flagellaDAO;
	public Dao<Examined, Object> examinedDAO;
	public Dao<Toughness, Object> toughnessDAO;
	
	public SqliteDbContext(String name) throws IOException, SQLException, URISyntaxException {
		this.connectionString = name;
		
		connectionSource = new JdbcConnectionSource(String.format("jdbc:sqlite:%s", name));
		flagellaDAO  = DaoManager.createDao(connectionSource, Flagella.class);
		examinedDAO  = DaoManager.createDao(connectionSource, Examined.class);
		toughnessDAO = DaoManager.createDao(connectionSource, Toughness.class);
		
		ensureDatabaseExist();
	}
	
	
	private void ensureDatabaseExist() throws IOException, SQLException, URISyntaxException {
		
		File f = new File(connectionString);
		if(f.exists()) {
			System.out.println(String.format("Database %s already exists", connectionString));
			return;
		}
		
		TableUtils.createTable(connectionSource, Flagella.class);
		TableUtils.createTable(connectionSource, Examined.class);
		TableUtils.createTable(connectionSource, Toughness.class);

		examinedDAO.executeRawNoArgs("INSERT INTO 'FLAGELLA' ('ALPHA', 'BETA', 'NUMBER') VALUES('12', '43', '1'),('33', '24', '3'),('34', '52', '2'),('32', '42', '2');");
		examinedDAO.executeRawNoArgs("INSERT INTO 'TOUGHNESS' ('BETA', 'GAMMA', 'RANK') VALUES('43', '23', 'd'),('24', '43', 'b'),('54', '12', 'b'),('43', '43', 'a');");
		examinedDAO.executeRawNoArgs("INSERT INTO 'EXAMINED' ('GENOTYPE', 'CLASS') VALUES('328734', '1d'),('653313', '3c'),('239322', '1c'),('853211', '2a');");
	}
	
	public String getConnectionString() {
		return connectionString;
	}
}
