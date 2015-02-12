package nakyma;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import kartta.Karttapaneeli;

import model.Pelaaja;
import model.Pelimoottori;

import org.xml.sax.SAXException;

/**Ikkuna-luokka, joka muodostaa pelin JFramen. Ikkuna sisaltaa ylapaneelin, 
 * jossa menu kolmella vaihtoehdolla kera hiirikuuntelijoiden (sisaluokkina) ja
 *  alapaneelin, jossa tekstia. Asettelijana BorderLayout.
 * 
 * @author Eeva
 *
 */

public class Ikkuna extends JFrame {
	private BorderLayout asettelija;
	private JMenuBar ylavalikko; //valikko
	private JMenu valikko1; //ensimmainen klikattava nappain valikossa
	private JMenuItem valikkoVaihtoehto1; //valikon vaihtoehto
	private JMenuItem valikkoVaihtoehto2;
	private JPanel alapaneeli;
	private JLabel alapaneelinSisalto;
	private JMenuItem valikkoVaihtoehto3;
	private Peli sivu;


	/**Ikkunan konstruktori: luodaan ylapaneeli ja valikko seka alapaneeli.
	 * 
	 * @param Pelin instanssi parametrina
	 */
	public Ikkuna(Peli sivu) {
		this.sivu = sivu;
		this.asettelija = new BorderLayout();
		this.setLayout(asettelija);
		this.ylavalikko = new JMenuBar();
		this.ylavalikko.setBackground(Color.LIGHT_GRAY);
		this.valikko1 = new JMenu("Peli");
		this.valikkoVaihtoehto1 = new JMenuItem("Aloita uusi peli");
		this.valikkoVaihtoehto2 = new JMenuItem("Lopeta peli");
		this.valikkoVaihtoehto3 = new JMenuItem("Ohjeet");
		this.alapaneelinSisalto = new JLabel("Peli kesken");
		this.alapaneelinSisalto.setBackground(Color.white);
		this.alapaneeli = new JPanel();
		this.alapaneeli.setBackground(Color.LIGHT_GRAY);

		this.add(this.ylavalikko, BorderLayout.NORTH);
		this.ylavalikko.add(this.valikko1);
		this.valikko1.add(this.valikkoVaihtoehto1);
		this.valikko1.addSeparator();
		this.valikko1.add(this.valikkoVaihtoehto3);
		this.valikko1.addSeparator();
		this.valikko1.add(this.valikkoVaihtoehto2);
		this.add(alapaneeli, BorderLayout.SOUTH);
		this.alapaneeli.add(alapaneelinSisalto);
		
		this.setTitle("Javan Tahti");
		this.setSize(new Dimension(1350, 900));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);

		//Kuuntelijat valikon vaihtoehdoille:
		this.valikkoVaihtoehto1.addActionListener(new UusiPeliKuuntelija(this));
		this.valikkoVaihtoehto2.addActionListener(new LopetaPeliKuuntelija());
		this.valikkoVaihtoehto3.addActionListener(new OhjeKuuntelija(this));
	}

/**Muutetaan alapaneelin tekstia. Teksti kertoo kenen vuoro on.
 * @param pelaaja, jonka vuoro on.
 */
	public void asetaAlapaneelinteksti(Pelaaja pelaaja) {
		this.alapaneelinSisalto.setText("Vuoro pelaajalla " + pelaaja);
	}


	/**
	 * 
	 * @return palauttaa Pelin instanssin
	 */
	public Peli annaSivu() {
		return this.sivu;
	}

	/**Valikon Lopeta-vaihtoehdon kuuntelija. Lopetaa pelaamisen ts. sulkee
	 * pelin
	 * 
	 * @author Eeva
	 *
	 */
	class LopetaPeliKuuntelija implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
/**Valikon Uusi peli -vaihtoehdon kuuntelija. Aloittaa uuden pelin.
 * 
 * @author Eeva
 *
 */
	class UusiPeliKuuntelija implements ActionListener {
		private Ikkuna ikkuna;
		public UusiPeliKuuntelija(Ikkuna i) {
			this.ikkuna = i;
		}
		@Override
		public void actionPerformed(ActionEvent a) {
			System.out.println("uusipelikuuntelija");
			this.ikkuna.annaSivu().uudestaanPeli();
			
		}
	}
	
	/**Valikon Ohje-vaihtoehdon kuuntelija. Vaihtaa nakyman ohjesivuun.
	 * @author Eeva
	 *
	 */
	class OhjeKuuntelija implements ActionListener {
		private Ikkuna ikkuna;

		public OhjeKuuntelija(Ikkuna ikkuna) {
			this.ikkuna = ikkuna;
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("ikkuna " + this.ikkuna.annaSivu());
			CardLayout cl = (CardLayout)(this.ikkuna.annaSivu().annaCard().getLayout());
			cl.show(this.ikkuna.annaSivu().annaCard(), 
					this.ikkuna.annaSivu().annaOhjesivu().annaString());
			System.out.println("nappi2");


		}

	}


}
