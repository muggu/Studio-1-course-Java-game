package ohjain;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import kartta.Ruutunappi;

import model.Pelilauta;
import model.Ruutu;

/**Ruutujen hiirikuuntelija.
 * 
 * @author Eeva
 *
 */

public class PeliruutujenHiiri implements MouseListener{

	private Ruutu peliruutu;
	private Pelilauta lauta;
	private JTextArea tekstikentta;

	public PeliruutujenHiiri(Ruutu peliruutu) {
		this.peliruutu = peliruutu;
		this.lauta = 
				this.peliruutu.annaKarttapaneeli().annaKanvaasi().annaMoottori().annaLauta();
	}

	/**Ruutua klikattaessa asetetaan pelilautaan klikattu hiiri ja kutsutaan 
	 * BFS-metodia.
	 * 
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == e.BUTTON1) {
			//asetetaan klikatuksi ruuduksi:
			System.out.println("klikattu esine: " + e.getSource());
			if (e.getSource() instanceof Ruutunappi) {
				Ruutunappi nappi = (Ruutunappi) e.getSource();
				this.lauta.asetaKlikattuRuutu(nappi.annaRuutu());
				System.out.println("nappi " + nappi);
				System.out.println("pelaajan sijainti hiiressa " + this.peliruutu.annaKarttapaneeli().annaKanvaasi().annaMoottori().annaVuorossa().annaSijainti());
				this.lauta.BFS();
				this.peliruutu.annaKarttapaneeli().annaKanvaasi().annaMoottori().annaVuorossa().teeSiirto();
			}
		}
	}


	
	/**Jos hiiri menee erikoisruutujen sisaan, naytetaan erikoisruutuun 
	 * liittyva teksti
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		if (this.peliruutu.equals(this.peliruutu.annaKarttapaneeli().annaNopeinruutu())) {
			System.out.println("nopein ruutu");
			this.naytaTietoRuutu();
			this.tekstikentta.setText("Ensimmainen Balille saapuva pelaaja saa 500 $ ylimaaraista lomarahaa.");
			this.tekstikentta.setVisible(true);
		}
		else if (this.peliruutu.equals(this.peliruutu.annaKarttapaneeli().annaOrjaruutu())) {
			System.out.println("orjatruutu");
			this.naytaTietoRuutu();
			this.tekstikentta.setText("Jos Joulusaarilla rikkoo rauhaa ja saa rosvon, joutuu jaamaan 2 vuoroksi orjaksi.");
			this.tekstikentta.setVisible(true);
		}
		else if (this.peliruutu.equals(this.peliruutu.annaKarttapaneeli().annaTuplatruutu())) {
			System.out.println("tuplatruut");
			this.naytaTietoRuutu();
			this.tekstikentta.setText("Timanteista kuuluisassa Martapurassa saa kahvipavusta tupla-hinnan.");
			this.tekstikentta.setVisible(true);
		}
		else if (this.peliruutu.equals(this.peliruutu.annaKarttapaneeli().annaLahtoruutu())){
			this.naytaTietoRuutu();
			this.tekstikentta.setText("Javan paakaupungista alkaa ja paattyy Kultaisen Kahvipavun metsastys.");
		}
	}
	/**Erikoisruuduista poistuttaessa laitetaan tekstikentta nakymattomaksi.
	 * 
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		if (this.peliruutu.equals(this.peliruutu.annaKarttapaneeli().annaNopeinruutu())) {
			this.tekstikentta.setVisible(false);
		}
		else if (this.peliruutu.equals(this.peliruutu.annaKarttapaneeli().annaOrjaruutu())) {
			this.tekstikentta.setVisible(false);
		}
		else if (this.peliruutu.equals(this.peliruutu.annaKarttapaneeli().annaTuplatruutu())) {
			this.tekstikentta.setVisible(false);
		}
		else if (this.peliruutu.equals(this.peliruutu.annaKarttapaneeli().annaLahtoruutu())) {
			this.tekstikentta.setVisible(false);
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
	/**Nayttaa tekstikentan, joka luodaan tassa.
	 * 
	 */
	public void naytaTietoRuutu() {
		this.tekstikentta = new JTextArea("tekstia");
			this.tekstikentta.setFont(new Font("Serif", Font.ITALIC, 15));
			this.tekstikentta.setLineWrap(true);
			this.tekstikentta.setWrapStyleWord(true);
			this.peliruutu.annaKarttapaneeli().add(this.tekstikentta);
			this.tekstikentta.setBounds(new Rectangle(this.peliruutu.annaSijaintiX()+30, 
					this.peliruutu.annaSijaintiY()-5, 200, 70));
	}
	
}
