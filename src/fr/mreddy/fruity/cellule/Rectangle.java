package fr.mreddy.fruity.cellule;

public class Rectangle 
{
    private int posX, posY;
    private int largeur, hauteur;


    public Rectangle(int posX, int posY, int largeur, int hauteur)
    {
        setX(posX);
        setY(posY);
        this.largeur = largeur;
        this.hauteur = hauteur;
    }
    
    public Rectangle(Rectangle rectangle) 
    {
    	this(rectangle.getX(), rectangle.getY(), rectangle.getLargeur(), rectangle.getHauteur());
	}

	public void setX(int iPosX)
    {
        posX = iPosX;
    }
	
	public int getX()
    {
        return posX;
    }

    public void setY(int iPosY)
    {
        posY = iPosY;
    }

    /**Retourne la position Y du rectangle*/
    public int getY()
    {
        return posY;
    }

    /**Retourne la position X droite du rectangle*/
    public int getX2()
    {
    	return posX + largeur;
    }
    
    /**Retourne la position Y bas du rectangle*/
    public int getY2()
    {
    	return posY + hauteur;
    }
    
    /**Retourne la hauteur du rectangle*/
    public int getHauteur()
    {
        return hauteur;
    }
    
    /**Retourne la largeur du rectangle*/
    public int getLargeur() 
    {
		return largeur;
	}

	public void deplacer(int x, int y) 
	{
		posX += x;
		posY += y;
	}
	
    /**Retourne un rectangle selon la position de l'objet courant et du déplacement demandé*/
	protected Rectangle getRectangle(int vitesseX, int vitesseY) 
	{
		Rectangle r = new Rectangle(this);
		r.deplacer(vitesseX, vitesseY);
		return r;
	}
}
