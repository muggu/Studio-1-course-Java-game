package model;

import java.awt.CardLayout;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import pelimerkit.Aaniefektit;

import nakyma.Peli;
import nakyma.Arpanoppa;


/**Pelimoottorissa jaetaan vuorot pelaajien kesken.
 * 
 * @author Eeva
 *
 */

public class Pelimoottori {

	private boolean pelikaynnissa;
	private Queue<Pelaaja> pelaajat;
	private int orjavuoro;
	private Pelilauta lauta;
	private Pelaaja vuoro;
	private Pelaaja voittaja;
	private boolean tahtiLoydetty;
	private Icon kultapapu;

	/**Pelimoottorin konstruktori
	 * 
	 */
	public Pelimoottori() {
		this.pelikaynnissa = true;
		this.pelaajat = new LinkedList<Pelaaja>();
		this.lauta = new Pelilauta(this);
		this.tahtiLoydetty = false;
		this.kultapapu = new ImageIcon("kultainenpapu.png");
	}

	public Pelilauta annaLauta() {
		return this.lauta;
	}
/**Lisaa pelaajan pelaajalistan.
 * 
 * @param pelaaja
 */
	public void lisaaPelaaja(Pelaaja pelaaja) {
		this.pelaajat.add(pelaaja);
	}
/**Jono vuorollisista pelaajista.
 * 
 * @return
 */
	public Queue<Pelaaja> annaPelaajat() {
		return this.pelaajat;
	}
	
	public boolean onPeliKaynnissa() {
		return this.pelikaynnissa;
	}



	/**kutsutaan aina kun teeSiirto onnistunut.
	 * Varmistetaan ensin, ettei pelia ole voitettu.
	 * arvotaan seuraavalle pelaajalle noppa, ja siirretaan vuoro.
	 */
	public void siirraVuoro() {
		if (!this.peliVoitettu()) {
			this.heitaNoppaa();
			this.vaihdaVuoroa();
			if (this.vuoro.onOrja()) {
				if (this.vuoro.annaOrjaKierros() < 2) {
					this.vuoro.asetaOrjaKierros(this.vuoro.annaOrjaKierros() + 1);
					this.orjaViestiruutu();
					this.heitaNoppaa();
					this.vaihdaVuoroa();
					System.out.println("vuoro vaihtuu, orjakierros " + this.vuoro.annaOrjaKierros());
					return;
				}
				else {
					this.vuoro.asetaOrjaksi(false);
					System.out.println("ei enaa orja");
				}
			}
		}
	}

	/**Kertoo kayttajalle, etta hanesta tuli orja.
	 */
	public void orjaViestiruutu() {
		JOptionPane.showMessageDialog(this.vuoro.annaKarttapaneeli(),
				this.vuoro + ", olet orjana ja juodut \n jattamaan vuorosi valiin",
				"Hoh...",
				JOptionPane.INFORMATION_MESSAGE);			
	}

	/**Vaihtaa vuoron siirtamalla seuraavalle jonossa olevalle vuoro ja siirtamalla
	 *  nykyinen pelaaja jonon paahan.
	 */
	public void vaihdaVuoroa() {
		this.voittaja = this.pelaajat.peek();
		this.vuoro = this.pelaajat.poll();
		this.vuoro.annaKarttapaneeli().annaIkkuna().asetaAlapaneelinteksti(this.vuoro);
		this.vuoro.asetaVuoro();
		this.pelaajat.add(this.vuoro);
	}

	/**Heitetaan noppaa. Arvotaan silmaluku, jonka Noppa nayttaa pelaajalle.
	 */
	public void heitaNoppaa() {
		Arpanoppa noppa = this.vuoro.annaKarttapaneeli().annaKanvaasi().annaNoppa();
		int luku  = noppa.annaSilmaluku();
		this.pelaajat.element().asetaSilmaluku(luku);
		noppa.muutaNoppaa(luku);
	}

	public void asetaVuoro(Pelaaja p) {
		this.vuoro = p;
	}

	public Pelaaja annaVuorossa() {
		return this.vuoro;
	}
	public boolean onkoTahtiLoydetty() {
		return this.tahtiLoydetty;
	}
	public void tahtiLoydetty() {
		this.tahtiLoydetty = true;
	}

	/**Kun Afrikan Tahden tai hevosenkengan loytaja on paassyt takaisin 
	 * lahtoruutuun, han on voittanut pelin.
	 * 
	 * @return true, jos peli voitettu
	 */
	public boolean peliVoitettu() {
		if (this.vuoro.annaSijainti() == this.vuoro.annaLahtoruutu()) {
			if (this.vuoro.loytanytKengan() || this.vuoro.loytanytTahden()) {
				this.pelikaynnissa = false;
				this.viestiruutu();
				return true;
			}
		}
		return false;
	}

	/**Kun pelaaja voittanut pelin, onnitellaan pelaajaa ja kysytaan, haluaako
	 *  han jatkaa vai lopettaa pelaamisen.
	 * 
	 */
	public void viestiruutu() {
		Peli sivu = this.vuoro.annaKarttapaneeli().annaKanvaasi().annaAloitussivu();
		Object[] options = {"Aloita uusi peli",
				"Lopeta pelaaminen", "Valikkoon"};
		Aaniefektit.VOITTO.play();
		int n = JOptionPane.showOptionDialog(this.vuoro.annaKarttapaneeli(),
				"Voitit! Onnea " + this.voittaja + "!\n Haluatko aloittaa uuden " +
						"pelin?",
						"WOW!",
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						this.kultapapu,
						options,
						options[2]);
		if (n == JOptionPane.YES_OPTION) {
			sivu.uudestaanPeli();
		}
		else if (n == JOptionPane.NO_OPTION) {
			System.exit(0);
		}
		else {
			CardLayout cl = (CardLayout)(sivu.annaCard().getLayout());
			cl.show(sivu.annaCard(), sivu.annaString());

		}
	}


}