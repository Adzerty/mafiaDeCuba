package mafiaDeCuba;

import mafiaDeCuba.metier.MafiaDeCuba;

public class Controleur
{
	
	private MafiaDeCuba metier;
	
	public Controleur(int nbJ, ArrayList<Joueur> al)
	{
		this.metier = new MafiaDeCuba(this, nbJ, false, al);
	}

	public static void main(String[] args)
	{
		Controleur c = new Controleur(9);
	}

}
