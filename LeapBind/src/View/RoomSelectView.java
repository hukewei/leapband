package View;

import jade.gui.GuiEvent;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;

import SMA.UserAgent;
import Utilities.Constance;
import Utilities.CustomImgPanel;
import Utilities.RoundedBorder;

public class RoomSelectView extends JAgentFrame {
	private JList<String> list_room;
	private JButton create_room;
	private JButton enter_room;
	
	@SuppressWarnings("unchecked")
	public RoomSelectView(UserAgent agent) {
		super(agent);
		this.setTitle("Room View");
		this.setSize(Constance.Windows_width, Constance.Windows_height);
		this.setLocationRelativeTo(null);
		
		CustomImgPanel imagePanel=new CustomImgPanel(Constance.Windows_width, Constance.Windows_height, "src/roomView.jpg");
		imagePanel.setLayout(null);
		this.add(imagePanel);
		
		//load model to list
		list_room = new JList<String>();
		//list_room.setModel(myAgent.getDict());
		list_room.setBorder(BorderFactory.createLoweredBevelBorder());
		list_room.setOpaque(false);
		list_room.setBackground(new Color(255,255,204,100));
		
		//list_room.setBackground(Color.LIGHT_GRAY);
		list_room.setBounds(300,150,500,500);
		list_room.setFixedCellHeight(80);
		list_room.setFont(new Font("Serif", Font.PLAIN, 30));
		imagePanel.add(list_room);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursorImage = toolkit.getImage("src/cursor.png");
		Point cursorHotSpot = new Point(0,0);
		Cursor customCursor = toolkit.createCustomCursor(cursorImage, cursorHotSpot, "Cursor");
		this.setCursor(customCursor);
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
				GuiEvent ev = new GuiEvent(this,UserAgent.CREATE_ROOM_EVENT);
				ev.addParameter(Constance.roomselect_Mode);
				myAgent.postGuiEvent(ev);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				create_room.setBorder(new RoundedBorder(new Color(224,224,224,150)));
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				create_room.setBorder(new RoundedBorder(new Color(224,224,224,100)));
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
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
				String room_name = list_room.getSelectedValue();
				if (room_name != null) {
					GuiEvent ev = new GuiEvent(this,UserAgent.JOINT_ROOM_EVENT);
					ev.addParameter(room_name);
					myAgent.postGuiEvent(ev);
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				enter_room.setBorder(new RoundedBorder(new Color(224,224,224,150)));
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				enter_room.setBorder(new RoundedBorder(new Color(224,224,224,100)));
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				enter_room.setBorder(new RoundedBorder(new Color(224,224,224,50)));
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				enter_room.setBorder(new RoundedBorder(new Color(224,224,224,150)));
				
			}
		});
		imagePanel.add(enter_room);
		JButton home = new JButton();
		Icon icon = new ImageIcon("src/home.png");
		home.setBounds(0,0,100,100);
		home.setIcon(icon);
		home.setContentAreaFilled(false);
		home.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				GuiEvent ev = new GuiEvent(this,UserAgent.SELECT_EVENT);
				ev.addParameter(UserAgent.return_Menu);
				myAgent.postGuiEvent(ev);
			}
		});
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
	
	public JList<String> getList_room() {
		return list_room;
	}

	public void setList_room(JList<String> list_room) {
		this.list_room = list_room;
	}
	
	

}
