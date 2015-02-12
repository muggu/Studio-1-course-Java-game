package model;

import java.awt.Color;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import nakyma.Pelaajapaneeli;

import kartta.Karttapaneeli;
import kartta.Pelaajanappula;

/**Pelaajaluokassa luodaan pelin pelaajan tietorakenne, joka reagoi ohjaimen 
 * viesteihin ja kutsuu nakymaa muuttumaan. Pelaajalla on saldo, nimi, lahtoruutu,
 * sen hetkinen sijainti(ruutu) ja mahdollisesti Afrikan Tahti. Luotaessa uusi
 * pelaaja kutsutaan myos pelaajan viewta muodostumaan. 
 * Luokassa on tarkea metodi: teeSiirto(), joka kayttajan komentojen mukaan
 * tekee siirron.
 * 
 * @author Eeva
 *
 */

public class Pelaaja {

	private int saldo;
	private int silmaluku;
	private Pelimoottori moottori;
	private Karttapaneeli karttapaneeli;
	private Random rand = new Random();
	private Ruutu lahtoruutu;
	private String nimi;
	private boolean AfrikanTahti, loytanytKengan;
	private Ruutu sijainti, orja, tuplat, nopein;
	private Ruutu klikattuRuutu;
	private Pelaajanappula nappula;
	private Pelaajapaneeli paneeli;
	private boolean onVuoro, onOrja, nopeinOnVierailtu;
	private int orjaKierros;


	/**Luodaan pelaaja, jolla on nimi, lahtoruutu, ja alkusaldona 300. Samalla
	 * luodaan Pelaajanappula-luokan instanssi, pelaajan view.
	 * @param icon1 
	 * 
	 * @param nimi, jonka kayttaja antaa
	 * @param peliruutu, joka on ennalta maaratta lahtoruutu
	 * @param moottori, pelin pelimoottori (hallitsee vuorottelua)
	 */
	public Pelaaja(String nimi, Ruutu peliruutu, Pelimoottori moottori, ImageIcon icon1) {
		this.onVuoro = false;
		this.loytanytKengan = false;
		this.orjaKierros = 0;
		this.klikattuRuutu = null;
		this.moottori = moottori;
		this.nimi = nimi;
		this.silmaluku = rand.nextInt(6)+1;
		this.saldo = 300;
		this.lahtoruutu = peliruutu;
		this.sijainti = peliruutu;
		this.AfrikanTahti = false;
		this.karttapaneeli = this.lahtoruutu.annaKarttapaneeli();
		this.moottori = moottori;
		this.nappula = new Pelaajanappula(this.sijainti.annaSijaintiX(), 
				this.sijainti.annaSijaintiY(), this.karttapaneeli, this, icon1);
		this.nappula.repaint();
		this.orja = this.karttapaneeli.annaOrjaruutu();
		this.nopein = this.karttapaneeli.annaNopeinruutu();
		this.tuplat = this.karttapaneeli.annaTuplatruutu();
		this.nopeinOnVierailtu = false;
	}

	/**Pelaajan tili, jota tarvitaan pelimerkkien avaamiseen. Alussa 300.
	 * 
	 * @return pelaajan tilin saldo.
	 */
	public int annaSaldo() {
		return this.saldo;
	}

	/**Pelaajan ennalta maaratty lahtoruutu, myos maali.
	 * 
	 * @return
	 */
	public Ruutu annaLahtoruutu() {
		return this.lahtoruutu;
	}

	/**
	 * @return pelaajan nimi
	 */
	public String annaNimi() {
		return this.nimi;
	}

	/** @return pelin pelimoottori
	 */
	public Pelimoottori annaMoottori(){
		return this.moottori;
	}

	/**Kertoo kuinka monta vuoroa ollaan menetetty orjuuden takia.
	 * @return int orjavuorojen maara
	 */
	public int annaOrjaKierros() {
		return this.orjaKierros;
	}


	/**Asettaa pelaajalle uuden sijainnin.
	 * @param ruutu, joka on pelaajan (uusi) sijainti.
	 */
	public void asetaSijainti(Ruutu ruutu) {
		this.sijainti = ruutu;
	}

