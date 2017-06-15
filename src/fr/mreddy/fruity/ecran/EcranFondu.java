package fr.mreddy.fruity.ecran;
import fr.mreddy.fruity.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class EcranFondu extends EcranModele
{
	/**Nombre d'animation de l'ecran*/
	private int iNbAnimation;
	
	private Rectangle rectHaut;
	private Rectangle rectBas;
	private Rectangle rectGauche;
	private Rectangle rectDroit;
	
	private int vitesse_verticale = 2;
	private int vitesse_horizontale = 3;
	
	// Simple balayage vu que le backbuffer n'est pas efface
	public EcranFondu()
	{
		rectHaut = new Rectangle();
		rectBas = new Rectangle();
		rectGauche = new Rectangle();
		rectDroit = new Rectangle();
		
		init();
	}
	
	public void charger()
	{
		rectHaut.setBounds(0, ManagerNiveaux.HAUTEUR_ZONE_INFOS, Parametres.LARGEUR_JEU, vitesse_verticale+5);
		
		rectBas.setBounds(0, Parametres.HAUTEUR_JEU, Parametres.LARGEUR_JEU, vitesse_verticale+5);
		
		rectGauche.setBounds(0, ManagerNiveaux.HAUTEUR_ZONE_INFOS, vitesse_horizontale+5, Parametres.HAUTEUR_JEU - ManagerNiveaux.HAUTEUR_ZONE_INFOS);
		
		rectDroit.setBounds(Parametres.LARGEUR_JEU, ManagerNiveaux.HAUTEUR_ZONE_INFOS, vitesse_horizontale+5, Parametres.HAUTEUR_JEU - ManagerNiveaux.HAUTEUR_ZONE_INFOS);
	}


	public void init() 
	{
		iNbAnimation = 0;
		vitesse_verticale = 2;
		vitesse_horizontale = 3;
	}
	
	public void animer()
	{
		iNbAnimation++;
		
		// Augmentation de la vitesse tous les 10 frames
		if ( iNbAnimation % 20 == 0.0 )
		{
			//System.out.println(iNbAnimation + " " + vitesse_horizontale);
			vitesse_horizontale++;
			vitesse_verticale++;
		}

		rectHaut.setLocation(rectHaut.x, rectHaut.y + vitesse_verticale);
		rectBas.setLocation(rectBas.x, rectBas.y - vitesse_verticale);
		
		rectGauche.setLocation(rectGauche.x + vitesse_horizontale, rectGauche.y);
		rectDroit.setLocation(rectDroit.x - vitesse_horizontale, rectDroit.y);
		
		if ( rectGauche.x > rectDroit.x )	// A modifier avec si rectdroit.largeur > rectGa.x
		{
			if ( Parametres.partieEnCours.isFin() )
				Parametres.applet.changerEcranCourant(Parametres.ecranGameOver);
			else
				Parametres.applet.changerEcranCourant(Parametres.ecranInfosNiveau);
		}
	}
	
	public void dessiner(Graphics g) 
	{
		//System.out.println("EcranFondu: dessiner()");
		Graphics2D g2D = (Graphics2D)g;
		//g2D.clearRect(0, 0, Parametres.applet.getWidth(), Parametres.applet.getHeight());
		
		g2D.setColor(Color.black);
		//System.out.println("PosX=" + rectGauche.x + " PosY=" + rectGauche.y + " Largeur=" + rectGauche.width + " Hauteur=" + rectGauche.height);
		g2D.fill(rectHaut);
		g2D.fill(rectBas);
		g2D.fill(rectGauche);
		g2D.fill(rectDroit);
		
		//g2D.dispose();
	}
}