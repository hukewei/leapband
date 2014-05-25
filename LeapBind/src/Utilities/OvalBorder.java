package Utilities;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.border.Border;

public class OvalBorder implements Border {  
  
protected int m_w = 6;  
protected int m_h = 6;  
protected Color m_Color = Color.white;  
  
public OvalBorder() {  
m_w = 6;  
m_h = 6;  
}  
  
public OvalBorder(int w, int h) {  
m_w = w;  
m_h = h;  
}  
  
public OvalBorder(int w, int h, Color color) {  
m_w = w;  
m_h = h;  
m_Color = color;  
}  
public Insets getBorderInsets(Component c) {  
return new Insets(m_h,m_w,m_h,m_w);  
}  
  
public boolean isBorderOpaque() {  
return true;  
}  
  
public void paintBorder(Component c,Graphics g, int x, int y, int w, int h) {  
w=w-5;  
h=h-5;  

Graphics2D g2 = (Graphics2D) g;
g2.setStroke(new BasicStroke(4));
//g2.drawOval(x, y, width, height);

g2.setColor(m_Color);  
g2.drawOval(x+2, y+2, w, h);
//g.drawLine(x, y+h-m_h, x, y+m_h);  
//g.drawArc(x,y, 2*m_w,2*m_h,180,-90);  
//g.drawLine(x+m_w,y, x+w-m_w,y);  
//g.drawArc(x+w-2*m_w, y, 2*m_w,2*m_h,90,-90);  
  
//g.setColor(m_bottomColor);  
//g.drawLine(x+w, y+m_h, x+w, y+h-m_h);  
//g.drawArc(x+w-2*m_w, y+h-2*m_h, 2*m_w, 2*m_h, 0,-90);  
//g.drawLine(x+m_w, y+h, x+w-m_w, y+h);  
//g.drawArc(x, y+h-2*m_h, 2*m_w, 2*m_h, -90,-90);  
}




}  