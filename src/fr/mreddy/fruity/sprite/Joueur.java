package fr.mreddy.fruity.sprite;

import java.awt.Image;

import fr.mreddy.fruity.ManagerNiveaux;
import fr.mreddy.fruity.ManagerSon;
import fr.mreddy.fruity.Parametres;
import fr.mreddy.fruity.cellule.Cellule;
import fr.mreddy.fruity.cellule.Element;

public class Joueur extends Sprite
{
	/**Gestion du son*/
	private ManagerSon managerSon;
    /**Vitesse de d�placement du sprite*/
    protected int vitesseX, vitesseY;
	private ManagerNiveaux managerNiveau;
    
    public Joueur(Image img, ManagerNiveaux managerNiveaux, ManagerSon managerSon)
    {
        super(img, managerNiveaux.getCelluleDepartJoueur());
        this.managerNiveau = managerNiveaux;
        this.managerSon = managerSon;
        
        NB_IMAGES = NB_IMAGES / 2;	// Images moiti� missile, moiti� sans missile

        numDirectionCourante = IMAGE_DROIT;
        vitesseX = 10;
        vitesseY = 6;
    }


    public void animer()
    {
        //System.out.println("Joueur: animer() posX=" + posX + " posY=" + posY);
    	if ( isMouvementEnCours() )
        {
    		incrementerImageCourante();
    		deplacer(deplacementX, deplacementY);

    		if (celluleAtteinte())
                mangerElement();
        }
    }

    /**Mange l'element*/
    public void mangerElement()
    {
        celluleCourante = celluleSuivante;

        if ( celluleCourante.getElement() != Element.CHEMIN )
        {
        	if ( celluleCourante.getElement() != null )
        	{
        		managerSon.jouerBruitage(celluleCourante.getElement().getBruitage());
        		Parametres.partieEnCours.ajouterScore(celluleCourante.getElement().getNbPoints());
        	}
        	celluleCourante.setElement( Element.CHEMIN );
        }
        else if ( celluleCourante.getSprite() != null && celluleCourante.getSprite() instanceof Centre )
        	managerNiveau.mangerCentre();
        
        // SI cellule entre cellule alors continue sur sa lanc�e
        if (deplacementY != 0 && celluleCourante.getHauteur() == ManagerNiveaux.HAUTEUR_PIXEL_INTERCELLULE)
        {
            if (deplacementY < 0)
                celluleSuivante = celluleCourante.getCelluleNord();
            else
                celluleSuivante = celluleCourante.getCelluleSud();
        }
        else
        	reinitialiserDeplacement();
    }

	/**D�placement si possible, si mouvement non en cours*/
    public void deplacerHaut()
    {
        if (!isMouvementEnCours())
        {
            Cellule celluleTmp = celluleCourante.getCelluleNord();
            if (isTraversable(celluleTmp, Direction.HAUT))
            {
                //System.out.println("Joueur: monter()");
                numDirectionCourante = IMAGE_HAUTBAS;
                deplacementY = -vitesseY;
                celluleSuivante = celluleTmp;
            }
        }
    }

    /**D�placement si possible, si mouvement non en cours*/
    public void deplacerBas()
    {
        if (!isMouvementEnCours())
        {
            Cellule celluleTmp = celluleCourante.getCelluleSud();
            if (isTraversable(celluleTmp, Direction.BAS))
            {
                numDirectionCourante = IMAGE_HAUTBAS;
                deplacementY = vitesseY;
                celluleSuivante = celluleTmp;
            }
        }
    }

    /**D�placement si possible, si mouvement non en cours*/
    public void deplacerDroite()
    {
        //System.out.println("Joueur: deplacerDroite()");
        if (!isMouvementEnCours())
        {
            Cellule celluleTmp = celluleCourante.getCelluleEst();
            if (isTraversable(celluleTmp, Direction.DROITE))
            {
                numDirectionCourante = IMAGE_DROIT;
                deplacementX = vitesseX;
                celluleSuivante = celluleTmp;
            }
        }
    }

    /**D�placement si possible, si mouvement non en cours*/
    public void deplacerGauche()
    {
        if (!isMouvementEnCours())
        {
            Cellule celluleTmp = celluleCourante.getCelluleOuest();
            if ( isTraversable(celluleTmp, Direction.GAUCHE) )
            {
                //System.out.println("Joueur: monter()");
                numDirectionCourante = IMAGE_GAUCHE;
                deplacementX = -vitesseX;
                celluleSuivante = celluleTmp;
            }
        }
    }

    /**Si le joueur peut traverser cet element
     * @param gauche */
    private boolean isTraversable(Cellule cellule, Direction direction)
    {
    	if ( cellule != null )
    	{
    		// En cas de d�placement vertical, ne prend pas en compte l'entrecellule
    		if ( cellule.isEntreCellule() )
    		{
	    		if ( direction == Direction.HAUT )
	    			cellule = cellule.getCelluleNord();
	    		else if ( direction == Direction.BAS)
	    			cellule = cellule.getCelluleSud();
    		}
    	
    		Sprite sprite = cellule.getSprite();
    		//if ( sprite == null || sprite instanceof Centre )
    		//	return true;
    		//else 
    		if ( sprite instanceof Pomme )
    		{
    			if ( direction == Direction.GAUCHE )
    				return ((Pomme)sprite).deplacerGauche(vitesseX);
    			else if ( direction == Direction.DROITE )
    				return ((Pomme)sprite).deplacerDroite(vitesseX);
    		}
    		else
    			return true;
    	}
  		return false;
    }
}
