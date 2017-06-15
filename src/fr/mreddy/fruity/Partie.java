package fr.mreddy.fruity;

public class Partie 
{
	/**Niveau en cours et pr�c�dent*/
	public static int iNiveauEnCours, iNiveauPrecedent;
    /**Nombre de vie restantes*/
    private int nbVies;
    /**Score du joueur*/
    private int score;
    
	public Partie()
	{
		//DEBUG 0 et 1
		iNiveauEnCours = 1;
		iNiveauPrecedent = iNiveauPrecedent - 1;
		nbVies = 3;
		score = 0;
	}

	/**D�termine quel �tait le niveau pr�c�dent (le m�me si joueur a perdu, sinon le niveau pr�c�dent)*/
	/*public int getNiveauPrecedent()
	{
		return iNiveauPrecedent;
	}*/
	
	/**D�termine quel est le niveau en cours de jeu*/
	public int getNiveauEnCours()
	{
		return iNiveauEnCours;
	}
	
	public int getNiveauJeu() 
	{
    	int iNiveau = iNiveauEnCours;
    	
		while (iNiveau > ManagerNiveaux.NB_NIVEAUX)
			iNiveau -= ManagerNiveaux.NB_NIVEAUX;
		return iNiveau;
	}

	public void ajouterScore(int nbPoints) 
	{
		if ( score < 1000 && score + nbPoints >= 1000 )
			nbVies++;
		score += nbPoints;
	}

	public void ajouterPointsMonstre(int nbPoints) 
	{
		ajouterScore(nbPoints);
	}
	
	public int getScore()
    {
        return score;
    }

    public int getNbVies()
    {
        return nbVies;
    }
    
	public void gagnerNiveau() 
	{
		majNiveauPrecedent();
		iNiveauEnCours++;
	}

	public void perdreNiveau() 
	{
		majNiveauPrecedent();
		nbVies--;
	}

	/**Met a jour le niveau pr�c�dent, en cas de victoire ou defaite du joueur*/
	private void majNiveauPrecedent()
	{
		iNiveauPrecedent = iNiveauEnCours;	
	}

	/**Determine si le niveau en cours est le même que celui précédemment joué (joueur perdu ou non)*/
	public boolean isNouveauNiveau() 
	{
		return iNiveauEnCours > iNiveauPrecedent;
	}
	
	public boolean isFin()
	{
		return nbVies == 0;
	}
}
