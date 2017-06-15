package fr.mreddy.fruity;

import java.util.Arrays;

public class Clavier
{
    /**Tableau des touches, si true alors touches appuyï¿½es, sinon relachï¿½es*/
    private boolean[] tabEtatTouches;

    public Clavier()
    {
        tabEtatTouches = new boolean[256];
    }

    /**Reinitialises toutes les touches a non appuyees*/
    public void reinitialiserTouches()
    {
        Arrays.fill(tabEtatTouches, false);
    }

	public void appuyerTouche(int iKeyCode) 
	{
		tabEtatTouches[iKeyCode] = true;
	}
	
	public void relacherTouche(int iKeyCode) 
	{
		tabEtatTouches[iKeyCode] = false;
	}

    /**Determine si la touche du clavier passée en paramétre a été appuyée
     * Si elle a ete appuyée, desactive la touche*/
    public boolean isTouche(int iKeyCode, boolean bRelacher)
    {
		if ( tabEtatTouches[iKeyCode] )
		{
			if ( bRelacher )
				relacherTouche(iKeyCode);
			return true;
		}
		else
			return false;
    }
}
