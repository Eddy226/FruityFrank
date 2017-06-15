package fr.mreddy.fruity.sprite;

import java.awt.Image;
import fr.mreddy.fruity.cellule.Cellule;

public class Centre extends SpriteTemporise
{
	public Centre(Image imgSprite, Cellule celluleCourante) 
	{
		super(imgSprite, celluleCourante, 5);
	}

    protected void animerTemporisation()
    {
   		incrementerImageCourante();
    }
}
