package fr.mreddy.fruity.ecran;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import fr.mreddy.fruity.FichierConfNiveau;
import fr.mreddy.fruity.ManagerJoueur;
import fr.mreddy.fruity.ManagerMonstres;
import fr.mreddy.fruity.ManagerNiveaux;
import fr.mreddy.fruity.ManagerSon;
import fr.mreddy.fruity.Parametres;
import fr.mreddy.fruity.sprite.MonstreJaune;
import fr.mreddy.fruity.sprite.Pomme;


public class EcranJeu extends EcranModele
{
	private ManagerNiveaux mNiveaux;
	private ManagerJoueur mJoueur;
	private ManagerMonstres mMonstres;
	private ManagerSon mSon;
	
	public EcranJeu()
	{
		//System.out.println("EcranJeu : EcranJeu() ");
		mSon = new ManagerSon();
		mNiveaux = new ManagerNiveaux();
		mJoueur = new ManagerJoueur(mNiveaux, mSon);
		mMonstres = new ManagerMonstres(mNiveaux);
	}

	public void charger()
	{
		FichierConfNiveau conf = new FichierConfNiveau(Parametres.partieEnCours.getNiveauEnCours());
		
		mNiveaux.initialiserJeu(conf, mMonstres);
		mJoueur.initialiserJeu();
		mMonstres.initialiserJeu(conf);
		mSon.jouerNiveau();
	}
	
	public void animer()
	{
		if ( Parametres.clavier.isTouche(KeyEvent.VK_P, true) )
			Parametres.applet.changerEcranCourant(Parametres.ecranPause);

		if ( mJoueur.isFinAnimation() || mNiveaux.isGagne() )
		{
			mSon.arreterNiveau();
			Parametres.ecranFondu.init();
			Parametres.applet.changerEcranCourant(Parametres.ecranFondu);
			if ( mNiveaux.isGagne() )
			{
				mSon.jouerBruitage("victoire");
				Parametres.partieEnCours.gagnerNiveau();
			}
			else
				Parametres.partieEnCours.perdreNiveau();
		}
		
		mNiveaux.animer();
		mMonstres.animer();
		mJoueur.animer();
		
		//Tester les collisions
		for ( MonstreJaune monstre : mMonstres.getLstMonstres() )
		{
			if ( mJoueur.getJoueur() != null )
				if ( monstre.intersect(mJoueur.getJoueur()) )
					joueurPerdre();
		}
		
		for ( Pomme pomme : mNiveaux.getLstPommes() )
		{
			LinkedList<MonstreJaune> lkl = (LinkedList<MonstreJaune>) mMonstres.getLstMonstres().clone(); 
			for ( MonstreJaune monstre :  lkl )
			{
				if ( pomme.intersect(monstre) )
					mMonstres.perdre(monstre);
			}
			if ( mJoueur.getJoueur() != null )
			{
				if ( pomme.intersect(mJoueur.getJoueur()) )
					joueurPerdre();
			}
		}
	}

	private void joueurPerdre() 
	{
		mSon.arreterNiveau();
		mJoueur.perdre();
	}

	public void dessiner(Graphics g)
	{
		// Supprime uniquement le haut de l'ecran score + NbVies
		g.clearRect(0, 0, Parametres.LARGEUR_JEU, ManagerNiveaux.HAUTEUR_ZONE_INFOS);

		g.setColor(Color.cyan);
	    g.setFont(Parametres.grandePolice);
	    g.drawString("SCORE : ", 10, 30);
	    g.setColor(Color.yellow);
	    g.drawString(" " + Parametres.partieEnCours.getScore(), 80, 30);

	    //TODO afficher le gain de points suite a destruction de monstre
	    /*if ( Parametres.partieEnCours.getPointMonstre() > 0 )
	    {
	    	
	    }*/
	    
		mNiveaux.dessiner(g);
		mJoueur.dessiner(g);
		mMonstres.dessiner(g);
	}
	
	// En cas de fermeture de l'applet, couope le son sinon il continue !
	public void decharger()
	{
		mSon.arreterNiveau();
	}
}
