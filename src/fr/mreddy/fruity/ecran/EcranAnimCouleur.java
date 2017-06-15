package fr.mreddy.fruity.ecran;
import java.awt.Graphics;

import fr.mreddy.fruity.ManagerNiveaux;
import fr.mreddy.fruity.Parametres;

public class EcranAnimCouleur extends EcranModele 
{
	public void animer()
	{
		int nb = 0;
		for (int x=0; x<Parametres.LARGEUR_JEU; x++)
		{
			for (int y=ManagerNiveaux.HAUTEUR_ZONE_INFOS; y<Parametres.HAUTEUR_JEU; y++)
			{
				 
				//if ( Color.black.getRGB() == Parametres.applet.buffImg.getRGB(x, y) )
				//{
					nb++;
					//Parametres.applet.buffImg.setRGB(x, y, Color.black.getRGB());				
				//}
				
			}
		}	
		//System.out.println("EcranAnimCouleur : " + nb + " recherche " + Color.black);
	
	}
	
	public void dessiner(Graphics g) 
	{
		
	}
	

}
