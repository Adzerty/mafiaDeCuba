package mafiaDeCuba.ihm;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mafiaDeCuba.Controleur;
import mafiaDeCuba.reseaux.ServeurMafiaDeCuba;

public class FrameHost extends JFrame implements ActionListener
{	
	private int largeurEcran, hauteurEcran;
	private Dimension dimEcran;
	
	private JPanel panelBoutonsPrc;
	
		private JPanel panelNbJoueurs;
			private JLabel 	lblNombreJoueur;
			private JButton btnPlus;
			private JButton btnMoins;
			private JLabel 	lblChoixNbJoueur;
			
		private JPanel panelNettoyeur;
			private JCheckBox cBNettoyeur;
			private JLabel lblANettoyeur;
			
	private JButton btnStart;

	
	public FrameHost()
	{	
		this.dimEcran     = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		this.hauteurEcran = (int) dimEcran.getHeight();
		this.largeurEcran = (int) dimEcran.getWidth();
		
		this.setTitle("Mafia De Cuba - Héberger une partie");
		this.setLocation( (largeurEcran/2)-350, (hauteurEcran/2)-250);
		this.setSize(700,500);
		
		/* Création du panelNbJoueur */
		this.panelNbJoueurs = new JPanel(new GridLayout(1,4));
		this.lblNombreJoueur = new JLabel("Nombre de joueurs : ");
		
		this.btnPlus = new JButton("+");
		this.btnPlus.addActionListener(this);
		
		this.btnMoins = new JButton("-");
		this.btnMoins.addActionListener(this);
		
		this.lblChoixNbJoueur = new JLabel("6");
		
		this.panelNbJoueurs.add(this.lblNombreJoueur);
		this.panelNbJoueurs.add(this.btnPlus);
		this.panelNbJoueurs.add(this.lblChoixNbJoueur);
		this.panelNbJoueurs.add(this.btnMoins);
		
		/* Création du panelNettoyeur */
		this.panelNettoyeur = new JPanel(new GridLayout(1,2));
		this.lblANettoyeur = new JLabel("Jouer avec le nettoyeur : ");
		this.cBNettoyeur = new JCheckBox();
		
		this.panelNettoyeur.add(this.lblANettoyeur);
		this.panelNettoyeur.add(this.cBNettoyeur);
		
		/* Création du panelBoutonsPrc */
		this.panelBoutonsPrc = new JPanel(new GridLayout(2,1));
		this.panelBoutonsPrc.add(this.panelNbJoueurs);
		this.panelBoutonsPrc.add(this.panelNettoyeur);
		
		
		/* Création du bouton lancer le serveur */
		this.btnStart = new JButton("Lancer le serveur");
		this.btnStart.addActionListener(this);
		
		
		JLabel picLabel = new JLabel(new ImageIcon(this.getClass().getResource("/title.png")));
		
		this.add(this.panelBoutonsPrc, "North");
		this.add(picLabel,"Center");
		this.add(this.btnStart,"South");
		
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this.btnPlus || e.getSource() == this.btnMoins)
		{
		
			if(e.getSource() == this.btnPlus)
			{
				int nb = Integer.parseInt(this.lblChoixNbJoueur.getText()) + 1;
				if(nb > 12) nb = 6;
				this.lblChoixNbJoueur.setText(""+nb);
			}
			else
			{
				int nb = Integer.parseInt(this.lblChoixNbJoueur.getText()) - 1;
				if(nb < 6) nb = 12;
				this.lblChoixNbJoueur.setText(""+nb);
			}
		}else
		{
			if(e.getSource() == this.btnStart)
			{
				System.out.println("START");
				int nbJoueur = Integer.parseInt(this.lblChoixNbJoueur.getText());
				boolean aNettoyeur = this.cBNettoyeur.isSelected();
				
				new ServeurMafiaDeCuba(nbJoueur, aNettoyeur, this);
				this.dispose();
			}
		}
	}
	
	public static void main(String[] args)
	{
		FrameHost frm = new FrameHost();
	}

}
