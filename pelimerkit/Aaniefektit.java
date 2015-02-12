package pelimerkit;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/**Pelin aaniefektit.
 * 
 * @author Eeva
 *
 */

public enum Aaniefektit {

	KACHING("cha-ching.wav"),  
	LAUKAUS("Laukaus.wav"),
	ROSVORAHAT("rosvorahat.wav"),
	AFRTAHTI("uutimantti.wav"),
	VOITTO("voitto.wav");    

	public static enum Volume {
		MUTE, LOW, MEDIUM, HIGH
	}

	public static Volume volume = Volume.LOW;
	private Clip clip;

	/**
	 * 
	 * @param soundFileName
	 */
	private Aaniefektit(String soundFileName) {
		System.out.println(soundFileName);
		try {
			if (soundFileName.equals("cha-ching.wav")) {
				System.out.println("jahu");
				File filu = new File("cha-ching.wav");
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(filu);
				clip = AudioSystem.getClip();
				clip.open(audioInputStream);
			}
			if (soundFileName.equalsIgnoreCase("Laukaus.wav")) {
				File filu2 = new File("Laukaus.wav");
				AudioInputStream audioInputStream2 = AudioSystem.getAudioInputStream(filu2);
				clip = AudioSystem.getClip();
				clip.open(audioInputStream2);
			}
			if (soundFileName.equalsIgnoreCase("rosvorahat.wav")) {
				File filu2 = new File("rosvorahat.wav");
				AudioInputStream audioInputStream2 = AudioSystem.getAudioInputStream(filu2);
				clip = AudioSystem.getClip();
				clip.open(audioInputStream2);
			}
			if (soundFileName.equalsIgnoreCase("uutimantti.wav")) {
				File filu2 = new File("uutimantti.wav");
				AudioInputStream audioInputStream2 = AudioSystem.getAudioInputStream(filu2);
				clip = AudioSystem.getClip();
				clip.open(audioInputStream2);
			}
			if (soundFileName.equalsIgnoreCase("voitto.wav")) {
				File filu2 = new File("voitto.wav");
				AudioInputStream audioInputStream2 = AudioSystem.getAudioInputStream(filu2);
				clip = AudioSystem.getClip();
				clip.open(audioInputStream2);
			}
		} 
		catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	/**Soittaa klipin, ja jos ennestaan aaniefekti pyorimassa, pysayttaa sen ja 
	 * aloittaa alusta.
	 * 
	 */
	public void play() {
		if (volume != Volume.MUTE) {
			if (clip.isRunning()) {
				clip.stop();
			}
			clip.setFramePosition(0); 
			clip.start();     
		}
	}
	static void init() {
		values(); //kutsuu kaikkien elementtien konstruktori..
	}


}
