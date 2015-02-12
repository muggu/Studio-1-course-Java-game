package kartta;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import model.Ruutu;
import nakyma.Ikkuna;
import nakyma.Kanvaasi;

import pelimerkit.Rosvo;
import pelimerkit.Timantti;

/**Karttapaneelissa muodostetaan karttapaneeli:
 * arvotaan pelimerkit paikoilleen,
 * ottaa parametreiksi leveyden, korkeuden ja kartta-luokan.
 * MouseListenerin avulla tutkitaan koordinaatteja testeissa.
 * 
 * @author Eeva
 *
 */
public class Karttapaneeli extends JPanel implements MouseListener {

	private Ikkuna ikkuna;
	private ArrayList<Ruutu> pelimerkilliset;
	private List<Ruutu> timantit, rosvot, tyhjat, hevosenkengat;
	private Image icon;
	private Ruutu lahtoruutu, orjaruutu, tuplatruutu, nopeinRuutu; // kaytetaan XMLparsassa
	private HashSet<Ruutu> peliruudut = new HashSet<Ruutu>(); //kaytetaan parsan kaa
	private Random rand = new Random();
	private Ruutu aTahti;
	private Kanvaasi kanvaasi;

	/**Arvotaan timantit, afrikan tahti, rosvot paikoilleen.
	 * Karttapaneelilla on tietty korkeus ja leveys, jotka maaritelty Kartta-
	 * luokassa. Karttapaneeli kuuluu Karttaan.
	 * 
	 * @param korkeus
	 * @param leveys
	 * @param kartta
	 */
	public Karttapaneeli (int korkeus, int leveys, Ikkuna kartta, Kanvaasi kanvaasi) {
		this.ikkuna = kartta;
		this.kanvaasi = kanvaasi;
		System.out.println("karttapaneelin ikkuna " + this.ikkuna);
		this.pelimerkilliset = new ArrayList<Ruutu>();
		this.timantit = new ArrayList<Ruutu>();
		this.rosvot = new ArrayList<Ruutu>();
		this.tyhjat = new ArrayList<Ruutu>();
		this.hevosenkengat = new ArrayList<Ruutu>();

		this.icon = new ImageIcon("kartta2.jpg").getImage();
		this.addMouseListener(this);
		this.setLayout(null);
	
	}

	/**Palauttaa komponentin (peliruutu) tietyissa koordinaateissa.
	 * 
	 * @param x komponentin x-koordinaatti
	 * @param y komponentin y-koordinaatti
	 * @return komponentti, joka koordinaateissa.
	 */
	public Component annaPeliruutu(int x, int y) {
		return (Component) this.getComponentAt(x, y);
	}
	public void asetaLahtoruutu(Ruutu ruutu) {
		this.lahtoruutu = ruutu;
	}
	public Ruutu annaLahtoruutu() {
		return this.lahtoruutu;
	}
	public void asetaNopeinruutu(Ruutu ruutu) {
		this.nopeinRuutu = ruutu;
	}
	public void asetaOrjaruutu(Ruutu ruutu) {
		this.orjaruutu = ruutu;
	}
	public void asetaTuplatruutu(Ruutu ruutu) {
		this.tuplatruutu = ruutu;
	}
	public Ruutu annaNopeinruutu() {
		return this.nopeinRuutu;
	}
	public Ruutu annaOrjaruutu() {
		return this.orjaruutu;
	}
	public Ruutu annaTuplatruutu() {
		return this.tuplatruutu;
	}

	/**Lisaa avattavien ruutujen listaan uuden ruudun r.
	 * 
	 * @param avattava ruutu r
	 */
	public void lisaaPelimerkillisiin(Ruutu r) {
		this.pelimerkilliset.add(r);
	}

	public Ikkuna annaIkkuna() {
		return this.ikkuna;
	}

	public Kanvaasi annaKanvaasi() {
		return this.kanvaasi;
	}


	public void arvotaanPelimerkit() {
		//arvotaan afrikan tahdelle paikka:
		int arvottuluku = rand.nextInt(this.pelimerkilliset.size());
		this.pelimerkilliset.get(arvottuluku).asetaAfrTahdeksi();
		this.aTahti = this.pelimerkilliset.get(arvottuluku);
		System.out.println("afrikan tahti " + this.aTahti);

		//arvotaan rosvot ja tahdet:
		for (int i = 0; i < this.pelimerkilliset.size(); i ++) {
			if (this.pelimerkilliset.get(i).equals(this.aTahti)){
				continue;
			}
			else {
				Double arpa = rand.nextDouble();
				Ruutu ruutu = this.pelimerkilliset.get(i);
				if (arpa < 0.15 ) { //0.15
					this.rosvot.add(ruutu);
					ruutu.asetaRosvoksi();
				}
				else if (arpa <0.35) { //0.35
					this.tyhjat.add(ruutu);
					ruutu.asetaTyhjaksi();
				}
				else if (arpa <0.65) { //0.65
					this.hevosenkengat.add(ruutu);
					ruutu.asetaHevosenkengaksi();
				}
				else {
					this.timantit.add(ruutu);
					ruutu.asetaTimantiksi();
				}
			}	
		}
	}

	public List<Ruutu> annaTimantit() {
		return this.timantit;
	}

	public List<Ruutu> annaRosvot() {
		return this.timantit;
	}

	public Ruutu annaAfrTahti() {
		return this.aTahti;
	}

/**Piirretaan karttapaneelin karttakuva.
 * 
 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(icon, 0, 0, null);

	}

	/**XML-parsean kaa kaytossa oleva lista kaikista peliruuduista
	 * 
	 */
	public void lisaaPeliruutuihin(Ruutu ruutu) {
		this.peliruudut.add(ruutu);
	}


	public ArrayList<Ruutu> annaPelimerkilliset() {
		return this.pelimerkilliset;
	}

	@Override

	public void mouseClicked(MouseEvent e) {
		System.out.println(e.getX());
		System.out.println(e.getY());
	}

	@Override
	public void mouseEntered(MouseEvent e) { }
	@Override
	public void mouseExited(MouseEvent e) { }
	@Override
	public void mousePressed(MouseEvent e) { }
	@Override
	public void mouseReleased(MouseEvent e) { }

}