package fr.mreddy.fruity;

import static fr.mreddy.fruity.cellule.Element.CHEMIN;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import fr.mreddy.fruity.cellule.Cellule;
import fr.mreddy.fruity.cellule.CelluleCentre;
import fr.mreddy.fruity.cellule.Element;
import fr.mreddy.fruity.cellule.ElementNiveau;
import fr.mreddy.fruity.sprite.Centre;
import fr.mreddy.fruity.sprite.Explosion;
import fr.mreddy.fruity.sprite.Pomme;
import fr.mreddy.fruity.sprite.Sprite.TypeEtat;

/**G�re le tableau de cellule des niveaux*/
public class ManagerNiveaux
{
    /**Espace a ne pas ecrire*/
	public static final int HAUTEUR_ZONE_INFOS = 42;
    public static final int CONTOUR_JEU = 10;
    
    public static final int NB_CELLULE_LARGEUR = 15;
    public static final int NB_CELLULE_HAUTEUR = 19;
    public static final int LARGEUR_PIXEL_CELLULE = 40;
    public static final int HAUTEUR_PIXEL_CELLULE = 30;
    public static final int HAUTEUR_PIXEL_INTERCELLULE = 6;
    public static final int NB_NIVEAUX = 7;

	private Cellule[][] tabCellule;
	private static CelluleCentre celluleCentre;	// Cellule d�part des monstres
	private static Cellule celluleDepartJoueur;	// Cellule d�part Joueur

	// Listes des images
    private static Image imgPomme;
    private static Image imgCentre;
    private static Image[] tabImgFonds;

//Sprites du niveau
    //TODO voir a grouper tous les sprites dans une meme linkedlist
    private LinkedList<Pomme> lklPomme;
    private LinkedList<Explosion> lklExplosion;
    private Centre centre;

    public ManagerNiveaux()
    {
    	lklPomme = new LinkedList<Pomme>();
        tabImgFonds = new Image[NB_NIVEAUX + 1];	// Commence a 1 et pas 0
        chargerImages();
        //chargerMusique();
    }

    /**Chargement de toutes les images des tableaux de jeu*/
    public void chargerImages()
    {
        //	Chargement des images �l�ments
        MediaTracker mt = new MediaTracker(Parametres.applet);

        imgPomme = Parametres.applet.getImage(Parametres.applet.getCodeBase(), Parametres.DOSSIER_IMAGES + "/pomme.png");
        mt.addImage(imgPomme, 0);
        imgCentre = Parametres.applet.getImage(Parametres.applet.getCodeBase(), Parametres.DOSSIER_IMAGES + "/centre.gif");
        mt.addImage(imgCentre, 0);
        for (int i = 1; i <= NB_NIVEAUX; i++)
        {
            tabImgFonds[i] = Parametres.applet.getImage(Parametres.applet.getCodeBase(), Parametres.DOSSIER_IMAGES + "/fondniveau" + i + ".png");
            mt.addImage(tabImgFonds[i], 0);
        }

        // Attendre chargement de toutes les images
        try
        {
            mt.waitForAll();
        }
        catch (InterruptedException e)
        {
        }
        // Detection Erreur
        if (mt.isErrorAny())
            System.out.println("ManagerNiveaux : Erreur Chargement Images");
    }

    public void initialiserJeu(FichierConfNiveau conf, ManagerMonstres mMonstres)
    {
    	ElementNiveau elementNiveau;
    	if ( Parametres.partieEnCours.isNouveauNiveau() )
    		elementNiveau = ElementNiveau.getInstance(conf);
    	else
    		elementNiveau = ElementNiveau.getInstance(tabCellule);
    	
    	tabCellule = new Cellule[NB_CELLULE_LARGEUR][NB_CELLULE_HAUTEUR];
    	creerTableauCellule();

        genererElements(conf, mMonstres, elementNiveau);
    }
    
