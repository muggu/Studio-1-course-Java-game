package pelimerkit;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import model.Ruutu;



public class Hevosenkenka extends Pelimerkki {

	private boolean onAvattu;
	private Ruutu ruutu;
	
	public Hevosenkenka(Ruutu m) {
		super(m);
		this.ruutu = m;
		this.onAvattu = false;
	}

	@Override
	public void pelimerkinAvaus() {
		this.ruutu.annaNappi().setBackground(Color.black);
		ImageIcon icon = new ImageIcon("ankkuri.png");
		this.ruutu.annaNappi().setIcon(icon);
		JOptionPane.showMessageDialog(this.ruutu.annaKarttapaneeli(),
			    "Sait ankkurin!",
			    "Jes!",
			    JOptionPane.INFORMATION_MESSAGE,
			    icon);	
	}

	@Override
	public String toString() {
		return "Hevosenkenka";
	}

	
	

}
