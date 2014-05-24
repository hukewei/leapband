package Utilities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class ScaleLabel extends JLabel{
	
	Image image;  
    BufferedImage bufImage; 
    BufferedImage originalBufImage;
    Graphics2D bufImageG; 
    public double scaleX = 1.0; 
    public double scaleY = 1.0; 
    

    
    public void loadImage(String fileName) {  
        image = this.getToolkit().getImage(fileName);
        MediaTracker mt = new MediaTracker(this); 
        mt.addImage(image, 0);  
        try {  
            mt.waitForAll(); 
        } catch (Exception ex) {  
            ex.printStackTrace(); 
        }  
        originalBufImage = new BufferedImage(image.getWidth(this), image.getHeight(this), BufferedImage.TYPE_INT_ARGB);   
        bufImage = originalBufImage;  
        bufImageG = bufImage.createGraphics(); 
        bufImageG.drawImage(image, 0, 0, this); 
        repaint(); 
    }  

    // 过滤图像  
    public void applyFilter() {  
        if (bufImage == null)  
            return; 
        BufferedImage filteredBufImage = new BufferedImage((int) (image.getWidth(this) * scaleX), (int) (image.getHeight(this) * scaleY), BufferedImage.TYPE_INT_ARGB); 
        AffineTransform transform = new AffineTransform(); 
        transform.setToScale(scaleX, scaleY); 
        AffineTransformOp imageOp = new AffineTransformOp(transform, null);
        imageOp.filter(originalBufImage, filteredBufImage);
        bufImage = filteredBufImage;   
        repaint(); 
    }  

    // 重载容器的paintComponent()方法  
    public void paint(Graphics g) {  
        super.paintComponent(g);  
        if (bufImage != null) {  
            Graphics2D g2 = (Graphics2D) g;  
            g2.drawImage(bufImage, (this.getWidth() - bufImage.getWidth()) / 2, (this.getHeight() - bufImage.getHeight()) / 2, this);  
        }  
    }  


}
