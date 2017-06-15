package fr.mreddy.fruity.sprite;

import fr.mreddy.fruity.*;
import fr.mreddy.fruity.cellule.Cellule;
import fr.mreddy.fruity.cellule.Rectangle;

import java.awt.Graphics;
import java.awt.Image;

public abstract class Sprite extends Rectangle
{
	public enum TypeEtat {ACTIF, EXPLOSION, MORT};
	
    protected Cellule celluleCourante;
    protected Cellule celluleSuivante;
    /**Pixel Déplacement du sprite*/
    protected int deplacementX, deplacementY;
    protected Image imgSprite;
    protected int numImageCourante;
    protected int numDirectionCourante;
    protected int NB_IMAGES;
    protected int IMAGE_DROIT = 0;
    protected int IMAGE_GAUCHE = 1;
    protected int IMAGE_HAUTBAS = 2;
    /**Etat du sprite*/
    private TypeEtat etat;
    
    public Sprite(Image imgSprite, Cellule celluleCourante)
    {
    	super(celluleCourante);
    	
    	this.imgSprite = imgSprite;
        NB_IMAGES = this.imgSprite.getWidth(null) / getLargeur();
        numImageCourante = 0;
        reinitialiserDeplacement();
        this.celluleCourante = celluleCourante;
        //this.celluleSuivante = this.celluleCourante;
        etat = TypeEtat.ACTIF;
    }

	public void incrementerImageCourante()
    {
        numImageCourante++;
        if (numImageCourante >= NB_IMAGES)
            numImageCourante = 0;
    }
    
    protected boolean celluleAtteinte()
    {
        boolean atteint = false;

        if (deplacementX > 0 && getX() >= celluleSuivante.getX() && getY() == celluleSuivante.getY())
            atteint = true;
        if (deplacementX < 0 && getX() <= celluleSuivante.getX() && getY() == celluleSuivante.getY())
            atteint = true;
        if (deplacementY > 0 && getX() == celluleSuivante.getX() && getY2() >= celluleSuivante.getY2() )
            //if ( deplacement_y > 0 && posX == celluleSuivante.getX() && posY + imgSprite.getHeight(null) >= celluleSuivante.getY() + celluleSuivante.getHauteur() )
            atteint = true;		// Filtre sur le bas de l'image comme sprite descend
        if (deplacementY < 0 && getX() == celluleSuivante.getX() && getY() <= celluleSuivante.getY())
            atteint = true;

        // Corrige les erreurs de deplacements
        if (atteint)
        {
        	int iPosTmp = celluleSuivante.getX();
        	if ( getX() != iPosTmp )
        	{
        		System.out.println(getClass().getName() + ": décalage de position X de " + getX() + " à " + iPosTmp);
        		setX(iPosTmp);
        	}
        	
            //	Replace en fonction du bas du sprite
			if ( deplacementY > 0 )
				iPosTmp = celluleSuivante.getY2() - ManagerNiveaux.HAUTEUR_PIXEL_CELLULE;
            else
            	iPosTmp = celluleSuivante.getY();
        	if ( getY() != iPosTmp )
        	{
        		System.out.println(getClass().getName() + ": décalage de position Y de " + getY() + " à " + iPosTmp);
        		setY(iPosTmp);
        	}
        }
        return atteint;
    }

    /**Réinitialisation du déplacement du sprite*/
    protected void reinitialiserDeplacement() 
    {
        deplacementX = 0;
        deplacementY = 0;
	}

    /**Determine si le sprite est en mouvement*/
    protected boolean isMouvementEnCours()
    {
    	return deplacementX != 0 || deplacementY != 0; 
    }
	
	
    /**Affectation de l'etat du sprite*/
    public void setEtat(TypeEtat etat)
	{
    	this.etat = etat;
	}
	
    /**Retourne l'etat du sprite*/
	public TypeEtat getEtat()
	{
		return etat;
	}
	
    public void dessiner(Graphics g)
    {
        int srcX1 = numImageCourante * ManagerNiveaux.LARGEUR_PIXEL_CELLULE;
        int srcY1 = numDirectionCourante * ManagerNiveaux.HAUTEUR_PIXEL_CELLULE;
        int srcX2 = srcX1 + ManagerNiveaux.LARGEUR_PIXEL_CELLULE;
        int srcY2 = srcY1 + ManagerNiveaux.HAUTEUR_PIXEL_CELLULE;

        g.drawImage(imgSprite,
                getX(), getY(), getX2(), getY2(),
                srcX1, srcY1, srcX2, srcY2, null);

        /*System.out.println("Sprite : dessiner() Destination destX1=" + destX1 + " destY1=" + destY1
        + " destX2=" + destX2 + " destY2=" + destY2);*/
    }
    //protected abstract boolean isTraversable(Element e);
	public boolean intersect(Rectangle rectangle)
	{
		return rectangle.getX2() > getX() && getX2() > rectangle.getX()
		    && rectangle.getY2() > getY() && getY2() > rectangle.getY();
	}

	public String toString()
	{
        return super.toString() + "\nSprite: " + this;
	}
}
