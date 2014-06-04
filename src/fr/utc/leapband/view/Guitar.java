package fr.utc.leapband.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.utc.leapband.utilities.Constance;
import fr.utc.leapband.utilities.CustomImgPanel;

public class Guitar extends JLabel{
	public static double x=Constance.Windows_width;
	public static double y=Constance.Windows_height*0.2;
	
	public static int width=(int)(Constance.Windows_width*0.09);
	public static int height=(int)(Constance.Windows_height*0.7);
//	int widths;
//	int heights;
	
	public Guitar(){
		
		 this.setBounds(10,(int)y,(int)x, (int)height);
         setLayout(null);  

		
         
        GuitarWidgetView widget1=new GuitarWidgetView("EM");
 		GuitarWidgetView widget2=new GuitarWidgetView("AM");
 		GuitarWidgetView widget3=new GuitarWidgetView("DM");
 		GuitarWidgetView widget4=new GuitarWidgetView("G");
 		GuitarWidgetView widget5=new GuitarWidgetView("C");
 		GuitarWidgetView widget6=new GuitarWidgetView("F");
 		GuitarWidgetView widget7=new GuitarWidgetView("Bb");
 		GuitarWidgetView widget8=new GuitarWidgetView("Bdim");
 		
 		
 		widget1.setBounds((int)(x*0.09),100,width,height);
 		widget2.setBounds((int)(x*0.2),100,width,height);
 		widget3.setBounds((int)(x*0.31),100,width,height);
 		widget4.setBounds((int)(x*0.42),100,width,height);
 		widget5.setBounds((int)(x*0.53),100,width,height);
 		widget6.setBounds((int)(x*0.64),100,width,height);
 		widget7.setBounds((int)(x*0.75),100,width,height);
 		widget8.setBounds((int)(x*0.86),100,width,height);
 		
 		this.add(widget1);
 		this.add(widget2);
 		this.add(widget3);
 		this.add(widget4);
 		this.add(widget5);
 		this.add(widget6);
 		this.add(widget7);
 		this.add(widget8);
 	
	}

}
