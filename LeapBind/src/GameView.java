import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class HandsTrackLabel extends JLabel {
	private int x_1;
	private int y_1;
	private int x_2;
	private int y_2;

	public HandsTrackLabel() {
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

public class GameView extends JFrame implements PropertyChangeListener {
	
	private HandsTrackLabel hands = new HandsTrackLabel();
	
	public GameView() {
		this.setTitle("Image View");
		this.setSize(Constance.Windows_width, Constance.Windows_height);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		SimpleModel.getInstance().addPropertyChangeListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//mouse = new MousePane();
		hands.setLayout(null);
		this.add(hands,BorderLayout.CENTER);
		hands.setSize(new Dimension(Constance.Windows_width, Constance.Windows_height));
		this.setVisible(true);
	}
	



	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("hand1")) {
			System.out.println("x = " + ((Cordinates) evt.getNewValue()).x
					+ " y = " + ((Cordinates) evt.getNewValue()).y);
			hands.setHand1(((Cordinates) evt.getNewValue()).x,
					((Cordinates) evt.getNewValue()).y);
		} else if (evt.getPropertyName().equals("hand2")) {
			System.out.println("x = " + ((Cordinates) evt.getNewValue()).x
					+ " y = " + ((Cordinates) evt.getNewValue()).y);
			hands.setHand2(((Cordinates) evt.getNewValue()).x,
					((Cordinates) evt.getNewValue()).y);
		}

	}

}
