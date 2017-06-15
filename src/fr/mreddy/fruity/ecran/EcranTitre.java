package fr.mreddy.fruity.ecran;
import fr.mreddy.fruity.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.KeyEvent;


/**
 * @author MrEddy
 *
 */
public class EcranTitre extends EcranTemporise 
{
	private Image imgTitre;
	
	/**Chargement des images*/
	public EcranTitre()
	{
		super(30);	// Temporisation de xx
		imgTitre = Parametres.applet.getImage(Parametres.applet.getCodeBase(),  
							Parametres.DOSSIER_IMAGES + "/ecranstart.jpg");
	
		MediaTracker mt = new MediaTracker(Parametres.applet);
		mt.addImage(imgTitre, 0);
		
		try 
		{
			mt.waitForAll();
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		if ( ! mt.checkAll() )
			System.out.println("EcranAccueil : Erreur chargement Image");
	}

	public void animer()
	{
		if ( Parametres.clavier.isTouche(KeyEvent.VK_ENTER, true) )
		{
			// Démarrage du jeu
			Parametres.partieEnCours = new Partie();
			Parametres.applet.changerEcranCourant(Parametres.ecranInfosNiveau);
		}
		
		if ( temporiser() )
			Parametres.applet.changerEcranCourant(Parametres.ecranConfig);
	}
	
	/**Dessine l'image d'accueil et un message d'infos*/
	public void dessiner(Graphics g) 
	{
		g.clearRect(0, 0, Parametres.LARGEUR_JEU, Parametres.HAUTEUR_JEU);
		g.drawImage(imgTitre, (Parametres.LARGEUR_JEU - imgTitre.getWidth(null)) / 2, 
				(Parametres.HAUTEUR_JEU - imgTitre.getHeight(null)) / 3, null);
	
		g.setFont(Parametres.moyennePolice);
		g.setColor(Color.yellow);	
		
		// TODO A centrer ... pkoi pas
		g.drawString("Developed by MrEddy - 2010", 200, Parametres.HAUTEUR_JEU - 50);
		g.drawString("Click here and push Enter key to begin the game", 120, Parametres.HAUTEUR_JEU - 30);	
	}
}
