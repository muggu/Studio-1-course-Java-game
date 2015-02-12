package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import javax.swing.JOptionPane;


/**Hallitsee pelaajan siirtymista: voiko ruutuun siirtya --> ruudun naapurit
 * mika ruutu on talla hetkella klikattuna. Myos hallitsee Bali-kaupungin 
 * tarkkailun: se joka ehtii ensimmaisena Balille saa ylimaaraista rahaa.
 * 
 * @author Eeva
 *
 */
public class Pelilauta {

	private Ruutu klikattuRuutu;
	private Map<Ruutu, Integer> kayntitaso; //kuinka kaukana klikattu ruutu on
	private Queue<Ruutu> jono;	//BFS-jono
	private Set<Ruutu> tarkistetut, nopanPaassaOlevatRuudut, avatutRuudut;
	private Pelimoottori moottori;
	private boolean onKayty; //onko tietysssa ruudussa jo kayty


	public Pelilauta(Pelimoottori moottori) {
		this.moottori = moottori;
		this.klikattuRuutu = null;
		this.kayntitaso = new HashMap<Ruutu, Integer>();
		this.jono = new LinkedList<Ruutu>();
		this.tarkistetut = new HashSet<Ruutu>();
		this.nopanPaassaOlevatRuudut = new HashSet<Ruutu>();
		this.avatutRuudut = new HashSet<Ruutu>();
		this.onKayty = false;
	}

/**Kerrotaan Pelilautaluokalle, mita ruutua pelaaja on klikannut.
 * @param ruutu jota pelaaja klikannut
 */
	public void asetaKlikattuRuutu(Ruutu ruutu) {
		this.klikattuRuutu = ruutu;
		this.moottori.annaVuorossa().asetaKlikattuRuutu(ruutu);
	}

	/**
	 * 
	 * @return palauttaa klikatun ruudun
	 */
	public Ruutu annaKlikattuRuutu() {
		return this.klikattuRuutu;
	}
	/**
	 * Kertoo, onko Balilla jo vierailtu
	 * @return true, jos balilla on kayty; false jos bali koskematon
	 */
	public boolean nopeinOnKayty() {
		return this.onKayty;
	}
	/**Kun joku pelaajista on kaynyt Balilla, ruutu asetetaan vierailluksi.
	 */
	public void asetaNopeinKaydyksi() {
		this.onKayty = true;
	}
/** Lisataan avattujen ruutujen listaan parametrina oleva ruutu r
 * 
 * @param avattu ruutu r
 */
	public void lisaaAvattuihinRuutuihin(Ruutu r) {
		this.avatutRuudut.add(r);
	}
/**Annetaan avattujen ruutujen joukku
 * 
 * @return joukko avattuja ruutuja
 */
	public Set<Ruutu> annaAvatutRuudut() {
		return this.avatutRuudut;
	}
	
	
/**Tarkastellaan BFS-algoritmin avulla klikatun ruudun etaisyytta pelaajan
 * nykyisesta sijainnista (mista). Ruutujen tarkistus mista-ruudusta ja 
 * tarkistettavana olevan ruudun naapurit tarkistetaan, ja jokaiselle 
 * ruudulle asetetaan kayntitaso, joka kertoo ruudun etaisyyden 
 * lahtoruudusta. Katso tarkemmin Loppuraportti.
 */
	public void BFS() {
		Ruutu mista = this.moottori.annaVuorossa().annaSijainti();
		this.jono.clear();
		this.kayntitaso.clear();
		this.tarkistetut.clear();
		this.jono.add(mista);
		this.nopanPaassaOlevatRuudut.clear();
		this.kayntitaso.put(mista, 0);
		this.tarkistetut.add(mista);
		while (!jono.isEmpty()){
			Ruutu t = this.jono.poll();
			if (this.kayntitaso.get(t) <= this.moottori.annaVuorossa().annaSilmaluku()) {
				for (Polku p : t.annaPolut()) {
					Ruutu r = p.annaToinenRuutu(t); //r on polun toinen paa
					if (!this.tarkistetut.contains(r)) {
						this.tarkistetut.add(r);
						this.jono.add(r);
						this.kayntitaso.put(r, this.kayntitaso.get(t)+1);
					}
				}
			}
		}
	}


	/**Voiko ruutuun siirtya. 
	 *Jos klikattu ruutu pelimerkillinen, siirto voi olla <= silmaluku.
	 *Muuten klikatun ruudun etaisyyden pitaa olla tasan nopan silmaluku.
	 * @return true, jos voi siirtya; return false jos siirto ei kelpaa.
	 */
	public boolean voikoSiirtya() {
		//jos pelimerkillinen, siirto <= nopan silmaluku
		if (this.kayntitaso.get(this.klikattuRuutu) != null) { 
			if (this.klikattuRuutu.annaKarttapaneeli().annaPelimerkilliset().contains(this.klikattuRuutu) 
					&& this.kayntitaso.get(this.klikattuRuutu) <= 
					this.moottori.annaVuorossa().annaSilmaluku()){
				//&& !this.avatutRuudut.contains(this.klikattuRuutu)
				System.out.println("true!");
				return true;

			}
			//muuten tasaluku
			if (this.kayntitaso.get(this.klikattuRuutu) == this.moottori.annaVuorossa().annaSilmaluku()) {
				return true;
			}
		}
		else {
			System.out.println("false!");

		}
		return false;
	}

}
