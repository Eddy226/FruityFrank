package fr.mreddy.fruity;
import fr.mreddy.fruity.ecran.*;

import java.awt.Font;

public class Parametres
{
	// Calcul de la taille de l'applet
	public static final int LARGEUR_JEU = ManagerNiveaux.NB_CELLULE_LARGEUR * ManagerNiveaux.LARGEUR_PIXEL_CELLULE 
														+ 2 * ManagerNiveaux.CONTOUR_JEU;
	public static final int HAUTEUR_JEU = ManagerNiveaux.HAUTEUR_ZONE_INFOS 
					+ (ManagerNiveaux.NB_CELLULE_HAUTEUR/2+1) * ManagerNiveaux.HAUTEUR_PIXEL_CELLULE 
					+ (ManagerNiveaux.NB_CELLULE_HAUTEUR/2) * ManagerNiveaux.HAUTEUR_PIXEL_INTERCELLULE
					+ 2 * ManagerNiveaux.CONTOUR_JEU;

	public static final String DOSSIER_IMAGES = "fr/mreddy/fruity/images";
	public static final String DOSSIER_SONS = "fr/mreddy/fruity/sons";
	public static final String DOSSIER_NIVEAU = "fr/mreddy/fruity/niveaux";
	public static final String FICHIER_NIVEAU = "niveau";

	public static Font grandePolice = new Font("Courier New", Font.BOLD, 18);
	public static Font moyennePolice = new Font("Courier New", Font.BOLD, 14);
	
	public static FruityFrank applet;
	public static Clavier clavier;

	public static Partie partieEnCours;
	
	public static EcranModele ecranCourant;
	public static EcranModele ecranPrecedent;
	
	public static EcranDemarrage ecranDemarrage;
	public static EcranTitre ecranTitre;
	public static EcranConfig ecranConfig;
	public static EcranMeilleursScores ecranMeilleursScores;
	public static EcranInfosNiveau ecranInfosNiveau;
	public static EcranJeu ecranJeu;
	public static EcranPause ecranPause;
	public static EcranAnimCouleur ecranAnimCouleur;
	public static EcranFondu ecranFondu;
	public static EcranGameOver ecranGameOver;
}
