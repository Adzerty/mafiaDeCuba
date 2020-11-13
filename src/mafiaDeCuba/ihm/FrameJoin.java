package mafiaDeCuba.ihm;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mafiaDeCuba.Controleur;
import mafiaDeCuba.reseaux.ClientMafiaDeCuba;

public class FrameJoin extends JFrame implements ActionListener
{	
	private int largeurEcran, hauteurEcran;
	private Dimension dimEcran;
	
	private JPanel panelNomJoueur;
		private JLabel lblNomJoueur;
		private JTextField txtNomJoueur;
	
	private JPanel panelIPServeur;
		private JLabel lblIP;
		private JTextField txtIP;
		
	private JPanel panelPortServeur;
		private JLabel lblPort;
		private JTextField txtPort;
		
	private JButton btnStart;

	
	public FrameJoin()
	{	
		this.dimEcran     = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		this.hauteurEcran = (int) dimEcran.getHeight();
		this.largeurEcran = (int) dimEcran.getWidth();
		
		this.setTitle("Mafia De Cuba - Héberger une partie");
		this.setLocation( (largeurEcran/2), (hauteurEcran/2));
		
		this.panelNomJoueur = new JPanel(new GridLayout(1,2));
		this.lblNomJoueur = new JLabel("Votre pseudo : ");
		this.txtNomJoueur = new JTextField();
		this.txtNomJoueur.setEditable(true);
		
		this.panelNomJoueur.add(this.lblNomJoueur);
		this.panelNomJoueur.add(this.txtNomJoueur);
		
		
		//panelIpServeur
		this.panelIPServeur = new JPanel(new GridLayout(1,2));
		this.lblIP = new JLabel("L'IP du serveur : ");
		this.txtIP = new JTextField();
		this.txtIP.setEditable(true);
		
		this.panelIPServeur.add(this.lblIP);
		this.panelIPServeur.add(this.txtIP);
		
		//panelIpServeur
		this.panelPortServeur = new JPanel(new GridLayout(1,2));
		this.lblPort = new JLabel("Le port du serveur : ");
		this.txtPort = new JTextField();
		this.txtPort.setEditable(true);
				
		this.panelPortServeur.add(this.lblPort);
		this.panelPortServeur.add(this.txtPort);
		
		//BtnStart
		this.btnStart = new JButton("Se connecter");
		
		this.setLayout(new GridLayout(4,1));
		this.add(this.panelNomJoueur);
		this.add(this.panelIPServeur);
		this.add(this.panelPortServeur);
		this.add(this.btnStart);
		this.btnStart.addActionListener(this);
		
		this.pack();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e)
	{
		String pseudo = this.txtNomJoueur.getText();
		String ip = this.txtIP.getText();
		String port = this.txtPort.getText();
		
		ClientMafiaDeCuba client = new ClientMafiaDeCuba(pseudo, ip, port);
		this.dispose();
	}
	
	public static void main(String[] args)
	{
		FrameJoin fJ = new FrameJoin();
	}
	
	
}
