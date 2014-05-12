package View;


import jade.gui.GuiEvent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import SMA.UserAgent;
import Utilities.Constance;




public class MenuView extends JFrame implements PropertyChangeListener{
	private JPanel buttonPane;
	private UserAgent myAgent;

	public MenuView(UserAgent agent) {
		myAgent = agent;
		//super(agent);
		this.setTitle("Menu View");
		this.setSize(Constance.Windows_width, Constance.Windows_height);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myAgent.addPropertyChangeListener(this);
		//mouse = new MousePane();
		buttonPane = new JPanel();
		buttonPane.setLayout(null);
		this.add(buttonPane,BorderLayout.CENTER);
		buttonPane.setSize(new Dimension(Constance.Windows_width, Constance.Windows_height));

		//*************** button
		//mouse.setLayout(null);
		JLabel leap_band = new JLabel("Leap Band");
		JButton single = new JButton("Single Mode");
		JButton multiple = new JButton("Multiple Mode");
		JButton exit = new JButton("Exit");
		
		
		leap_band.setBounds(500, 30, 500, 150);
		leap_band.setFont(new Font("Serif", Font.PLAIN, 50));
		leap_band.setHorizontalAlignment(SwingConstants.CENTER);
		leap_band.setForeground(Color.MAGENTA);
		single.setBounds(500, 150,500, 120);
		single.setFont(new Font("Serif", Font.PLAIN, 30));
		multiple.setBounds(500,350,500,120);
		multiple.setFont(new Font("Serif", Font.PLAIN, 30));
		exit.setBounds(500,550,500,120);
		exit.setFont(new Font("Serif", Font.PLAIN, 30));
				
		buttonPane.add(leap_band);
		buttonPane.add(single);
		buttonPane.add(multiple);
		buttonPane.add(exit);
		

		
		
		single.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selectSingleMode();
			}
		});
		
		multiple.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selectMultiMode();
				GuiEvent ev = new GuiEvent(this,UserAgent.TEXT_EVENT);
				ev.addParameter("listGroup");
				myAgent.postGuiEvent(ev);
				
			}
		});
		
		exit.addActionListener(new java.awt.event.ActionListener() {  
            public void actionPerformed(java.awt.event.ActionEvent evt) {  
                System.exit(1);  
            }  
        });
		//this.getGraphicsConfiguration().getDevice().setFullScreenWindow(this);
		
		// personnel cursor
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursorImage = toolkit.getImage("src/cursor.png");
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
