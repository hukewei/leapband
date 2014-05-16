package Utilities;

import javax.swing.JPanel;  

import java.awt.BorderLayout;
import java.awt.Graphics;  
import java.awt.Graphics2D;  
import java.awt.Toolkit;  
import java.awt.Image;  

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
      
    public CustomImgPanel(double _width,double _height,String _imgPath){
    	//this.setLayout(new BorderLayout());
        width = (int)_width;  
        height = (int)_height;  
        imgPath = _imgPath;  
        setSize(width,height);  
        setVisible(true);  
    }  
  
    @Override  
    public void paintComponent(Graphics gs) {  
        Graphics2D g = (Graphics2D) gs;  
        super.paintComponent(g);  
        
        Image image = Toolkit.getDefaultToolkit().getImage(imgPath);  
        g.drawImage(image, 0, 0,width,height, this);  
    }  
}  