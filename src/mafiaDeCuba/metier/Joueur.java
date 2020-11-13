package mafiaDeCuba.metier;

import java.io.Serializable;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Joueur implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	private String prenom;
	private int port;
	private InetAddress iA;
	private int nbDiamantPris;
	private Jeton role;
	
	/* CONSTRUCTEUR */
	public Joueur(String prenom)
	{
		this.prenom = prenom;
	}
	
	/* GETTERS & SETTERS */
	public String getPrenom()
	{
		return prenom;
	}
	public void setPrenom(String prenom)
	{
		this.prenom = prenom;
	}
	
	public int getNbDiamantPris()
	{
		return nbDiamantPris;
	}
	public void setNbDiamantPris(int nbDiamantPris)
	{
		this.nbDiamantPris = nbDiamantPris;
	}
	
	public Jeton getRole()
	{
		return role;
	}
	public void setRole(Jeton role)
	{
		this.role = role;
	}
	
	public void setPort(int port)
	{
		this.port = port;
	}
	
	public int getPort()
	{
		return this.port;
	}
	
	public InetAddress getiA()
	{
		return iA;
	}

	public void setiA(InetAddress iA)
	{
		this.iA = iA;
	}

	/* ToString */
	@Override
	public String toString()
	{
		return "Joueur : " + this.prenom + '\n' + "Port : " + port + " iAdress : " + iA;
	}
	
	
	
}
