package fr.mreddy.fruity.sprite;

import java.awt.Graphics;

import fr.mreddy.fruity.ManagerNiveaux;
import fr.mreddy.fruity.Parametres;
import fr.mreddy.fruity.cellule.Rectangle;

public class ExplosionJoueur extends Explosion
{
	private Rectangle rectMG, rectMH, rectMD, rectMB;
	
	public ExplosionJoueur(Sprite sprite) 
	{
		super(sprite);

		int x = sprite.getX() + (sprite.getLargeur() - NB_PIXEL_LARGEUR) / 2;
		int y = sprite.getY() + (sprite.getHauteur() - NB_PIXEL_HAUTEUR) / 2;
	
		rectMG = new Rectangle(sprite.getX() + iDepartDansSprite, y, NB_PIXEL_LARGEUR, NB_PIXEL_HAUTEUR);
		rectMH = new Rectangle(x, sprite.getY() + iDepartDansSprite, NB_PIXEL_LARGEUR, NB_PIXEL_HAUTEUR);
		rectMD = new Rectangle(sprite.getX2() - iDepartDansSprite - NB_PIXEL_LARGEUR, y, NB_PIXEL_LARGEUR, NB_PIXEL_HAUTEUR);
		rectMB = new Rectangle(x, sprite.getY2() - iDepartDansSprite - NB_PIXEL_HAUTEUR, NB_PIXEL_LARGEUR, NB_PIXEL_HAUTEUR);
	}

	public void animer()
	{
		super.animer();
	
		if ( rectMG != null )
		{
			rectMG.deplacer(-iVitesse, 0);
			if ( rectMG.getX() < ManagerNiveaux.CONTOUR_JEU )
				rectMG = null;
		}

		if ( rectMH != null )
		{
			rectMH.deplacer(0, -iVitesse);
			if ( rectMH.getY() < ManagerNiveaux.HAUTEUR_ZONE_INFOS + ManagerNiveaux.CONTOUR_JEU )
				rectMH = null;
		}

		if ( rectMD != null )
		{
			rectMD.deplacer(iVitesse, 0);
			if ( rectMD.getX2() > Parametres.LARGEUR_JEU - ManagerNiveaux.CONTOUR_JEU )
				rectMD = null;
		}
		
		if ( rectMB != null )
		{
			rectMB.deplacer(0, iVitesse);
			if ( rectMB.getY2() > Parametres.HAUTEUR_JEU - ManagerNiveaux.CONTOUR_JEU )
				rectMB = null;
		}
	}
	
	public void dessiner(Graphics g) 
	{
		super.dessiner(g);
	
		if ( rectMG != null )
			g.fillRect(rectMG.getX(), rectMG.getY(), rectMG.getLargeur(), rectMG.getHauteur());
		if ( rectMH != null )
			g.fillRect(rectMH.getX(), rectMH.getY(), rectMH.getLargeur(), rectMH.getHauteur());
		if ( rectMD != null )
			g.fillRect(rectMD.getX(), rectMD.getY(), rectMD.getLargeur(), rectMD.getHauteur());
		if ( rectMB != null )
			g.fillRect(rectMB.getX(), rectMB.getY(), rectMB.getLargeur(), rectMB.getHauteur());
	}

	public boolean isFinAnimation() 
	{
		return super.isFinAnimation() && rectMG == null && rectMH == null && rectMD == null && rectMB == null;
	}
}
