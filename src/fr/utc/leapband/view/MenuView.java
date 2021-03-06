package fr.utc.leapband.view;


import jade.gui.GuiEvent;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import fr.utc.leapband.sma.user.UserAgent;
import fr.utc.leapband.utilities.Constance;
import fr.utc.leapband.utilities.CustomImgPanel;
import fr.utc.leapband.utilities.ImageTimerTask;
import fr.utc.leapband.utilities.RoundedBorder;




@SuppressWarnings("serial")
public class MenuView extends JAgentFrame {
	//private JPanel buttonPane;
	private JButton single;
	private JButton multiple;
	Timer click_task = null;
	JButton exit;

	public MenuView(UserAgent agent) {
		super(agent);
		this.setTitle("Menu View");
		this.setSize(Constance.Windows_width, Constance.Windows_height);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		CustomImgPanel imagePanel=new CustomImgPanel(Constance.Windows_width, Constance.Windows_height);
		imagePanel.setImagePath( "images/back.jpg");
		imagePanel.setLayout(null);
		this.add(imagePanel);
		
		JLabel leap_band = new JLabel(new ImageIcon("images/leapband.png"));
		
		leap_band.setBounds(500, 30, 500, 150);
		leap_band.setHorizontalAlignment(SwingConstants.CENTER);
		leap_band.setForeground(Color.MAGENTA);
		
		imagePanel.add(leap_band);
		
		JLabel gif=new JLabel(new ImageIcon("images/notesdorees.gif"));
		imagePanel.add(gif);
		gif.setBounds(950, 80, 500, 150);
		single = new JButton("Single Mode");
		single.setFont(new Font("Lucida Calligraphy", Font.BOLD, 30));
		single.setBorder(new RoundedBorder(new Color(0,128,255,100)));
		single.setContentAreaFilled( false );
		single.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				single.setBorder(new RoundedBorder(new Color(0,128,255,100)));
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				single.setBorder(new RoundedBorder(new Color(0,128,255,150)));
				System.out.println("mouse pressed...");
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				changeCursorImage("images/cursor.png");
				single.setBorder(new RoundedBorder(new Color(0,128,255,100)));
				System.out.println("mouse exit...");
				if (click_task != null) {
					click_task.cancel();
					click_task = null;
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				//setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				click_task = new Timer();
				click_task.schedule(new ImageTimerTask(myAgent),0,Constance.click_delay/12);
				click_task.schedule( 
				        new java.util.TimerTask() {
				            @Override
				            public void run() {
				            	selectSingleMode(); 
				            }
				        }, 
				        Constance.click_delay 
				);
				single.setBorder(new RoundedBorder(new Color(0,128,255,50)));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				single.setBorder(new RoundedBorder(new Color(0,128,255,150)));
				
			}
		});
		
		
		multiple = new JButton("Multiple Mode");
		multiple.setFont(new Font("Lucida Calligraphy", Font.BOLD, 30));
		multiple.setBorder(new RoundedBorder(new Color(255,102,178,100)));
		multiple.setContentAreaFilled( false );
		multiple.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				multiple.setBorder(new RoundedBorder(new Color(255,102,178,100)));
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				multiple.setBorder(new RoundedBorder(new Color(255,102,178,150)));
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				multiple.setBorder(new RoundedBorder(new Color(255,102,178,100)));
				changeCursorImage("images/cursor.png");
				single.setBorder(new RoundedBorder(new Color(0,128,255,100)));
				System.out.println("mouse exit...");
				if (click_task != null) {
					click_task.cancel();
					click_task = null;
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				click_task = new Timer();
				click_task.schedule(new ImageTimerTask(myAgent),0,Constance.click_delay/12);
				click_task.schedule( 
				        new java.util.TimerTask() {
				            @Override
				            public void run() {
				            	selectMultiMode();
								GuiEvent ev = new GuiEvent(this,UserAgent.TEXT_EVENT);
								ev.addParameter("listGroup");
								myAgent.postGuiEvent(ev);
								System.out.println("envoyer listGroup\n");
				            }
				        }, 
				        Constance.click_delay 
				);
				multiple.setBorder(new RoundedBorder(new Color(255,102,178,50)));
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				multiple.setBorder(new RoundedBorder(new Color(255,102,178,150)));
				
			}
		});
		
		exit = new JButton("Exit");
		exit.setFont(new Font("Lucida Calligraphy", Font.BOLD, 30));
		exit.setBorder(new RoundedBorder(new Color(51,255,51,100)));
		exit.setContentAreaFilled( false );
		exit.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				exit.setBorder(new RoundedBorder(new Color(51,255,51,100)));
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				exit.setBorder(new RoundedBorder(new Color(51,255,51,150)));
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				exit.setBorder(new RoundedBorder(new Color(51,255,51,100)));
				changeCursorImage("images/cursor.png");
				if (click_task != null) {
					click_task.cancel();
					click_task = null;
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				click_task = new Timer();
				click_task.schedule(new ImageTimerTask(myAgent),0,Constance.click_delay/12);
				click_task.schedule( 
				        new java.util.TimerTask() {
				            @Override
				            public void run() {
								 System.exit(1);
				            }
				        }, 
				        Constance.click_delay 
				);
				exit.setBorder(new RoundedBorder(new Color(51,255,51,50)));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				exit.setBorder(new RoundedBorder(new Color(51,255,51,150)));
				
			}
		});
		
		
		
		single.setBounds(80, 150,500, 150);
		multiple.setBounds(80,350,500,150);
		exit.setBounds(80,550,500,150);
				
		imagePanel.add(single);
		imagePanel.add(multiple);
		imagePanel.add(exit);
		
		

		//this.getGraphicsConfiguration().getDevice().setFullScreenWindow(this);
		
		// personnel cursor
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursorImage = toolkit.getImage("images/cursor.png");
		Point cursorHotSpot = new Point(0,0);
		Cursor customCursor = toolkit.createCustomCursor(cursorImage, cursorHotSpot, "Cursor");
		this.setCursor(customCursor);
	}

	private void selectSingleMode() {
		GuiEvent ev = new GuiEvent(this,UserAgent.SELECT_EVENT);
		ev.addParameter(UserAgent.Single_Mode);
		myAgent.postGuiEvent(ev);
	}
	
	private void selectMultiMode(){
		GuiEvent ev = new GuiEvent(this,UserAgent.SELECT_EVENT);
		ev.addParameter(UserAgent.Multiple_Mode);
		myAgent.postGuiEvent(ev);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);
	}
}
