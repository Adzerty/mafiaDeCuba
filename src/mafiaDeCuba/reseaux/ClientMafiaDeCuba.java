package mafiaDeCuba.reseaux;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

import mafiaDeCuba.metier.Joueur;


public class ClientMafiaDeCuba
{
	
	private String nom;
	private String ip;
	private int portNumber;
	
	private boolean aCommence;
	
  public ClientMafiaDeCuba(String nom, String ip, String port)
  {
	  
	  Thread t = new Thread(new Runnable()
	  {
		  public void run()
		  {
			  try
		      {
		          portNumber = Integer.parseInt(port);

		          InetAddress iA = InetAddress.getByName(ip);
		          DatagramSocket ds = new DatagramSocket();
		          
		          
		          Joueur j = new Joueur(nom, ds.getPort(), ds.getInetAddress());
		          
		          final ByteArrayOutputStream baos = new ByteArrayOutputStream(6400);
		          final ObjectOutputStream oos = new ObjectOutputStream(baos);
		          oos.writeObject(j);
		          final byte[] data = baos.toByteArray();
				
		          final DatagramPacket packet = new DatagramPacket(data, data.length, iA, portNumber);
		          ds.send(packet);

		          
		          // Reception et affichage du message venant du serveur
		          byte[] buf = new byte[1024];
		          DatagramPacket dp = new DatagramPacket(buf, 1024);
		          ds.receive(dp);
		          String recu = new String(dp.getData(), 0, dp.getLength());
		          System.out.println(recu);
		          

		          /*
		          while (! aCommence)
		          {
		              //On lit un String qui DOIT CONTENIR un ENTIER (sinon il y aura une erreur)
		              Scanner sc = new Scanner(System.in);
		              String s = sc.nextLine();
		              DatagramPacket dp = new DatagramPacket(s.getBytes(), s.length(), ia, portNumber);
		              ds.send(dp);

		              // Reception et affichage du message venant du serveur
		              byte[] buf = new byte[1024];
		              dp = new DatagramPacket(buf, 1024);
		              ds.receive(dp);

		              String recu = new String(dp.getData(), 0, dp.getLength());
		              nbTry--;

		              //Si il reste des essais
		              if (nbTry != 0)
		              {
		                  //affiche le message envoyé par le serveur
		                  System.out.println(recu);
		                  //Si on a gagné on coupe le client
		                  if (recu.equals("Gagne !"))
		                  {
		                      break;
		                  }

		                  //Sinon on affiche le nb d'essais restant
		                  System.out.println("Il ne vous reste que " + nbTry + " essai(s)");
		              }
		          }

		          //Si on n'a plus d'essai, on a perdu.
		          if (nbTry == 0)
		          {
		              System.out.println("C'est perdu");
		          }*/
		          
		          while(true)
		          {
		        	 
		          }
		          //ds.close();
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
