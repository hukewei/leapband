package View;



import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;

import SMA.UserAgent;


public abstract class JAgentFrame extends JFrame implements PropertyChangeListener{
	UserAgent myAgent;
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

}
