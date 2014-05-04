import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class mainview extends JFrame{
//	private JLabel lb;
	private ImageIcon background;
	public mainview(){
		
		//background = new ImageIcon("/Users/akeharuxiao/Dropbox/image/main.jpg");
		//JLabel label = new JLabel(background); 
		//label.setBounds(0, 0, background.getIconWidth(), background.getIconHeight()); 
		
		JButton singlemode = new JButton("Single Mode");
		JButton multimode = new JButton("Multiple Mode");
		JButton quit = new JButton("Exit");
		singlemode.setBounds(200,120,200,50);
		multimode.setBounds(200,200,200,50);
		quit.setBounds(250,280,100,40);

				
/*		start.setBackground(Color.yellow);
		start.setOpaque(true);
		start.setBorderPainted(false);
		
		stop.setBackground(Color.pink);
		stop.setOpaque(true);
		stop.setBorderPainted(false);
	*/	
		this.setTitle("Leap Band");
		this.setSize(600,450);
		this.setLocationRelativeTo(null);		//窗口显示在屏幕中间
		this.setLayout(null);
		//this.add(label);
		this.add(singlemode);
		this.add(multimode);
		this.add(quit);		
				
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //arret du programme
		this.setVisible(true);
		
		singlemode.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				new chooseview();
			}
		});
		
		multimode.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				new chooseview();
			}
		});
		
		quit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(DO_NOTHING_ON_CLOSE);
			}
		});
	}
	/*public void paintComponent(Graphics g) {
		g.drawImage(background.getImage(), 0, 0, this);
		super.paintComponents(g);    
		}
	*/
}
