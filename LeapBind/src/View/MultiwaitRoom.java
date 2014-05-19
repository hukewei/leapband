package View;




import jade.gui.GuiEvent;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;

import SMA.user.UserAgent;
import Utilities.Constance;




public class MultiwaitRoom extends JAgentFrame {
	private JList<String> list_player;
	private JButton start;
	private JButton exit;
	
	@SuppressWarnings("unchecked")
	public MultiwaitRoom(UserAgent agent) {
		super(agent);
		this.setTitle("Waiting View");
		this.setSize(Constance.Windows_width, Constance.Windows_height);
		this.setLocationRelativeTo(null);
		//load model to list
		list_player = new JList<String>();
		//list_room.setModel(myAgent.getDict());
		list_player.setBorder(BorderFactory.createLoweredBevelBorder());
		list_player.setBackground(Color.LIGHT_GRAY);
		list_player.setBounds(250,150,500,500);
		list_player.setFixedCellHeight(80);
		list_player.setFont(new Font("Serif", Font.PLAIN, 30));
		this.add(list_player);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursorImage = toolkit.getImage("src/images/cursor.png");
		Point cursorHotSpot = new Point(0,0);
		Cursor customCursor = toolkit.createCustomCursor(cursorImage, cursorHotSpot, "Cursor");
		this.setCursor(customCursor);
		this.setLayout(null);
		start = new JButton("Start");
		start.setBounds(850, 200,300, 150);
		this.add(start);
		exit = new JButton("Exit");
		exit.setBounds(850, 450,300, 150);
		this.add(exit);
		JButton home = new JButton();
		Icon icon = new ImageIcon("src/images/home.png");
		home.setBounds(140,50,100,100);
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
		this.add(home);
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GuiEvent ev = new GuiEvent(this,UserAgent.CONFIRM_ROOM_EVENT);
				ev.addParameter(UserAgent.wait_Mode);
				myAgent.postGuiEvent(ev);
				
			}
		});
		
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GuiEvent ev = new GuiEvent(this,UserAgent.EXIT_ROOM_EVENT);
				//ev.addParameter(UserAgent.instrument_Mode);
				myAgent.postGuiEvent(ev);
				
			}
		});
	}

	public JList<String> getList_player() {
		return list_player;
	}

	public void setList_player(JList<String> list_player) {
		this.list_player = list_player;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
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
