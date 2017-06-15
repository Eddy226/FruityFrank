package fr.mreddy.fruity.ecran;
import fr.mreddy.fruity.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.KeyEvent;

public class EcranConfig extends EcranTemporise
{
	private Image imgConfig;
	
	public EcranConfig()
	{
		super(60);
		imgConfig = Parametres.applet.getImage(Parametres.applet.getCodeBase(),  
					Parametres.DOSSIER_IMAGES + "/config.jpg");
	
		MediaTracker mt = new MediaTracker(Parametres.applet);
		mt.addImage(imgConfig, 0);
		
		try 
		{
			mt.waitForAll();
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}

		if ( ! mt.checkAll() )
			System.out.println("EcranConfig : Erreur chargement Image");
	}
	
	public void animer()
	{
		if ( Parametres.clavier.isTouche(KeyEvent.VK_ENTER, true) )
			Parametres.applet.changerEcranCourant(Parametres.ecranTitre);
		
		if ( temporiser() )
			Parametres.applet.changerEcranCourant(Parametres.ecranMeilleursScores);
	}
	
	public void dessiner(Graphics g) 
	{
		g.clearRect(0, 0, Parametres.LARGEUR_JEU, Parametres.HAUTEUR_JEU);
		g.drawImage(imgConfig, 	
					(Parametres.LARGEUR_JEU - imgConfig.getWidth(null) ) / 2, 
					(Parametres.HAUTEUR_JEU - imgConfig.getHeight(null)) / 2,
					null);
	}
}
