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
	
	private boolean aCommence; // false = on est dans le lobby | true = la partie a commencee
	
	private MafiaDeCuba metier;
	private int portNumber; //9000 pour l'instant, peut etre autrechose apres
	
	private static ArrayList<Joueur> alJoueur; 	// list qui stock les joueurs
	
	private static FrameLobbyHost fLobbyHost;	//Frame de lobby pour le host
	
    public ServeurMafiaDeCuba(int nbJoueur, boolean aNettoyeur, FrameHost f)
    {
        //Arraylist comportant les ports de tous les clients connectés.
    	f.dispose();
        this.alJoueur = new ArrayList<Joueur>();
        
        this.fLobbyHost = new FrameLobbyHost(this, this.alJoueur);
        this.portNumber = 9000;
        
        
        
        Thread t = new Thread(new Runnable() // Thread principal
        {
        		public void run()
        		{
        			try
        	        {
        				
        				/*
        	        	 * 
        	        	 * CONNEXION D'UN PREMIER JOUEUR
        	        	 */
        				
        				ServerSocket serverSocket = new ServerSocket(portNumber);
        				Socket sClient = serverSocket.accept();
        				
        		        ObjectOutputStream os = new ObjectOutputStream(sClient.getOutputStream());
        		        ObjectInputStream is = new ObjectInputStream(sClient.getInputStream());
        		        
        		        Joueur j1 = null;
						try
						{
							j1 = (Joueur) is.readObject();
							j1.setSocket(sClient);
						} catch (ClassNotFoundException e)
						{
							e.printStackTrace();
						}

        				//On ajoute le client à nos arraylist.
        				System.out.println(j1);
        	            alJoueur.add(j1);
        	            ServeurMafiaDeCuba.fLobbyHost.majIHM();
        	            
        	            

        	            int position = 0;
        	            boolean verifNewUser = false;
        	            
        	            
        	            // Tant que la partie n'a pas commence (on est dans le lobby)
        	            while(! aCommence)
        	            {
        	            	os.writeObject(new String("Serveur : Joueur connecte avec succes"));
        	                
        	            	// Reception du nouveau client
        	            	Socket sJoueur = serverSocket.accept();
        	                
        	            	os = new ObjectOutputStream(sJoueur.getOutputStream());
            		        is = new ObjectInputStream(sJoueur.getInputStream());
            		        
            		        Joueur j = null;
    						try
    						{
    							j = (Joueur) is.readObject();
    							j.setSocket(sJoueur);
    						} catch (ClassNotFoundException e)
    						{
    							e.printStackTrace();
    						}
        	    			
    	                    if (!alJoueur.contains(j)) //Si l'aL ne contient pas le port on dit qu'on ajoute un nouveau client
    	                        verifNewUser = true;
    	                    else //Sinon on récupère l'index du client (son port et son boolean)
    	                        position = alJoueur.indexOf(j);

        	                if (verifNewUser) //Si on doit ajouter un client
        	                {
        	                	alJoueur.add(j);
        	                    position = alJoueur.size() - 1;
        	                    verifNewUser = false;
        	                }
        	               
        	                System.out.println(j);
        	                ServeurMafiaDeCuba.fLobbyHost.majIHM();
        	                
        	            }
        	       
        	            
        	            /*
        	             *  final ByteArrayOutputStream baos = new ByteArrayOutputStream(6400);
        					final ObjectOutputStream oos = new ObjectOutputStream(baos);
        					oos.writeObject(o);
        					final byte[] data = baos.toByteArray();
        					
        					final DatagramPacket packet = new DatagramPacket(data, data.length);
        					// Send the packet

        	             */
        	            
        	        }
        	        catch (IOException e)
        	        {
        	            e.printStackTrace();
        	        }
        		}
        		});
        
        
        Thread checkConnectionThread = new Thread( new Runnable() {
        	public void run()
        	{
        		while(true)
        		{	
        			ArrayList<Integer> alIndexJoueurDeco = new ArrayList<Integer>();
	                for(Joueur jLoop : alJoueur)
	                {
	                	
	                	try {
	                		ObjectOutputStream o = new ObjectOutputStream(jLoop.getSocket().getOutputStream());
	                		o.writeObject(new String("ping"));
	                	}catch(Exception e)
	                	{
	                		alIndexJoueurDeco.add(alJoueur.indexOf(jLoop));
	                	}
	                }

	                for(Integer ind : alIndexJoueurDeco){ alJoueur.remove((int)ind); } //Pour chacun des joueurs deco on les supprime de la liste
	                ServeurMafiaDeCuba.fLobbyHost.majIHM();
	                
	                try
					{
						Thread.sleep(1000);
					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        	}
        });
        
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
    
}
