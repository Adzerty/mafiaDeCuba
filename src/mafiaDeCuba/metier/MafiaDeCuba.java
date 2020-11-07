package mafiaDeCuba.metier;

import java.util.ArrayList;
import mafiaDeCuba.Controleur;

public class MafiaDeCuba
{
	private int nbJoueur;
	
	private Controleur ctrl;
	private Boite boite;
	private ArrayList<Joueur> ensJoueurs;
	
	public MafiaDeCuba(Controleur ctrl, int nbJ, boolean aNettoyeur, ArrayList<Joueur> al)
	{
		this.nbJoueur = nbJ;
		
		this.ctrl = ctrl;
		this.boite = this.genererBoite(nbJ,aNettoyeur);
		this.ensJoueurs = al;
	}
	
	private Boite genererBoite(int nbJ, boolean aNettoyeur)
	{
		ArrayList<Jeton> alJeton = new ArrayList<Jeton>();
		
		switch(nbJ)
		{
			case 6:
				ajouterJetonALaBoite(1,aNettoyeur,1,1,0,alJeton);
				break;
			case 7:
				ajouterJetonALaBoite(2,aNettoyeur,1,1,0,alJeton);
				break;
			case 8:
				ajouterJetonALaBoite(3,aNettoyeur,1,1,1,alJeton);
				break;
			case 9:
				ajouterJetonALaBoite(4,aNettoyeur,1,1,1,alJeton);
				break;
			case 10:
				ajouterJetonALaBoite(4,aNettoyeur,2,1,1,alJeton);
				break;
			case 11:
				ajouterJetonALaBoite(4,aNettoyeur,2,2,2,alJeton);
				break;
			case 12:
				ajouterJetonALaBoite(5,aNettoyeur,2,2,2,alJeton);
				break;
			default:
				return null;
		}
		
		return new Boite(alJeton);
	}
	
	// Méthode ajouterJetonALaBoite
	// Permet d'ajouter les jetons nécessaires à la boîte selon le nombre de joueur
	private static void ajouterJetonALaBoite(int nbFidele, boolean aNettoyeur, int nbAgent, int nbChauffeur, int nbJoker, ArrayList<Jeton> alJeton)
	{
		for(int i = 0; i < nbFidele; i++)
			if(i == 0)
				if(aNettoyeur)
					alJeton.add(new Jeton("Nettoyeur", "Rouge"));
				else
					alJeton.add(new Jeton("Fidèle", "Blanc"));
			else
				alJeton.add(new Jeton("Fidèle", "Blanc"));
		
		for(int i = 0; i < nbAgent; i++)
			alJeton.add(new Jeton("Agent", "Bleu"));
		
		for(int i = 0; i < nbChauffeur; i++)
			alJeton.add(new Jeton("Chauffeur", "Vert"));
		
		for(int i = 0; i < nbJoker; i++)
			alJeton.add(new Jeton("Joker", "Joker"));
	}

	
	/* GETTERS */
	public int getNbJoueur()
	{
		return nbJoueur;
	}

	public Controleur getCtrl()
	{
		return ctrl;
	}

	public Boite getBoite()
	{
		return boite;
	}

	public ArrayList<Joueur> getEnsJoueurs()
	{
		return ensJoueurs;
	}
	
	/* SETTERS */
	public void setEnsJoueur(ArrayList<Joueur> al)
	{
		this.ensJoueurs = al;
	}
	
}
