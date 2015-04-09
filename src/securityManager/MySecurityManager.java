package securityManager;

import java.sql.SQLException;

import databaseManager.UserSql;

public class MySecurityManager {
		
	public boolean isAuthorized(String id, String nom, UserSql db) {
		try {
			return db.authorized(id, nom);
		} catch (SQLException e) {
			return false;
		}	
	}
	
}
