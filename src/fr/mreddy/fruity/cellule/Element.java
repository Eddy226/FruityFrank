package fr.mreddy.fruity.cellule;

import java.awt.Image;
import java.awt.MediaTracker;

import fr.mreddy.fruity.Parametres;

public enum Element
{
	ABRICOT("abricot.jpg", 200),
	BANANE("banane.jpg", 20)
	{
		public String getBruitage() 
		{
			return name().toLowerCase();
		}
	},
	CERISE("cerise.jpg", 10)
	{
		public String getBruitage() 
		{
			return name().toLowerCase();
		}
	},
	CHEMIN("chemin.jpg", 0),
	CITRON("citron.jpg", 60),
	GROSEILLE("groseille.jpg", 100)
	{
		public String getBruitage() 
		{
			return name().toLowerCase();
		}
	},
	NOIX("noix.jpg", 70),
	POIRE("poire.jpg", 40)
	{
		public String getBruitage() 
		{
			return name().toLowerCase();
		}
	};
    
	private Image img;
    private int nbPoints;

    private Element(String sImage, int nbPoints)
    {
        //	Chargement des images �l�ments
  //TODO voir optimisation des chargements des images pas de mt pour chaque element, statique ?
        MediaTracker mt = new MediaTracker(Parametres.applet);
       	img = Parametres.applet.getImage(Parametres.applet.getCodeBase(), Parametres.DOSSIER_IMAGES + "/" + sImage);
       	mt.addImage(img, 0);

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
            System.out.println(getClass().getName() + ": Erreur Chargement Images");
        this.nbPoints = nbPoints;
    }

    public Image getImage()
    {
        return img;
    }

    public int getNbPoints()
    {
        return nbPoints;
    }

	public String getBruitage() 
	{
		return null;
	}
}
