package fr.utc.leapband.view;

//import musicview;
import jade.gui.GuiEvent;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import fr.utc.leapband.sma.user.UserAgent;
import fr.utc.leapband.utilities.Constance;
import fr.utc.leapband.utilities.OvalBorder;
import fr.utc.leapband.utilities.RoundedBorder;


@SuppressWarnings("serial")
public class ControlPane extends JPanel {
	// personnel cursor
	private UserAgent myAgent;
	private Boolean propietaire = true;
	private int width;
	private int height;
	public JLabel player;
	JLabel rewind;
	JLabel forward;
	Timer click_task = null;

	private JLabel home;
	private JLabel play;
	private JLabel volume;
	private JButton music;
	
	boolean isPlay=false;
	public ControlPane(UserAgent agent) {
		this.width=Constance.Windows_width;
		this.height=Constance.Windows_height;
		this.myAgent=agent;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursorImage = toolkit.getImage("images/cursor.png");
		Point cursorHotSpot = new Point(0,0);
		Cursor customCursor = toolkit.createCustomCursor(cursorImage, cursorHotSpot, "Cursor");
		this.setCursor(customCursor);
		this.setLayout(null);
		this.setBackground(new Color(255,255,204));
		//this.setBackground(Color.BLACK);
		
		
		home = new JLabel(new ImageIcon("images/home.png"));
		
		//home.setPreferredSize(new Dimension(100,100));
		home.setBounds((int) (width*0.01),(int) (height*0.02),100,100);
		//home.setBackground(Color.WHITE);
		//home.setOpaque(false);
		
		home.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseExited(MouseEvent e) {
				home.setBorder(null);
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Image cursorImage = toolkit.getImage("images/cursor.png");
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
				home.setBorder(new OvalBorder(home.getWidth(),home.getHeight(),new Color(153,153,255)));
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				click_task = new Timer();
				click_task.schedule( 
				        new java.util.TimerTask() {
				            @Override
				            public void run() {
				            	play.setIcon(new ImageIcon("images/play.png"));
			            		isPlay=false;
			            		GuiEvent ev = null;
			            		if (!myAgent.isMultipleMode() || myAgent.isHost()) {
			            			ev = new GuiEvent(this,UserAgent.CONTROL_MUSIC_EVENT);
			            			ev.addParameter(isPlay);
			            			myAgent.postGuiEvent(ev);
			            		}
				    			ev = new GuiEvent(this,UserAgent.SELECT_EVENT);
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
		player = new JLabel(new ImageIcon("images/person2.jpg"));
		//player.setPreferredSize(new Dimension(100,100));
		player.setBounds((int) (width*0.2), (int) (height*0.02), 100, 100);
		Border border=BorderFactory.createLineBorder(Color.BLACK, 5);

		player.setBorder(border);
		
		
		
		music = new JButton("Choose a music");
		music.setFont(new Font("Chalkboard", Font.BOLD, 30));
		//music.setPreferredSize(new Dimension(400,100));

		music.setBounds((int) (width*0.3), (int) (height*0.019), 400, 100);
		//music.setBackground(Color.WHITE);
		music.setBorder(new RoundedBorder(new Color(0,128,255,100)));
		music.setContentAreaFilled( false );
		//music.setBorder(border);
		/*music.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new musicview(new File("src/songs/"),myAgent);
			}
		});*/
		
		music.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Image cursorImage = toolkit.getImage("images/cursor.png");
				Point cursorHotSpot = new Point(0,0);
				Cursor customCursor = toolkit.createCustomCursor(cursorImage, cursorHotSpot, "Cursor");
				setCursor(customCursor);
				if (click_task != null) {
					click_task.cancel();
					click_task = null;
				}
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				click_task = new Timer();
				click_task.schedule( 
				        new java.util.TimerTask() {
				            @Override
				            public void run() {
				            	new MusicSelectView(myAgent);
				            }
				        }, 
				        Constance.click_delay 
				);
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		if(!is_proprietaire()){
			music.setEnabled(false);
		}
		
		
		play = new JLabel(new ImageIcon("images/play.png"));
		play.setBounds((int) (width*0.7), (int) (height*0.02), 100, 100);
		rewind=new JLabel(new ImageIcon("images/rewind.png"));
		rewind.setBounds((int) (width*0.62), (int) (height*0.02), 100, 100);
		forward=new JLabel(new ImageIcon("images/fast_forward.png"));
		forward.setBounds((int) (width*0.78), (int) (height*0.02), 100, 100);
		
		play.addMouseListener(new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent arg0) {
			
			
		}
		
		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseExited(MouseEvent arg0) {
			 
			 play.setBorder(null);		
			 Toolkit toolkit = Toolkit.getDefaultToolkit();
				Image cursorImage = toolkit.getImage("images/cursor.png");
				Point cursorHotSpot = new Point(0,0);
				Cursor customCursor = toolkit.createCustomCursor(cursorImage, cursorHotSpot, "Cursor");
				setCursor(customCursor);
				if (click_task != null) {
					click_task.cancel();
					click_task = null;
				}
		}
		
		@Override
		public void mouseEntered(MouseEvent arg0) {
            play.setBorder(new OvalBorder(play.getWidth(), play.getHeight(), new Color(153,153,255)));
			//play.setBorder(BorderFactory.createBorder(Color.blue));
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			click_task = new Timer();
			click_task.schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			            	if(!isPlay){
			    				
			            		play.setIcon(new ImageIcon("images/pause.png"));
			            		isPlay=true;
			            		
			            	}else{
			            		play.setIcon(new ImageIcon("images/play.png"));
			            		isPlay=false;
			            	}
			    			GuiEvent ev = new GuiEvent(this,UserAgent.CONTROL_MUSIC_EVENT);
			    			ev.addParameter(isPlay);
			    			myAgent.postGuiEvent(ev);
			            }
			        }, 
			        Constance.click_delay 
			);
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	});
		
		forward.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				 
				forward.setBorder(null);		
				 Toolkit toolkit = Toolkit.getDefaultToolkit();
					Image cursorImage = toolkit.getImage("images/cursor.png");
					Point cursorHotSpot = new Point(0,0);
					Cursor customCursor = toolkit.createCustomCursor(cursorImage, cursorHotSpot, "Cursor");
					setCursor(customCursor);
					if (click_task != null) {
						click_task.cancel();
						click_task = null;
					}
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
	            forward.setBorder(new OvalBorder(forward.getWidth(), forward.getHeight(), new Color(153,153,255)));
				//play.setBorder(BorderFactory.createBorder(Color.blue));	
	            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	            click_task = new Timer();
				click_task.schedule( 
				        new java.util.TimerTask() {
				            @Override
				            public void run() {
				           
				    			GuiEvent ev = new GuiEvent(this,UserAgent.CONTROL_MUSIC_RHYTHM);
				    			ev.addParameter(Constance.Forward);
				    			myAgent.postGuiEvent(ev);
				            }
				        }, 
				        Constance.click_delay 
				);
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		rewind.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				 
				 rewind.setBorder(null);		
				 Toolkit toolkit = Toolkit.getDefaultToolkit();
					Image cursorImage = toolkit.getImage("images/cursor.png");
					Point cursorHotSpot = new Point(0,0);
					Cursor customCursor = toolkit.createCustomCursor(cursorImage, cursorHotSpot, "Cursor");
					setCursor(customCursor);
					if (click_task != null) {
						click_task.cancel();
						click_task = null;
					}
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
	            rewind.setBorder(new OvalBorder(rewind.getWidth(), rewind.getHeight(), new Color(153,153,255)));
				//play.setBorder(BorderFactory.createBorder(Color.blue));	
	            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	            click_task = new Timer();
				click_task.schedule( 
				        new java.util.TimerTask() {
				            @Override
				            public void run() {
				            	
				    			GuiEvent ev = new GuiEvent(this,UserAgent.CONTROL_MUSIC_RHYTHM);
				    			ev.addParameter(Constance.Rewind);
				    			myAgent.postGuiEvent(ev);
				    			
				            }
				        }, 
				        Constance.click_delay 
				);
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	
		
		volume = new JLabel(new ImageIcon("images/volume.png"));
		volume.setBounds((int) (width*0.9), (int) (height*0.02), 100, 100);
		
		this.add(home);
		this.add(player);
		this.add(music);
		this.add(play);
		this.add(forward);
		this.add(rewind);
		this.add(volume);
		
		
		//this.add(userId);
		//this.add(play);
	}
	public JLabel getVolume() {
		return volume;
	}
	public Boolean is_proprietaire(){
		return propietaire;
	}
	public void set_proprietaire(boolean b){
		propietaire = b;
	}
	
	
	
	   
	
}