	/**@return palauttaa pelaajan klikkaaman ruudun.
	 */
	public Ruutu annaKlikattuRuutu() {
		return this.klikattuRuutu;
	}

	/**@return palauttaa pelaajan nykyisen sijainnin.
	 */
	public Ruutu annaSijainti() {
		return this.sijainti;
	}

	/**@return pelaajaa ilmentava nakyma, Pelaajanappula.
	 */
	public Pelaajanappula annaPelNappi() {
		return this.nappula;
	}

	/** @return palauttaa pelin karttapaneelin (missa mm. ruudut sijaitsevat)
	 */
	public Karttapaneeli annaKarttapaneeli(){
		return this.karttapaneeli;
	}


	/** @return boolean onko pelaaja loytanyt Afrikan Tahden
	 */
	public boolean loytanytTahden() {
		return this.AfrikanTahti;
	}

	/**Jos tulee rosvo pelimerkista, koko omaisuus menee.
	 */
	public void nollaaOmaisuus() {
		this.saldo = 0;
	}

	/**Pelaajalle asetetaan vuoro.
	 */
	public void asetaVuoro() {
		this.onVuoro = true;
	}

	/**Asetetaan klikatuksi ruuduksi parametrina saatu ruutu.
	 * @param ruutu
	 */
	public void asetaKlikattuRuutu(Ruutu r) {
		this.klikattuRuutu = r;
	}

	/**Pelaaja tekee siirron, johon vaikuttavat pelaajan saldo ja pelaajan
	 * klikattu ruutu (onko pelimerkillinen). Ensimmainen tapaus: pelaaja 
	 * haluaa avata pelimerkillisen ruudun, jossa jo sijaitsee (usein rahaton 
	 * pelaaja). Ruutu aukeaa, jos silmaluku >=4. Tapaus 2: Pelaaja haluaa 
	 * siirtya pelimerkilliseen ruutuun, jolloin siirron etaisyys voi olla alle
	 * silmaluvun, jolloin kysytaan, halutaanko avata ruutu. Tapaus 3: siirto 
	 * tavalliseen ruutuu, jolloin silmaluvun taytyy olla sama kuin etaisyys.
	 * Muuten siirto ei kelpaa.
	 * Tarkistetaan myos, ollaanko siirrytty Bali-ruutuun eli nopeinRuutuun.
	 * Lopuksi siirretaan vuoro seuraavalle pelaajalle.
	 */
	public void teeSiirto() {
		System.out.println("silmaluku " + this.silmaluku);
		//this.klikattuRuutu = this.moottori.annaLauta().annaKlikattuRuutu();

		//1. rahaton pelaaja haluaa avata pelimerkin:
		if (this.klikattuRuutu == this.sijainti && 
				this.klikattuRuutu.annaKarttapaneeli().annaPelimerkilliset().
				contains(this.klikattuRuutu)) {
			if (!this.moottori.annaLauta().annaAvatutRuudut().contains(this.klikattuRuutu) 
					&& this.silmaluku >= 4) {
				System.out.println("silmaluku suurempi kuin 4");
				this.annaPelNappi().siirraPelNappi(klikattuRuutu, true);
				this.avaaPelimerkki();
				this.asetaSijainti(klikattuRuutu);
				this.moottori.annaLauta().lisaaAvattuihinRuutuihin(klikattuRuutu);
			}
			else {
				this.annaPelNappi().siirraPelNappi(klikattuRuutu, false);//siirtyy kuitenkin
				this.asetaSijainti(klikattuRuutu);
			}
		}
		//2. jos klikattu on pelimerkki ja <= silmaluku:
		else if (this.karttapaneeli.annaPelimerkilliset().contains(this.klikattuRuutu) 
				&& this.moottori.annaLauta().voikoSiirtya()) {
			if (this.saldo >= 100 && 
					!this.moottori.annaLauta().annaAvatutRuudut().contains(this.klikattuRuutu)) {
				int i = this.viestiruutu(klikattuRuutu);
				if (i == 0) {
					return;
				}
				if (i == 1){
					this.annaPelNappi().siirraPelNappi(klikattuRuutu, true);
					this.avaaPelimerkki();
					this.moottori.annaLauta().lisaaAvattuihinRuutuihin(klikattuRuutu);
					this.asetaSijainti(this.klikattuRuutu);
				}
				else {
					this.annaPelNappi().siirraPelNappi(klikattuRuutu, false);
					this.asetaSijainti(this.klikattuRuutu);
				}
				this.nopeinRuutu();
			}
			else {
				this.annaPelNappi().siirraPelNappi(klikattuRuutu, false);
				this.asetaSijainti(this.klikattuRuutu);
				this.nopeinRuutu();
			}
		}
		//3. jos klikattu ei pelimerkki, etaisyyden taytyy olla == silmaluku.
		else if (!this.karttapaneeli.annaPelimerkilliset().contains(klikattuRuutu)
				&& this.moottori.annaLauta().voikoSiirtya()) {	
			this.annaPelNappi().siirraPelNappi(klikattuRuutu, false);
			this.asetaSijainti(this.klikattuRuutu);
			this.nopeinRuutu();
		} //4. siirto ei kelpaa
		else {
			this.kelpaamatonSiirto();
			return;
		} //siirretaan vuoro seuraavalle pelaajalle
		this.moottori.siirraVuoro();
	}



