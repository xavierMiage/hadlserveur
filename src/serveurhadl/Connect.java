package serveurhadl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONObject;

public class Connect implements Runnable {
	private Thread thd ;
	private Socket socket ;
	private PrintWriter out ;
	//private InputStream in  ;
 
	private static ArrayList<Connect> listC = new ArrayList<Connect>(1);
	public Connect (Socket socket_) {
		listC.add(this);
		socket = socket_ ;
		thd = new Thread (this) ;
		thd.start();
	}//connect
 
	public void run () {

		JSONObject json = new JSONObject();
		json = Connect.this.read();
		System.out.println(json);
		
		String id = (String) json.get("id");
		
		System.out.println(id);
		/*MySQLAccess dao = new MySQLAccess();
		try {
			dao.readDataBase();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
			
		json.put("name", "foo");
		json.put("num", new Integer(100));
		json.put("balance", new Double(1000.21));
		json.put("is_vip", new Boolean(true));

	    Connect.this.send(json.toJSONString());
 
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
			System.out.println("ici");
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