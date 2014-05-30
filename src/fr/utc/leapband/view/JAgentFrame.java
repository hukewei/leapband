package fr.utc.leapband.view;



import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;

import javax.swing.JFrame;

import fr.utc.leapband.sma.user.UserAgent;


public abstract class JAgentFrame extends JFrame implements PropertyChangeListener{
	UserAgent myAgent;
	Timer click_task = null;
	public JAgentFrame(UserAgent agent) {
		super();
		myAgent = agent;
		myAgent.addPropertyChangeListener(this);
	}
	@Override
	abstract public void propertyChange(PropertyChangeEvent evt);
	
	public void hideCursor() {
		Image image = Toolkit.getDefaultToolkit().createImage(
				new MemoryImageSource(0, 0, new int[0], 0, 0));
		this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(image,
				new Point(0, 0), null));
	}
	
	public void changeCursorImage(String new_image) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursorImage = toolkit.getImage(new_image);
		Point cursorHotSpot = new Point((int)myAgent.pointer.x,(int)myAgent.pointer.y);
		Cursor customCursor = toolkit.createCustomCursor(cursorImage, cursorHotSpot, "Cursor");
		setCursor(customCursor);
	}

}
