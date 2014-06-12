package fr.utc.leapband.utilities;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.border.AbstractBorder;

@SuppressWarnings("serial")
public class RoundedBorder extends AbstractBorder  {

	private Color background; 
    public RoundedBorder(Color background) {
    	this.background=background;
       
    }
    public void paintBorder(Component c, Graphics g, int x, int y, int width, 
    		int height) { 

    		Graphics2D g2 = (Graphics2D) g; 
    		
    		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
    		RenderingHints.VALUE_ANTIALIAS_ON); 
    		

    		
    		g2.drawRoundRect(x, y, width - 1, height - 1, 50, 50);
    		
    		g2.setColor(background);
    		g2.fillRoundRect(x, y, width, height, 50, 50);
    }
}
