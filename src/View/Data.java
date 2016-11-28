package View;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Vector;

import Model.CardType;
import Model.CardValue;

public class Data {
	final String path_file = "./ressources/link.csv";
	final int MAX_ATOUT = 21;
	final int MAX_BASIC = 14;
	private HashMap<Integer, String> path_images = new HashMap<Integer, String>();
	private Vector<String> texts = new Vector<String>();
	private Vector<String> path_sounds = new Vector<String>();

	// Excuse = 0
	// Atout = 1 to 21
	// Trefle = 22 to 35 (As to Roi)
	// Pique = 36 to 49
	// Carreau = 50 to 63
	// Coeur = 64 to 77
	Data() {
		take_info();
	}
	private enum Read_State {
		IMAGE, TEXT, SOUND
	}

	public void take_info() {
		Read_State state = null;
		try {
			InputStream ips = new FileInputStream(path_file);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String line = br.readLine();
			while (line != null) {
				switch (line) {
				case "IMAGE":
					state = Read_State.IMAGE;
					break;
				case "TEXT":
					state = Read_State.TEXT;
					break;
				case "SOUND":
					state = Read_State.SOUND;
					break;
				default:
					switch (state) {
					case IMAGE:
						//System.out.println(line);
						path_images.put(Integer.parseInt(line.split(":")[0]), line.split(":")[1]);
						break;
					case SOUND:
						path_sounds.add(line);
						break;
					case TEXT:
						texts.add(line);
						break;
					default:
						break;
					}
					break;
				}
				line = br.readLine();
			}
			br.close();
		} catch (Exception e) {
			System.out.println(e.toString());
			
		}
	}

	public String getSound(int i) {
		return path_sounds.get(i);
	}

	public String getText(int i) {
		return texts.get(i);
	}

	public String getImage(CardType type, CardValue value) {
		if (type.getBasics() != null) {
			switch (type.getBasics()) {
			case CARREAUX:
				return path_images.get(MAX_ATOUT + (2 * MAX_BASIC) + value.getVal());
			case COEUR:
				return path_images.get(MAX_ATOUT + (3 * MAX_BASIC) + value.getVal());
			case PIQUE:
				return path_images.get(MAX_ATOUT + (1 * MAX_BASIC) + value.getVal());
			case TREFLE:
				return path_images.get(MAX_ATOUT + value.getVal());
			default:
				break;

			}
		} else {
			switch (type.getSpecials()) {
			case ATOUT:
				return path_images.get(value.getVal());
			case EXCUSE:
				return path_images.get(0);
			default:
				break;

			}
		}
		return null;
	}
}
