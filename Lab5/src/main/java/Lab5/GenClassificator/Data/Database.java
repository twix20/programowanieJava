package Lab5.GenClassificator.Data;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Lab5.GenClassificator.Entities.Examined;

public class Database {
	
	private SqliteDbContext context;
	
	public Database(SqliteDbContext context) throws IOException, SQLException, URISyntaxException {
		this.context = context;
		
		ensureDatabaseExist();
	}
	
	
	public List<Examined> getAllExamined() throws SQLException{
		
		List<Examined> result = new ArrayList<>();
		
		context.runInTransaction(conn -> {
			String sql = "SELECT * FROM `EXAMINED`";
			
			Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            
            while (rs.next()) {
            	Examined entity = new Examined();
            	entity.setClazz(rs.getString("CLASS"));
            	entity.setGenotype(rs.getString("GENOTYPE"));
            	
            	result.add(entity);
            }
		});
		
		return result;
	}
	
	private void ensureDatabaseExist() throws IOException, SQLException, URISyntaxException {
		
		File f = new File(context.getDbPath());
		if(f.exists()) {
			System.out.println(String.format("Database %s already exists", context.getDbPath()));
			f.delete();
			//return;
		}
		
		String createDbSql = new String(Files.readAllBytes(Paths.get(getClass().getResource("db_create.sql").toURI())));
		String seedDbSql = new String(Files.readAllBytes(Paths.get(getClass().getResource("db_seed.sql").toURI())));
		
		context.runInTransaction(conn -> {
			
			Statement statement = conn.createStatement();
			
			statement.executeUpdate(createDbSql);
			statement.executeUpdate(seedDbSql);
			
			statement.close();
		});
	}

}
