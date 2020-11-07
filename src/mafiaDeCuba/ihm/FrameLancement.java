package mafiaDeCuba.ihm;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mafiaDeCuba.Controleur;

public class FrameLancement extends JFrame implements ActionListener
{
	private int largeurEcran, hauteurEcran;
	private Dimension dimEcran;
	
	private JPanel panelNord;
	private JPanel panelCenter;
	
	private JButton btnHeberger;
	private JButton btnRejoindre;
	
	public FrameLancement()
	{
		
		this.dimEcran     = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		this.hauteurEcran = (int) dimEcran.getHeight();
		this.largeurEcran = (int) dimEcran.getWidth();
		
		this.setTitle("Mafia de cuba - Java edition");
		this.setLocation( (largeurEcran/2)-350, (hauteurEcran/2)-250);
		this.setSize(700,500);
		
		/* Création du panel du haut */
		this.panelNord = new JPanel();

		this.btnHeberger = new JButton("Héberger une partie");
		this.btnRejoindre = new JButton("Rejoindre une partie");
		
		this.btnHeberger.addActionListener(this);
		this.btnRejoindre.addActionListener(this);
		
		this.panelNord.add(this.btnHeberger);
		this.panelNord.add(this.btnRejoindre);
	
		
		/* Création du panel du centre */
		this.panelCenter = new JPanel();
		
		JLabel picLabel = new JLabel(new ImageIcon(this.getClass().getResource("/title.png")));
		this.panelCenter.add(picLabel);
		
		this.add(this.panelNord,"North");
		this.add(this.panelCenter,"Center");
		
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static void main(String[] args)
	{
		FrameLancement frm = new FrameLancement();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this.btnHeberger)
		{
			new FrameHost();
			this.dispose();
		}
		else
			if(e.getSource() == this.btnRejoindre)
			{
				new FrameJoin();
				this.dispose();
			}
	}
	
}
