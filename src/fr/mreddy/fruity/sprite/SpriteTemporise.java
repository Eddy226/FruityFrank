package fr.mreddy.fruity.sprite;

import java.awt.Image;

import fr.mreddy.fruity.cellule.Cellule;

public abstract class SpriteTemporise extends Sprite 
{
	/**Nombre de frame a mettre le sprite en veille*/
	private int iNbFrameTemporisation;
	/**Compteur interne du nombre de frame "veille"*/
	private int iNbCompteurFrame;

	/**Méthode animer du sprite a redefinir*/
	abstract protected void animerTemporisation();
	
	public SpriteTemporise(Image imgSprite, Cellule celluleCourante, int iTemporisation) 
	{
		super(imgSprite, celluleCourante);
		iNbFrameTemporisation = iTemporisation;
		iNbCompteurFrame = 0;
	}

	/**Affectation de la temporisation*/
	protected void setTemporisation(int iTemporisation)
	{
		this.iNbFrameTemporisation = iTemporisation;
	}

	/**Animation du sprite si la temporisation le permet*/
	public void animer()
	{
		if ( iNbCompteurFrame >= iNbFrameTemporisation)
		{
			iNbCompteurFrame = 0;
			animerTemporisation();
		}
		else
			iNbCompteurFrame++;
	}
	
}
