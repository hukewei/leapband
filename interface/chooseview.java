import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class chooseview extends JFrame{
	
	private JLabel label;
	private JLabel test;
	private JLabel labeleft;
	private JLabel labelright;
	private JLabel text;
	private int i=0;
	@SuppressWarnings("null")
	public chooseview(){
		
		final String[] choix = new String[3];
		final String[] choixmini = new String[3];
		choix[0]= "/Users/akeharuxiao/Dropbox/image/gu.png";
		choix[1]= "/Users/akeharuxiao/Dropbox/image/piano.png";
		choix[2]= "/Users/akeharuxiao/Dropbox/image/guitar.png";
		choixmini[0]= "/Users/akeharuxiao/Dropbox/image/test.jpg";
		choixmini[1]= "/Users/akeharuxiao/Dropbox/image/mini.png";
		choixmini[2]= "/Users/akeharuxiao/Dropbox/image/minihuang.png";
		JButton suivant = new JButton("suivant");
		suivant.setBounds(320,370,120,50);
		JButton precedent = new JButton("precedent");
		precedent.setBounds(170,370,120,50);
		JButton valider = new JButton("Valider");
		valider.setBounds(200,320,220,50);
		this.setTitle("ChooseView");
		this.setSize(600,450);
		this.setLocationRelativeTo(null);		//窗口显示在屏幕中间
		this.setLayout(null);
		
		JButton home = new JButton();
		Icon icon = new ImageIcon("/Users/akeharuxiao/Dropbox/image/fangzi.png");
		home.setBounds(15,15,35,35);
		home.setIcon(icon);
		//test = new JLabel(new ImageIcon("/Users/akeharuxiao/Desktop/1.png"));
		//test.setBounds(200, 100, 30, 30);
		//Image src = Toolkit.getDefaultToolkit().getImage("/Users/akeharuxiao/Desktop/1.png");
		//ImageFilter colorfilter = new RedBlueSwapFilter();
		//Image img = createImage(new FilteredImageSource(src.getSource(),colorfilter));
		int size=22;
		text = new JLabel("Choose your instrument");
		text.setFont(new Font("Serif",Font.PLAIN,size));
		text.setBounds(190, 30, 225, 50);
		label = new JLabel(new ImageIcon(choix[0]));
		label.setBounds(160, 60, 300, 300);
		labeleft = new JLabel(new ImageIcon(choixmini[2]));
		labeleft.setBounds(50, 220, 100, 100);
		labelright = new JLabel(new ImageIcon(choixmini[1]));
		labelright.setBounds(460,220,100,100);
		//test.setIcon(new ImageIcon(img));
		suivant.addActionListener(new ActionListener(){
			int left = 0;
			int right = 0;
			public void actionPerformed(ActionEvent e) {
				++i;
				if(i<0){
					i=i+1;
				}
				if(i==choix.length){
					i=0;
				}
				if(i-1 < 0){
					left = choix.length-1;
				}
				else{left = i-1;}
				if(i+1 == choix.length){
					right = 0;
				}
				else {right = i+1;}
				label.setIcon(new ImageIcon(choix[i]));
				labeleft.setIcon(new ImageIcon(choixmini[left]));
				labelright.setIcon(new ImageIcon(choixmini[right]));
				System.out.println("suivant" +i);
			}
		});
		precedent.addActionListener(new ActionListener(){
			int left = 0;
			int right = 0;
			public void actionPerformed(ActionEvent e) {
				--i;
				if(i == choix.length){
					i=i-1;
				}
				if(i < 0){
					i=choix.length-1;
				}
				if(i-1 < 0){
					left = choix.length-1;
				}
				else{left = i-1;}
				if(i+1 == choix.length){
					right = 0;
				}
				else {right = i+1;}
				label.setIcon(new ImageIcon(choix[i]));
				labeleft.setIcon(new ImageIcon(choixmini[left]));
				labelright.setIcon(new ImageIcon(choixmini[right]));
				System.out.println("precedent "+i);
			}
		});
		valider.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				new playview();
			}
		});
		this.add(label);
		this.add(labeleft);
		this.add(labelright);
		this.add(home);
		//this.add(test);
		this.add(suivant);
		this.add(precedent);
		this.add(valider);	
		this.add(text);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //arret du programme
		this.setVisible(true);
		home.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			setVisible(false);
			}
		});
	}
}
/*class  RedBlueSwapFilter  extends  java.awt.image.RGBImageFilter  {
    public  RedBlueSwapFilter()  {
            //  The  filter's  operation  does  not  depend  on  the
            //  pixel's  location,  so  IndexColorModels  can  be
            //  filtered  directly.
            canFilterIndexColorModel  =  true;
    }
    public  int  filterRGB(int  x,  int  y,  int  rgb)  {
            return  ((rgb  &  0xff00ff00)
                      |  ((rgb  &  0xff0000)  >>  16)
                      |  ((rgb  &  0xff)  <<  16)); 
            }
}*/
