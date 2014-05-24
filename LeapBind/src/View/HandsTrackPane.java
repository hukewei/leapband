package View;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Utilities.Constance;


class HandsTrackPane extends JPanel {
	private int x_1;
	private int y_1;
	private int x_2;
	private int y_2;
	private float size_1;
	private float size_2;
	private JLabel play;

	public HandsTrackPane() {
		this.setSize(Constance.Windows_width, Constance.Windows_height);
		x_1 = 50;
		y_1 = 50;
		x_2 = 50;
		y_2 = 50;
		size_1 = 2;
		size_2 = 2;
		this.setLayout(null);
		//play = new JLabel(new ImageIcon("src/images/play1.png"));
		//play.setBounds(0, 270, Constance.Windows_width, 300);
		//this.add(play);
		this.hideCursor();
		this.setOpaque(false);
	}
	
	public float getHandSize(float f) {
		float size = 0;
//		if (f < -150) {
//			size = 1;
//		} else if (f < -50) {
//			size = 2;
//		} else if ( f < 0 ) {
//			size = 3;
//		} else if ( f < 50) {
//			size = 4;
//		} else if (f < 150) {
//			size = 5;
//		} else {
//			size = 6;
//		}
		size = (f+150)/150;
		return size;
	}

	public void setHand1(float x, float y, float z) {
		this.x_1 = (int) x;
		this.y_1 = (int) y;
		this.size_1 = getHandSize(z);
		repaint();
	}
	
	public void setHand2(float x, float y, float z) {
		this.x_2 = (int) x;
		this.y_2 = (int) y;
		this.size_2 = getHandSize(z);
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.yellow);
		g.fillOval(x_1, y_1, (int)(Constance.Hand_width * size_1), (int)(Constance.Hand_height * size_1));
		g.setColor(Color.blue);
		g.fillOval(x_2, y_2, (int)(Constance.Hand_width * size_2), (int)(Constance.Hand_height * size_2));
	}
	
	public void hideCursor() {
		Image image = Toolkit.getDefaultToolkit().createImage(
				new MemoryImageSource(0, 0, new int[0], 0, 0));
		this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(image,
				new Point(0, 0), null));
	}
}