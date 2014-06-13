package fr.utc.leapband.view;

import javax.swing.JLabel;

import fr.utc.leapband.sma.user.UserAgent;
import fr.utc.leapband.utilities.Constance;

@SuppressWarnings("serial")
public class Guitar extends JLabel{
	public static double x=Constance.Windows_width;
	public static double y=Constance.Windows_height*0.1;
	
	public static int width=(int)(Constance.Windows_width*0.09);
	public static int height=(int)(Constance.Windows_height*0.85);
	
	public Guitar(UserAgent myAgent){
		
		 this.setBounds(10,(int)y,(int)x, (int)height);
         setLayout(null);

		
         
        GuitarWidgetView widget1=new GuitarWidgetView("EM", 1, myAgent);
 		GuitarWidgetView widget2=new GuitarWidgetView("AM", 2, myAgent);
 		GuitarWidgetView widget3=new GuitarWidgetView("DM", 3, myAgent);
 		GuitarWidgetView widget4=new GuitarWidgetView("G", 4, myAgent);
 		GuitarWidgetView widget5=new GuitarWidgetView("C", 5, myAgent);
 		GuitarWidgetView widget6=new GuitarWidgetView("F", 6, myAgent);
 		GuitarWidgetView widget7=new GuitarWidgetView("Bb", 7, myAgent);
 		GuitarWidgetView widget8=new GuitarWidgetView("Bdim", 8, myAgent);
 		
 		
 		widget1.setBounds((int)(x*0.01),100,width,height);
 		widget2.setBounds((int)(x*0.14),100,width,height);
 		widget3.setBounds((int)(x*0.27),100,width,height);
 		widget4.setBounds((int)(x*0.4),100,width,height);
 		widget5.setBounds((int)(x*0.53),100,width,height);
 		widget6.setBounds((int)(x*0.66),100,width,height);
 		widget7.setBounds((int)(x*0.79),100,width,height);
 		widget8.setBounds((int)(x*0.9),100,width,height);
 		
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
