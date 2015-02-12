package model;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import ohjain.PeliruutujenHiiri;

import kartta.Karttapaneeli;
import kartta.Ruutunappi;


/**Ruutu-luokka on solmukohtien tietomalli. (View on luokassa Ruutunappi)
 * Ruutu on kartan kohta, johon pelaaja voi siirtya. Ruutu voi olla 
 * avattava, jolloin siina on pelimerkki. Ruudut yhdistyvat toisiinsa 
 * poluilla, ja Ruuduilla on omat naapurinsa.
 * 
 * 
 *  
 * @author Eeva
 *
 */

public class Ruutu {

	private int sijaintiX;
	private int sijaintiY;
	private boolean onPelimerkki, onTimantti, onRosvo, onTyhja, onATahti, onHevosenkenka;
	private Karttapaneeli karttapaneeli;
	private ArrayList<Polku> polut;
	private Ruutunappi nappi;
	

	/**Luodaan peliruutu (=polussa oleva solmukohta), jossa voi olla pelimerkki.
	 * Peliruudulla on tietty sijainti (x,y) ja se sijaitsee karttapaneelissa.
	 * 
	 * @param sijaintiX
	 * @param sijaintiY
	 * @param karttapaneeli
	 */
	public Ruutu(int sijaintiX, int sijaintiY, Karttapaneeli karttapaneeli){
		this.sijaintiX = sijaintiX;
		this.sijaintiY = sijaintiY;
		this.karttapaneeli = karttapaneeli;
		//this.ruudunNaapurit = new ArrayList<Ruutu>();
		this.polut = new ArrayList<Polku>();
		this.nappi = new Ruutunappi(this);

	}

	public Ruutunappi annaNappi() {
		return this.nappi;
	}
	
	public int annaSijaintiX() {
		return this.sijaintiX;
	}

	public int annaSijaintiY() {
		return this.sijaintiY;
	}
	
	public void lisaaPolku(Polku p) {
		this.polut.add(p);
	}
	
	public ArrayList<Polku> annaPolut() {
		return this.polut;
	}

	public Karttapaneeli annaKarttapaneeli() {
		return this.karttapaneeli;
	}
	
	public PeliruutujenHiiri annaHiiri() {
		return this.nappi.annaHiiri();
	}
	
	public boolean onPelimerkki() {
		return this.onPelimerkki;
	}
	
	public void asetaPelimerkiksi() {
		this.onPelimerkki = true;
	}
	public boolean onTimantti() {
		return this.onTimantti;
	}
	
	public void asetaTimantiksi() {
		this.onTimantti = true;
	}
	public boolean onRosvo() {
		return this.onRosvo;
	}
	
	public void asetaRosvoksi() {
		this.onRosvo = true;
	}
	
	public void asetaTyhjaksi() {
		this.onTyhja = true;
	}
	public boolean onTyhja() {
		return this.onTyhja;
	}
	
	public boolean onAfrTahti() {
		return this.onATahti;
	}
	public boolean onHevosenkenka() {
		return this.onHevosenkenka;
	}
	
	public void asetaAfrTahdeksi() {
		this.onATahti = true;
	}

	public void asetaHevosenkengaksi() {
		this.onHevosenkenka = true;
	}
	
			
	public String toString() {
		return "Ruutu " + this.annaSijaintiX() + ", " + this.annaSijaintiY();
	}


}
