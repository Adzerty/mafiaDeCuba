package mafiaDeCuba.reseaux;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
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
        	        	
        	        	//Créer une socket pour recevoir le message
        	            DatagramSocket ds = new DatagramSocket(portNumber);
        	            byte[] buf = new byte[1024];
        	            DatagramPacket dp = new DatagramPacket(buf, 1024);
        	            
        	            System.out.println("Serveur lancé !");
        	            
        	            
        	            // Reception du message
        	            ds.receive(dp);
        	            ByteArrayInputStream baos = new ByteArrayInputStream(buf);
        	            ObjectInputStream oos = new ObjectInputStream(baos);
        	            Joueur j1 = null;
        	            
        				try
        				{
        					j1 = (Joueur)oos.readObject();
        					j1.setPort(dp.getPort());
        				} catch (ClassNotFoundException e){e.printStackTrace();}
        	            
        				//System.out.println(j1);
        				
        				//On ajoute le client à nos arraylist.
        				System.out.println(j1);
        	            alJoueur.add(j1);
        	            ServeurMafiaDeCuba.fLobbyHost.majIHM();

        	            int position = 0;
        	            boolean verifNewUser = false;
        	            
        	            // Tant que la partie n'a pas commence (on est dans le lobby)
        	            while(! aCommence)
        	            {
        	                DatagramPacket dp2 = ServeurMafiaDeCuba.envoieMessage("Serveur : Joueur reçu avec succes !", dp);
        	                ds.send(dp2);
        	                
        	            	// Reception du nouveau client
        	                ds.receive(dp);
        	                baos = new ByteArrayInputStream(buf);
        	                oos = new ObjectInputStream(baos);
        	                Joueur j = null;
        	    			try
        	    			{
        	    				j = (Joueur)oos.readObject();
        	    				j.setPort(dp.getPort());
        	    			} catch (ClassNotFoundException e){e.printStackTrace();}
        	    			
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
	                
	                	try { // On essaye de creer une socket avec le port de chaque joueur
	                		  // Si cela marche ca veut dire que le port est libre donc que le joueur est deco
	                		DatagramSocket dsTest = new DatagramSocket(jLoop.getsA());        		
	                		dsTest.close();
	                		alIndexJoueurDeco.add(alJoueur.indexOf(jLoop));
	                	}catch(Exception e){} // Sinon cela veut dire que le joueur est co donc tout va bien
	                }
	                
	                
	                for(Integer ind : alIndexJoueurDeco){ alJoueur.remove((int)ind); } //Pour chacun des joueurs deco on les supprime de la liste
	                ServeurMafiaDeCuba.fLobbyHost.majIHM();
	                try
					{
						Thread.sleep(1000);
					} catch (InterruptedException e){}
        		}
        	}
        });
        t.start();
        checkConnectionThread.start();
        
    }

    public static DatagramPacket envoieMessage(String s, DatagramPacket dp)
    {
        byte[] buf2 = new String(s).getBytes();
        DatagramPacket dp1 = new DatagramPacket(buf2, buf2.length, dp.getAddress(), dp.getPort());

        return dp1;
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
