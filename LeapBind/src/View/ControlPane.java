package View;

//import musicview;
import jade.gui.GuiEvent;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Timer;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import SMA.user.UserAgent;
import Utilities.Constance;


public class ControlPane extends JPanel{
	// personnel cursor
	private UserAgent myAgent;
	private Boolean propietaire = true;
	private int width;
	private int height;
	JLabel player;
	Timer click_task = null;
	private JButton home;
	public ControlPane(UserAgent agent) {
		this.width=Constance.Windows_width;
		this.height=Constance.Windows_height;
		this.myAgent=agent;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursorImage = toolkit.getImage("src/images/cursor.png");
		Point cursorHotSpot = new Point(0,0);
		Cursor customCursor = toolkit.createCustomCursor(cursorImage, cursorHotSpot, "Cursor");
		this.setCursor(customCursor);
		this.setLayout(null);
		this.setBackground(new Color(255,255,204,200));
		
		
		home = new JButton();
		Icon icon = new ImageIcon("src/images/home.png");
		//home.setPreferredSize(new Dimension(100,100));
		home.setBounds((int) (width*0.1),(int) (height*0.05),100,100);
		home.setIcon(icon);
		//home.setBackground(Color.WHITE);
		home.setOpaque(false);
		home.setBorderPainted(false);
		home.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseExited(MouseEvent e) {
				home.setBorderPainted(false);
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Image cursorImage = toolkit.getImage("src/images/cursor.png");
				Point cursorHotSpot = new Point(0,0);
				Cursor customCursor = toolkit.createCustomCursor(cursorImage, cursorHotSpot, "Cursor");
				setCursor(customCursor);
				if (click_task != null) {
					click_task.cancel();
					click_task = null;
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				home.setBorderPainted(true);
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				click_task = new Timer();
				click_task.schedule( 
				        new java.util.TimerTask() {
				            @Override
				            public void run() {
				            	GuiEvent ev = new GuiEvent(this,UserAgent.SELECT_EVENT);
								ev.addParameter(UserAgent.return_Menu);
								myAgent.postGuiEvent(ev);
				            }
				        }, 
				        Constance.click_delay 
				);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		/*JLabel userId=new JLabel("player1");
		userId.setBounds(220, 20, 100, 20);
		userId.setHorizontalAlignment(SwingConstants.CENTER);*/
		player = new JLabel(new ImageIcon("src/images/person2.jpg"));
		//player.setPreferredSize(new Dimension(100,100));
		player.setBounds((int) (width*0.2), (int) (height*0.05), 100, 100);
		Border border=BorderFactory.createLineBorder(Color.BLACK, 5);

		player.setBorder(border);
		JButton music = new JButton("Choose a music");
		music.setFont(new Font("Serif", Font.PLAIN, 30));
		//music.setPreferredSize(new Dimension(400,100));
		music.setBounds((int) (width*0.35), (int) (height*0.05), 400, 100);
		music.setBackground(Color.WHITE);
		//music.setBorder(border);
		music.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new musicview(new File("src/songs/"),myAgent);
			}
		});
		if(!is_proprietaire()){
			music.setEnabled(false);
		}
		JButton stop = new JButton();
		Icon icone = new ImageIcon("src/images/stop.png");
		//stop.setPreferredSize(new Dimension(100,100));
		stop.setBounds((int) (width*0.7), (int) (height*0.05), 100, 100);
		stop.setIcon(icone);
		stop.setBackground(Color.WHITE);
		
		JLabel volume = new JLabel(new ImageIcon("src/images/volume.png"));
		volume.setBounds((int) (width*0.9), (int) (height*0.05), 100, 100);
		//volume.setPreferredSize(new Dimension(100,100));
		
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
