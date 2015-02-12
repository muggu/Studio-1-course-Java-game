package model;

/**Polku Peliruutujen valilla. Polku muodostuu tasan 2 peliruudun valille.
 * Peliruuduilla on tieto poluistaan. Polun tietaminen on oleellista lyhyimman
 *  reitin selvittamisessa (BFS-metodi).
 * 
 * @author Eeva
 *
 */


public class Polku {
	private Ruutu ruutu1, ruutu2;
	
	/**Luodaan polku parametrien valille
	 * 
	 * @param p1, polun paa
	 * @param p2 polun toinen paa
	 */
	public Polku(Ruutu p1, Ruutu p2) {
		this.ruutu1 = p1;
		this.ruutu2 = p2;
		this.ruutu1.lisaaPolku(this);
		this.ruutu2.lisaaPolku(this);
	}
	
	/**Palauttaa polun paan, joka ei ole parametrina.
	 * 
	 * @param 
	 * @return null, jos ei ole toista paata (ei tallaista tilannetta)
	 */
	public Ruutu annaToinenRuutu(Ruutu p) {
		if (p == this.ruutu1) {
			return this.ruutu2;
		}
		else if (p == this.ruutu2) {
			return this.ruutu1;
		}
		else {
			return null;
		}
	}
	
	public String toString() {
		return "Polku valilla " + this.ruutu1 + " ja " + this.ruutu2;
	}
	
	

}