	/**Nayttaa viestiruudun, ettei siirto kelvannut.
	 */
	public void kelpaamatonSiirto() {
		System.out.println("Siirto ei kelvannut");
		JOptionPane.showMessageDialog(this.annaKarttapaneeli(),
				"Siirto ei kelpaa.",
				"Hups..",
				JOptionPane.INFORMATION_MESSAGE);
	}

	/** Avataan pelimerkit (merkitaan avatuiksi): jos rosvo, tili nollataan(jos 
	 * rosvo orjaruudussa, kutsutaan toista metodia); jos timantti, tilille rahaa;
	 * jos tyhja, nada; jos Afrikan Tahti, pelaajalle Afr.Tahti.
	 */
	public void avaaPelimerkki() {
		this.moottori.annaLauta().lisaaAvattuihinRuutuihin(this.klikattuRuutu);
		
		if (this.klikattuRuutu.onRosvo()) {
			this.nollaaOmaisuus();
			if (this.orja.annaSijaintiX() == this.klikattuRuutu.annaSijaintiX()) {
				this.asetaOrjaksi(true);
				this.orjaRuutu();
				//	this.annaMoottori().orjaRuutu();
				System.out.println("onko orja " + this.onOrja);
			}
		}
		else if (this.klikattuRuutu.onTimantti()) {
			Ruutu tuplat = this.karttapaneeli.annaTuplatruutu();
			if (tuplat.annaSijaintiX() == this.klikattuRuutu.annaSijaintiX()) {
				this.saldo += 2*300;
			}
			else {
				this.saldo += 300;
			}
			System.out.println("TAHTI");
		}
		else if (this.klikattuRuutu.onTyhja()) {
			System.out.println("TYHJA");
		}
		else if (this.klikattuRuutu.onHevosenkenka()) {
			System.out.println("Hevosenkenka");
			if (this.moottori.onkoTahtiLoydetty()) {
				this.loytanytKengan = true;
			}
		}
		else if (this.klikattuRuutu.onAfrTahti()) {
			this.AfrikanTahti = true;
			this.moottori.tahtiLoydetty();
			System.out.println("afrtahti");
		}
		else {
			return;
		}
	}
/**JDialog, jossa kerrotaan pelaajalle, etta han joutui orjaksi.
 */
	public void orjaRuutu() {
		JOptionPane.showMessageDialog(this.annaKarttapaneeli(),
				"Et saanut timanttia Orjaruudussa, \n joten joudut jaamaan " +
						"ruutuun orjaksi 2 heittovuoron ajaksi",
						"O-ou...",
						JOptionPane.INFORMATION_MESSAGE);			
	}
	/**JDialog, jossa kerrotaan, etta pelaaja sai timanteista tupla-arvon.
	 */
	public void tuplatRuutu() {
		JOptionPane.showMessageDialog(this.annaKarttapaneeli(),
				"Timanteista kuuluisassa Martapurassa sait \n kahvipavusta" +
						"tuplahinnan!",
						"Jahuu!",
						JOptionPane.INFORMATION_MESSAGE);			
	}

