package fr.mreddy.fruity.ecran;
import fr.mreddy.fruity.*;
import java.awt.Color;
import java.awt.Graphics;

public class EcranInfosNiveau extends EcranTemporise
{
	public EcranInfosNiveau() 
	{
		super(12);	// Temporisation de xx
	}

	public void animer()
	{
		if ( temporiser() )
			Parametres.applet.changerEcranCourant(Parametres.ecranJeu);	
	}
	
	
	public void dessiner(Graphics g) 
	{
		g.clearRect(0, 0, Parametres.LARGEUR_JEU, Parametres.HAUTEUR_JEU);
		g.setFont(Parametres.grandePolice);
		g.setColor(Color.magenta);
		g.drawString("LEVEL ", 250, Parametres.HAUTEUR_JEU / 2); 
		g.setColor(Color.yellow);
		g.drawString(" " + Parametres.partieEnCours.getNiveauEnCours(), 320, Parametres.HAUTEUR_JEU / 2);	
	}

}