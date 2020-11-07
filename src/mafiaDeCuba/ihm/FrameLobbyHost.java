package mafiaDeCuba.ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.List;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

import mafiaDeCuba.metier.Joueur;
import mafiaDeCuba.reseaux.ServeurMafiaDeCuba;


public class FrameLobbyHost extends JFrame
{
	private int largeurEcran, hauteurEcran;
	private Dimension dimEcran;
	
	private ServeurMafiaDeCuba serveur;
	
	private ArrayList<Joueur> alJoueur;
	
	private JList listConnectes;
	private JButton btnStart;
	
	public FrameLobbyHost(ServeurMafiaDeCuba serv, ArrayList<Joueur> alJ)
	{
		this.serveur = serv;
		this.alJoueur = alJ;
		
		this.dimEcran     = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		this.hauteurEcran = (int) dimEcran.getHeight();
		this.largeurEcran = (int) dimEcran.getWidth();
		
		this.setTitle("Lobby (hébergeur)");
		this.setLocation( (largeurEcran/2)-350, (hauteurEcran/2)-250);
		this.setSize(700,500);
		this.setLayout(new BorderLayout());
		
		this.listConnectes = new JList(alJ.toArray());
		
		this.add(this.listConnectes,BorderLayout.CENTER);
		this.add(new JLabel("                      L O B B B Y"), BorderLayout.NORTH);
		
		System.out.println("Ca se lance ca");
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void majIHM()
	{
		this.alJoueur = this.serveur.getAlJoueurCo();
		this.listConnectes.setListData(this.alJoueur.toArray());
	}
	
	public static void main(String[] args)
	{
		FrameLobbyHost fLobby = new FrameLobbyHost(null, null);
	}

}
