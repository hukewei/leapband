package fr.utc.leapband.view;

import jade.gui.GuiEvent;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.util.Timer;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;

import fr.utc.leapband.sma.user.UserAgent;
import fr.utc.leapband.utilities.Constance;
import fr.utc.leapband.utilities.CustomImgPanel;
import fr.utc.leapband.utilities.ImageTimerTask;
import fr.utc.leapband.utilities.RoundedBorder;

public class RoomSelectView extends JAgentFrame {
	private JList<String> list_room;
	private JButton create_room;
	private JButton enter_room;
	private JButton home;
	
	public RoomSelectView(UserAgent agent) {
		super(agent);
		this.setTitle("Room View");
		this.setSize(Constance.Windows_width, Constance.Windows_height);
		this.setLocationRelativeTo(null);
		
		CustomImgPanel imagePanel=new CustomImgPanel(Constance.Windows_width, Constance.Windows_height, "images/roomView.jpg");
		imagePanel.setLayout(null);
		this.add(imagePanel);
		
		//load model to list
		
		list_room = new JList<String>();
		
		//list_room.setModel(myAgent.getDict());
		list_room.setBorder(BorderFactory.createLoweredBevelBorder());
		list_room.setOpaque(false);	
		((JComponent) list_room.getCellRenderer()).setOpaque(false);
		list_room.setBackground(new Color(255,255,204,100));
		//list_room.setBackground(Color.LIGHT_GRAY);
		list_room.setBounds((int) (Constance.Windows_width*0.3),(int) (Constance.Windows_height*0.2),(int) (Constance.Windows_width*0.35),(int) (Constance.Windows_height*0.7));
		list_room.setFixedCellHeight(80);
		list_room.setFont(new Font("Serif", Font.PLAIN, 30));
		imagePanel.add(list_room);
		
		//this.setLayout(null);
		create_room = new JButton("create room");
		create_room.setBounds(950,150,300, 150);
		create_room.setFont(new Font("Lucida Calligraphy", Font.BOLD, 30));
		create_room.setForeground(Color.BLACK);
		create_room.setBorder(new RoundedBorder(new Color(224,224,224,100)));
		create_room.setContentAreaFilled( false );
		create_room.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
				create_room.setBorder(new RoundedBorder(new Color(224,224,224,100)));
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				create_room.setBorder(new RoundedBorder(new Color(224,224,224,150)));
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				System.out.println("mouse exit");
				changeCursorImage("images/cursor.png");
				if (click_task != null) {
					click_task.cancel();
					click_task = null;
				}
				create_room.setBorder(new RoundedBorder(new Color(224,224,224,100)));
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				System.out.println("mouse entered");
				click_task = new Timer();
				click_task.schedule(new ImageTimerTask(myAgent),0,Constance.click_delay/12);
				click_task.schedule( 
				        new java.util.TimerTask() {
				            @Override
				            public void run() {
				            	GuiEvent ev = new GuiEvent(this,UserAgent.CREATE_ROOM_EVENT);
								ev.addParameter(Constance.roomselect_Mode);
								myAgent.postGuiEvent(ev);
				            }
				        }, 
				        Constance.click_delay 
				);
				create_room.setBorder(new RoundedBorder(new Color(224,224,224,50)));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				create_room.setBorder(new RoundedBorder(new Color(224,224,224,150)));
				
			}
		});
		imagePanel.add(create_room);
		enter_room = new JButton("enter room");
		enter_room.setBounds(950,350,300, 150);
		enter_room.setFont(new Font("Lucida Calligraphy", Font.BOLD, 30));
		enter_room.setForeground(Color.BLACK);
		enter_room.setBorder(new RoundedBorder(new Color(224,224,224,100)));
		enter_room.setContentAreaFilled( false );
		enter_room.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				enter_room.setBorder(new RoundedBorder(new Color(224,224,224,100)));
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				enter_room.setBorder(new RoundedBorder(new Color(224,224,224,150)));
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				enter_room.setBorder(new RoundedBorder(new Color(224,224,224,100)));
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
				            	String room_name = list_room.getSelectedValue();
								if (room_name != null) {
									GuiEvent ev = new GuiEvent(this,UserAgent.JOINT_ROOM_EVENT);
									ev.addParameter(room_name);
									myAgent.postGuiEvent(ev);
								}
				            }
				        }, 
				        Constance.click_delay 
				);
				enter_room.setBorder(new RoundedBorder(new Color(224,224,224,50)));
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				enter_room.setBorder(new RoundedBorder(new Color(224,224,224,150)));
				
			}
		});
		imagePanel.add(enter_room);
		home = new JButton();
		Icon icon = new ImageIcon("images/home.png");
		home.setBounds(0,0,100,100);
		home.setIcon(icon);
		//home.setContentAreaFilled(false);
		
		home.addMouseListener(new HomeMouseListener(this,home));
		home.setContentAreaFilled(false);
		home.setOpaque(false);
		imagePanel.add(home);
		
		/*create_room.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GuiEvent ev = new GuiEvent(this,UserAgent.CREATE_ROOM_EVENT);
				ev.addParameter(Constance.roomselect_Mode);
				myAgent.postGuiEvent(ev);
				
			}
		});*/
		
		/*enter_room.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String room_name = list_room.getSelectedValue();
				if (room_name != null) {
					GuiEvent ev = new GuiEvent(this,UserAgent.JOINT_ROOM_EVENT);
					ev.addParameter(room_name);
					myAgent.postGuiEvent(ev);
				}
				
			}
		});*/
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (isVisible()) {
			if (evt.getPropertyName().equals("swipe")) {
				int current_index = list_room.getSelectedIndex();
				if (current_index > -1 ) {
					if ((String)evt.getNewValue() == "UP") {
						current_index--;
					} else if ((String)evt.getNewValue() == "DOWN") {
						current_index++;
					}
				}
				list_room.setSelectedIndex(current_index);
				
			}
		}
	}
	
	public JList<String> getList_room() {
		return list_room;
	}

	public void setList_room(JList<String> list_room) {
		this.list_room = list_room;
	}
	
	

}
