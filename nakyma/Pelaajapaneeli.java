package nakyma;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;


import model.Pelaaja;
/**Paneeli, jossa nakyy pelaajan nimi, saldo, onko tahti/hevosenkenka tahden 
 * loytymisen jalkeen..
 * 
 * @author Eeva
 *
 */


public class Pelaajapaneeli extends JPanel implements ActionListener{

	private JLabel saldoRuutu, onkoTimantti, nimileibeli;
	private GridLayout asettelija;
	private Pelaaja pelaaja;
	private int tili;
	private String nimi;
	private Timer ajastin;
	private Border reuna;
	private Border raisedbevel;
	private Border loweredbevel;
	private Icon icon, papu, ankkuri;
	

/**Pelaajapaneelin konstruktori. Pelaajapaneeli on GridLayoutilla muodostettu, 
 * kolmeen jakautuva paneeli: siina nakyy pelaajan nimi ja kuva (nimi JLABEL),
 *  tili ja onko Afrikan Tahtea loydetty.
 * 
 * @param kanvaasi
 * @param pelaaja
 * @param icon2, pelaajan kuva
 */
	public Pelaajapaneeli(Kanvaasi kanvaasi, Pelaaja pelaaja, ImageIcon icon2) {

		raisedbevel = BorderFactory.createRaisedBevelBorder();
		loweredbevel = BorderFactory.createLoweredBevelBorder();
		this.reuna = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
		this.setBorder(this.reuna);
		this.pelaaja = pelaaja;
		this.tili = this.pelaaja.annaSaldo();
		this.nimi = this.pelaaja.annaNimi();
		this.setBackground(Color.gray);
		this.asettelija = new GridLayout(3, 1);
		this.setLayout(this.asettelija);
		this.icon = icon2;
		this.papu = new ImageIcon("kultainenpapu.png");
		this.ankkuri = new ImageIcon("ankkuri.png");
		this.nimileibeli = new JLabel(this.nimi, this.icon, JLabel.CENTER);
		this.nimileibeli.setIconTextGap(2);
		this.nimileibeli.setVerticalTextPosition(JLabel.BOTTOM);
		this.nimileibeli.setHorizontalTextPosition(JLabel.CENTER);
		this.nimileibeli.setFont(new Font("MV Boli", Font.BOLD, 18));
		this.add(this.nimileibeli);
		this.saldoRuutu = new JLabel("Pelaajan tililla " + this.tili + " $");
		this.saldoRuutu.setFont(new Font("MV Boli", Font.PLAIN, 15));
		this.add(this.saldoRuutu);
		this.onkoTimantti = new JLabel(null, null, JLabel.CENTER);
		this.add(this.onkoTimantti);
		this.onkoTimantti.setFont(new Font("MV Boli", Font.PLAIN, 15));
		this.ajastin = new Timer(600, this);
		this.ajastin.setInitialDelay(1900);
		this.ajastin.start();
		this.setBackground(new Color(185, 155, 131));
	}

	/**Paivittaa pelaajan tilin ja onko tahtea loytynyt. Ajastin kutsuu.
	 */
	public void paivita() {
		this.saldoRuutu.setText("Pelaajan tililla " + 
				this.pelaaja.annaSaldo() + " $"); 
		if (this.pelaaja.loytanytTahden()){
			this.timanttiLoytynyt();
		}
		if (this.pelaaja.loytanytKengan()) {
			this.kenkaLoytynyt();
		}
		repaint();
	}

	/**Muodostetaan uusi leibeli, kun Afrikan Tahti loytyy.
	 * 
	 */
	public void timanttiLoytynyt() {
		this.onkoTimantti.setText("Kultainen Kahvipapu!");
		this.onkoTimantti.setIcon(this.papu);
		this.onkoTimantti.setIconTextGap(2);
		repaint();
	}
	
	/**Muutetaan onkoTimantti-leibelia vastaamaan sita, etta hevosenkenka
	 * on loytynyt
	 */
	public void kenkaLoytynyt() {
		this.onkoTimantti.setIcon(this.ankkuri);
		this.onkoTimantti.setText("Hevosenkenka!");
		this.onkoTimantti.setIconTextGap(2);
		repaint();
	}

	/**Ajastin auttaa pelaajapaneelin paivityksessa.
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		this.paivita();
		this.ajastin.restart();

	}

}
