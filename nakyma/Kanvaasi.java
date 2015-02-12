package nakyma;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import kartta.Karttapaneeli;

import model.Pelimoottori;

/**Pelinakyma, joka lisataan Pelin kortiksi (CardLayout). Kanvaasiin on aseteltu
 *  pelaajapaneelit sisaltava pelpaneelinpaneeli, arpanoppa ja itse kartta(paneeli).
 * 
 * @author Eeva
 *
 */

@SuppressWarnings("serial")
public class Kanvaasi extends JPanel {

	private JPanel sivupaneeeli;
	private BorderLayout sivupaneeliAsettelija;
	private Arpanoppa noppa;
	private JPanel pelpaneelinpaneeli;
	private Karttapaneeli karttapaneeli;
	private Ikkuna ikkuna;
	private Pelimoottori moottori;
	private BorderLayout asettelija;
	final static String KANVAASI = "Kanvaasi";

	
	/**Kanvaasin konstruktori. Luodaan ikkunaan. Sisaltaa sivupaneelin, jonka 
	 * sisaan tulee noppa ja pelaajapaneelit sisalta pelpaanelinpaneeli. 
	 * Luodaan myos uusi karttapaneeli.
	 * 
	 * @param ikkuna
	 * @param moottori
	 */
	public Kanvaasi(Ikkuna ikkuna, Pelimoottori moottori) {
		this.asettelija = new BorderLayout();
		this.setLayout(asettelija);
		this.moottori = moottori;
		this.ikkuna = ikkuna;
		this.sivupaneeeli = new JPanel();
		this.sivupaneeeli.setBackground(Color.blue);
		this.add(this.sivupaneeeli, BorderLayout.WEST);
		this.sivupaneeeli.setPreferredSize(new Dimension (200, 300));
		this.sivupaneeliAsettelija = new BorderLayout();
		this.sivupaneeeli.setLayout(sivupaneeliAsettelija);

		this.noppa = new Arpanoppa(30, 30, this.ikkuna);
		this.noppa.setBackground(new Color(233, 227, 213));
		this.noppa.setMaximumSize(new Dimension(150, 150));
		this.noppa.setMinimumSize(new Dimension(40, 40));
		this.noppa.setPreferredSize(new Dimension (150, 150));

		this.pelpaneelinpaneeli = new JPanel();

		this.sivupaneeeli.add(this.noppa, BorderLayout.NORTH);
		this.sivupaneeeli.add(this.pelpaneelinpaneeli, BorderLayout.CENTER);
		GridLayout GL = new GridLayout(2,1);
		this.pelpaneelinpaneeli.setLayout(GL);

		this.karttapaneeli = new Karttapaneeli(500, 600, this.ikkuna, this);
		this.karttapaneeli.setBackground(Color.gray);
		this.karttapaneeli.setPreferredSize(new Dimension (1127,795));
		this.add(karttapaneeli, BorderLayout.CENTER);

	}

	public Karttapaneeli annaKarttapaneeli() {
		return this.karttapaneeli;
	}

	public Ikkuna annaIkkuna() {
		return this.ikkuna;
	}

	public JPanel annaPelpanPaneeli() {
		return this.pelpaneelinpaneeli;
	}

		
	public Pelimoottori annaMoottori() {
		return this.moottori;
	
	}
	
	public Peli annaAloitussivu() {
		return this.ikkuna.annaSivu();
	}

	public Arpanoppa annaNoppa() {
		return this.noppa;
	}
	
	/**Palauttaa kanvaasin tunnistemerkkijonon, jonka avulla nakymaa muutetaan
	 *  CardLayoutin avulla
	 * 
	 * @return kanvaasin merkkijono
	 */
	public String annaString() {
		return Kanvaasi.KANVAASI;
	}



}
