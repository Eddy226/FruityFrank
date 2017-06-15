package fr.mreddy.fruity;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import fr.mreddy.fruity.ecran.EcranTemporise;

public class EcranGameOver extends EcranTemporise 
{
	private int iNbAffichage, iNbClignotement;
	private String sMessage = "G A M E   O V E R";
	private int iPosX, iPosY;
	
	public EcranGameOver() 
	{
		super(5);
	}

	public void charger() 
	{
		iNbAffichage = 0;
		iNbClignotement = 0;
	}
	
	@Override
	public void dessiner(Graphics g) 
	{
		if ( iNbAffichage == 0 )
		{
			g.setColor( Color.WHITE );
			g.setFont( new Font("IMPACT", Font.BOLD, 15) );

			FontMetrics fontMetrics = g.getFontMetrics();
			iPosX = (Parametres.LARGEUR_JEU - fontMetrics.stringWidth(sMessage)) / 2;
			iPosY = (Parametres.HAUTEUR_JEU - fontMetrics.getHeight()) / 2;

		}
		iNbAffichage++;
		
		if ( iNbAffichage <= sMessage.length() )
			g.drawString( sMessage.substring(0, iNbAffichage), iPosX, iPosY );
		else
		{
			if ( iNbClignotement % 2 == 0 )
				g.clearRect(0, ManagerNiveaux.HAUTEUR_ZONE_INFOS, Parametres.LARGEUR_JEU, Parametres.HAUTEUR_JEU - ManagerNiveaux.HAUTEUR_ZONE_INFOS);
			else
				g.drawString( sMessage, iPosX, iPosY );
			iNbClignotement++;
		}
		
		if ( iNbClignotement > 30 )
			Parametres.applet.changerEcranCourant(Parametres.ecranTitre);
		
	}
	
}
