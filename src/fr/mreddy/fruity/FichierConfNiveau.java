package fr.mreddy.fruity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

public class FichierConfNiveau
{
	private Properties properties;

	/**Generation aléatoire*/
	private Random rd;
	
	public FichierConfNiveau(int iNumNiveau)
	{
        rd = new Random(new Date().getTime());

    	properties = new Properties();
    	InputStream inputStream = null;
    	try
        {
    		inputStream = getClass().getResourceAsStream("/" + Parametres.DOSSIER_NIVEAU + "/niveau" + iNumNiveau + ".txt");
        	properties.load(inputStream);
        }
	    catch (Exception e)
	    {
	        System.out.println("outilsFichier : trouverParam \r\n --> Exception : " + e);
	    }
	    finally
	    {
	    	if ( inputStream != null )
	    	{
				try 
	    		{
					inputStream.close();
				} 
	    		catch (IOException ex) 
	    		{
					ex.printStackTrace();
				}
	    	}
	    }
	}

    public int getNombre(String sClef)
    {
    	if ( properties != null && properties.containsKey(sClef) )
    	{
    		String[] sTabValeur = properties.getProperty(sClef).trim().split("-");
    		int iZoneAleatoire;
    		if ( sTabValeur.length > 1 )
    			iZoneAleatoire = rd.nextInt(Integer.parseInt(sTabValeur[sTabValeur.length-1]) - Integer.parseInt(sTabValeur[0]) + 1);
    		else
    			iZoneAleatoire = 0;
    		return Integer.parseInt(sTabValeur[0]) + iZoneAleatoire;
    	}
    	else
    		return 0;
    }
}
