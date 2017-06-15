package fr.mreddy.fruity.cellule;

import fr.mreddy.fruity.sprite.Centre;
import fr.mreddy.fruity.sprite.Sprite;

public class CelluleCentre extends Cellule
{
	private Sprite centre;
	
	public CelluleCentre(int posX, int posY, int largeur, int hauteur) 
	{
		super(posX, posY, largeur, hauteur);
	}

	/*public CelluleCentre(Centre centre, int posX, int posY, int largeur, int hauteur) 
	{
		super(posX, posY, largeur, hauteur);
		this.centre = centre;
	}*/
	
	/**Affectation du sprite positionn� sur la cellule du centre
	 * si d�part du sprite de la cellule alors r�affectation du sprite du centre si celui ci n'est pas mang�
	 * par le joueur*/
    public void setSprite(Sprite sprite)
    {
    	if ( sprite == null )
    		super.setSprite(centre);
    	else
    		super.setSprite(sprite);
    }
	
}
