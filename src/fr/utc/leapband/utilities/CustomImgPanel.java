package fr.utc.leapband.utilities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CustomImgPanel extends JPanel{  
    private int width = 0;  
    private int height = 0;  
    private String imgPath = "";  
    
//    public CustomImgPanel(int _width,int _height,String _imgPath){  
//        width = _width;  
//        height = _height;  
//        imgPath = _imgPath;  
//        setSize(width,height);  
//        setVisible(true);  
//    }  
    
   
      
    public CustomImgPanel(double _width,double _height){
    	//this.setLayout(new BorderLayout());
        width = (int)_width;  
        height = (int)_height;  
         
        setSize(width,height);  
        setVisible(true);  
    }
    public void setImagePath(String _imgPath){
    	 imgPath = _imgPath;
    }
    
  
    @Override  
    public void paintComponent(Graphics gs) {  
        Graphics2D g = (Graphics2D) gs;  
        super.paintComponent(g);  
        
        Image image = Toolkit.getDefaultToolkit().getImage(imgPath);  
        g.drawImage(image, 0, 0,width,height, this);  
    }  
}  