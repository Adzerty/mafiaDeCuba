package mafiaDeCuba.ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;

import mafiaDeCuba.reseaux.ClientMafiaDeCuba;
import mafiaDeCuba.metier.Joueur;

public class FrameLobbyClient extends JFrame
{

	private int largeurEcran, hauteurEcran;
	private Dimension dimEcran;
	
	private ClientMafiaDeCuba client;
	
	private ArrayList<Joueur> alJoueur;
	
	private JList listConnectes;
	
	public FrameLobbyClient(ClientMafiaDeCuba client)
	{
		this.client = client;
		this.alJoueur = new ArrayList<Joueur>();
		
		this.dimEcran     = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		this.hauteurEcran = (int) dimEcran.getHeight();
		this.largeurEcran = (int) dimEcran.getWidth();
		
		this.setTitle("Lobby (client)");
		this.setLocation( (largeurEcran/2)-350, (hauteurEcran/2)-250);
		this.setSize(200,200);
		this.setLayout(new BorderLayout());
		
		this.listConnectes = new JList(alJoueur.toArray());
		
		this.add(this.listConnectes,BorderLayout.CENTER);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	
	public static void main(String[] args)
	{
		FrameLobbyHost fLobby = new FrameLobbyHost(null, null);
	}
	
	public void majIHM(ArrayList<Joueur> al)
	{
		this.alJoueur = al;
		this.listConnectes.setListData(this.alJoueur.toArray());
	}



}
