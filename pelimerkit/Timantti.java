package pelimerkit;
import java.awt.Color;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import model.Ruutu;

/**Timantti-pelimerkin nakyma
 * 
 * @author Eeva
 *
 */

public class Timantti extends Pelimerkki{

	private Ruutu ruutu;

	public Timantti(Ruutu k) {
		super(k);
		this.ruutu = k;
	}

	@Override
	public void pelimerkinAvaus() {
		this.setOpaque(false);
		ImageIcon icon = new ImageIcon("papu1.png");
		setIcon(icon);
		JOptionPane.showMessageDialog(this.ruutu.annaKarttapaneeli(),
				"Loysit pavun!",
				"rahaa!",
				JOptionPane.INFORMATION_MESSAGE,
				icon);
		Aaniefektit.KACHING.play();

		repaint();
	}

	@Override
	public String toString() {
		return "Timantti " + this.ruutu.annaSijaintiX() + this.ruutu.annaSijaintiY();
	}


}
