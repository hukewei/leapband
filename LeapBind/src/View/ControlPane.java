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

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

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
		Icon icon = new ImageIcon("src/home.png");
		home.setBounds(100,50,100,100);
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
		/*JLabel userId=new JLabel("player1");
		userId.setBounds(220, 20, 100, 20);
		userId.setHorizontalAlignment(SwingConstants.CENTER);*/
		JLabel player = new JLabel(new ImageIcon("src/person2.jpg"));
		player.setBounds(300, 50, 100, 100);
		Border border=BorderFactory.createLineBorder(Color.BLACK, 5);

		player.setBorder(border);
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
		stop.setBounds(1000, 50, 100, 100);
		stop.setIcon(icone);
		JLabel volume = new JLabel(new ImageIcon("src/volume.png"));
		volume.setBounds(1200, 50, 100, 100);
		
		this.add(home);
		this.add(player);
		this.add(music);
		this.add(stop);
		this.add(volume);
		//this.add(userId);
		//this.add(play);
	}			
}
