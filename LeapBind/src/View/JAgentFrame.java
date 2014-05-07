package View;



import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;

import SMA.UserAgent;


abstract class JAgentFrame extends JFrame implements PropertyChangeListener{
	UserAgent myAgent;
	public JAgentFrame(UserAgent agent) {
		super();
		myAgent = agent;
		myAgent.addPropertyChangeListener(this);
	}
	@Override
	abstract public void propertyChange(PropertyChangeEvent evt);

}
