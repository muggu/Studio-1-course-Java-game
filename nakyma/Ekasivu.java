package nakyma;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 * 
 * @author Eeva
 *
 */

public class Ekasivu extends JPanel implements MouseListener{

	private JLabel ohje, uusiPeli;
	private Peli sivu;
	private JLabel tervehdys;
	private JPanel testi;
	private JPopupMenu popUp;
	private Image icon;
	private JLabel lopeta;
	private Font font;

	public Ekasivu(Peli sivu) {
		this.font = new Font("MV Boli", Font.PLAIN, 25);
		this.setLayout(null);
		this.sivu = sivu;
		this.icon = new ImageIcon("Valikkotausta.jpg").getImage();
		this.ohje = new JLabel();
		this.uusiPeli = new JLabel();
		this.ohje.setText("Peliohjeet");
		this.ohje.setBounds(600, 360, 200, 30);
		this.uusiPeli.setText("Aloita uusi peli");
		this.uusiPeli.setBounds(330, 280, 200, 30);
		this.ohje.setFont(this.font);
		this.uusiPeli.setFont(this.font);
		this.add(this.ohje);
		this.add(this.uusiPeli);
		this.lopeta = new JLabel();
		this.lopeta.setText("Sulje peli");
		this.lopeta.setBounds(900, 280, 200, 30);
		this.lopeta.setFont(this.font);
		this.add(this.lopeta);

		this.ohje.addMouseListener(this);
		this.uusiPeli.addMouseListener(this);
		this.lopeta.addMouseListener(this);
		this.tervehdys = new JLabel("Javan Kultainen Kahvipapu");
		this.tervehdys.setFont(new Font("Mistral", Font.BOLD, 70));
		this.tervehdys.setBounds(330, 70, 1000, 100);
		this.add(this.tervehdys);
		this.testi = new JPanel();



	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(this.icon, 0, 0, null);

	}


	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println(e.getX() + " " + e.getY());
		//ohjeisiin
		if (e.getButton() == MouseEvent.BUTTON1 && e.getSource().equals(this.ohje)) {
			CardLayout cl = (CardLayout)(this.sivu.annaCard().getLayout());
			cl.show(this.sivu.annaCard(), this.sivu.annaOhjesivu().annaString());
			System.out.println("nappi2");
		}
		if (e.getButton() == MouseEvent.BUTTON1 && e.getSource().equals(this.uusiPeli)) {
			this.sivu.uudestaanPeli();
		}
		if (e.getButton() == MouseEvent.BUTTON1 && e.getSource().equals(this.lopeta)) {
			System.exit(0);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
