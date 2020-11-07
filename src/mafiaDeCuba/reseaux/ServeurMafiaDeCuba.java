package mafiaDeCuba.reseaux;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

import mafiaDeCuba.ihm.FrameHost;
import mafiaDeCuba.ihm.FrameLobbyHost;
import mafiaDeCuba.metier.Joueur;
import mafiaDeCuba.metier.MafiaDeCuba;



public class ServeurMafiaDeCuba
{
	private boolean aCommence;
	
	private MafiaDeCuba metier;
	private int portNumber;
	
	private ArrayList<Joueur> alJoueur;
	private ArrayList<Integer> alPortJoueur;
	
	private FrameLobbyHost fLobbyHost;
	
    public ServeurMafiaDeCuba(int nbJoueur, boolean aNettoyeur, FrameHost f)
    {
        //Arraylist comportant les ports de tous les clients connectés.
    	f.dispose();
    	this.fLobbyHost = new FrameLobbyHost(this,);
        this.alJoueur = new ArrayList<Joueur>();
        this.alPortJoueur = new ArrayList<Integer>();
        this.portNumber = 9000;
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
            
			System.out.println(j1);
			
			//On ajoute le client à nos arraylist.
            alPortJoueur.add(dp.getPort());
            alJoueur.add(j1);

            int position = 0;
            boolean verifNewUser = false;
            while(! aCommence)
            {
                DatagramPacket dp2 = ServeurMafiaDeCuba.envoieMessage("Serveur : Joueur reçu avec succés !", dp);
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
    			
                //On parcourt notre aList de port
                for (Integer i : alPortJoueur)
                {
                    if (!alPortJoueur.contains(dp.getPort())) //Si l'aL ne contient pas le port on dit qu'on ajoute un nouveau client
                        verifNewUser = true;
                    else //Sinon on récupère l'index du client (son port et son boolean)
                        position = alPortJoueur.indexOf(i);
                }

                if (verifNewUser) //Si on doit ajouter un client
                {
                	alPortJoueur.add(dp.getPort());
                	alJoueur.add(j);
                    position = alPortJoueur.size() - 1;
                    verifNewUser = false;
                }
                
                for(Joueur instanceJ : alJoueur)
                {
                	System.out.println(instanceJ);
                }
                
            }
            
            
            /*
             *  final ByteArrayOutputStream baos = new ByteArrayOutputStream(6400);
				final ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(o);
				final byte[] data = baos.toByteArray();
				
				final DatagramPacket packet = new DatagramPacket(data, data.length);
				// Send the packet

             */

            /*
            //Transforme le message reçu en un entier (la proposition du client)
            int proposition = Integer.parseInt(recu);

            //On ajoute le client à nos arraylist.
            alPortClient.add(dp.getPort());
            albOk.add((new Boolean(true)));

            int position = 0;
            boolean verifNewUser = false;

            while (! partieCommence) // Tant que la partie n'a pas commencé on récupère les joueurs dans le lobby
            {
            	// Faire les actions nécessaires au lobby (ajout au tableau réaffichage etc)
            	
            	//	DatagramPacket dp2 = ServeurMafiaDeCuba.envoieMessage("C'est moins !", dp);
                //   ds.send(dp2);
            	 

                ds.receive(dp); // On attend un nouveau message
                recu = new String(dp.getData(), 0, dp.getLength());
                proposition = Integer.parseInt(recu);

                //On parcourt notre aList de port
                for (Integer i : alPortClient)
                {
                    if (!alPortClient.contains(dp.getPort())) //Si l'aL ne contient pas le port on dit qu'on ajoute un nouveau client
                        verifNewUser = true;
                    else //Sinon on récupère l'index du client (son port et son boolean)
                        position = alPortClient.indexOf(i);
                }

                if (verifNewUser) //Si on doit ajouter un client
                {
                    alPortClient.add(dp.getPort());
                    albOk.add(new Boolean(true));
                    position = alPortClient.size() - 1;
                    verifNewUser = false;
                }
            }
            
            while(partieCommence)
            {
            	//Partie en cours
            }
            */
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static DatagramPacket envoieMessage(String s, DatagramPacket dp)
    {
        byte[] buf2 = new String(s).getBytes();
        DatagramPacket dp1 = new DatagramPacket(buf2, buf2.length, dp.getAddress(), dp.getPort());

        return dp1;
    }
}
