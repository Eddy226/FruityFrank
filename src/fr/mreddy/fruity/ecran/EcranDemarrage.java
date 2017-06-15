package fr.mreddy.fruity.ecran;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;

import fr.mreddy.fruity.Parametres;

public class EcranDemarrage extends EcranTemporise 
{
	private Image imgDemarrage;
	
	public EcranDemarrage() 
	{
		super(80);
		//imgDemarrage = Parametres.applet.getImage( getClass().getResource(Parametres.DOSSIER_IMAGES + "/fruityfrank.png"));
		imgDemarrage = Parametres.applet.getImage(Parametres.applet.getCodeBase(),  
				Parametres.DOSSIER_IMAGES + "/fruityfrank.png");

		MediaTracker mt = new MediaTracker(Parametres.applet);
		mt.addImage(imgDemarrage, 0);

		try 
		{
			mt.waitForAll();
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}

		if ( ! mt.checkAll() )
			System.out.println(getClass().getName() + ": Erreur chargement Image");
	}

	public void animer()
	{
		if ( temporiser() )
			Parametres.applet.changerEcranCourant(Parametres.ecranTitre);
	}
	
	@Override
	public void dessiner(Graphics g) 
	{
		g.clearRect(0, 0, Parametres.LARGEUR_JEU, Parametres.HAUTEUR_JEU);
		g.drawImage(imgDemarrage, (Parametres.LARGEUR_JEU - imgDemarrage.getWidth(null)) / 2, 
				(Parametres.HAUTEUR_JEU - imgDemarrage.getHeight(null)) / 2, null);
	}
}
