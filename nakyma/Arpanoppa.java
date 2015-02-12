package nakyma;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import model.Pelimoottori;

/**Arpanoppa on omassa paneelissaan.
 * Joka vuoron alussa arpanoppa pyorahtaa itsestaan.
 * 
 * @author Eeva
 *
 */
public class Arpanoppa extends JPanel {

	private Random rand = new Random();
	private Ikkuna kartta;
	private int leveys;
	private int korkeus;
	private JLabel leibeli;
	private Pelimoottori moottori;
	private Border reuna;
	private TitledBorder otsikko;
	private Border raisedbevel;
	private Icon icon1, icon2, icon3, icon4, icon5, icon6;
	private Font titleFont;

	public Arpanoppa(int leveys, int korkeus, Ikkuna kartta) {
		this.raisedbevel = BorderFactory.createRaisedBevelBorder();
		this.reuna = BorderFactory.createLineBorder(Color.black);
		this.kartta = kartta;
		this.leveys = leveys;
		this.korkeus = korkeus;
		this.leibeli = new JLabel();
		this.icon1 = new ImageIcon("noppa1.png");
		this.icon2 = new ImageIcon("noppa2.png");
		this.icon3 = new ImageIcon("noppa3.png");
		this.icon4 = new ImageIcon("noppa4.png");
		this.icon5 = new ImageIcon("noppa5.png");
		this.icon6 = new ImageIcon("noppa6.png");
		this.setBorder(this.reuna);
		this.otsikko = BorderFactory.createTitledBorder(this.reuna, "Noppa");
		this.titleFont = new Font("MV Boli", Font.PLAIN, 20);
		this.otsikko.setTitleFont(titleFont);
		this.setBorder(this.otsikko);
		this.add(this.leibeli);

	}

	/**Arvotaan nopan silmaluku valilta 1-6.
	 * 
	 * @return silmaluku, [1,6]
	 */
	public int annaSilmaluku() {
		int silmaluku = rand.nextInt(6) + 1;
		return silmaluku;
	}

	
	/**Muuttaa nopan kuvan silmalukua vastaavaksi.
	 * 
	 * @param n, silmaluku
	 */
	public void muutaNoppaa(int n) {
		if (n == 1) {
			this.leibeli.setIcon(this.icon1);
		}
		if (n == 2) {
			this.leibeli.setIcon(this.icon2);
		}
		if (n == 3) {
			this.leibeli.setIcon(this.icon3);
		}
		if (n == 4) {
			this.leibeli.setIcon(this.icon4);
		}
		if (n == 5) {
			this.leibeli.setIcon(this.icon5);
		}
		if (n == 6) {
			this.leibeli.setIcon(this.icon6);
		}
		repaint();
	}


/**Palauttaa nopan merkkijonon (testauksessa kaytossa)
 */
	public String toString() {
		return "Arpanoppa";
	}



}