    /**Initialisation des cellules*/
    private void creerTableauCellule()
    {
    	// Calcul al�atoire de la cellule du milieu
    	int cellule_milieu_y = NB_CELLULE_HAUTEUR / 2 - 1;
        Random rd = new Random();
        int cellule_milieu_x = rd.nextInt(NB_CELLULE_LARGEUR);
        		
        int hauteurcellule;
        for (int cellule_x = 0; cellule_x < NB_CELLULE_LARGEUR; ++cellule_x)
        {
            for (int cellule_y = 0; cellule_y < NB_CELLULE_HAUTEUR; ++cellule_y)
            {
                if (cellule_y % 2 == 0)	// Une sur deux a dimension reduite
                    hauteurcellule = HAUTEUR_PIXEL_CELLULE;
                else
                    hauteurcellule = HAUTEUR_PIXEL_INTERCELLULE;

                Cellule cellule;
                if ( cellule_x == cellule_milieu_x && cellule_y == cellule_milieu_y )
                {
                	celluleCentre = new CelluleCentre(cellule_x * LARGEUR_PIXEL_CELLULE + CONTOUR_JEU, calculerPosY(cellule_y), LARGEUR_PIXEL_CELLULE, hauteurcellule);
                	centre = new Centre(imgCentre, celluleCentre);
                	celluleCentre.setSprite(centre);
                	cellule = celluleCentre;
                }
                else
                	cellule = new Cellule(cellule_x * LARGEUR_PIXEL_CELLULE + CONTOUR_JEU, calculerPosY(cellule_y), LARGEUR_PIXEL_CELLULE, hauteurcellule);
                tabCellule[cellule_x][cellule_y] = cellule;
            }
        }

        // Association des cellules entre elles
        for (int cellule_x = 0; cellule_x < NB_CELLULE_LARGEUR; cellule_x++)
        {
            for (int cellule_y = 0; cellule_y < NB_CELLULE_HAUTEUR; cellule_y++)
            {
                if (cellule_y > 0)
                    tabCellule[cellule_x][cellule_y].setCelluleNord(tabCellule[cellule_x][cellule_y - 1]);
                if (cellule_x > 0)
                    tabCellule[cellule_x][cellule_y].setCelluleOuest(tabCellule[cellule_x - 1][cellule_y]);
                if (cellule_x + 1 < NB_CELLULE_LARGEUR)
                    tabCellule[cellule_x][cellule_y].setCelluleEst(tabCellule[cellule_x + 1][cellule_y]);
                if (cellule_y + 1 < NB_CELLULE_HAUTEUR)
                    tabCellule[cellule_x][cellule_y].setCelluleSud(tabCellule[cellule_x][cellule_y + 1]);
            }
        }

        // Milieu horizontal
        for (int cellule_x = 0; cellule_x < NB_CELLULE_LARGEUR; cellule_x++)
            tabCellule[cellule_x][cellule_milieu_y].setElement(CHEMIN);
        // Milieu vertical
        //System.out.println("ManagerNiveaux : genererCheminDepart() col = " + cellule_x + " ligne " + cellule_milieu_y);
        for (int cellule_y = 0; cellule_y < NB_CELLULE_HAUTEUR; ++cellule_y)
        	tabCellule[cellule_milieu_x][cellule_y].setElement(CHEMIN);

        // Cellule départ du joueur
        celluleDepartJoueur = tabCellule[cellule_milieu_x][NB_CELLULE_HAUTEUR-1];
    }

	/**Récupère le nombre de chaque elements et les places dans le tableau
     * @param conf */
    private void genererElements(FichierConfNiveau conf, ManagerMonstres mMonstres, ElementNiveau elementNiveau)
    {
        Random rd = new Random();

        //Liste des cellules disponibles pour placer les elements / sprites
        List<Cellule> lstCelluleLibre = getCelluleLibre();
        
        // Recherche et creation des elements selon la configuration des fichiers
        for ( Element element : Element.values() )
        {
        	if ( element != Element.CHEMIN )
        	{
        		//System.out.println("ELement " + tabNomElements[i] + " nb = " + nbElements);
	            for (int nb = 0; nb < elementNiveau.getNbElement(element); nb++)
	            {
	                Cellule cellule = lstCelluleLibre.get(rd.nextInt(lstCelluleLibre.size()));
	                cellule.setElement(element);
	                lstCelluleLibre.remove(cellule);
	            }
        	}
        }

        // Suppression des cellules restant sur la ligne du dessus du chemin d'origine
    	//for ( Cellule celluleLibre : lstCelluleLibre )
    	for (int indice=0; indice<lstCelluleLibre.size(); ++indice)
        {
    		Cellule celluleLibre = lstCelluleLibre.get(indice);
    		//System.out.println(indice + "<" + lstCelluleLibre.size() + " " + celluleLibre);
    		if ( celluleLibre.getCelluleSud() != null && celluleLibre.getCelluleSud().getCelluleSud().getElement() == CHEMIN )
    		{
    			indice--;
    			lstCelluleLibre.remove(celluleLibre);
    		}
    	}
    	
    	lklPomme.clear();
    	int nbPomme = conf.getNombre("POMME");
    	//Toujours 10 pommes par niveau
        for (int nb = 0; nb < nbPomme; nb++)
        {
            Cellule cellule = lstCelluleLibre.get(rd.nextInt(lstCelluleLibre.size()));
            Pomme pomme = new Pomme(imgPomme, this, mMonstres, cellule);
            lklPomme.add(pomme);
            
            cellule.setElement(CHEMIN);
            cellule.setSprite(pomme);
            
            lstCelluleLibre.remove(cellule);
        }
    }

