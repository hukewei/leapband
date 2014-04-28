import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

class Cordinates {
	float x;
	float y;
}


public class SimpleModel {
	private PropertyChangeSupport changes = new PropertyChangeSupport(this);
	private static SimpleModel model = null;
	
	private Cordinates pointer = new Cordinates();
	
	private Cordinates hand_1 = new Cordinates();
	private Cordinates hand_2 = new Cordinates();
		
	public static SimpleModel getInstance() {
		if (model == null) {
			model = new SimpleModel();
		}
		return model;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		changes.addPropertyChangeListener(pcl);
	}
	
	public void updatePosition(float x, float y) {
		pointer.x = x;
		pointer.y = y;
		changes.firePropertyChange("pos", null, pointer);
	}
	
	public void updateHands(float x_1, float y_1, float x_2, float y_2) {
		hand_1.x = x_1;
		hand_1.y = y_1;
		hand_2.x = x_2;
		hand_2.y = y_2;
		changes.firePropertyChange("hand1", null, hand_1);
		changes.firePropertyChange("hand2", null, hand_2);
	}

}
