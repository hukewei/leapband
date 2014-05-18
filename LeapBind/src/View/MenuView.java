package View;


import jade.gui.GuiEvent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import SMA.user.UserAgent;
import Utilities.Constance;
import Utilities.CustomImgPanel;
import Utilities.RoundedBorder;




public class MenuView extends JAgentFrame {
	//private JPanel buttonPane;
	private JButton single;
	private JButton multiple;
	JButton exit;

	public MenuView(UserAgent agent) {
		super(agent);
		this.setTitle("Menu View");
		this.setSize(Constance.Windows_width, Constance.Windows_height);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		CustomImgPanel imagePanel=new CustomImgPanel(Constance.Windows_width, Constance.Windows_height, "src/images/back.jpg");
		imagePanel.setLayout(null);
		this.add(imagePanel);
		
		JLabel leap_band = new JLabel(new ImageIcon("src/images/leapband.png"));
		
		leap_band.setBounds(500, 30, 500, 150);
		//leap_band.setFont(new Font("Serif", Font.PLAIN, 50));
		leap_band.setHorizontalAlignment(SwingConstants.CENTER);
		leap_band.setForeground(Color.MAGENTA);
		
		imagePanel.add(leap_band);
		
		JLabel gif=new JLabel(new ImageIcon("src/images/notesdorees.gif"));
		imagePanel.add(gif);
		gif.setBounds(950, 80, 500, 150);
		

		myAgent.addPropertyChangeListener(this);
		//mouse = new MousePane();
		
	
//		buttonPane = new JPanel(new BorderLayout());
//		buttonPane.setLayout(null);
//		this.add(buttonPane,BorderLayout.CENTER);
//		buttonPane.setSize(new Dimension(Constance.Windows_width, Constance.Windows_height));
		
		
		//this.add(imagePanel);
		

		//*************** button
		//mouse.setLayout(null);
		/*JLabel leap_band = new JLabel("Leap Band");*/
		single = new JButton("Single Mode");
		single.setFont(new Font("Lucida Calligraphy", Font.BOLD, 30));
		single.setBorder(new RoundedBorder(new Color(0,128,255,100)));
		single.setContentAreaFilled( false );
		single.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
				single.setBorder(new RoundedBorder(new Color(0,128,255,100)));
				selectSingleMode();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				single.setBorder(new RoundedBorder(new Color(0,128,255,150)));
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				single.setBorder(new RoundedBorder(new Color(0,128,255,100)));
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				single.setBorder(new RoundedBorder(new Color(0,128,255,50)));
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				single.setBorder(new RoundedBorder(new Color(0,128,255,150)));
				
			}
		});
		
		
		multiple = new JButton("Multiple Mode");
		multiple.setFont(new Font("Lucida Calligraphy", Font.BOLD, 30));
		multiple.setBorder(new RoundedBorder(new Color(255,102,178,100)));
		multiple.setContentAreaFilled( false );
		multiple.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				multiple.setBorder(new RoundedBorder(new Color(255,102,178,100)));
				selectMultiMode();
				GuiEvent ev = new GuiEvent(this,UserAgent.TEXT_EVENT);
				ev.addParameter("listGroup");
				myAgent.postGuiEvent(ev);
				System.out.println("envoyer listGroup\n");
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				multiple.setBorder(new RoundedBorder(new Color(255,102,178,150)));
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				multiple.setBorder(new RoundedBorder(new Color(255,102,178,100)));
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				multiple.setBorder(new RoundedBorder(new Color(255,102,178,50)));
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				multiple.setBorder(new RoundedBorder(new Color(255,102,178,150)));
				
			}
		});
		
		exit = new JButton("Exit");
		exit.setFont(new Font("Lucida Calligraphy", Font.BOLD, 30));
		exit.setBorder(new RoundedBorder(new Color(51,255,51,100)));
		exit.setContentAreaFilled( false );
		exit.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				exit.setBorder(new RoundedBorder(new Color(51,255,51,100)));
				 System.exit(1);  
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				exit.setBorder(new RoundedBorder(new Color(51,255,51,150)));
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				exit.setBorder(new RoundedBorder(new Color(51,255,51,100)));
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				exit.setBorder(new RoundedBorder(new Color(51,255,51,50)));
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				exit.setBorder(new RoundedBorder(new Color(51,255,51,150)));
				
			}
		});
		
		
		
		single.setBounds(80, 150,500, 150);
		multiple.setBounds(80,350,500,150);
		exit.setBounds(80,550,500,150);
				
		imagePanel.add(single);
		imagePanel.add(multiple);
		imagePanel.add(exit);
		
		

		//this.getGraphicsConfiguration().getDevice().setFullScreenWindow(this);
		
		// personnel cursor
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursorImage = toolkit.getImage("src/images/cursor.png");
		Point cursorHotSpot = new Point(0,0);
		Cursor customCursor = toolkit.createCustomCursor(cursorImage, cursorHotSpot, "Cursor");
		this.setCursor(customCursor);
		
		//this.hideCursor();
		
		
	}

	private void selectSingleMode() {
		GuiEvent ev = new GuiEvent(this,UserAgent.SELECT_EVENT);
		ev.addParameter(UserAgent.Single_Mode);
		myAgent.postGuiEvent(ev);
	}
	
	private void selectMultiMode(){
		GuiEvent ev = new GuiEvent(this,UserAgent.SELECT_EVENT);
		ev.addParameter(UserAgent.Multiple_Mode);
		myAgent.postGuiEvent(ev);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
//		if (evt.getPropertyName().equals("pos")) {
//			System.out.println("x = " + ((Cordinates) evt.getNewValue()).x
//					+ " y = " + ((Cordinates) evt.getNewValue()).y);
//			mouse.setPos(((Cordinates) evt.getNewValue()).x,
//					((Cordinates) evt.getNewValue()).y);
//		}

	}



}
