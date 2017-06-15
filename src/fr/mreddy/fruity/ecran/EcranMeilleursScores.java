package fr.mreddy.fruity.ecran;
import fr.mreddy.fruity.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.KeyEvent;

public class EcranMeilleursScores extends EcranTemporise 
{
	private Image imgLogo;
	private String[][] tabScores = new String[11][4];
	private int coefLigne;
	private int coefColonne;
	private int depColonne = 20;
	private int depLigne = 150;

	public EcranMeilleursScores()
	{
		super(70);
		imgLogo = Parametres.applet.getImage(Parametres.applet.getCodeBase(),  
							Parametres.DOSSIER_IMAGES + "/logo.jpg");
	
		MediaTracker mt = new MediaTracker(Parametres.applet);
		mt.addImage(imgLogo, 0);
		
		try 
		{
			mt.waitForAll();
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		if ( ! mt.checkAll() )
			System.out.println("EcranMeilleursScores : Erreur chargement Image");

		tabScores[0][0] = new String("Pseudo");
		tabScores[0][1] = "Score";
		tabScores[0][2] = "Niveau";
		tabScores[0][3] = "Date";

		// Centrage � l'ecran, calcul des coefs		
		coefLigne = Parametres.HAUTEUR_JEU / 2 / tabScores.length;
		coefColonne = Parametres.LARGEUR_JEU / tabScores[0].length;
	}
	
	/**Chargement des infos DB dans tableau Score*/
	public void charger()
	{
		/*try
		{
			Class.forName("org.gjt.mm.mysql.Driver");
		}
		catch (ClassNotFoundException e)
		{
			System.out.println(e);
		}
		
		try 
		{
			Connection conn = DriverManager.getConnection("jdbc:mysql://mysql.freesurf.fr/oldjeux?user=oldjeux&password=parissg");
			
			conn.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}*/
	}
	
	public void animer()
	{
		if ( Parametres.clavier.isTouche(KeyEvent.VK_ENTER, true) )
			Parametres.applet.changerEcranCourant(Parametres.ecranTitre);

		if ( temporiser() )
			Parametres.applet.changerEcranCourant(Parametres.ecranTitre);	
	}
	
	public void dessiner(Graphics g) 
	{
		g.clearRect(0, 0, Parametres.LARGEUR_JEU, Parametres.HAUTEUR_JEU);
		// Logo
		g.drawImage(imgLogo, (Parametres.LARGEUR_JEU - imgLogo.getWidth(null))/2, 10, null);
		// HighScore
		g.setFont(Parametres.grandePolice);
		g.setColor(Color.magenta);
		g.drawString("Meilleurs Scores :", Parametres.LARGEUR_JEU/3, depLigne - 35);
		// Tableau des scores
		g.setFont(Parametres.moyennePolice);
		g.setColor(Color.yellow);
		for (int i=0; i<tabScores.length; i++)
		{
			for (int y=0; y<tabScores[i].length; y++)
			{
				if (tabScores[i][y] != null)
					g.drawString(tabScores[i][y], y*coefColonne + depColonne, i*coefLigne + depLigne);
			}	
		}
	}

}

