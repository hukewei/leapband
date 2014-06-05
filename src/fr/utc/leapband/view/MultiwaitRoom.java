package fr.utc.leapband.view;




import jade.gui.GuiEvent;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;

import fr.utc.leapband.sma.user.UserAgent;
import fr.utc.leapband.utilities.Constance;
import fr.utc.leapband.utilities.CustomImgPanel;
import fr.utc.leapband.utilities.ImageTimerTask;
import fr.utc.leapband.utilities.RoundedBorder;




public class MultiwaitRoom extends JAgentFrame {
	private JList<String> list_player;
	private JButton start;
	private JButton exit;
	
	public MultiwaitRoom(UserAgent agent) {
		super(agent);
		this.setTitle("Waiting View");
		this.setSize(Constance.Windows_width, Constance.Windows_height);
		this.setLocationRelativeTo(null);
		
		CustomImgPanel imagePanel=new CustomImgPanel(Constance.Windows_width, Constance.Windows_height);
		imagePanel.setImagePath("images/roomBg.png");
		imagePanel.setLayout(null);
		this.add(imagePanel);
		
		//load model to list
		list_player = new JList<String>();
		//list_room.setModel(myAgent.getDict());
		list_player.setBorder(new RoundedBorder(new Color(100,100,100,100)));
		list_player.setOpaque(false);	
		((JComponent) list_player.getCellRenderer()).setOpaque(false);
		//list_player.setBackground(new Color(255,255,204,100));
		list_player.setBounds((int) (Constance.Windows_width*0.3),(int) (Constance.Windows_height*0.2),(int) (Constance.Windows_width*0.35),(int) (Constance.Windows_height*0.7));
		list_player.setFixedCellHeight(80);
		//list_player.setBounds(250,150,500,500);
		list_player.setFont(new Font("Serif", Font.PLAIN, 30));
		imagePanel.add(list_player);
		//this.setLayout(null);
		
		start = new JButton("Start");
		start.setBounds(950,(int) (Constance.Windows_height*0.2),300, 150);
		start.setFont(new Font("Lucida Calligraphy", Font.BOLD, 30));
		start.setForeground(Color.BLACK);
		start.setBorder(new RoundedBorder(new Color(224,224,224,100)));
		start.setContentAreaFilled( false );
		start.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {		
				start.setBorder(new RoundedBorder(new Color(224,224,224,100)));
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				start.setBorder(new RoundedBorder(new Color(224,224,224,150)));
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				start.setBorder(new RoundedBorder(new Color(224,224,224,100)));
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
				            	GuiEvent ev = new GuiEvent(this,UserAgent.CONFIRM_ROOM_EVENT);
								ev.addParameter(UserAgent.wait_Mode);
								myAgent.postGuiEvent(ev);
				            }
				        }, 
				        Constance.click_delay 
				);
				start.setBorder(new RoundedBorder(new Color(224,224,224,50)));
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				start.setBorder(new RoundedBorder(new Color(224,224,224,150)));
				
			}
		});
		imagePanel.add(start);
		exit = new JButton("Exit");
		exit.setBounds(950,350,300, 150);
		exit.setFont(new Font("Lucida Calligraphy", Font.BOLD, 30));
		exit.setForeground(Color.BLACK);
		exit.setBorder(new RoundedBorder(new Color(224,224,224,100)));
		exit.setContentAreaFilled( false );
		exit.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {			
				exit.setBorder(new RoundedBorder(new Color(224,224,224,100)));
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				exit.setBorder(new RoundedBorder(new Color(224,224,224,150)));
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				exit.setBorder(new RoundedBorder(new Color(224,224,224,100)));
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
				            	GuiEvent ev = new GuiEvent(this,UserAgent.EXIT_ROOM_EVENT);
								myAgent.postGuiEvent(ev);
				            }
				        }, 
				        Constance.click_delay 
				);
				exit.setBorder(new RoundedBorder(new Color(224,224,224,50)));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				exit.setBorder(new RoundedBorder(new Color(224,224,224,150)));
				
			}
		});
		imagePanel.add(exit);
		
	
	}
	
	public JButton getStartButton(){
		return start;
	}
	
	public JList<String> getList_player() {
		return list_player;
	}

	public void setList_player(JList<String> list_player) {
		this.list_player = list_player;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (isVisible()) {
			if (evt.getPropertyName().equals("swipe")) {
				int current_index = list_player.getSelectedIndex();
				if (current_index > -1 ) {
					if ((String)evt.getNewValue() == "UP") {
						current_index--;
					} else if ((String)evt.getNewValue() == "DOWN") {
						current_index++;
					}
				}
				list_player.setSelectedIndex(current_index);
				
			}
		}
	}
}
