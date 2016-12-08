package View;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Vector;

import Model.CardType;

public class Data {
	final String file = "./ressources/link.csv";
	final int MAX_ATOUT = 23;
	final int MAX_BASIC = 14;
	private HashMap<Integer, String> path_images = new HashMap<Integer, String>();

	/**
	 * <i> <b> Constructeur de la classe data </b> </i><br>
	 * <br>
	 * <code> Data() </code> <br>
	 * 
	 * <p>
	 * Initialise la classe data en récupérant les informations du fichier contenu dans la variable file.
	 * </p>
	 * 
	 * 
	 */
	Data() {
		take_info();
	}
	
	/**
	 * <i> <b> take_info </b> </i><br>
	 * <br>
	 * <code> public void take_info() </code> <br>
	 * 
	 * <p>
	 * Récupère les chemin d'accès des images en fonction de leur identifiants contenu dans le ficheir link.csv.
	 * </p>
	 * 
	 * 
	 */
	public void take_info() {
		try {
			InputStream ips = new FileInputStream(file);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String line = br.readLine();
			while (line != null) {
				System.out.println(line);
				path_images.put(Integer.parseInt(line.split(":")[0]), line.split(":")[1]);
				line = br.readLine();
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <i> <b> getImage </b> </i><br>
	 * <br>
	 * <code> public void getImage() </code> <br>
	 * 
	 * <p>
	 * Retourne le chemin d'accès de l'image d'identifiant id.
	 * </p>
	 * 
	 * @param id : Identifiant de l'image.
	 * @return le chemin d'accès de l'image d'ID .
	 * 
	 */ 
	public String getImage(int id) {
		return path_images.get(id);
	}
}
