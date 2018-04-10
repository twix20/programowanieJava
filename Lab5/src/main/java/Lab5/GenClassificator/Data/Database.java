package Lab5.GenClassificator.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import Lab5.GenClassificator.Entities.Examined;

public class Database {

	private SqliteDbContext context;

	public Database(SqliteDbContext context) throws IOException, SQLException, URISyntaxException {
		this.context = context;

		ensureDatabaseExist();
	}

	public List<Examined> getAllExamined() throws SQLException {

		List<Examined> result = new ArrayList<>();

		context.runInTransaction(conn -> {
			String sql = "SELECT * FROM `EXAMINED`";

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Examined entity = new Examined();
				entity.setClazz(rs.getString("CLASS"));
				entity.setGenotype(rs.getString("GENOTYPE"));

				result.add(entity);
			}
		});

		return result;
	}
	
	public void dumpExaminedToXML(String path) throws JAXBException, SQLException, FileNotFoundException {
		JAXBContext jc = JAXBContext.newInstance(ExaminedXmlResult.class);
		
		List<Examined> allExamined = getAllExamined();
		ExaminedXmlResult result = new ExaminedXmlResult(allExamined);
		
		
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(result, System.out);
        
        //OutputStream os = new FileOutputStream(path);
        //marshaller.marshal(result, os);
	}

	private void ensureDatabaseExist() throws IOException, SQLException, URISyntaxException {

		File f = new File(context.getDbPath());
		if (f.exists()) {
			System.out.println(String.format("Database %s already exists", context.getDbPath()));
			f.delete();
			// return;
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
