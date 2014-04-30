package View;




import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JList;

import SMA.UserAgent;
import Utilities.Constance;



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
		//load model to list
		list_room = new JList<String>();
		list_room.setModel(myAgent.getDictModel());
		list_room.setBorder(BorderFactory.createLoweredBevelBorder());
		list_room.setBackground(Color.yellow);
		list_room.setBounds(0,50,300, 300);
		this.add(list_room);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursorImage = toolkit.getImage("src/cursor.png");
		Point cursorHotSpot = new Point(0,0);
		Cursor customCursor = toolkit.createCustomCursor(cursorImage, cursorHotSpot, "Cursor");
		this.setCursor(customCursor);
		this.setLayout(null);
		create_room = new JButton("create room");
		create_room.setBounds(300, 50,200, 150);
		this.add(create_room);
		enter_room = new JButton("enter room");
		enter_room.setBounds(600, 50,200, 150);
		this.add(enter_room);
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

}
