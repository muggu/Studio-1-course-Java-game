package pelimerkit;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import model.Ruutu;



public class AfrikanTahti extends Pelimerkki {
	
private Ruutu ruutu;
	
	public AfrikanTahti(Ruutu l) {
		super(l);
		this.ruutu = l;
	
	}
	
	@Override
	/**Naytetaan afrikan tahti kun pelimerkki avataan.
	 * 
	 */
	public void pelimerkinAvaus() {
		ImageIcon icon = new ImageIcon("kultainenpapu.png");
		setIcon(icon);
		Aaniefektit.AFRTAHTI.play();
		JOptionPane.showMessageDialog(this.ruutu.annaKarttapaneeli(),
			    "Loysit Javan Kultaisen pavun! \n Nyt kipin kapin kotiin!",
			    "Wow!",
			    JOptionPane.INFORMATION_MESSAGE,
			    icon);
		
	}

	@Override
	public String toString() {
		return "Afrikan Tahti ";
	}

	
	

}
