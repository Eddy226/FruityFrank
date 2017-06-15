package fr.mreddy.fruity.sprite;

import java.awt.Color;
import java.awt.Graphics;

import fr.mreddy.fruity.ManagerNiveaux;
import fr.mreddy.fruity.Parametres;
import fr.mreddy.fruity.cellule.Rectangle;


//TODO implementer une interface genre Sprite afin d'utiliser des listes
public class Explosion
{
	protected final static int NB_PIXEL_LARGEUR = 8;
	protected final static int NB_PIXEL_HAUTEUR = 6;
	
	private Rectangle rectHG, rectHD, rectBG, rectBD;
	
	protected int iDepartDansSprite, iVitesse;
	
	private Color couleur;
	
	public Explosion(Sprite sprite)
	{
		iDepartDansSprite = 8;
		iVitesse = 10;
		
		switch ( Parametres.partieEnCours.getNiveauJeu() )
		{
			case 1:
				couleur = new Color(255, 0, 0);
				break;
			case 2:
				couleur = new Color(255, 255, 0);
				break;
			case 3:
				couleur = new Color(146, 0, 255);
				break;
			case 4:
				couleur = new Color(0, 255, 255);
				break;
			case 5:
				couleur = new Color(146, 255, 0);
				break;
			case 6:
				couleur = new Color(255, 255, 255);
				break;		
			case 7:
				couleur = new Color(146, 0, 170);
				break;		
		}
		
		rectHG = new Rectangle(sprite.getX() + iDepartDansSprite, sprite.getY() + iDepartDansSprite, NB_PIXEL_LARGEUR, NB_PIXEL_HAUTEUR);
		rectHD = new Rectangle(sprite.getX2() - iDepartDansSprite - NB_PIXEL_LARGEUR, sprite.getY() + iDepartDansSprite, NB_PIXEL_LARGEUR, NB_PIXEL_HAUTEUR);
		rectBG = new Rectangle(sprite.getX() + iDepartDansSprite, sprite.getY2() - iDepartDansSprite - NB_PIXEL_HAUTEUR, NB_PIXEL_LARGEUR, NB_PIXEL_HAUTEUR);
		rectBD = new Rectangle(sprite.getX2() - iDepartDansSprite - NB_PIXEL_LARGEUR, sprite.getY2() - iDepartDansSprite - NB_PIXEL_HAUTEUR, NB_PIXEL_LARGEUR, NB_PIXEL_HAUTEUR);	
	}
	
	public void animer()
	{
		if ( rectHG != null )
		{
			rectHG.deplacer(-iVitesse, -iVitesse);
			if ( rectHG.getX() < ManagerNiveaux.CONTOUR_JEU || rectHG.getY() < ManagerNiveaux.HAUTEUR_ZONE_INFOS + ManagerNiveaux.CONTOUR_JEU )
				rectHG = null;
		}
		
		if ( rectHD != null )
		{
			rectHD.deplacer(iVitesse, -iVitesse);
			if ( rectHD.getX2() > Parametres.LARGEUR_JEU - ManagerNiveaux.CONTOUR_JEU || rectHD.getY() < ManagerNiveaux.HAUTEUR_ZONE_INFOS + ManagerNiveaux.CONTOUR_JEU)
				rectHD = null;
		}
		
		if ( rectBG != null )
		{
			rectBG.deplacer(-iVitesse, iVitesse);
			if ( rectBG.getX() < ManagerNiveaux.CONTOUR_JEU || rectBG.getY2() > Parametres.HAUTEUR_JEU - ManagerNiveaux.CONTOUR_JEU )
				rectBG = null;
		}
		
		if ( rectBD != null )
		{
			rectBD.deplacer(iVitesse, iVitesse);
			if ( rectBD.getX2() > Parametres.LARGEUR_JEU - ManagerNiveaux.CONTOUR_JEU || rectBD.getY2() > Parametres.HAUTEUR_JEU - ManagerNiveaux.CONTOUR_JEU)
				rectBD = null;
		}
	}
	
	public void dessiner(Graphics g) 
	{
		//Color couleurOrigine = g.getColor();
		g.setColor( couleur );
		if ( rectHG != null )
			g.fillRect(rectHG.getX(), rectHG.getY(), rectHG.getLargeur(), rectHG.getHauteur());
		if ( rectHD != null )
			g.fillRect(rectHD.getX(), rectHD.getY(), rectHD.getLargeur(), rectHD.getHauteur());
		if ( rectBG != null )
			g.fillRect(rectBG.getX(), rectBG.getY(), rectBG.getLargeur(), rectBG.getHauteur());
		if ( rectBD != null )
			g.fillRect(rectBD.getX(), rectBD.getY(), rectBD.getLargeur(), rectBD.getHauteur());
	}

	public boolean isFinAnimation() 
	{
		return rectHG == null && rectHD == null && rectBG == null && rectBD == null;
	}
}
