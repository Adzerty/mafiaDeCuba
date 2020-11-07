package mafiaDeCuba.metier;

public class Jeton
{
	public String lienImg;
	public String nom;
	
	/* CONSTRUCTOR */
	public Jeton(String nom, String couleur)
	{
		this.lienImg = "/jeton"+couleur+".png";
		this.nom = nom;
	}
	
	/* GETTERS & SETTERS */
	public String getLienImg()
	{
		return lienImg;
	}

	public void setLienImg(String lienImg)
	{
		this.lienImg = lienImg;
	}

	public String getNom()
	{
		return nom;
	}

	public void setNom(String nom)
	{
		this.nom = nom;
	}
	
	/* TOSTRING */
	public String toString()
	{
		return this.nom;
	}
	
}
