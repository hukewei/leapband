package fr.utc.leapband.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import fr.utc.leapband.utilities.Constance;
import fr.utc.leapband.utilities.RectBorder;

@SuppressWarnings("serial")
public class GuitarWidgetView extends JButton implements MouseListener{
	public static double width=Constance.Windows_width*0.09;
	public static double height=Constance.Windows_height*0.7;
	private int positionX;
	private int positionY;
	
	 int dx, dy;
	
	public GuitarWidgetView(String titre){
		this.setText(titre);
		this.setVerticalAlignment(SwingConstants.TOP);
		this.setFont(new Font("Chalkboard", Font.PLAIN, 30));
		this.setForeground(Color.WHITE);
		this.setBorder(new RectBorder(new Color(87,52,10,150)));
		this.setSize((int)width,(int)height);
		
		this.setContentAreaFilled(false);
		
	}

	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.setBorder(new RectBorder(new Color(46,74,80,150)));		
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.setBorder(new RectBorder(new Color(87,52,10,150)));
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
