package View;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import javafx.scene.control.Button;

/**
 *  <i> <b> ButtonView </b> </i><br>
 *  
 * Classe permettant la r�cup�ration du chemin d'acc�s des images grace au fichier link.csv
 */
public class Data {
	final String FILE = "./ressources/link.csv";
	final int MAX_ATOUT = 23;
	final int MAX_BASIC = 14;
	private HashMap<Integer, String> path_images = new HashMap<Integer, String>();

	/**
	 * <i> <b> Constructeur de la classe data </b> </i><br>
	 * <br>
	 * <code> Data() </code> <br>
	 * 
	 * <p>
	 * Initialise la classe data en r�cup�rant les informations du fichier contenu dans la variable file.
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
	 * <code> private void take_info() </code> <br>
	 * 
	 * <p>
	 * R�cup�re les chemin d'acc�s des images en fonction de leur identifiants contenu dans le ficheir link.csv.
	 * </p>
	 * 
	 * 
	 */
	private void take_info() {
		try {
			InputStream ips = new FileInputStream(FILE);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String line = br.readLine();
			while (line != null) {
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
	 * <code> public void getImage(int id) </code> <br>
	 * 
	 * <p>
	 * Retourne le chemin d'acc�s de l'image d'identifiant id.
	 * </p>
	 * 
	 * @param id : Identifiant de l'image.
	 * @return le chemin d'acc�s de l'image d'ID .
	 * 
	 */ 
	public String getImage(int id) {
		return path_images.get(id);
	}
}
