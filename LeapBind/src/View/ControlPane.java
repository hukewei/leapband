package View;

//import musicview;
import jade.gui.GuiEvent;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.Border;

import SMA.user.UserAgent;
import Utilities.RoundedBorder;


public class ControlPane extends JPanel{
	// personnel cursor
	private UserAgent myAgent;
	private Boolean propietaire = true;
	
	public ControlPane(UserAgent agent) {
		this.myAgent=agent;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursorImage = toolkit.getImage("src/images/cursor.png");
		Point cursorHotSpot = new Point(0,0);
		Cursor customCursor = toolkit.createCustomCursor(cursorImage, cursorHotSpot, "Cursor");
		this.setCursor(customCursor);
		this.setLayout(null);
		this.setBackground(new Color(255,255,204,200));
		
		JButton home = new JButton();
		Icon icon = new ImageIcon("src/images/home.png");
		home.setBounds(100,50,100,100);
		home.setIcon(icon);
		home.setBackground(Color.WHITE);
		home.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				GuiEvent ev = new GuiEvent(this,UserAgent.SELECT_EVENT);
				ev.addParameter(UserAgent.return_Menu);
				myAgent.postGuiEvent(ev);
			}
		});
		/*JLabel userId=new JLabel("player1");
		userId.setBounds(220, 20, 100, 20);
		userId.setHorizontalAlignment(SwingConstants.CENTER);*/
		JLabel player = new JLabel(new ImageIcon("src/images/person2.jpg"));
		player.setBounds(300, 50, 100, 100);
		Border border=BorderFactory.createLineBorder(Color.BLACK, 5);

		player.setBorder(border);
		JButton music = new JButton("Choose a music");
		music.setFont(new Font("Serif", Font.PLAIN, 30));
		music.setBounds(520, 50, 400, 100);
		music.setBackground(Color.WHITE);
		//music.setBorder(border);
		music.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new musicview();
			}
		});
		if(!is_proprietaire()){
			music.setEnabled(false);
		}
		JButton stop = new JButton();
		Icon icone = new ImageIcon("src/images/stop.png");
		stop.setBounds(1000, 50, 100, 100);
		stop.setIcon(icone);
		stop.setBackground(Color.WHITE);
		
		JLabel volume = new JLabel(new ImageIcon("src/images/volume.png"));
		volume.setBounds(1200, 50, 100, 100);
		
		
		
		
		
		
		this.add(home);
		this.add(player);
		this.add(music);
		this.add(stop);
		this.add(volume);
		
		
		//this.add(userId);
		//this.add(play);
	}
	public Boolean is_proprietaire(){
		return propietaire;
	}
	public void set_proprietaire(boolean b){
		propietaire = b;
	}
	
	
	
	
}
