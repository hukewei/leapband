package SMA;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

import Controller.LeapListener;
import View.GameView;
import View.MenuView;
import View.RoomSelectView;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Gesture;

@SuppressWarnings("serial")
public class UserAgent extends GuiAgent{
	private PropertyChangeSupport pcs;
	public static int TEXT_EVENT = 0;
	
	protected void setup() {
		super.setup();
		System.out.println(getLocalName()+"--> Installed");
		pcs = new PropertyChangeSupport(this);
		MenuView menu_view = new MenuView();
		GameView game_view = new GameView();
		RoomSelectView room_view = new RoomSelectView();
		menu_view.setVisible(true);
		
		LeapListener listener = new LeapListener();
        Controller controller = new Controller();
        //controller.enableGesture( Gesture.Type.TYPE_KEY_TAP );
        controller.enableGesture( Gesture.Type.TYPE_CIRCLE);
        controller.enableGesture( Gesture.Type.TYPE_SWIPE);
        listener.setDebug(true);
        listener.setClickType(1);
        listener.setCalibratedScren(true);
        controller.addListener(listener);
        System.out.println("Press Enter to quit...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Remove the sample listener when done
        controller.removeListener(listener);
	}
	
	
	@Override
	protected void onGuiEvent(GuiEvent arg0) {
		// TODO Auto-generated method stub
		String message = arg0.getParameter(0).toString();
	}
	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		pcs.addPropertyChangeListener(pcl);
    }

	public void firePropertyChange(String msg) {
		pcs.firePropertyChange("chat", null, msg); 
	}

}
