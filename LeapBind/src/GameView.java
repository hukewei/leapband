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
import javax.swing.JSplitPane;



public class GameView extends JFrame implements PropertyChangeListener {
	
	
	
	private HandsTrackLabel hands;
	private JSplitPane split_pane;
	private ControlPane control_pane;
	
	public GameView() {
		this.setTitle("Image View");
		this.setSize(Constance.Windows_width, Constance.Windows_height);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		SimpleModel.getInstance().addPropertyChangeListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		hands = new HandsTrackLabel();
		//hands.setLayout(null);
		//this.add(hands,BorderLayout.CENTER);
		hands.setSize(new Dimension(Constance.Windows_width, Constance.Windows_height));
		control_pane = new ControlPane();
		control_pane.setLayout(null);
		JPanel hand_pane = new JPanel();
		hand_pane.add(hands);
		split_pane= new JSplitPane(0,control_pane,hands);
		split_pane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		split_pane.setDividerLocation(250);
		split_pane.setSize(Constance.Windows_width, Constance.Windows_height);
		this.add(split_pane);
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
