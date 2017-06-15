package fr.mreddy.fruity;

import java.awt.AWTEvent;
import java.util.LinkedList;

public class GestionEvenement
{
    private LinkedList<AWTEvent> lstEvenements;
    private GestionnaireEvenements traiteur;

    public GestionEvenement(GestionnaireEvenements traiteur)
    {
        lstEvenements = new LinkedList<AWTEvent>();
        this.traiteur = traiteur;
    }

    /**Sauvegarde les evenements*/
    public void ajouterEvenement(AWTEvent e)
    {
        synchronized (lstEvenements)
        {
            lstEvenements.add(e);
        }
    }

    /**Renvoye les evenements au traiteur*/
    public void envoyerEvenements()
    {
        AWTEvent evenement;

        for (int i = 0; i < lstEvenements.size(); i++)
        {
            synchronized (lstEvenements)
            {
                evenement = (AWTEvent) lstEvenements.removeFirst();
            }
            traiteur.gererEvenement(evenement);
        }
    }
}
