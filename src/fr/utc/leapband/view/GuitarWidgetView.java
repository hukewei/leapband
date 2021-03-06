package fr.utc.leapband.view;

import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import fr.utc.leapband.sma.user.UserAgent;
import fr.utc.leapband.utilities.Constance;
import fr.utc.leapband.utilities.RectBorder;
import fr.utc.leapband.utilities.RoundedBorder;

@SuppressWarnings("serial")
public class GuitarWidgetView extends JButton implements PropertyChangeListener{
	public static double width=Constance.Windows_width*0.09;
	public static double height=Constance.Windows_height*0.7;
	private int id;
	
	 int dx, dy;
	
	public GuitarWidgetView(String titre, int id, UserAgent agent){
		this.id = id;
		this.setText(titre);
		this.setVerticalAlignment(SwingConstants.TOP);
		this.setFont(new Font("Chalkboard", Font.PLAIN, 30));
		this.setForeground(Color.WHITE);
		this.setBorder(new RoundedBorder(new Color(87,52,10,150)));
		this.setSize((int)width,(int)height);
		agent.addPropertyChangeListener(this);
		this.setContentAreaFilled(false);
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (isVisible()) {
			if (evt.getPropertyName().equals("chord")) {
				if (id == (int)evt.getNewValue()) {
					this.setBorder(new RectBorder(new Color(46,74,80,150)));
					new Timer().schedule( 
					        new java.util.TimerTask() {
					            @Override
					            public void run() {
					            	setBorder(new RectBorder(new Color(87,52,10,150))); 
					            }
					        }, 
					        1000 
					);
				}
			}
		}
		
	}
}
