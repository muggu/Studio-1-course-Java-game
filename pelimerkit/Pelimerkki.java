package pelimerkit;

import kartta.Ruutunappi;
import model.Ruutu;

/**Pelimerkin view, abstrakti luokka
 * 
 * @author Eeva
 *
 */


public abstract class Pelimerkki extends Ruutunappi {
	
	public Pelimerkki(Ruutu r) {
		super(r);
	}
	
	/**Kun pelimerkki avataan, jotain tietty kuva tulee nakyviin, ja
	 * pelimerkki asetetaan avatuksi.
	 * 
	 */
	public abstract void pelimerkinAvaus();
	
	
	/**Mita palauttaa.
	 * 
	 */
	public abstract String toString();
}

