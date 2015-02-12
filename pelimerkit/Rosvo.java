package pelimerkit;
import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import model.Ruutu;


public class Rosvo extends Pelimerkki {

	private boolean onAvattu;
	private Ruutu ruutu;
	private Timer ajastin;
	
	public Rosvo(Ruutu m) {
		super(m);
		this.ruutu = m;
		this.onAvattu = false;
	}

	@Override
	public void pelimerkinAvaus() {
		this.ruutu.annaNappi().setBackground(Color.black);
		ImageIcon icon = new ImageIcon("paakallo.png");
		this.ruutu.annaNappi().setIcon(icon);
		JOptionPane.showMessageDialog(this.ruutu.annaKarttapaneeli(),
			    "Tormasit rosvoon, ja menetit kaiken!",
			    "O-ou...",
			    JOptionPane.INFORMATION_MESSAGE,
			    icon);	
		Aaniefektit.LAUKAUS.play();
		try {
			System.out.println("NUKKUUUUKUKKUU");
			Thread.sleep(2300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Aaniefektit.ROSVORAHAT.play();
	}

	@Override
	public String toString() {
		return "Rosvo ";
	}

	
	
	
}
