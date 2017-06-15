package fr.mreddy.fruity.cellule;

import fr.mreddy.fruity.FichierConfNiveau;

public class ElementNiveau 
{
	/**Nombre d'element du niveau*/
	private int[] tabNbElements;
	
	
	private ElementNiveau()
	{
		tabNbElements = new int[Element.values().length];
	}
	
	/**Création à partir d'un niveau*/
	public static ElementNiveau getInstance(Cellule[][] tabCellule) 
	{
		ElementNiveau elementNiveau = new ElementNiveau();
		elementNiveau.charger(tabCellule);
		return elementNiveau;
	}

	/**Création à partir du fichier de configuration et du niveau en cours*/
	public static ElementNiveau getInstance(FichierConfNiveau conf) 
	{
		ElementNiveau elementNiveau = new ElementNiveau();
		elementNiveau.charger(conf);
		return elementNiveau;
	}

	/**Chargement du nombre d'element restant a partir d'une grille de jeu*/
	private void charger(Cellule[][] tabCellule) 
	{
		for ( Cellule[] ligneCellule : tabCellule )
		{
			for ( Cellule cellule : ligneCellule )
			{
				Element element = cellule.getElement();
				if ( element != null )
					tabNbElements[element.ordinal()]++;
			}
		}
	}
	
	/**Chargement du nombre d'element selon le fichier de configuration*/
	private void charger(FichierConfNiveau conf) 
	{
		for ( Element element : Element.values() )
			tabNbElements[element.ordinal()] = conf.getNombre(element.name());
	}

	/**Retourne le nombre d'element passé en paramètre a généré dans le niveau*/
	public int getNbElement(Element element) 
	{
		return tabNbElements[element.ordinal()];
	}
}
