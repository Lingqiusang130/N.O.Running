package tool;


import java.io.File;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;


public class HibernateSchemaExport {

	public static void main(String[] args) {

		File configFile = new File("src/main/java/com/fjnu/data/configure/hibernate.cfg.xml");

		Configuration config = new Configuration().configure(configFile);

		System.out.println("Creating tables...");

		SchemaExport schemaExport = new SchemaExport(config);
		schemaExport.create(true, true);

		System.out.println("Table created.");
	}

}
