package databaseManager;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONObject;

public class UserSql extends DatabaseManager {
	
	public boolean authorized(String id, String nom) throws SQLException {
		
		ResultSet resultSet = this.statement.executeQuery("Select * from user where iduser = " + id + " and nom like '" + nom + "'");
		
		return resultSet.next();
	}
}
