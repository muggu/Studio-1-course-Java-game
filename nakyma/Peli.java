package nakyma;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import kartta.XMLParser;

import model.Pelaaja;
import model.Pelimoottori;

import org.xml.sax.SAXException;
/**Peli-luokka (entinen nimi Aloitussivu), joka huolehtii pelin aloituksesta,
 *  nakymista ja johon CardLayout kortit lisataan. Huolehtii myos peliruutujen 
 *  asettelusta oikeille paikoilleen (XML-Parser) ja uusien pelien aloittamisesta.
 * 
 * @author Eeva
 *
 */

public class Peli {

	final static String BUTTONPANEL = "Card with JButtons";
	private JPanel card;
	private JPanel aloitussivu;
	private Ohjesivu ohjesivu;
	private Ikkuna ikkuna;
	private Pelimoottori moottori;
	private Kanvaasi kanvaasi;
	private String pelaaja1, pelaaja2;


	public Peli() {
		this.pelaaja1 = null;
		this.pelaaja2 = null;
		this.ikkuna = new Ikkuna(this);
		this.card = new JPanel(new CardLayout());
		this.aloitussivu = new Ekasivu(this);
		this.ohjesivu = new Ohjesivu(this);
		this.moottori = new Pelimoottori();
		this.uusiPeli();
		this.card.add(this.aloitussivu, BUTTONPANEL);
		this.card.add(this.ohjesivu, this.ohjesivu.annaString());
		this.card.add(this.kanvaasi, this.kanvaasi.annaString());
		this.ikkuna.add(this.card);
		this.ikkuna.pack();
		this.ikkuna.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.ikkuna.setVisible(true);
	}

	/**Aloitussivun main-ohjelmametodi, jolla aloitetaan peli
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		Peli uusi = new Peli();
	}


/**
 * @return palauttaa Ikkunaluokan instanssi
 */
	public Ikkuna annaIkkuna() {
		return this.ikkuna;
	}
	
	/**
	 * @return palauttaa peli  pelimoottorin
	 */
	public Pelimoottori annaMoottori() {
		return this.moottori;
	}


	/**Lisataan uusi CardLayoutin kortti cardiin.
	 * 
	 * @param k, lisattava kortti
	 */
	public void lisaaKortteihin(Kanvaasi k) {
		this.card.add(k, k.annaString());
	}

	/**
	 * 
	 * @return palauttaa CardLayoutin cardin, jonka avulla voidaan nayttaa 
	 * muita kortteja.
	 */
	public JPanel annaCard() {
		return this.card;
	}
	/**
	 * 
	 * @return palauttaa Ekasivun tunnisteen
	 */
	public String annaString () {
		return Peli.BUTTONPANEL;
	}
	/**
	 * 
	 * @return palauttaaa pelin ohjesivun
	 */
	public Ohjesivu annaOhjesivu() {
		return this.ohjesivu;
	}
	/**
	 * 
	 * @return palauttaa pelin kanvaasin (pelinakyma)
	 */
	public Kanvaasi annaKanvaasi() {
		return this.kanvaasi;
	}

	/**Luetaan XML-Parserin avulla XML-tiedosto, joka sisaltaa peliruutujen 
	 * koordinaatit. Asetetaan myos pelaajat paikoilleen ruutuihin, pelaajille
	 * ikonit, ja luodaan pelaajapaneelit. Samalla myos arvotaan pelimerkit 
	 * paikoilleen.
	 * 
	 */
	public void peliruudutPaikoilleen() {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
			SAXParser sp = spf.newSAXParser();
			XMLParser parsa = new XMLParser(this.kanvaasi.annaKarttapaneeli());
			File file1 = new File("1751.xml");
			sp.parse(file1, parsa);
			parsa.lueListat();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
		ImageIcon icon1paneeli = new ImageIcon("pelaaja1.png");
		ImageIcon icon2paneeli = new ImageIcon("pelaaja2.png");
		ImageIcon icon1 = new ImageIcon("pelaaja1pienempi.png");
		ImageIcon icon2 = new ImageIcon("pelaaja2pienempi.png");
		Pelaaja pelaaja = new Pelaaja(this.pelaaja2, 
				this.kanvaasi.annaKarttapaneeli().annaLahtoruutu(), moottori, icon1);
		Pelaaja pelaaja2 = new Pelaaja(this.pelaaja1, 
				kanvaasi.annaKarttapaneeli().annaLahtoruutu(), moottori, icon2);
		moottori.lisaaPelaaja(pelaaja);
		moottori.lisaaPelaaja(pelaaja2);
		
		Pelaajapaneeli ppaneeli = new Pelaajapaneeli(kanvaasi, pelaaja, icon1paneeli);
		Pelaajapaneeli paneeli = new Pelaajapaneeli(kanvaasi, pelaaja2, icon2paneeli);
		kanvaasi.annaPelpanPaneeli().add(paneeli);
		kanvaasi.annaPelpanPaneeli().add(ppaneeli);
		kanvaasi.annaKarttapaneeli().arvotaanPelimerkit();
		kanvaasi.annaMoottori().asetaVuoro(pelaaja2);
		kanvaasi.annaNoppa().muutaNoppaa(kanvaasi.annaMoottori().annaVuorossa().annaSilmaluku());
	}

