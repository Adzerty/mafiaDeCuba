package mafiaDeCuba.metier;

import java.util.ArrayList;

public class Boite
{
	private ArrayList<Jeton> ensJeton;
	private int		nbDiamantRestant;
	
	/* CONSTRUCTOR */
	public Boite(ArrayList<Jeton> al)
	{
		this.ensJeton = al;
		this.nbDiamantRestant = 15;
	}

	
	/* GETTERS */
	public ArrayList<Jeton> getEnsJeton()
	{
		return ensJeton;
	}

	public int getNbDiamantRestant()
	{
		return nbDiamantRestant;
	}
	
	/* TOSTRING */
	public String toString()
	{
		String sRet = "";
		for(int i = 0; i<this.ensJeton.size(); i++)
			sRet += this.ensJeton.get(i).toString() + '\n';
		
		return sRet;
			
	}
	
	
}