	/**Asetetaan pelaajalle arpanopasta saatu silmaluku.
	 */
	public void asetaSilmaluku(int i) {
		this.silmaluku = i;
	}

	/**@return palauttaa pelaajan saaman silmaluvun.
	 */
	public int annaSilmaluku() {
		return this.silmaluku;
	}


	/**Viestiruutu, jossa kysytaan, haluaako pelaaja avata ruudun, pelkastaan
	 * siirtya siihe tai perua siirtoyrityksen.
	 * @param ruutu, jota klikattu
	 * @return int 0, 1, 2, jonka perusteella pelaaja siirtyy ja/tai avaa ruudun.
	 */
	public int viestiruutu(Ruutu ruutu) {
		Object[] options = {"Kylla",
				"Siirra ruutuun avaamatta",
		"Peruuta"};
		int n = JOptionPane.showOptionDialog(this.lahtoruutu.annaKarttapaneeli(),
				"Haluatko avata peliruudun rahalla?",
				"Kysymys",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[2]);
		//avataan ruutu rahalla
		if (n == JOptionPane.YES_OPTION) {
			this.saldo = this.saldo - 100;
			System.out.println("kylla");
			return 1;
		}
		//pelkastaan siirrytaan ruutuun
		else if (n == JOptionPane.NO_OPTION) {
			System.out.println("ei");
			return 2;
		} // peruuta: ei tehda mitaan, uusi klikkaus pelaajalta.
		else {
			System.out.println("peruuta");
			return 0;
		}
	}
	
	

	/**Muutetaan tilin saldoa parametrin verran
	 * @param summa (int), jonka verran tilia muutetaan.
	 */
	public void muutaSaldoa(int i) {
		this.saldo += i;
	}
	/**Tarkistetaan, onko siirryttava ruutu Bali-ruutu, jossa ensimmaisena 
	 * vieraileva pelaaja saa 500 ylimaaraista. Sisaltaa viestiruudun.
	 * 
	 */
	public void nopeinRuutu() {
		if (this.klikattuRuutu.annaSijaintiX() == this.nopein.annaSijaintiX()
				&& !this.moottori.annaLauta().nopeinOnKayty()) {
			this.saldo =+ 500;
			this.moottori.annaLauta().asetaNopeinKaydyksi();
			JOptionPane.showMessageDialog(this.annaKarttapaneeli(),
					"Olit ensimmainen Balilla, \n joten saat ylimaaraista lomarahaa " +
							"tilillesi",
							"Kylla kelpaa!",
							JOptionPane.INFORMATION_MESSAGE);	
		}
	}

	/** Pelaaja kysyttaessa tulostetaan sen nimi.
	 */
	public String toString() {
		return this.annaNimi();
	}

	/**
	 * 
	 * @return true, pelaaja on orjana; false, pelaaja ei orja
	 */
	public boolean onOrja() {
		return this.onOrja;
	}

	/**Asetetaan pelaaja orjaksi.
	 * 
	 * @param b
	 */
	public void asetaOrjaksi(boolean b) {
		if (b == true) {
			this.onOrja = true;
		}
		else {
			this.onOrja = false;
		}
	}
	/**astetaan orjakierros maara: kuinka monta kierrosta ollaan oltu orjana.
	 * 
	 * @param i
	 */
	public void asetaOrjaKierros(int i) {
		this.orjaKierros = i;
	}
	/**Onko pelaaja loytanyt hevosenkengan Afrikantahden loytymisen jalkeen.
	 * 
	 * @return true, jos loytanyt
	 */
	public boolean loytanytKengan() {
		return this.loytanytKengan;
	}


}
