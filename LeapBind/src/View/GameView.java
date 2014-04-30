package View;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.jfugue.Pattern;
import org.jfugue.Player;
import org.jfugue.Rhythm;

import Model.Constance;
import Model.Cordinates;
import Model.SimpleModel;



public class GameView extends JFrame implements PropertyChangeListener {
	
	
	
	private HandsTrackLabel hands;
	private JSplitPane split_pane;
	private ControlPane control_pane;
	private Player player = new Player();
	
	public GameView() {
		this.setTitle("Game View");
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
		} else if (evt.getPropertyName().equals("swipe")) {
			if ((String)evt.getNewValue() == "DOWN") {
				Rhythm rhythm = new Rhythm();
				//Bang out your drum beat  
				rhythm.setLayer(1, "O..oO...O..oOO.."); 
				rhythm.addSubstitution('o', "Rs [BASS_DRUM]s"); 
				Pattern pattern = rhythm.getPattern(); 
				pattern.repeat(1); 
				player.play(pattern);
			}
		}

	}

}
