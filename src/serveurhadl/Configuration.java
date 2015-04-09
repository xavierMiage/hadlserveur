package serveurhadl;

import securityManager.MySecurityManager;
import databaseManager.DatabaseManager;

public class Configuration {
	
	private String connectionString;
	private DatabaseManager db = null;
	private MySecurityManager protect = null;
	
	public Configuration(String connectionString) {

		try {
			this.db = new DatabaseManager(connectionString);
			this.protect = new MySecurityManager();
		} catch (Exception e) {
			System.out.println("Erreur de connection à la base de données.");
			e.printStackTrace();
		}
	}

	public DatabaseManager getDb() {
		return db;
	}

	public void setDb(DatabaseManager db) {
		this.db = db;
	}

	public MySecurityManager getProtect() {
		return protect;
	}

	public void setProtect(MySecurityManager protect) {
		this.protect = protect;
	}

}
