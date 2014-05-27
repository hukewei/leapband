package View;

//import musicview;
import jade.gui.GuiEvent;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.IOException;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicSliderUI;

import SMA.user.UserAgent;
import Utilities.Constance;
import Utilities.OvalBorder;


public class ControlPane extends JPanel{
	// personnel cursor
	private UserAgent myAgent;
	private Boolean propietaire = true;
	private int width;
	private int height;
	JLabel player;
	JLabel rewind;
	JLabel forward;
	Timer click_task = null;

	private JLabel home;
	private JLabel play;
	private JLabel volume;
	private JButton music;
	private double current_rotation = 0;
	
	boolean isPlay=false;
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
		this.setBackground(new Color(255,255,204));
		//this.setBackground(Color.BLACK);
		
		
		home = new JLabel(new ImageIcon("src/images/home.png"));
		
		//home.setPreferredSize(new Dimension(100,100));
		home.setBounds((int) (width*0.01),(int) (height*0.04),100,100);
		//home.setBackground(Color.WHITE);
		//home.setOpaque(false);
		
		home.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseExited(MouseEvent e) {
				home.setBorder(null);
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
				home.setBorder(new OvalBorder(home.getWidth(),home.getHeight(),new Color(153,153,255)));
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
		music = new JButton("Choose a music");
		music.setFont(new Font("Chalkboard", Font.BOLD, 30));
		//music.setPreferredSize(new Dimension(400,100));

		music.setBounds((int) (width*0.3), (int) (height*0.05), 400, 100);
		music.setBackground(Color.WHITE);
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
			public void mouseEntered(MouseEvent arg0) {
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				click_task = new Timer();
				click_task.schedule( 
				        new java.util.TimerTask() {
				            @Override
				            public void run() {
				            	new musicview(new File("src/songs/"),myAgent);
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
		
		
		play = new JLabel(new ImageIcon("src/images/play.png"));
		play.setBounds((int) (width*0.7), (int) (height*0.05), 100, 100);
		rewind=new JLabel(new ImageIcon("src/images/rewind.png"));
		rewind.setBounds((int) (width*0.62), (int) (height*0.05), 100, 100);
		forward=new JLabel(new ImageIcon("src/images/fast_forward.png"));
		forward.setBounds((int) (width*0.78), (int) (height*0.05), 100, 100);
		
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
			    				
			            		play.setIcon(new ImageIcon("src/images/pause.png"));
			            		isPlay=true;
			            		
			            	}else{
			            		play.setIcon(new ImageIcon("src/images/play.png"));
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
				    			ev.addParameter("forward");
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
				    			ev.addParameter("rewind");
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
	
		
		volume = new JLabel(new ImageIcon("src/images/volume.png"));
		volume.setBounds((int) (width*0.9), (int) (height*0.05), 100, 100);
		//volume.setPreferredSize(new Dimension(100,100));
		volume.addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				int notches = e.getWheelRotation();
				if (notches < 0) {
					current_rotation += 30;
					System.out.println("up");
			       } else {
			    	   current_rotation -= 30;
			    	   System.out.println("down");
			       }
				volume.setIcon(new RotatedIcon(new ImageIcon("src/images/volume.png"), current_rotation));
			}
		});
		
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
	public Boolean is_proprietaire(){
		return propietaire;
	}
	public void set_proprietaire(boolean b){
		propietaire = b;
	}
	
	   
	
}
