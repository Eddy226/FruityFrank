package fr.mreddy.fruity.ecran;
import java.awt.AWTEvent;
import java.awt.Graphics;

public abstract class EcranModele
{
	public void charger() {}

	public void animer() {}

	public void gererEvenement(AWTEvent e) {}

	public abstract void dessiner(Graphics g);

	public void decharger() {}
}
