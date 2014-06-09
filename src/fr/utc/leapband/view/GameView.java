package fr.utc.leapband.view;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JSplitPane;

import fr.utc.leapband.sma.user.UserAgent;
import fr.utc.leapband.utilities.Constance;
import fr.utc.leapband.utilities.Cordinates;
import fr.utc.leapband.utilities.CustomImgPanel;
import fr.utc.leapband.utilities.ScaleLabel;

@SuppressWarnings("serial")
public class GameView extends JAgentFrame {
	
	private HandsTrackPane hands;
	private JSplitPane split_pane;
	private ControlPane control_pane;

	//private Player player = new Player();
	public ScaleLabel playDrumLeft;
	public ScaleLabel playDrumRight;
	public int instrumentX1;
	public int instrumentY1;
	public int instrumentX2;
	public int instrumentY2;
	private CustomImgPanel imagePanel;
	private boolean can_fire_change = true;

	


	public Piano pianoPane=new Piano();
	public Guitar guitarPane=new Guitar(myAgent);
	

//	public static double x=Constance.Windows_width;
//	public static double y=Constance.Windows_height*0.3;

	
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

		imagePanel=new CustomImgPanel(Constance.Windows_width, Constance.Windows_height);
		imagePanel.setLayout(null);
		this.add(imagePanel);
		
		
		
		split_pane= new JSplitPane(0,control_pane,hands);
		split_pane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		split_pane.setDividerLocation(Constance.Control_Pane_height);
		split_pane.setSize(Constance.Windows_width, Constance.Windows_height);
		split_pane.setOpaque(false);
		imagePanel.add(split_pane);
		
		imagePanel.add(playDrumLeft);
		imagePanel.add(playDrumRight);
		
		//setGuitar();
		imagePanel.add(pianoPane);
		imagePanel.add(guitarPane);
		
		
		
	}
	
	public void disableChange() {
		can_fire_change = false;
	}
	
	public void enableChange() {
		can_fire_change = true;
	}
	
	public boolean isCan_fire_change() {
		return can_fire_change;
	}

	public void changeVolume(String up_or_down) {
    	double current = myAgent.getCurrent_rotation();
		if ( up_or_down == Constance.Volume_Up) {
			myAgent.setCurrent_rotation(current + 30);
			if (myAgent.getCurrent_rotation() > 180) {
				myAgent.setCurrent_rotation(180);
			}
	    } else if (up_or_down == Constance.Volume_Down) {
	    	myAgent.setCurrent_rotation(current - 30);
	    	   if (myAgent.getCurrent_rotation() < -180) {
					myAgent.setCurrent_rotation(-180);
				}
	    }
		control_pane.getVolume().setIcon(new RotatedIcon(new ImageIcon("images/volume.png"), myAgent.getCurrent_rotation()));
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);
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
			} else if (evt.getPropertyName().equals("Circle")) {
				if ((String) evt.getNewValue() == Constance.Volume_Up) {
					System.out.println("volume up");
					changeVolume(Constance.Volume_Up);
				} else if  ((String) evt.getNewValue() == Constance.Volume_Down) {
					System.out.println("volume down");
					changeVolume(Constance.Volume_Down);
				}
			}
		}

	}
	
	public ControlPane getControl_pane() {
		return control_pane;
	}



	public void setControl_pane(ControlPane control_pane) {
		this.control_pane = control_pane;
	}
	
	public CustomImgPanel getImagePanel() {
		return imagePanel;
	}

	public void setImagePanel(CustomImgPanel imagePanel) {
		this.imagePanel = imagePanel;
	}
}
