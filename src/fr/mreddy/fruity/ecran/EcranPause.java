package fr.mreddy.fruity.ecran;
import fr.mreddy.fruity.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class EcranPause extends EcranModele
{
	// Astuce pour eviter de supprimer la pause tout de suite ...
	// A revoir surement
	//private boolean toucheRelachee; 
	
	/*public void charger()
	{
		toucheRelachee = false;
	}*/
	
	public void animer()
	{
		//System.out.println("EcranPause: aniner()");
		//if ( ! Parametres.clavier.tabEtatTouches[KeyEvent.VK_P])
		//	toucheRelachee = true;
		//if ( toucheRelachee && Parametres.clavier.tabEtatTouches[KeyEvent.VK_P] )
		if ( Parametres.clavier.isTouche(KeyEvent.VK_P, true) )
			Parametres.applet.changerEcranCourant(Parametres.ecranPrecedent);
	}

	public void dessiner(Graphics g) 
	{
		//System.out.println("EcranPause: dessiner()");
		g.setColor(Color.green);
		//TODO voir a calculer pour le placer automatiquement dans le chemin horizontale milieu / centre
		g.drawString("P a u s e", Parametres.LARGEUR_JEU / 2 - 30, Parametres.HAUTEUR_JEU / 2 + 10);	
	}
	
	
}