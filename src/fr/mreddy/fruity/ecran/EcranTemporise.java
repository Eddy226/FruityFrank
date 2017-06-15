
package fr.mreddy.fruity.ecran;
// Ecran qui a un compteur pour changement ecran
public abstract class EcranTemporise extends EcranModele 
{
	private int TEMPS_TEMPORISATION;
	private int temporisation;

	public EcranTemporise(int tempsMax)
	{
		TEMPS_TEMPORISATION = tempsMax;
		temporisation = 0;
	}
	
	protected boolean temporiser()
	{
		temporisation++;
		if ( temporisation >= TEMPS_TEMPORISATION)
		{
			temporisation = 0;
			return true;
		}
		else
			return false;
	}
}