    /**Retourne la liste des cellules libres pour le placement des elements*/
    private List<Cellule> getCelluleLibre()
    {
    	List<Cellule> lstCellule = new ArrayList<Cellule>();
    	
    	for ( Cellule[] ligneCellule : tabCellule )
    		for ( Cellule cellule : ligneCellule )
                if ( !cellule.isEntreCellule() && cellule.getElement() == null )
                	lstCellule.add(cellule);
    	
    	return lstCellule;
    }

    /**Calcul de la position y de la cellule en fonction du numero de cellule*/
    private int calculerPosY(int y)
    {
        return (y / 2) * HAUTEUR_PIXEL_INTERCELLULE + (y - (y / 2)) * HAUTEUR_PIXEL_CELLULE + HAUTEUR_ZONE_INFOS + CONTOUR_JEU;
    }

    /**Retourne la cellule du d�part du joueur*/
	public Cellule getCelluleDepartJoueur() 
	{
		return celluleDepartJoueur;
	}
	
    /**Retourne la cellule du centre du niveau*/
	public Cellule getCelluleCentre() 
	{
		return celluleCentre;
	}
	
    /**Retourne toutes les cellules destin�s a accueilir une pomme**/
    public LinkedList<Pomme> getLstPommes()
    {
    	return lklPomme;
    }

    /**Teste si il y a encore des trucs a manger*/
    public boolean isGagne()
    {
    	for ( Cellule[] ligneCellule : tabCellule )
    		for ( Cellule cellule : ligneCellule )
                if ( cellule.getElement() != null && cellule.getElement().getNbPoints() > 0)
                    return false;
        return true;
    }

    public void animer()
    {
        if ( centre != null )
       		centre.animer();

        LinkedList<Pomme> clone = (LinkedList<Pomme>) lklPomme.clone();
        for ( Pomme pomme : clone )
        {
       		pomme.animer();
        	if ( pomme.getEtat() == TypeEtat.MORT )
        		lklPomme.remove(pomme);
        }
    }

    /**Dessine le tableau*/
    public void dessiner(Graphics g)
    {
    	//System.out.println(getClass().getName() + ": dessiner()");

    	int iNiveau = Parametres.partieEnCours.getNiveauJeu();
		for (int posX = 0; posX < Parametres.LARGEUR_JEU; posX += tabImgFonds[iNiveau].getWidth(null) )
			for (int posY = HAUTEUR_ZONE_INFOS; posY < Parametres.HAUTEUR_JEU; posY += tabImgFonds[iNiveau].getHeight(null) )
				g.drawImage(tabImgFonds[iNiveau], posX, posY, null);
    	
    	//System.out.println("ManagerNiveaux : dessiner()");
	   	for ( Cellule[] ligneCellule : tabCellule )
    		for ( Cellule cellule : ligneCellule )
    			cellule.dessiner(g);
        
        //Affichage des sprites du niveau
        if ( centre != null )
        	centre.dessiner(g); 	
    	for ( Pomme pomme : lklPomme )
   			pomme.dessiner(g);	
    }

	public void reinitialiserCentre()
	{
		if ( centre != null )
			celluleCentre.setSprite(centre);
	}

	public void mangerCentre()
	{
		centre = null;
		//centre.setEtat(TypeEtat.MORT);
	}
}
