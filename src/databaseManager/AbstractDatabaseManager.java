package databaseManager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONObject;

/**
 * 
 */

/**
 * @author John G
 *
 */

public abstract class AbstractDatabaseManager {

	protected Connection connect = null;
	protected Statement statement = null;
	protected PreparedStatement preparedStatement, preparedStatement2 = null;
	protected String connectionString;

	public void readDataBase() throws Exception {
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DriverManager
					.getConnection("jdbc:" + this.connectionString);
			connect.setAutoCommit(true);

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();

		} catch (Exception e) {
			throw e;
		} 
	}
	
	public JSONObject select(String select) throws SQLException {
		return null;
	}

	// You need to close the resultSet
	private void close() {
		try {
			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}
}
