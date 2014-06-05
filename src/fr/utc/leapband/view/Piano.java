package fr.utc.leapband.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.security.Key;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import fr.utc.leapband.utilities.Constance;




@SuppressWarnings({ "rawtypes", "serial" })

public class Piano extends JPanel implements MouseListener {  
	
	Vector keys = new Vector();  
	  Vector whiteKeys = new Vector();
      Vector blackKeys = new Vector();  
      Key prevKey;  
      final int kw = 90, kh = 400;  
      final int ON = 0, OFF = 1;  
      boolean record;  
      final Color jfcBlue = new Color(204, 204, 255);  
      final Color pink = new Color(255, 175, 175);
      final int NOTEON = 144;  
      final int NOTEOFF = 128;
      JCheckBox mouseOverCB = new JCheckBox("mouseOver", true);  




      public Piano() {  
    	  this.setBounds(20, (int)(Constance.Windows_height*0.4),14*kw, kh);
          setLayout(new BorderLayout());  
         // setPreferredSize(new Dimension(42*kw, kh+1));  
          int transpose = 24;    
          int whiteIDs[] = { 0, 2, 4, 5, 7, 9, 11 };   
          
          for (int i = 0, x = 0; i < 3; i++) {  
              for (int j = 0; j < 7; j++, x += kw) {  
                  int keyNum = i * 12 + whiteIDs[j] + transpose;  
                  whiteKeys.add(new Key(x, 0, kw, kh, keyNum));  
              }  
          }  
          for (int i = 0, x = 0; i < 3; i++, x += kw) {  
              int keyNum = i * 12 + transpose;  
              blackKeys.add(new Key((x += kw)-4, 0, kw/2, kh/2, keyNum+1));  
              blackKeys.add(new Key((x += kw)-4, 0, kw/2, kh/2, keyNum+3));  
              x += kw;  
              blackKeys.add(new Key((x += kw)-4, 0, kw/2, kh/2, keyNum+6));  
              blackKeys.add(new Key((x += kw)-4, 0, kw/2, kh/2, keyNum+8));  
              blackKeys.add(new Key((x += kw)-4, 0, kw/2, kh/2, keyNum+10));  
          }  
          keys.addAll(blackKeys);  
          keys.addAll(whiteKeys);  

          addMouseMotionListener(new MouseMotionAdapter() {  
              public void mouseMoved(MouseEvent e) {  
                  if (mouseOverCB.isSelected()) {  
                      Key key = getKey(e.getPoint());  
                      if (prevKey != null && prevKey != key) {  
                          prevKey.off();  
                      }   
                      if (key != null && prevKey != key) {  
                          key.on();  
                      }  
                      prevKey = key;  
                      repaint();  
                  }  
              }  
          });  
          addMouseListener(this);  
      }  

      public void mousePressed(MouseEvent e) {   
          prevKey = getKey(e.getPoint());  
          if (prevKey != null) {  
              prevKey.on();  
              repaint();  
          }  
      }  
      public void mouseReleased(MouseEvent e) {   
          if (prevKey != null) {  
              prevKey.off();  
              repaint();  
          }  
      }  
      public void mouseExited(MouseEvent e) {   
          if (prevKey != null) {  
              prevKey.off();  
              repaint();  
              prevKey = null;  
          }  
      }  
      public void mouseClicked(MouseEvent e) { }  
      public void mouseEntered(MouseEvent e) { }  


      public Key getKey(Point point) {  
          for (int i = 0; i < keys.size(); i++) {  
              if (((Key) keys.get(i)).contains(point)) {  
                  return (Key) keys.get(i);  
              }  
          }  
          return null;  
      }  

      public void paint(Graphics g) {  
          Graphics2D g2 = (Graphics2D) g;  
          Dimension d = getSize();  

          g2.setBackground(getBackground());  
          g2.clearRect(0, 0, d.width, d.height);  

          g2.setColor(Color.white);  
          g2.fillRect(0, 0, 14*kw, kh);  

          for (int i = 0; i < whiteKeys.size(); i++) {  
              Key key = (Key) whiteKeys.get(i);  
              if (key.isNoteOn()) {  
                  g2.setColor(record ? pink : jfcBlue);  
                  g2.fill(key);  
              }  
              g2.setColor(Color.black);  
              g2.draw(key);  
          }  
          for (int i = 0; i < blackKeys.size(); i++) {  
              Key key = (Key) blackKeys.get(i);  
              if (key.isNoteOn()) {  
                  g2.setColor(record ? pink : jfcBlue);  
                  g2.fill(key);  
                  g2.setColor(Color.black);  
                  g2.draw(key);  
              } else {  
                  g2.setColor(Color.black);  
                  g2.fill(key);  
              }  
          }  
      }  
      
      /** 
       * Black and white keys or notes on the piano. 
       */  
      class Key extends Rectangle {  
          int noteState = OFF;  
          int kNum;  
          public Key(int x, int y, int width, int height, int num) {  
              super(x, y, width, height);  
              kNum = num;  
          }  
          public boolean isNoteOn() {  
              return noteState == ON;  
          }  
          public void on() {  
              setNoteState(ON);  
//              cc.channel.noteOn(kNum, cc.velocity);  
//              if (record) {  
//                  createShortEvent(NOTEON, kNum);  
//              }  
          }  
          public void off() {  
              setNoteState(OFF);  
//              cc.channel.noteOff(kNum, cc.velocity);  
//              if (record) {  
//                  createShortEvent(NOTEOFF, kNum);  
//              }  
          }  
          public void setNoteState(int state) {  
              noteState = state;  
          }  
      } // End class Key  
    
    
  } // End class Piano  
