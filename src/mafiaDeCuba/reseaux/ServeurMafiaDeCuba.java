package mafiaDeCuba.reseaux;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import mafiaDeCuba.ihm.FrameHost;
import mafiaDeCuba.ihm.FrameLobbyHost;
import mafiaDeCuba.metier.Joueur;
import mafiaDeCuba.metier.MafiaDeCuba;



public class ServeurMafiaDeCuba
{
	
	private static boolean aCommence; // false = on est dans le lobby | true = la partie a commencee
	
	private MafiaDeCuba metier;
	private int portNumber; // Port sur lequel est hébergée la partie
	
	private static ArrayList<Joueur> alJoueur; 			// liste qui stock les joueurs
	private static ArrayList<Socket> alSocketJoueur; 	// liste qui stock les sockets des joueurs
	private static ArrayList<ObjectOutputStream> alObjectOutputStream; 	// list qui stock les OOS des joueurs
	
	private static FrameLobbyHost fLobbyHost;	//Frame de lobby pour le host
	
    public ServeurMafiaDeCuba(int nbJoueur, boolean aNettoyeur, FrameHost f)
    {
    	f.dispose();
    	
    	// INITITALISATION
        alJoueur = new ArrayList<Joueur>();
        alSocketJoueur = new ArrayList<Socket>();
        alObjectOutputStream = new ArrayList<ObjectOutputStream>();
        this.portNumber = 9000;
        
        //On affiche le lobby
        fLobbyHost = new FrameLobbyHost(this, alJoueur);
        
        
        
        
        /* Premier thread
         * Thread principal, il s'occupe de l'arrivée des joueurs et d'envoyer les objets aux joueurs
         * 
         */
        Thread t = new Thread(new Runnable() // Thread principal
        {
        		public void run()
        		{
        			try
        	        {

        				ServerSocket serverSocket = new ServerSocket(portNumber); //On ouvre le serveur
        				
        	            // Tant que la partie n'a pas commence (on est dans le lobby)
        	            while(! aCommence)
        	            {   
        	            	// Reception du nouveau client
        	            	try {
        	            		serverSocket.setSoTimeout(1000);
	        	            	Socket sJoueur = serverSocket.accept();
	        	                
	        	            	ObjectOutputStream os = new ObjectOutputStream(sJoueur.getOutputStream());       	
	            		        ObjectInputStream is = new ObjectInputStream(sJoueur.getInputStream());
	            		        
	            		        Joueur j = null;
	    						try // On récupère le joueur envoye par le client
	    						{
	    							j = (Joueur) is.readObject();
	    						} catch (ClassNotFoundException e){}
	
	        	                if (!alJoueur.contains(j)) //Si le joueur n'existe pas encore dans nos arraylist on l'ajoute
	        	                {
	        	                	alJoueur.add(j);
	        	                	alSocketJoueur.add(sJoueur);
	        	                	alObjectOutputStream.add(os);  
	        	                }
        	            	}catch(Exception e) {}
        	            }
        	            
        	            while(aCommence)
        	            {
        	            	System.out.println("lancé");
        	            	for(Joueur jLoop : alJoueur) // Pour chaque joueur qu'on connait
        	                {
        	                	try {
        	                		ObjectOutputStream o = alObjectOutputStream.get(alJoueur.indexOf(jLoop));
        	                		o.writeObject(new String("Partie commencée !")); 
        	                	}catch(Exception e)
        	                	{}
        	                	
        	                	try
        						{
        							Thread.sleep(6000);
        						} catch (InterruptedException e){}
        	                }
        	            }
        	            
        	            serverSocket.close();
        	            
        	        }
        	        catch (IOException e){}
        		}
        		});
        
        /* Second thread
         * Thread de ping, il s'occupe de vérifier que les joueurs présents le sont toujours
         * 
         */
        Thread checkConnectionThread = new Thread( new Runnable() {
        	public void run()
        	{
        		while(true)
        		{	
        			ArrayList<Integer> alIndexJoueurDeco = new ArrayList<Integer>(); // Arraylist qui va stocker les indice des joueurs absent
	                for(Joueur jLoop : alJoueur) // Pour chaque joueur qu'on connait
	                {
	                	
	                	try { // On essaye d'envoyer "ping" au joueur
	                		ObjectOutputStream o = alObjectOutputStream.get(alJoueur.indexOf(jLoop));
	                		o.writeObject(new String("ping")); 
	                	}catch(Exception e) // Si on y parvient pas, c'est que le joueur est déco, on ajoute donc son indice à l'alIndexJoueurDeco
	                	{
	                		System.out.println("Joueur deco");
	                		alIndexJoueurDeco.add(alJoueur.indexOf(jLoop));
	                	}
	                }
	                
	                for(Integer ind : alIndexJoueurDeco) // Pour chaque indice de l'alIndexJoueurDeco on retire cet indice de tous nos ALists
	                { 
	                	ServeurMafiaDeCuba.alJoueur.remove((int)ind); 
	                	ServeurMafiaDeCuba.alSocketJoueur.remove((int)ind); 
	                	ServeurMafiaDeCuba.alObjectOutputStream.remove((int)ind); 
	                }
    
	                //On met à jour les lobbies clients et serveur
	                ServeurMafiaDeCuba.majLobby();
	                
	                try
					{
						Thread.sleep(100);
					} catch (InterruptedException e){}
	                
	                
        		}
        	}
        });
        
        //On lance les threads
        t.start();
        checkConnectionThread.start();
        
    }
    public static ArrayList<Joueur> getAlJoueurCo()
    {
    	return ServeurMafiaDeCuba.alJoueur;
    }
    
    public static FrameLobbyHost getFLobby()
    {
    	return ServeurMafiaDeCuba.fLobbyHost;
    }
    
    /*
     * Méthode majLobby
     * Met le lobby serveur à jour, puis à chaque joueur envoit l'arrayList de joueur pour que les clients mettent a jour leur lobby
     */
    public static void majLobby()
    {
    	for(ObjectOutputStream o : alObjectOutputStream)
        {
        	try
			{
				o.writeUnshared(ServeurMafiaDeCuba.alJoueur);
			} catch (IOException e){}
        }
    	fLobbyHost.majIHM();	
    }
    
    public static void startGame()
    {
    	aCommence = true;
    }
    
}
