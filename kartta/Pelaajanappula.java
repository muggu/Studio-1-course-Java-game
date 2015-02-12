package kartta;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashSet;
import java.util.Set;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;


import model.Pelaaja;
import model.Ruutu;

import pelimerkit.AfrikanTahti;
import pelimerkit.Hevosenkenka;
import pelimerkit.Rosvo;
import pelimerkit.Timantti;
import pelimerkit.Tyhja;

/**Pelaajaa ilmentava Pelaajanappu,a
 * 
 * @author Eeva
 *
 */
public class Pelaajanappula extends JButton {

	private int sijaintiX;
	private int sijaintiY;
	private Karttapaneeli karttapaneeli;
	private Image icon;
	private Pelaaja pelaaja;

	public Pelaajanappula(int sijaintiX, int sijaintiY, Karttapaneeli 
			karttapaneeli, Pelaaja pelaaja, ImageIcon icon1) {
		System.out.println("luodaan pelaajanappula");
		System.out.println("pelaaja " + pelaaja);
		this.sijaintiX = sijaintiX;
		this.sijaintiY = sijaintiY;
		this.karttapaneeli = karttapaneeli;
		this.pelaaja = pelaaja;
		this.setBackground(Color.black);
		this.setOpaque(false);
		this.setBounds(sijaintiX, sijaintiY, 20, 20);
		this.setLocation(this.sijaintiX, this.sijaintiY);
		this.icon = icon1.getImage();
		this.karttapaneeli.add(this);

	}
/**Piiretaan pelaajanappula
 * 
 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(icon, 0, 0, null);
	}

	public Pelaaja annaPelaaja() {
		return this.pelaaja;
	}
	
	/**Siirtaa pelaajan kuvan ruutuun.
	 * Jos kyseessa avattava ruutu, se avataan. 
	 * @param ruutu
	 */
	public void siirraPelNappi(Ruutu ruutu, boolean avataanko) {
		this.sijaintiX = ruutu.annaNappi().getLocation().x;
		this.sijaintiY = ruutu.annaNappi().getLocation().y;
		
			if (this.pelaaja.annaKarttapaneeli().annaPelimerkilliset().contains(ruutu)) {
			Ruutunappi alla = ruutu.annaNappi();
			if (!this.pelaaja.annaMoottori().annaLauta().annaAvatutRuudut().contains(ruutu)) {
				if (avataanko) {
					System.out.println("avaamaton ruutu");
					if (ruutu.onRosvo()) {
						Rosvo rosvo = new Rosvo(ruutu);
						alla = rosvo;
						if (alla instanceof Rosvo) {
							Rosvo rosmo = (Rosvo) alla;
							rosmo.pelimerkinAvaus();
							repaint();
						}
					}
					else if (ruutu.onAfrTahti()) {
						AfrikanTahti afr = new AfrikanTahti(ruutu);
						alla = afr;
						if (alla instanceof AfrikanTahti) {
							AfrikanTahti af = (AfrikanTahti) alla;
							af.pelimerkinAvaus();
							this.karttapaneeli.add(af);
							repaint();
						}
					} 
					else if (ruutu.onTyhja()) {
						Tyhja tyhja = new Tyhja(ruutu);
						alla = tyhja;
						if (alla instanceof Tyhja) {
							Tyhja uusi = (Tyhja) alla;
							uusi.pelimerkinAvaus();
							this.karttapaneeli.add(uusi);
							repaint();
						}
					}
					else if (ruutu.onHevosenkenka()) {
						Hevosenkenka kenka = new Hevosenkenka(ruutu);
						alla = kenka;
						if (alla instanceof Hevosenkenka) {
							Hevosenkenka uusi = (Hevosenkenka) alla;
							uusi.pelimerkinAvaus();
							this.karttapaneeli.add(uusi);
							repaint();
						}
					}
					else if (ruutu.onTimantti()) {
						Timantti tim = new Timantti(ruutu);
						alla = tim;
						if (alla instanceof Timantti) {
							Timantti t = (Timantti) alla;
							
							t.pelimerkinAvaus();
							this.karttapaneeli.add(t);
							repaint();
						}
					}
				}
			}
		}
		this.setLocation(this.sijaintiX, this.sijaintiY);
		repaint();
		System.out.println("pelaajan sijainti: " + this.pelaaja.annaSijainti());
	}
	
}

