package mafiaDeCuba.reseaux;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import mafiaDeCuba.ihm.FrameJoin;
import mafiaDeCuba.ihm.FrameLobbyClient;
import mafiaDeCuba.metier.Joueur;


public class ClientMafiaDeCuba
{
	
	private String nom;
	private String ip;
	private int portNumber;
	
	private static FrameLobbyClient fLobbyClient;
	
	private static boolean aCommence;
	
  public ClientMafiaDeCuba(String nom, String ip, String port, FrameJoin f)
  {
	  f.dispose();
	  Joueur j = new Joueur(nom);
	  
	  fLobbyClient = new FrameLobbyClient(this);
	  Thread t = new Thread(new Runnable()
	  {
		  public void run()
		  {
			  try
		      {
		          portNumber = Integer.parseInt(port);

		          Socket socket = new Socket(ip, portNumber);
		       
		          // On envoit le port 
		          ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
		          ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
		          
		          os.writeObject(j);

		          // Reception et affichage du message venant du serveur 
		          while(!aCommence)
		          {
		        	  Object recu = is.readUnshared();
		        	  
		        	  if(recu instanceof ArrayList<?>)
		        	  {
							if(((ArrayList<?>)recu).get(0) instanceof Joueur)
							{
								fLobbyClient.majIHM((ArrayList<Joueur>)recu);
							}
		        	  }
		        	  
		        	  if(recu instanceof String)
		        	  {
							if(recu.toString().equals("Partie commencée !"))
							{
								System.out.println(recu);
								ClientMafiaDeCuba.aCommence = true;
								System.out.println(aCommence);
							}
		        	  }
		        	  
		        	  recu = new Object();
			          //is = new ObjectInputStream(socket.getInputStream());
			          //is.reset();
		          }	


		          //socket.close();

		      }
		      catch(Exception e)
		      {
		          e.printStackTrace();
		      }  
		  }
	  });
	  
	  t.start();
      
  }
}
