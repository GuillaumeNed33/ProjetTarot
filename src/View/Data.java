package View;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

public class Data {
	final String path_file = "haha";
	private Vector<String> path_images;
	private Vector<String> texts;
	private Vector<String> path_sounds;
	
	private enum Read_State {
		IMAGE,
		TEXT,
		SOUND
	}
	
	public void take_info() {
		Read_State state = null;
		try{
			InputStream ips=new FileInputStream(path_file); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String line = br.readLine();
			while (line != null){
				switch(line){
				case "IMAGE" :
					state = Read_State.IMAGE;
					break;
				case "TEXT" : 
					state = Read_State.TEXT;
					break;
				case "SOUND" :
					state = Read_State.SOUND;
					break;
				default:
					switch(state) {
					case IMAGE:
						path_images.add(line);
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
				
			}
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
	public String getImage(int i) {
		return path_images.get(i);
	}
	public String getSound(int i) {
		return path_sounds.get(i);
	}
	public String getText(int i) {
		return texts.get(i);
	}
}
