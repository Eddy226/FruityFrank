package fr.mreddy.fruity;

import java.applet.AudioClip;
import java.net.URL;

public class ManagerSon 
{
    private static URL[] tabUrlMusique;
    
    private AudioClip audioClipMusique;
    
    private AudioClip audioClipBruitage;
    
    // TODO voir si chargement des sons dans un tableau avant de les utiliser car ils sont charger a chaque appel
    public ManagerSon()
    {
    }
    
    public void jouerNiveau()
    {
    	// Calcul de la musique selon le niveau
    	int iNum = Parametres.partieEnCours.getNiveauJeu();
    	if ( iNum > 7 )
    		iNum %= 7;
		try 
		{
			URL url = getClass().getResource("/" + Parametres.DOSSIER_SONS + "/musiques/niveau" + iNum + ".wav");
        	audioClipMusique = Parametres.applet.getAudioClip(url);
        	audioClipMusique.loop();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
        	audioClipMusique = null;
		}
    }
    
    public void arreterNiveau()
    {
    	if ( audioClipMusique != null )
    	{
    		audioClipMusique.stop();
    		audioClipMusique = null;
    	}
    }
    
    public void jouerBruitage(String sSon)
    {
    	if ( sSon != null )
    	{
			try 
			{
				URL url = getClass().getResource("/" + Parametres.DOSSIER_SONS + "/bruitages/" + sSon + ".wav");
				audioClipBruitage = Parametres.applet.getAudioClip(url);
				audioClipBruitage.play();
			}
			catch (Exception e) 
			{
				e.printStackTrace();
	        	audioClipMusique = null;
			}
    	}
    }
}
