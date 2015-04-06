package serveurhadl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

public class Serveur {
	 
	private boolean ok=true ;
	private ServerSocket server;
 
	private final int PORT= 5163;
 
	public static void main (String [] args ){
		new Serveur();
	}
 
	public Serveur (){
		try{
			server = new ServerSocket (PORT);
		}//try
		catch (SocketException e) {
			System.out.println(e.getMessage() + " 01");
		} // catch
		catch (IOException e) {
			System.out.println(e.getMessage()+" 02");
		}//catch
		System.out.println("En écoute sur le port : " + PORT );
		while (ok) {
			try {
				Connect co = new Connect(server.accept());
			} //try
			catch (IOException e) {
				System.out.println(e.getMessage()+ " 03");
			}//catch
 
		}//while
	}//Serve
 
}//class
