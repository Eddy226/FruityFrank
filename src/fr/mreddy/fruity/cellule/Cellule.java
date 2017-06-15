package fr.mreddy.fruity.cellule;

import java.awt.Graphics;

import fr.mreddy.fruity.ManagerNiveaux;
import fr.mreddy.fruity.sprite.Sprite;

public class Cellule extends Rectangle
{
    private Element element;
    private Sprite sprite;
    private Cellule celluleNord;
    private Cellule celluleSud;
    private Cellule celluleOuest;
    private Cellule celluleEst;

    public Cellule(int posX, int posY, int largeur, int hauteur)
    {
    	super(posX, posY, largeur, hauteur);
    }

	public boolean isEntreCellule() 
	{
		return getHauteur() == ManagerNiveaux.HAUTEUR_PIXEL_INTERCELLULE;
	}
	
    public void setElement(Element element)
    {
        this.element = element;
    }

    public Element getElement()
    {
        return element;
    }
  
    public void setSprite(Sprite sprite)
    {
    	this.sprite = sprite;
    }
    
    public Sprite getSprite() 
	{
		return sprite;
	}

    public void setCelluleNord(Cellule cellule)
    {
        this.celluleNord = cellule;
    }

    public Cellule getCelluleNord()
    {
        return celluleNord;
    }

    public void setCelluleSud(Cellule cellule)
    {
        this.celluleSud = cellule;
    }

    public Cellule getCelluleSud()
    {
        return celluleSud;
    }

    public void setCelluleOuest(Cellule cellule)
    {
        this.celluleOuest = cellule;
    }

    public Cellule getCelluleOuest()
    {
        return celluleOuest;
    }

    public void setCelluleEst(Cellule cellule)
    {
        this.celluleEst = cellule;
    }

    public Cellule getCelluleEst()
    {
        return celluleEst;
    }
	
    public void dessiner(Graphics g)
    {
    	if ( element != null )
    	{
            /*System.out.println(getClass().getName() + ": Cellule " + element.name() + ": dessiner() posX=" + posX +
        			" posY=" + posY + " largeur=" + largeur + " hauteur=" + hauteur);*/
    		g.drawImage(element.getImage(),
	                getX(), getY(), getLargeur(), getHauteur(), null);
    	}
    }

    /**Retournes qq infos*/
    public String toString()
    {
        return super.toString() + "\nCellule:"
        						+ "\n- element=" + element
        						+ "\n- sSprite: " + sprite;
    }


}