	/**Luodaan ensimmainen uusipeli. Uusi moottori, uusi kanvaasi, arvotaan
	 * pelimerkit, ja pelinakyma esiin.
	 * 
	 */
	public void uusiPeli() {
		if (this.kanvaasi != null) {
			this.card.remove(this.kanvaasi);
		}
		this.moottori = new Pelimoottori();
		this.kanvaasi = new Kanvaasi(this.ikkuna, this.moottori);
		this.peliruudutPaikoilleen();
		this.card.add(this.kanvaasi, this.kanvaasi.annaString());
		this.kanvaasi.validate();
		this.ikkuna.validate();
	}

	/**Luodaan uudestaan uusi Peli. Jos pelaaja antaa nimet, luodaan uusipeli.
	 * Muuten mitaan ei tapahdu.
	 */
	public void uudestaanPeli() {
		if (this.pelaajienNimeaminen()) {
			this.uusiPeli();
			CardLayout cl = (CardLayout)(this.annaCard().getLayout());
			cl.show(this.annaCard(), this.annaKanvaasi().annaString());
			this.annaIkkuna().asetaAlapaneelinteksti(this.moottori.annaVuorossa());
			this.viestiruutu();
		}
		else {
			CardLayout cl = (CardLayout)(this.annaCard().getLayout());
			cl.show(this.annaCard(), this.BUTTONPANEL);
		}
	}

	/**Kayttajalta kysytaan pelaajille nimet, jotka eivat saa olla null (tai
	 * cancel) eika yli 15 merkkia pitkat. Jos yli 15 merkkia, pelaajalle tulee
	 * ennalta maaritellyt nimet.
	 * 
	 * @return true, jos pelaajien nimet ei null (/= painettu cancel)
	 */
	public boolean pelaajienNimeaminen() {
		this.pelaaja1 = (String)JOptionPane.showInputDialog(
				this.ikkuna,
				"Anna ensimmaiselle pelaajalle nimi (max. 15 merkkia): ",
				"",
				JOptionPane.PLAIN_MESSAGE,
				null,
				null,
				"Pelaaja1");
		this.pelaaja2 = (String)JOptionPane.showInputDialog(
				this.ikkuna,
				"Anna toisella pelaajalle nimi: ",
				"",
				JOptionPane.PLAIN_MESSAGE,
				null,
				null,
				"Pelaaja2");
		//jos painettu "cancel"
		if (this.pelaaja1 == null || this.pelaaja2 == null) {
			return false;
		}
		if (this.pelaaja1.length() > 15) {
			this.pelaaja1 = "Pelaaja1";
		}
		if (this.pelaaja2.length() > 15) {
			this.pelaaja2 = "Heppatytto92";
		}
		return true;
	}

	/**JDialog, joka kertoo kummalla pelaajalla ensimaisena vuoro.
	 */
	public void viestiruutu() {
		JOptionPane.showMessageDialog(this.kanvaasi,
				"Ensimmaisena pelivuorossa " + this.kanvaasi.annaMoottori().annaVuorossa(),
				"Javan tahti",
				JOptionPane.INFORMATION_MESSAGE,
				null);	
	}
	

}
