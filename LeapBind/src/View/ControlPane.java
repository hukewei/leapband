package View;

//import musicview;
import jade.gui.GuiEvent;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import SMA.UserAgent;


public class ControlPane extends JPanel{
	// personnel cursor
	private UserAgent myAgent;
	
	
	public ControlPane(UserAgent agent) {
		this.myAgent=agent;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursorImage = toolkit.getImage("src/cursor.png");
		Point cursorHotSpot = new Point(0,0);
		Cursor customCursor = toolkit.createCustomCursor(cursorImage, cursorHotSpot, "Cursor");
		this.setCursor(customCursor);
		this.setLayout(null);
		
		JButton home = new JButton();
		Icon icon = new ImageIcon("src/fangzi.png");
		home.setBounds(0, 0, 50, 50);
		home.setIcon(icon);
		home.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				GuiEvent ev = new GuiEvent(this,UserAgent.SELECT_EVENT);
				ev.addParameter(UserAgent.return_Menu);
				myAgent.postGuiEvent(ev);
			}
		});
		JLabel userId=new JLabel("player1");
		userId.setBounds(220, 20, 100, 20);
		userId.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel player = new JLabel(new ImageIcon("src/player.png"));
		player.setBounds(200, 20, 150, 150);
		JButton music = new JButton("Choose a music");
		music.setFont(new Font("Serif", Font.PLAIN, 30));
		music.setBounds(520, 50, 400, 100);
		music.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new musicview();
			}
		});
		JButton stop = new JButton();
		Icon icone = new ImageIcon("src/stop.png");
		stop.setBounds(1000, 50, 80, 80);
		stop.setIcon(icone);
		JLabel volume = new JLabel(new ImageIcon("src/volume.png"));
		volume.setBounds(1200, 50, 80, 80);
		//JLabel play = new JLabel(new ImageIcon("/Users/akeharuxiao/Dropbox/image/play.png"));
		//play.setBounds(0, 170, 600, 256);
		
		this.add(home);
		this.add(player);
		this.add(music);
		this.add(stop);
		this.add(volume);
		this.add(userId);
		//this.add(play);
/*		JButton home=new JButton();
		home.setIcon(new ImageIcon("src/home.jpg"));
		home.setBounds(0, 0, 50, 50);
		this.add(home);
		
		JLabel userId=new JLabel("player1");
		userId.setBounds(100, 0, 100, 20);
		userId.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel userImage=new JLabel(new ImageIcon("src/userImage.JPG"));
		userImage.setBounds(100, 25, 100, 100);
		this.add(userId);
		this.add(userImage);
		
		JButton chooseMusic=new JButton("Choose a music");
		chooseMusic.setBounds(500, 50, 300, 100);
		chooseMusic.setFont(new Font("Serif", Font.PLAIN, 30));
		this.add(chooseMusic);
		
		JButton before=new JButton();
		JButton play=new JButton();
		JButton after=new JButton();
		before.setIcon(new ImageIcon("src/before.JPG"));
		play.setIcon(new ImageIcon("src/play.JPG"));
		after.setIcon(new ImageIcon("src/after.JPG"));
		before.setBounds(850, 50, 80, 80);
		play.setBounds(950, 50, 80, 80);
		after.setBounds(1050, 50, 80, 80);
		this.add(before);
		this.add(play);
		this.add(after);
	   
   
	        
		
		
		home.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GuiEvent ev = new GuiEvent(this,UserAgent.SELECT_EVENT);
				ev.addParameter(UserAgent.return_Menu);
				myAgent.postGuiEvent(ev);
				
			}
		});
*/
		
//		JButton start = new JButton("start");
//		start.setBounds(0, 50,200, 150);
//		this.add(start);
//		JButton end = new JButton("end");
//		end.setBounds(300, 50,200, 150);
//		this.add(end);
	}
	
	

	
			
}
