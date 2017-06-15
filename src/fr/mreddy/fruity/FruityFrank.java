package fr.mreddy.fruity;

import java.awt.AWTEvent;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import fr.mreddy.fruity.ecran.*;
import javax.swing.JApplet;

public class FruityFrank extends JApplet implements Runnable, KeyListener, GestionnaireEvenements
{
    private BufferedImage buffImg;
    private Graphics gBuff;
    private Thread boucle;
    private GestionEvenement gestEvenement;

    /**D�but Prgm*/
    public void init()
    {
    	//System.out.println(getClass().getName() + ": Largeur=" + Parametres.LARGEUR_JEU + " Hauteur=" + Parametres.HAUTEUR_JEU);
        getContentPane().setLayout(null);
        setIgnoreRepaint(true);
        setSize(Parametres.LARGEUR_JEU, Parametres.HAUTEUR_JEU);
        System.out.println(getClass().getName() + ": Hauteur Jeu=" + Parametres.HAUTEUR_JEU);
        System.out.println(getClass().getName() + ": CodeBase = " + getCodeBase()); 
        System.out.println(getClass().getName() + ": DocumentBase = "+getDocumentBase());
        setFocusable(true);
        requestFocus();
        //setFocusTraversalKeysEnabled(false);
        buffImg = new BufferedImage(Parametres.LARGEUR_JEU, Parametres.HAUTEUR_JEU, BufferedImage.TYPE_INT_RGB);
        gBuff = buffImg.getGraphics();

        initialiserJeu();

        addKeyListener(this);
    }

    /**Instanciation des ecrans, sons, et initialisation du premier ecran courant*/
    public void initialiserJeu()
    {
        gestEvenement = new GestionEvenement(this);
        Parametres.applet = this;
        Parametres.clavier = new Clavier();
        
        // Initialisation des ecrans
        Parametres.ecranDemarrage = new EcranDemarrage();
        Parametres.ecranTitre = new EcranTitre();
        Parametres.ecranConfig = new EcranConfig();
        Parametres.ecranMeilleursScores = new EcranMeilleursScores();
        Parametres.ecranInfosNiveau = new EcranInfosNiveau();
        Parametres.ecranJeu = new EcranJeu();
        Parametres.ecranPause = new EcranPause();
        Parametres.ecranAnimCouleur = new EcranAnimCouleur();
        Parametres.ecranFondu = new EcranFondu();
        Parametres.ecranGameOver = new EcranGameOver();

        Parametres.ecranCourant = Parametres.ecranPrecedent = Parametres.ecranDemarrage;
        // Debug
        //Parametres.ecranCourant = Parametres.ecranPrecedent = Parametres.ecranTitre;
        
        
        Parametres.ecranCourant.charger();
    }

    /**Chargement et Changement d'ecran*/
    public void changerEcranCourant(EcranModele ecranNouveau)
    {
        Parametres.ecranCourant.decharger();

        Parametres.ecranPrecedent = Parametres.ecranCourant;
        Parametres.ecranCourant = ecranNouveau;
        Parametres.ecranCourant.charger();
    }

    /**R�cup�re le focus et d�marre le Thread*/
    public void start()
    {
        requestFocus();
        //System.out.println("FruityFrank : start() ");
        boucle = new Thread(this);
        boucle.start();
    }

    /**Boucle du jeu, gestion des evenements, animations et affichages*/
    public void run()
    {
        //System.out.println("FruityFrank : run()");
        long heureDepart, tempsPasse, dureePause;
        int delai = 1000 / 12;

        Thread ceThread = Thread.currentThread();
        while (ceThread == boucle)
        {
            heureDepart = System.currentTimeMillis();

            gestEvenement.envoyerEvenements();

            Parametres.ecranCourant.animer();

            Parametres.ecranCourant.dessiner(gBuff);

            Graphics g = getGraphics();
            g.drawImage(buffImg, 0, 0, null);
            g.dispose();

            tempsPasse = System.currentTimeMillis() - heureDepart;
            
            //System.out.println("Temps pass� " + tempsPasse);
            dureePause = Math.max(delai - tempsPasse, 5);
            try
            {
                Thread.sleep(dureePause);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void keyPressed(KeyEvent e)
    {
        //System.out.println("FruityFrank : keyPressed()");
        gestEvenement.ajouterEvenement(e);
    }

    public void keyReleased(KeyEvent e)
    {
        //System.out.println("FruityFrank : keyReleased()");
        gestEvenement.ajouterEvenement(e);
    }

    public void keyTyped(KeyEvent e)
    {
        //gestEvenement.ajouterEvenement(e);
    }

    public void gererEvenement(AWTEvent e)
    {
        if (!gererEvenementGlobal(e))
            Parametres.ecranCourant.gererEvenement(e);
    }

    /**G�re les evenements globaux a l'application*/
    private boolean gererEvenementGlobal(AWTEvent e)
    {
        //System.out.println("FruityFrank: gererEvenementGlobal()");
        switch (e.getID())
        {
            case KeyEvent.KEY_PRESSED:
                Parametres.clavier.appuyerTouche(((KeyEvent)e).getKeyCode());
                break;
            case KeyEvent.KEY_RELEASED:
                Parametres.clavier.relacherTouche(((KeyEvent)e).getKeyCode());
                break;
        }
        return false;
    }


    /**Arrete la boucle du jeu*/
    public void stop()
    {
        //System.out.println("FruityFrank : stop()");
        boucle = null;
    }


    public void destroy()
    {
        Parametres.ecranCourant.decharger();
        boucle = null;
    }
}
