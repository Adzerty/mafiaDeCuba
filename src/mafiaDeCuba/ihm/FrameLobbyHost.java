package mafiaDeCuba.ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

import mafiaDeCuba.metier.Joueur;
import mafiaDeCuba.reseaux.ServeurMafiaDeCuba;


public class FrameLobbyHost extends JFrame implements ActionListener
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
		
		this.btnStart = new JButton("Lancer");
		this.btnStart.addActionListener(this);
		this.add(this.btnStart, "South");
		
		
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

	@Override
	public void actionPerformed(ActionEvent e)
	{
		ServeurMafiaDeCuba.startGame();
	}

}
