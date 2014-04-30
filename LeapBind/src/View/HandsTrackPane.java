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

	public HandsTrackPane() {
		this.setSize(Constance.Windows_width, Constance.Windows_height);
		x_1 = 50;
		y_1 = 50;
		x_2 = 50;
		y_2 = 50;
		this.hideCursor();
	}

	public void setHand1(float x, float y) {
		this.x_1 = (int) x;
		this.y_1 = (int) y;
		repaint();
	}
	
	public void setHand2(float x, float y) {
		this.x_2 = (int) x;
		this.y_2 = (int) y;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.yellow);
		g.fillOval(x_1, y_1, 70, 40);
		g.setColor(Color.blue);
		g.fillOval(x_2, y_2, 70, 40);
	}
	
	public void hideCursor() {
		Image image = Toolkit.getDefaultToolkit().createImage(
				new MemoryImageSource(0, 0, new int[0], 0, 0));
		this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(image,
				new Point(0, 0), null));
	}
}