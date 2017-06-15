package fr.mreddy.fruity.sprite;

import java.awt.Image;

import fr.mreddy.fruity.ManagerMonstres;
import fr.mreddy.fruity.ManagerNiveaux;
import fr.mreddy.fruity.cellule.Cellule;
import fr.mreddy.fruity.cellule.Element;
import fr.mreddy.fruity.cellule.Rectangle;

public class Pomme extends SpriteTemporise
{
    private static final int VITESSE_INTERCELLULE = 2;
    private ManagerNiveaux managerNiveaux;
    private ManagerMonstres managerMonstre;
    protected int vitesseY;
    /**Nombre de cellule descendue*/
    private int iNbCelluleDescendue;
    
	public Pomme(Image imgSprite, ManagerNiveaux managerNiveaux, ManagerMonstres managerMonstre, Cellule celluleCourante)
    {
        super(imgSprite, celluleCourante, 0);
        this.managerNiveaux = managerNiveaux;
        this.managerMonstre = managerMonstre;
        vitesseY = 6;
    }

    protected void animerTemporisation()
    {
    	if ( getEtat() == TypeEtat.ACTIF )
    	{
        	if ( isMouvementEnCours() )
        	{
        		deplacer(deplacementX, deplacementY);
            	
        		if ( celluleAtteinte() )
        		{
        			mangerElement();
        			verifierExplosion();
        			if ( getEtat() == TypeEtat.ACTIF )
        				deplacerBas();
        		}
        	}
        	else
        		deplacerBas();
    	}
    	else if ( getEtat() == TypeEtat.EXPLOSION )
    	{
    		incrementerImageCourante();
    		if ( numImageCourante == 0 )
    		{
    			setEtat(TypeEtat.MORT);
    			celluleCourante.setSprite(null);
    		}
    	}
    }
	
	private void verifierExplosion() 
	{
		Cellule celluleSud = celluleCourante.getCelluleSud();
		if ( celluleSud == null || celluleSud.isEntreCellule() && celluleSud.getElement() == null )
   		{
			if ( iNbCelluleDescendue >= 5 )
			{
				setEtat(TypeEtat.EXPLOSION);
	    		setTemporisation(1);
			}
   		}
	}

	public void deplacerBas()
    {
		if ( isTraversable(celluleCourante.getCelluleSud(), Direction.BAS) )
		{
	        // Si cellule contient elementfond alors vitesse moindre que si chemin
	        if (celluleCourante.getCelluleSud().getElement() == null)
	        {
	        	deplacementY = VITESSE_INTERCELLULE;
	        	setTemporisation(2);
	        }
	        else
	        {
	            deplacementY = vitesseY;
	            setTemporisation(0);
	        }
	        // Le deplacement vers le bas annule le deplacement latéral
	        deplacementX = 0;
	        celluleSuivante = celluleCourante.getCelluleSud();
		}
    }
    
    /**Bouge la pomme vers la gauche si cela est possible*/
    public boolean deplacerGauche(int iVitesse) 
	{
    	if ( isTraversable(celluleCourante.getCelluleOuest(), Direction.GAUCHE ) )
    	{
    		// Vérifie si il n'y a pas un monstre sur le futur emplacement de la pomme
    		if ( !managerMonstre.isMonstre(getRectangle(-iVitesse, 0)) )
    		{
	    		deplacementX = -iVitesse;
	    		celluleSuivante = celluleCourante.getCelluleOuest();
	    		return true;
    		}
    	}
   		return false;
	}

    public boolean deplacerDroite(int iVitesse) 
	{
    	if ( isTraversable(celluleCourante.getCelluleEst(), Direction.DROITE ) )
    	{
    		// Vérifie si il n'y a pas un monstre sur le futur emplacement de la pomme
    		if ( !managerMonstre.isMonstre(getRectangle(iVitesse, 0)) )
    		{
    			deplacementX = iVitesse;
    			celluleSuivante = celluleCourante.getCelluleEst();
    			return true;
    		}
    	}
   		return false;
	}
    
	/**Determine si la pomme peut se deplacer sur la cellule passer en paramètre
	 * @param droite */
    private boolean isTraversable(Cellule cellule, Direction direction) 
    {
    	if ( !isMouvementEnCours() && cellule != null )
    	{
    		if ( direction == Direction.DROITE || direction == Direction.GAUCHE )
    		{
	    		if (cellule.getElement() == null || cellule.getElement() == Element.CHEMIN) 
	    		{
	    			if (cellule.getSprite() == null || cellule.getSprite() instanceof Centre)
	    				return true;
	    		}
    		}
    		else if ( direction == Direction.BAS )
    		{
   	    		if ( cellule.isEntreCellule() )
   	    			return isTraversable(cellule.getCelluleSud(), direction);
   	    		else if ( cellule.getElement() == Element.CHEMIN )
    			{
    				if ( cellule.getSprite() == null || !(cellule.getSprite() instanceof Pomme) )
    					return true;
    			}
    		}
    	}
   		return false;
	}

	private void mangerElement()
    {
        //Sauvegarde du nombre de cellule descendu
        if ( deplacementY > 0 && celluleSuivante.getElement() == Element.CHEMIN )
        	iNbCelluleDescendue++;
        else
        	iNbCelluleDescendue = 0;	// Réinitialisation du compteur

		//Quitte la cellule courante
        //if ( celluleCourante == managerNiveaux.getCelluleCentre() )
        //	managerNiveaux.genererSpriteCentre();
        //else
        	celluleCourante.setSprite(null);
        
        celluleCourante = celluleSuivante;
        celluleCourante.setElement(Element.CHEMIN);        
        celluleCourante.setSprite(this);
        
        //reinitialisation des déplacements
        reinitialiserDeplacement();
    }
}
