package nakyma;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
/**Ohjesivu-luokka, joka on yksi CardLayoutin paneeleista. Ohjesivusta pelaaja
 * voi lukea pelisaannot ja palata takaisin keskeytyneeseen peliin tai 
 * valikoon. Ohjesivu-luokkaan kuuluu hiirikuuntelija, silla nappeja taytyy
 * tarkkailla.
 * 
 * @author Eeva
 *
 */

public class Ohjesivu extends JPanel implements MouseListener{

	final static String OHJEPANEELI = "Ohjeet";
	private JLabel nappi2; //otsikko
	private JButton napppi3, pelinappi; //napit
	private Peli sivu; //CardLayout
	private JTextArea tekstikentta; //kentta ohjeille
	private Font font, font2;
	private Image icon;

	/**Ohjesivun konstruktori, jossa luodaan tarvittavat napit 
	 * (asettelija null) ja kutsutaan metodia, joka luo ohjekentan.
	 * 
	 * @param sivu, ottaa parametriksi Aloitussivun, jonka CardLayouttiin
	 * ohjesivu kuuluu.
	 */
	public Ohjesivu(Peli sivu) {
		this.setLayout(null);
		this.font = new Font("MV Boli", Font.PLAIN, 15);
		this.font2 = new Font("MV Boli", Font.BOLD, 15);
		this.sivu = sivu;
		this.nappi2 = new JLabel();
		this.nappi2.setText("Kultaisen Kahvipavun Saannot");
		this.nappi2.setFont(new Font("Mistral", Font.PLAIN, 40));
		this.nappi2.setBounds(300, 60, 400, 35);
		this.add(this.nappi2);
		this.napppi3 = new JButton("Takaisin valikkoon");
		this.napppi3.setFont(this.font2);
		this.add(napppi3);
		this.napppi3.addMouseListener(this);
		this.napppi3.setBounds(50, 20, 200, 40);
		this.pelinappi = new JButton("Takaisin peliin");
		this.pelinappi.setFont(this.font2);
		this.pelinappi.setBounds(50, 80, 200, 40);
		this.add(this.pelinappi);
		this.pelinappi.addMouseListener(this);
		this.naytaOhjeet();
		this.icon = new ImageIcon("Valikkotausta.jpg").getImage();
		this.pelinappi.setBackground(new Color(185, 155, 131));
		this.napppi3.setBackground(new Color(185, 155, 131));
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(icon, 0, 0, null);

	}


	/**alauttaa Ohjesivun merkkijonon, jonka avulla CardLayout tunnistaa
	 * vaihdettavan nakyman.
	 * 
	 * @return ohjesivun merkkijono: "OHJEPANEELI"
	 */
	public String annaString() {
		return Ohjesivu.OHJEPANEELI;
	}

	/**Tekee JTextArean, jonka sisalla lukee pelinohjeet.
	 * 
	 */
	public void naytaOhjeet() {
		this.tekstikentta = new JTextArea("\nTERVETULOA \nseikkailemaan " +
				"Java-saarelle ja etsimaan Kultaista Kahvipapua! Tavoitteena on " +
				"ensimmaisena loytaa tuo Javan historiallinen kultapatsas ja palauttaa se " +
				"lahtosatamaan, Jakartaan. Peli ei ole kuitenkaan menetetty, vaikket loytaisi " +
				"kultaa; loytaessasi Ankkurin Kultaisen Kahvipavun loytymisen jalkeen " +
				"voit voittaa voittaa pelin, jos ehdit ensimmaisena maaliin. \n \n" +
				"LIIKKUMINEN\nPelaaja siirty kaupungista toiseen nopan antaman " +
				"silmaluvun etaisyyden verran klikkaamalla kartan ruutua. Jos " +
				"pelaaja haluaa siirtya avattavaan ruutuu, jotka ovat valkoisia ja tavallisia ruutuja suurempia, " +
				"siirron etaisyys voi myos olla alle silmaluvun verran. Ruudun saa auki " +
				"100 $ vastaan, tai saamalla seuraavalla vuorolla nopasta 4 tai suurempi " +
				"luku ja klikkaamalla ruutua uudestaan. \n \n" +
				"AVATTAVAT RUUDUT\n Kartassa on avattavia valkoisia ruutuja, joista voi " +
				"paljastua tavallisia kahvipapuja, joista saa rahaa uusien ruutujen " +
				"avaamista varten. Tyhjasta ruudusta ei hyody mitenkaan, kuten ei " +
				"hevosenkengistakaan ennen Kultaisen Pavun loytymista. Varo " +
				"kuitenkin merirosvoja, jotka anastavat omaisuutesi! \n\n" +
				"ERIKOISRUUDUT\n Kartasta loytyy myos erikoisempia kaupunkeja. Balille" +
				" ensimmaisena saapuva matkamies saa 500 $ tervetuliaslahjana. " +
				"Toisaalta Joulusaarilla rosvon kohtaava ja rauhaa rikkova matkamies joutuu kahden vuoron ajaksi orjaksi. " +
				"Timanteista kuuluisassa Murtapurassa taas onni voi potkaista pelaajaa: " +
				"ruudusta paljastuvasta kahvipavusta saa kaksinkertaisen hinnan!. \n \n" +
				"ONNEA MATKAAN! :)");
		this.tekstikentta.setFont(new Font("MV Boli", Font.PLAIN, 15));
		this.tekstikentta.setLineWrap(true);
		this.tekstikentta.setWrapStyleWord(true);
		this.add(this.tekstikentta);
		this.tekstikentta.setBounds(new Rectangle(250, 100, 850, 650));
	}

	/**Hiirikuuntelija reagoi nappi2:n ja nappi3:n klikkauksiin vaihtamalla
	 * CardLayoutin nakymaa joko pelikanvaasiksi tai valikoksi.
	 * 
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		//Valikkoon:
		if (e.getButton() == MouseEvent.BUTTON1 && e.getSource().equals(this.napppi3)) {
			CardLayout cl = (CardLayout)(this.sivu.annaCard().getLayout());
			cl.show(this.sivu.annaCard(), this.sivu.annaString());
		}
		//Peliin:
		if (e.getButton() == MouseEvent.BUTTON1 && e.getSource().equals(this.pelinappi)) {
			//jos pelia ei ole kaynnissa
			if (!this.sivu.annaMoottori().onPeliKaynnissa() || 
					this.sivu.annaMoottori().annaVuorossa().annaNimi() == null) {
				JOptionPane.showMessageDialog(this,
						"Pelia ei ole kesken.\nAloita uusi peli valikosta.",
						"Javan tahti", JOptionPane.INFORMATION_MESSAGE, null);	
			}
			else {
				CardLayout cl = (CardLayout)(this.sivu.annaCard().getLayout());
				cl.show(this.sivu.annaCard(), this.sivu.annaKanvaasi().annaString());
			}
		}
	}



	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}



}
