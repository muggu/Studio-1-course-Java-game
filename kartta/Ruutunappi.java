package kartta;

import java.awt.Color;

import javax.swing.JButton;

import ohjain.PeliruutujenHiiri;

import model.Ruutu;

/**Ruudun view.
 * 
 * @author Eeva
 *
 */
public class Ruutunappi extends JButton {

	private Ruutu ruutu;
	private int x, y;
	private PeliruutujenHiiri hiiri;

/**Luodaan ruutu, asetetaan sille sijainti ja Boundsit. Jos pelimerkillinen 
 * ruutu, ruudusta tulee suurempi.
 * 
 * @param ruutu
 */
	public Ruutunappi(Ruutu ruutu) {
		this.ruutu = ruutu;
		this.x = this.ruutu.annaSijaintiX();
		this.y = this.ruutu.annaSijaintiY();
		this.setBounds(x, y, 20, 20);
		if (ruutu.annaKarttapaneeli().annaPelimerkilliset().contains(ruutu)) {
			this.setBounds(x, y, 40, 40);
		}
		Color c = new Color(255, 150, 255);
		this.setBackground(c);
		this.setOpaque(false); //lapinakyva ruutu
		this.setBorderPainted(false); //TaLLa SAADAAN REUNATKIN VEKS!
		this.setLocation(x, y);
		this.hiiri = new PeliruutujenHiiri(this.ruutu);
		this.addMouseListener(hiiri);
	}
	
	
	public PeliruutujenHiiri annaHiiri() {
		return this.hiiri;
	}
	
	public Ruutu annaRuutu() {
		return this.ruutu;
	}
	
	public String toString() {
		return "Ruutunappi " + this.x + ", " + this.y;
	}
}
