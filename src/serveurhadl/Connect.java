package serveurhadl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONObject;

import securityManager.MySecurityManager;
import databaseManager.ArticleSql;
import databaseManager.DatabaseManager;
import databaseManager.UserSql;

public class Connect implements Runnable {
	private Thread thd ;
	private Socket socket ;
	private PrintWriter out ;
	private Configuration conf;
	//private InputStream in  ;
 
	private static ArrayList<Connect> listC = new ArrayList<Connect>(1);
	public Connect (Socket socket_) {
		listC.add(this);
		socket = socket_ ;
		this.conf = new Configuration("mysql://localhost/hadl?"
				+ "user=root&password=root");
		thd = new Thread (this) ;
		thd.start();
	}//connect
 
	public void run () {

		JSONObject json = new JSONObject();
		json = Connect.this.read();
		
		String id = (String) json.get("id");
		String nom = (String) json.get("nom");
		String article = (String) json.get("id_article");
		
		if(!this.conf.getProtect().isAuthorized(id, nom, this.conf.getDb().getUser())) {
			json.clear();
			json.put("error", "Vous n'avez pas les droits de vous connecter.");
			Connect.this.send(json.toJSONString());
			try {
				this.socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.thd.destroy();
		}
		else {
			try {
				json = this.conf.getDb().getArticle().select("Select * from article where idarticle = " + article);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				json.put("error", "Un problème est survenu.");
			}
			
			Connect.this.send(json.toJSONString());
		}
 
			/*while (true){
				try {
					/*if (in.ready()==true) {*/
 
//-------------------------Reception des caracteres----------------------------------
					/*int t;
					String tmp="" ;
					do {t = socket.getInputStream().read() ; tmp = tmp + (char)t; }
					while (t!=10) ;
 
//-----------------------------------------------------------------------------------
 
					System.out.println("|" + tmp + "|" ) ;
// 					dispatch(tmp, );
					switch(tmp.charAt(0)) {
						case 0 :
							System.out.println("MSG> " + tmp) ;
							dispatch(tmp , socket) ;
							break ;
						case 1 :
							System.out.println("ATK> " + tmp) ;
							break ;
						case 2 :
							System.out.println("MOV> " + tmp) ;
							break ;
						default :
							System.out.println("INC>" + tmp) ;
					}//switch
				}//try
				catch (IOException a ) {
					System.out.println("erreur2") ;
					a.printStackTrace() ;  }//catch IO
 
			}//while*/
	}//run
 
	/*public static void dispatch (String scor, Socket socket) {
		for (int i = 0 ; i < listC.size() ; i++) {
			if (listC.get(i).socket!=socket){try{listC.get(i).send(scor) ;}
				catch (NullPointerException e) {
					System.out.println("morte la connection :(" ) ;listC.remove(i) ;
				}//catch
			}//if
		}//for
	}//dispatch
	*/
 
 
	public void send (String scor){
		PrintWriter output;
		try {
			output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			output.println(scor) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//send
	
	public JSONObject read() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String,String> map = new HashMap<String,String>();
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String message = input.readLine();
			map = mapper.readValue(message, new TypeReference<HashMap<String,String>>(){});
			JSONObject json = new JSONObject(map);
			return json;
	// 		out2.writeBytes(entree.readLine() + "\n");
		} catch( IOException e ) {
			System.out.println("Erreur lors de la lecture");
			e.printStackTrace();
		}
		return null;
	}
}//class