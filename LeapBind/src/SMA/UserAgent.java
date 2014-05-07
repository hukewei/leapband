package SMA;

import jade.gui.GuiAgent;
import jade.gui.GuiEvent;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

import javax.swing.DefaultListModel;



import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Controller;

import Controller.LeapListener;
import Utilities.Cordinates;
import View.GameView;
import View.InstrumentView;
import View.MenuView;
import View.RoomSelectView;



@SuppressWarnings("serial")
public class UserAgent extends GuiAgent{
	
	private PropertyChangeSupport changes;
	public static int TEXT_EVENT = 0;
	public static int SELECT_EVENT = 1;
	public static String Single_Mode = "100";
	public static String Multiple_Mode = "101";
	public static String return_Menu = "102";
	public static String instrument_Mode = "103";
	private MenuView menu_view;
	private GameView game_view;
	private InstrumentView instrument_view;
	private RoomSelectView room_view;
	private boolean single_mode = false;
	private boolean multiple_mode = false;
	private Cordinates pointer = new Cordinates();
	private Cordinates hand_1 = new Cordinates();
	private Cordinates hand_2 = new Cordinates();
	
	private DefaultListModel<String> dict = null;
	
	private LeapListener listener;
	private Controller controller;
	

	
	protected void setup() {
		super.setup();
		System.out.println(getLocalName()+"--> Installed");
		changes = new PropertyChangeSupport(this);
		menu_view = new MenuView(this);
		instrument_view = new InstrumentView(this);
		game_view = new GameView(this);
		room_view = new RoomSelectView(this);
		menu_view.setVisible(true);
		//game_view.setVisible(true);
		
		listener = new LeapListener(this);
        controller = new Controller();
        
        //controller.enableGesture( Gesture.Type.TYPE_KEY_TAP );
        controller.enableGesture( Gesture.Type.TYPE_CIRCLE);
        controller.enableGesture( Gesture.Type.TYPE_SWIPE);
        //listener.setDebug(true);
        listener.setClickType(1);
        listener.setCalibratedScren(true);
        controller.addListener(listener);
        
        System.out.println("Press Enter to quit...");
//        try {
//            System.in.read();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // Remove the listener when done
        //controller.removeListener(listener);
	}
	
	
	@Override
	protected void onGuiEvent(GuiEvent arg0) {
		String message = arg0.getParameter(0).toString();
		this.addBehaviour(new ModeSelectBehaviour(this, message));
	}

	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		changes.addPropertyChangeListener(pcl);
    }
	
	public void changeToRoomSelectView() {
		room_view.setVisible(true);
		menu_view.setVisible(false);
	}
	
	public void changeToInstrumentView(){
		instrument_view.setVisible(true);
		menu_view.setVisible(false);
	}
	public void changeToGameView(){
		game_view.setVisible(true);
		instrument_view.setVisible(false);
	}
	
	public void changeToMeunuView(){
		menu_view.setVisible(true);
		room_view.setVisible(false);
		game_view.setVisible(false);
		
		
		
	}
	
	public boolean isSingle_mode() {
		return single_mode;
	}
	
	public void setSingle_mode(boolean single_mode) {
		this.single_mode = single_mode;
		this.multiple_mode = !single_mode;
	}

	public boolean isMultiple_mode() {
		return multiple_mode;
	}

	public void setMultiple_mode(boolean multiple_mode) {
		this.multiple_mode = multiple_mode;
		this.single_mode = !multiple_mode;
	}
	
	public DefaultListModel<String> getDictModel(){
		if (dict == null) {
			dict = new DefaultListModel<String>();
			dict.addElement("room1");
			dict.addElement("room2");
			dict.addElement("room3");
			dict.addElement("room4");
		}
		return dict;
	}
	
	public void updatePosition(float x, float y) {
		pointer.x = x;
		pointer.y = y;
		changes.firePropertyChange("pos", null, pointer);
	}
	
	public void updateHands(float x_1, float y_1, float x_2, float y_2, float z_1, float z_2) {
		hand_1.x = x_1;
		hand_1.y = y_1;
		hand_2.x = x_2;
		hand_2.y = y_2;
		hand_1.z = z_1;
		hand_2.z = z_2;
		changes.firePropertyChange("hand1", null, hand_1);
		changes.firePropertyChange("hand2", null, hand_2);
	}
	
	public void doSwipe(String direction) {
		changes.firePropertyChange("swipe", null, direction);
	}


	
	

}
