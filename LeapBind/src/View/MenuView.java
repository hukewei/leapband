package View;

import jade.gui.GuiEvent;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.Constance;
import SMA.UserAgent;



public class MenuView extends JAgentFrame {
	private JPanel buttonPane;

	public MenuView(UserAgent agent) {
		super(agent);
		this.setTitle("Menu View");
		this.setSize(Constance.Windows_width, Constance.Windows_height);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		exit.addActionListener(new java.awt.event.ActionListener() {  
            public void actionPerformed(java.awt.event.ActionEvent evt) {  
                System.exit(1);  
            }  
        });
		leap_band.setBounds(500, 50, 500, 150);
		single.setBounds(500, 150,500, 150);
		multiple.setBounds(500,350,500,150);
		exit.setBounds(500,550,500,150);
				
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
