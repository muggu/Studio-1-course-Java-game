package pelimerkit;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import model.Ruutu;



public class Tyhja extends Pelimerkki {

	private boolean onAvattu;
	private Ruutu ruutu;
	
	public Tyhja(Ruutu m) {
		super(m);
		this.ruutu = m;
		this.onAvattu = false;
	}

	@Override
	public void pelimerkinAvaus() {
		this.ruutu.annaNappi().setBackground(Color.black);
		ImageIcon icon = new ImageIcon("coconut.png");
		this.ruutu.annaNappi().setIcon(icon);
		JOptionPane.showMessageDialog(this.ruutu.annaKarttapaneeli(),
			    "Avasit tyhjan ruudun.",
			    "Hoh...",
			    JOptionPane.INFORMATION_MESSAGE,
			    icon);	
	}

	@Override
	public String toString() {
		return "Tyhja";
	}


}
