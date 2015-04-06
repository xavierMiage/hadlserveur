package serveurhadl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONObject;

public class ArticleSql extends MySQLAccess {
	
	@Override
	public JSONObject select(String select) throws SQLException {
		
		JSONObject json = new JSONObject();
		ResultSet resultSet = this.statement.executeQuery(select);
		if(resultSet.next()) {
			resultSet.beforeFirst();
			while (resultSet.next()) {
				// It is possible to get the columns via name
				// also possible to get the columns via the column number
				// which starts at 1
				// e.g. resultSet.getSTring(2);
				String nom = resultSet.getString("nom");
				String prix = resultSet.getString("prix");

				json.put("nom", nom);
				json.put("prix", prix);
			}
		}
		else {
			json.put("error", "Pas de produit trouvé.");
		}
		return json;
		
	}

}
