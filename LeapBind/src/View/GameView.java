package View;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.jfugue.Pattern;
import org.jfugue.Player;
import org.jfugue.Rhythm;

import SMA.user.UserAgent;
import Utilities.Constance;
import Utilities.Cordinates;
import Utilities.CustomImgPanel;
import Utilities.ScaleLabel;

public class GameView extends JAgentFrame {
	
	private HandsTrackPane hands;
	private JSplitPane split_pane;
	private ControlPane control_pane;
	private Player player = new Player();
	public ScaleLabel playDrumLeft;
	public ScaleLabel playDrumRight;
	public int instrumentX1;
	public int instrumentY1;
	public int instrumentX2;
	public int instrumentY2;
	
	public GameView(UserAgent agent) {
		super(agent);
		this.setTitle("Game View");
		this.setSize(Constance.Windows_width, Constance.Windows_height);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		hands = new HandsTrackPane();
		//hands.setLayout(null);
		//this.add(hands,BorderLayout.CENTER);
		hands.setSize(new Dimension(Constance.Windows_width, Constance.Windows_height));
		control_pane = new ControlPane(agent);
		control_pane.setLayout(null);
//		JPanel hand_pane = new JPanel();
//		hand_pane.add(hands);
		playDrumLeft = new ScaleLabel();
		instrumentX1=(int)(Constance.Windows_width*0.05);
		instrumentY1= (int)(Constance.Windows_height*0.66);
		playDrumLeft.setBounds(instrumentX1,instrumentY1, Constance.Windows_width/2, 300);
		//hands.add(playDrumLeft);
		
		playDrumRight = new ScaleLabel();
		instrumentX2=(int)(Constance.Windows_width*0.47);
		instrumentY2= (int)(Constance.Windows_height*0.66);
		playDrumRight.setBounds(instrumentX2,instrumentY2, Constance.Windows_width/2, 300);
		//hands.add(playDrumRight);
		
		/*play.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				 play.scaleX = 1.0; 
				 play.scaleY = 1.0;
				 play.applyFilter();  
				 play.repaint();   
	             
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				 play.scaleX *= 1.2; 
				 play.scaleY *= 1.2;   
				 play.applyFilter();  
				 play.repaint();   
	               
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
*/		
		

		CustomImgPanel imagePanel=new CustomImgPanel(Constance.Windows_width, Constance.Windows_height, "src/images/drumBack.jpg");
		imagePanel.setLayout(null);
		this.add(imagePanel);
		
		
		
		split_pane= new JSplitPane(0,control_pane,hands);
		split_pane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		split_pane.setDividerLocation(170);
		split_pane.setSize(Constance.Windows_width, Constance.Windows_height);
		split_pane.setOpaque(false);
		imagePanel.add(split_pane);
		
		imagePanel.add(playDrumLeft);
		imagePanel.add(playDrumRight);
		
	}



	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (isVisible()) {
			if (evt.getPropertyName().equals("hand1")) {
//			System.out.println("x = " + ((Cordinates) evt.getNewValue()).x
//					+ " y = " + ((Cordinates) evt.getNewValue()).y);
				hands.setHand1(((Cordinates) evt.getNewValue()).x,
						((Cordinates) evt.getNewValue()).y,
						((Cordinates) evt.getNewValue()).z);
			} else if (evt.getPropertyName().equals("hand2")) {
	//			System.out.println("x = " + ((Cordinates) evt.getNewValue()).x
	//					+ " y = " + ((Cordinates) evt.getNewValue()).y);
				hands.setHand2(((Cordinates) evt.getNewValue()).x,
						((Cordinates) evt.getNewValue()).y,
						((Cordinates) evt.getNewValue()).z);
//			} else if (evt.getPropertyName().equals("swipe")) {
//				if ((String)evt.getNewValue() == "DOWN") {
//					Rhythm rhythm = new Rhythm();
//					//Bang out your drum beat  
//					rhythm.setLayer(1, "O..oO...O..oOO.."); 
//					rhythm.addSubstitution('o', "Rs [BASS_DRUM]s"); 
//					Pattern pattern = rhythm.getPattern(); 
//					pattern.repeat(1); 
//					player.play(pattern);
//				}
			} else if (evt.getPropertyName().equals("drum_left")) {
				System.out.println("property change for drum_left");
				playDrumLeft.scaleX = 1.3; 
				playDrumLeft.scaleY = 1.3;
				playDrumLeft.applyFilter();  
				playDrumLeft.repaint();
				 new Timer().schedule( 
					        new java.util.TimerTask() {
					            @Override
					            public void run() {
					            	playDrumLeft.scaleX = 1.0; 
					            	playDrumLeft.scaleY = 1.0;
					            	playDrumLeft.applyFilter();  
					            	playDrumLeft.repaint(); 
					            }
					        }, 
					        50 
				);
			} else if (evt.getPropertyName().equals("drum_right")) {
				System.out.println("property change for drum_right");
				playDrumRight.scaleX = 1.3; 
				playDrumRight.scaleY = 1.3;
				playDrumRight.applyFilter();  
				playDrumRight.repaint();
				 new Timer().schedule( 
					        new java.util.TimerTask() {
					            @Override
					            public void run() {
					            	playDrumRight.scaleX = 1.0; 
					            	playDrumRight.scaleY = 1.0;
					            	playDrumRight.applyFilter();  
					            	playDrumRight.repaint(); 
					            }
					        }, 
					        50 
				);
			}
		}

	}

}
