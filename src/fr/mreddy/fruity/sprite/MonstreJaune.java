package fr.mreddy.fruity.sprite;

import java.util.Random;

import fr.mreddy.fruity.ManagerMonstres;
import fr.mreddy.fruity.ManagerNiveaux;
import fr.mreddy.fruity.cellule.Cellule;
import fr.mreddy.fruity.cellule.Element;

public class MonstreJaune extends SpriteTemporise
{
	private ManagerNiveaux managerNiveaux;
    /**Vitesse de d�placement du sprite*/
    protected int vitesseX, vitesseY;
    
    public MonstreJaune(ManagerNiveaux managerNiveaux)
    {
    	//TODO temporisation et vitesse a calculer en fonction du niveau
        super(ManagerMonstres.imgMonstreJaune, managerNiveaux.getCelluleCentre(), 2);
        
        this.managerNiveaux = managerNiveaux;
        //System.out.println("MonstreJaune : Constructeur() posX=" + celluleDepart.getX() + " posY=" + celluleDepart.getY());
        // A calculer en fonction du niveau
        vitesseX = 10;
        vitesseY = 6;
        selectionDirection();
    }

    public int getNbPoints()
    {
    	return 40;
    }
    
    @Override
    protected void animerTemporisation()
    {
        // Changement d'images
    	incrementerImageCourante();
    	deplacer(deplacementX, deplacementY);
        
        if (celluleAtteinte())
        {
            //System.out.println("MonstreJaune : animer() celluleSuivante atteinte");
        	if ( managerNiveaux.getCelluleCentre() == celluleCourante )
        		managerNiveaux.reinitialiserCentre();
        	else
        		celluleCourante.setSprite(null);
        	celluleCourante = celluleSuivante;	// Chagement position dans tableau
            celluleCourante.setSprite(this);
            selectionDirection();
        }
    }

    private void selectionDirection()
    {
        //System.out.println("MonstreJaune : selectionDirection() Debut");
        int directionTmp;
        boolean directionLibre = false;
        boolean tmp = true;

        // Recupération des cellules voisines
        Cellule celluleEst = celluleCourante.getCelluleEst();
        Cellule celluleOuest = celluleCourante.getCelluleOuest();

        Cellule celluleNord;
        if (isTraversable(celluleCourante.getCelluleNord()))
            celluleNord = celluleCourante.getCelluleNord().getCelluleNord();
        else
        	celluleNord = null;

        Cellule celluleSud;
        if (isTraversable(celluleCourante.getCelluleSud()))
            celluleSud = celluleCourante.getCelluleSud().getCelluleSud();
        else
        	celluleSud = null;

        // Continue sur sa lanc�e ou pas
        if (deplacementX > 0 && celluleEst != null)
        {
        	if (isTraversable(celluleNord))
        		tmp = false;

             if (isTraversable(celluleSud))
            	 tmp = false;

            if (isTraversable(celluleEst) && tmp == true)
            {
                directionLibre = true;
                celluleSuivante = celluleEst;
            }
            else	// Empeche le demi tour si d'autre possibilité
            	celluleOuest = null;
        }
        else if (deplacementX < 0 && celluleOuest != null)
        {
            if (isTraversable(celluleNord))
                tmp = false;

            if (isTraversable(celluleSud))
                tmp = false;

            if (isTraversable(celluleOuest) && tmp == true)
            {
                directionLibre = true;
                celluleSuivante = celluleOuest;
            }
            else	// Empeche le demi tour si d'autre possibilité
            	celluleEst = null;
        }
        else if (deplacementY < 0 && celluleNord != null)
        {
            if (isTraversable(celluleOuest))
                tmp = false;

            if (isTraversable(celluleEst))
                tmp = false;

            if (isTraversable(celluleNord) && tmp == true)
            {
                directionLibre = true;
                celluleSuivante = celluleNord;
            }
            else	// Empeche le demi tour si d'autre possibilité
            	celluleSud = null;
        }
        else if (deplacementY > 0 && celluleSud != null)
        {
            if (isTraversable(celluleOuest))
                tmp = false;

            if (isTraversable(celluleEst))
                tmp = false;

            if (isTraversable(celluleSud) && tmp == true)
            {
                directionLibre = true;
                celluleSuivante = celluleSud;
            }
            else	// Empeche le demi tour si d'autre possibilité
            	celluleNord = null;
        }

        // Choix direction al�atoire
        Random rd = new Random();
        //while (!directionLibre)
        if ( ! directionLibre )
        {
            //System.out.println("MonstreJaune : selectionDirection()");
            directionTmp = rd.nextInt(4);
            switch (directionTmp)
            {
                case 0:	// DROITE
                    //System.out.println("MonstreJaune : SelectionDirection() Droite");
                    celluleSuivante = celluleEst;
                    deplacementX = vitesseX;
                    deplacementY = 0;
                    numDirectionCourante = IMAGE_DROIT;
                    break;
                case 1:	// GAUCHE
                    //System.out.println("MonstreJaune : SelectionDirection() Gauche");
                	
                    celluleSuivante = celluleOuest;
                    deplacementX = -vitesseX;
                    deplacementY = 0;
                    numDirectionCourante = IMAGE_GAUCHE;
                    break;
                case 2: // HAUT
                    //System.out.println("MonstreJaune : SelectionDirection() Haut");
                    celluleSuivante = celluleNord;
                    deplacementX = 0;
                    deplacementY = -vitesseY;
                    numDirectionCourante = IMAGE_HAUTBAS;
                    break;
                case 3: // BAS
                    //System.out.println("MonstreJaune : SelectionDirection() Bas");
                    celluleSuivante = celluleSud;
                    deplacementX = 0;
                    deplacementY = vitesseY;
                    numDirectionCourante = IMAGE_HAUTBAS;
                    break;
            }

            //System.out.println("MonstreJaune : SelectionDirection() " + celluleSuivante);
            if (celluleSuivante != null)
            {
                if (isTraversable(celluleSuivante))
                    directionLibre = true;
                //else
                //	System.out.println("MonstreJaune : SelectionDirection() Direction impossible");
            }
            else
            {
            	deplacementX = 0;
            	deplacementY = 0;
            }
        }// Fin while
        //System.out.println("MonstreJaune : selectionDirection() Fin");
    }

    /**Si le monstre peut traverser cet element*/
    protected boolean isTraversable(Cellule cellule)
    {
        if ( cellule != null && cellule.getElement() != null && cellule.getElement() == Element.CHEMIN )
        {
        	//if ( cellule.getSprite() == null || cellule.getSprite() == this || cellule.getSprite() instanceof Centre )
        		return true;
        }
        return false;
    }
}
