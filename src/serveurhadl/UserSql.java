package serveurhadl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONObject;

public class UserSql extends MySQLAccess {

	@Override
	public JSONObject select(String select) throws SQLException {
		JSONObject json = new JSONObject();
		ResultSet resultSet = this.statement.executeQuery(select);
		while (resultSet.next()) {
			// It is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g. resultSet.getSTring(2);
			String user = resultSet.getString("myuser");
			String website = resultSet.getString("webpage");
			String summary = resultSet.getString("summary");
			Date date = resultSet.getDate("datum");
			String comment = resultSet.getString("comments");
			System.out.println("User: " + user);
			System.out.println("Website: " + website);
			System.out.println("Summary: " + summary);
			System.out.println("Date: " + date);
			System.out.println("Comment: " + comment);
		}
		return null;
	}
	
	public boolean authorized(String id, String nom) throws SQLException {
		
		ResultSet resultSet = this.statement.executeQuery("Select * from user where iduser = " + id + " and nom like '" + nom + "'");
		
		return resultSet.next();
	}
}
