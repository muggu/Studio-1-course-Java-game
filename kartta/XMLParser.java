package kartta;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import model.Polku;
import model.Ruutu;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**Kaydaan lapi xml-tiedostona olevat peliruudut ja niiden polut.
 * Peliruudut ja niiden id:t tallennetaan Mappiin polkujen luomista varten.
 * 
 * @author Eeva
 *
 */

public class XMLParser extends DefaultHandler {

	private Map<String, Ruutu> map; //id avain, 
	private String temp;
	private Karttapaneeli paneeli;
	private Ruutu ruutu, pelimerkillinen, lahtoruutu; //valiaikainen peliruutu
	private String id; //ruudun id (myos valiaikainen)
	private Polku polku;
	private ArrayList<Ruutu> ruudut, pelimerkilliset; 
	private ArrayList<Polku> polut;
	private Ruutu nopeinRuutu, orjaRuutu, tuplatRuutu;

	public XMLParser(Karttapaneeli paneeli) {
		this.map = new HashMap<String, Ruutu>();
		this.ruutu = null;
		this.polku = null;
		this.id = null;
		this.ruudut = new ArrayList<Ruutu>();
		this.pelimerkilliset = new ArrayList<Ruutu>();
		this.polut = new ArrayList<Polku>();
		this.paneeli = paneeli;
		this.pelimerkillinen = null;
	}

	
	/**Kun parseri kohtaa uuden elementin, joka tunnistetaan qNamen perusteella, 
	 * metodi kutsutaan. Jos qName on "entity", muodostetaan ruutu, jos se on 
	 * "link", tehdaan polku. Muodostetaan myos erikoisruudut: lahtoruutu,
	 * orjaruutu, tuplatruutu ja nopeinruutu.
	 */
	public void startElement(String URI, String localName,
			String qName, Attributes atts) throws SAXException  {
		//jos entity, kyseessa ruutu
		if (qName.equalsIgnoreCase("ivml:entity")) {
			this.ruutu = null; //nollataan taas attribuutti.
			this.id = atts.getValue("","id"); //n=peliruudun id, tarvitaan polkuja muodostettaessa
			String type = atts.getValue(1); //tyyppi:entity
			String x = atts.getValue(3);
			String y = atts.getValue(4);
			String vari = atts.getValue(6);
			if (type.equalsIgnoreCase("entity")) {
				//muodostetaan uusi valiaikainen peliruutu
				this.ruutu = new Ruutu(Integer.parseInt(x), Integer.parseInt(y), this.paneeli);
				if (vari.equalsIgnoreCase("#F0F0F0")) { //tavallinen ruutu
					this.ruutu.annaNappi().setOpaque(true);
				}
				else if (vari.equalsIgnoreCase("#000000")) { //pelimerkillinen ruutu
					this.pelimerkillinen = this.ruutu;
					this.ruutu.annaNappi().setBackground(Color.black);
				}
				else if(vari.equalsIgnoreCase("#329664")) {
					this.lahtoruutu = this.ruutu;
				}
				else if(vari.equalsIgnoreCase("#963200")) { //tuplaruutu, martapura
					this.tuplatRuutu = this.ruutu;
				}
				else if (vari.equals("#0000FF")) { //orjaruutu, joulusaari
					this.orjaRuutu = this.ruutu;
				}
				else if (vari.equals("#FF00FF")) { //nopeinruutu, bali
					this.nopeinRuutu = this.ruutu;
				}
				else {
					return;
				}
				
			}
		}
		//jos link, kyseessa polku; tiedostoon tallennettu polut vain yhteen suuntaan
		else if (qName.equalsIgnoreCase("ivml:link")) {
			String mista = atts.getValue(1); //ruutu from
			String mihin = atts.getValue(2); //ruutu to 
			String type = atts.getValue(4);
			if (type.equalsIgnoreCase("link")) {
				Ruutu p1 = this.map.get(mista);
				Ruutu p2 = this.map.get(mihin);
				this.polku = new Polku(p1, p2);
			}
		}
	}

/**Metodia kutsutaan kun lukija on paassyt sulkevaan elementtiin. Jos qName on
 * "entity", luodaan varsinainen ruutu. Jos qname link, luodaan varsinainen 
 * polku.
 */
	public void endElement(String uri, String localName,
			String qName) throws SAXException {
		if(qName.equalsIgnoreCase("ivml:entity")) {
			//lisataan valiaikainen peliruutu peliruutujen listaan
			this.ruudut.add(this.ruutu);
			if (this.ruutu.equals(this.pelimerkillinen)) {
				this.pelimerkilliset.add(this.ruutu);
			}
			if (this.ruutu.equals(this.lahtoruutu)) {
			//	this.paneeli.lisaaPelimerkillisiin(this.lahtoruutu);
				this.paneeli.asetaLahtoruutu(this.lahtoruutu);
				System.out.println("lahtoruutu " + this.ruutu);
			//	this.paneeli.asetaOrjaruutu(this.lahtoruutu);
				
			}
			if (this.ruutu.equals(this.tuplatRuutu)) {
				this.paneeli.asetaTuplatruutu(this.tuplatRuutu);
				this.pelimerkilliset.add(this.ruutu);
			}
			if (this.ruutu.equals(this.nopeinRuutu)) {
				this.paneeli.asetaNopeinruutu(this.nopeinRuutu);
				this.pelimerkilliset.add(this.ruutu);
			}
			if (this.ruutu.equals(this.orjaRuutu)) {
				this.paneeli.asetaOrjaruutu(this.orjaRuutu);
				this.pelimerkilliset.add(this.ruutu);
				System.out.println("orjaruutu " + this.ruutu);
			}
			//lisataan peliruutu ja sen id mappiin polkujen muodostamista varten.
			this.map.put(this.id, this.ruutu);
		}
		else if (qName.equalsIgnoreCase("ivml:link")) {
			this.polut.add(this.polku);
			//lisataan valiaiken polku polkujen listaan.
		}
	}

	
	/**Muodostetaan ruudut ja pelimerkilliset, samoin lahtoruutu.
	 * 
	 */
	public void lueListat() {
		for (Ruutu r : this.ruudut) {
			r.annaNappi().setOpaque(false);
			this.paneeli.add(r.annaNappi());
			if (this.paneeli.annaLahtoruutu() == r) {
				r.annaNappi().setBounds(r.annaSijaintiX(), r.annaSijaintiY(), 
						100, 100);
			}
			r.annaNappi().repaint();
		}
		for (Ruutu r : this.pelimerkilliset){
			r.annaNappi().setBounds(r.annaSijaintiX(), r.annaSijaintiY(), 50, 50);
			this.paneeli.lisaaPelimerkillisiin(r);
		}
	}

}
