package View;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JPanel;


public class ControlPane extends JPanel{
	// personnel cursor
	public ControlPane() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursorImage = toolkit.getImage("src/cursor.png");
		Point cursorHotSpot = new Point(0,0);
		Cursor customCursor = toolkit.createCustomCursor(cursorImage, cursorHotSpot, "Cursor");
		this.setCursor(customCursor);
		this.setLayout(null);
		JButton start = new JButton("start");
		start.setBounds(0, 50,200, 150);
		this.add(start);
		JButton end = new JButton("end");
		end.setBounds(300, 50,200, 150);
		this.add(end);
	}
	
			
}
