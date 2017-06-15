package fr.mreddy.fruity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.util.LinkedList;
import java.util.Random;

import fr.mreddy.fruity.cellule.Rectangle;
import fr.mreddy.fruity.sprite.Centre;
import fr.mreddy.fruity.sprite.Explosion;
import fr.mreddy.fruity.sprite.MonstreJaune;

public class ManagerMonstres
{
	/**Lien vers le manager de niveaux*/
	private ManagerNiveaux managerNiveaux;
	
    public static Image imgMonstreJaune;
    public static Image imgMonstreViolet;
    private int nbMonstresJaune;
    //private int nbMonstresViolet;
    private LinkedList<MonstreJaune> lklMonstresJaune;
    //private LinkedList lsMonstresViolet;
    private LinkedList<Explosion> lklExplosion;
    private int temporisationMJauneEnCours;
    private int temporisationMJauneApparition;

    public ManagerMonstres(ManagerNiveaux managerNiveaux)
    {
    	this.managerNiveaux = managerNiveaux;
        chargerImages();
    }

    public void initialiserJeu(FichierConfNiveau conf)
    {
        nbMonstresJaune = conf.getNombre("enemiJaune");
        lklMonstresJaune = new LinkedList<MonstreJaune>();
        lklExplosion = new LinkedList<Explosion>();
        initialiserTemporisation();
        
        /*nbMonstresViolet = outilsFichier.trouverParam("enemiViolet",
                Parametres.applet.getCodeBase() + "/" + Parametres.DOSSIER_NIVEAU + "/" +
                Parametres.FICHIER_NIVEAU + Parametres.numNiveauEnCours + ".txt");
        lsMonstresViolet = new LinkedList<>();*/
    }

    public void chargerImages()
    {
        MediaTracker mt = new MediaTracker(Parametres.applet);

        imgMonstreJaune = Parametres.applet.getImage(Parametres.applet.getCodeBase(), Parametres.DOSSIER_IMAGES + "/monstrejaune.jpg");
        mt.addImage(imgMonstreJaune, 0);
        //imgMonstreViolet = Parametres.applet.getImage(Parametres.applet.getCodeBase(), Parametres.DOSSIER_IMAGES + "/monstreviolet.jpg");
        //mt.addImage(imgMonstreViolet, 0);

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

//TODO ajouter les autres monstres dans la liste et faire une classe mere monstre
	public LinkedList<MonstreJaune> getLstMonstres() 
	{
		return lklMonstresJaune;
	}
	
	/**Determine si il y a un monstre dans la position donn�e*/
	public boolean isMonstre(Rectangle sprite) 
	{
		for ( MonstreJaune monstreJaune : lklMonstresJaune )
		{
			if ( monstreJaune.intersect(sprite) )
				return true;
		}
		return false;
	}

	
    public void animer()
    {
        for ( MonstreJaune monstre : lklMonstresJaune )
        	monstre.animer();
        
        // Les montres ne sont générés que si le Centre n'a pas été mangé par le joueur
        if (lklMonstresJaune.size() < nbMonstresJaune ) //&& managerNiveaux.getCelluleCentre().getSprite() instanceof Centre )
        {
            temporisationMJauneEnCours++;
            if (temporisationMJauneEnCours > temporisationMJauneApparition)
            {
            	//TODO voir si pomme dans le centre si on met qd meme le monstre
            	//TODO modifier acces a celluleCentre
                lklMonstresJaune.add(new MonstreJaune(managerNiveaux));
                initialiserTemporisation();
            }
        }

        LinkedList<Explosion> lkl = (LinkedList<Explosion>)lklExplosion.clone();
        for ( Explosion explosion : lkl )
        {
        	explosion.animer();
        	if ( explosion.isFinAnimation() )
        		lklExplosion.remove(explosion);
        }
    }

    /**G�n�ration d'une temporisation al�atoire pour l'apparition des monstres jaunes*/
    private void initialiserTemporisation() 
    {
    	 temporisationMJauneEnCours = 0;
    	 
    	 temporisationMJauneApparition = new Random().nextInt(80) - (Parametres.partieEnCours.getNiveauEnCours() * 2) + 20;
    	 if ( temporisationMJauneApparition <= 0)
    		 temporisationMJauneApparition = 20;
 	}

	public void dessiner(Graphics g)
    {
        for ( MonstreJaune monstre : lklMonstresJaune )
        	monstre.dessiner(g);
        for ( Explosion explosion : lklExplosion )
        	explosion.dessiner(g);
    }

	public void perdre(MonstreJaune monstre) 
	{
		Parametres.partieEnCours.ajouterPointsMonstre(monstre.getNbPoints());
		lklMonstresJaune.remove(monstre);
		lklExplosion.add(new Explosion(monstre));
	}


}
