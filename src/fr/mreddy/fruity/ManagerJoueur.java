package fr.mreddy.fruity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.KeyEvent;

import fr.mreddy.fruity.sprite.ExplosionJoueur;
import fr.mreddy.fruity.sprite.Joueur;

public class ManagerJoueur
{
	/**Lien vers le manager de son*/
	private ManagerSon managerSon;
	/**Lien vers le manager de niveaux*/
	private ManagerNiveaux managerNiveaux;
	
    private Joueur joueur;
    private static Image imgJoueur;
    private ExplosionJoueur explosion;
    
    public ManagerJoueur(ManagerNiveaux managerNiveaux, ManagerSon managerSon)
    {
    	this.managerNiveaux = managerNiveaux;
    	this.managerSon = managerSon;
        chargerImages();
    }

    /**Chargement des images relatives au joueur*/
    public void chargerImages()
    {
        MediaTracker mt = new MediaTracker(Parametres.applet);
        imgJoueur = Parametres.applet.getImage(Parametres.applet.getCodeBase(), Parametres.DOSSIER_IMAGES + "/player.jpg");
        mt.addImage(imgJoueur, 0);
        try
        {
            mt.waitForAll();
        }
        catch (InterruptedException e)
        {
            System.out.println(e);
        }
        // Detection Erreur
        if (mt.isErrorAny())
            System.out.println("ManagerMonstres : Erreur Chargement Images");
    }

    public void initialiserJeu()
    {
        joueur = new Joueur(imgJoueur, managerNiveaux, managerSon);
    }

    public void animer()
    {
    	if ( joueur != null )
    	{
	        joueur.animer();
	    	if (Parametres.clavier.isTouche(KeyEvent.VK_UP, false))
	            joueur.deplacerHaut();
	        else if (Parametres.clavier.isTouche(KeyEvent.VK_DOWN, false))
	            joueur.deplacerBas();
	        else if (Parametres.clavier.isTouche(KeyEvent.VK_RIGHT, false))
	            joueur.deplacerDroite();
	        else if (Parametres.clavier.isTouche(KeyEvent.VK_LEFT, false))
	            joueur.deplacerGauche();
    	}
    	else if ( explosion != null )
    		explosion.animer();
    }

    public void dessiner(Graphics g)
    {
	    int posX = Parametres.LARGEUR_JEU - ManagerNiveaux.LARGEUR_PIXEL_CELLULE;
	    // NB Vies
	    for (int i = 1; i < Parametres.partieEnCours.getNbVies(); i++)
	    {
	        g.drawImage(imgJoueur,
	                posX, 10, posX + ManagerNiveaux.LARGEUR_PIXEL_CELLULE, 10 + ManagerNiveaux.HAUTEUR_PIXEL_CELLULE,
	                0, 0, ManagerNiveaux.LARGEUR_PIXEL_CELLULE, ManagerNiveaux.HAUTEUR_PIXEL_CELLULE, null);
	        posX -= ManagerNiveaux.LARGEUR_PIXEL_CELLULE;
	    }

	    if ( explosion == null )
        	joueur.dessiner(g);
        else
        	explosion.dessiner(g);
    }
    
    /**Retourne le sprite du joueur*/
    public Joueur getJoueur()
    {
    	return joueur;
    }

	public void perdre() 
	{
		managerSon.jouerBruitage("joueurperdre");
		explosion = new ExplosionJoueur(joueur);
		joueur = null;
	}
	
	public boolean isFinAnimation()
	{
		if ( joueur == null && explosion.isFinAnimation() )
		{
			explosion = null;
			return true;
		}
		else
			return false;
	}
}
