package SMA;



import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.DefaultListModel;

import Controller.LeapListener;
import Utilities.Cordinates;
import View.GameView;
import View.InstrumentSelectView;
import View.MenuView;
import View.MultiwaitRoom;
import View.RoomSelectView;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Gesture;



@SuppressWarnings("serial")
public class UserAgent extends GuiAgent{
	
	private PropertyChangeSupport changes;
	public static int TEXT_EVENT = 0;
	public static int SELECT_EVENT = 1;
	public static int SELECT_INSTRUMENT_EVENT = 2;
	public static int CREAT_ROOM_EVENT = 3;
	public static int JOINT_ROOM_EVENT = 4;
	public static int CONFIRM_ROOM_EVENT = 5;
	public static String Single_Mode = "100";
	public static String Multiple_Mode = "101";
	public static String return_Menu = "102";
	public static String instrument_Mode = "103";
	public static String roomselect_Mode = "104";
	public static String wait_Mode = "105";
	public static String piano = "0";
	public static String druma = "1";
	public static String guitar = "2";
	private MenuView menu_view;
	private GameView game_view;
	private InstrumentSelectView instrument_view;
	private RoomSelectView room_view;
	private MultiwaitRoom wait_view;
	private boolean single_mode = false;
	private boolean multiple_mode = false;
	private Cordinates pointer = new Cordinates();
	private Cordinates hand_1 = new Cordinates();
	private Cordinates hand_2 = new Cordinates();

	
	private DefaultListModel<String> dict = null;
	
	private LeapListener listener;
	private Controller controller;
	
	private ACLMessage messageDemande=new ACLMessage(ACLMessage.REQUEST);
	

	
	protected void setup() {
		super.setup();
		System.out.println(getLocalName()+"--> Installed");
		changes = new PropertyChangeSupport(this);
		menu_view = new MenuView(this);
		instrument_view = new InstrumentSelectView(this);
		game_view = new GameView(this);
		room_view = new RoomSelectView(this);
		wait_view = new MultiwaitRoom(this);
		menu_view.setVisible(true);
		//game_view.setVisible(true);
		
		listener = new LeapListener(this);
        controller = new Controller();
        
        controller.enableGesture( Gesture.Type.TYPE_KEY_TAP );
        controller.enableGesture( Gesture.Type.TYPE_CIRCLE);
        controller.enableGesture( Gesture.Type.TYPE_SWIPE);
        controller.enableGesture( Gesture.Type.TYPE_SCREEN_TAP);
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
		if(arg0.getType()==1){
			String messageMode = arg0.getParameter(0).toString();
			this.addBehaviour(new ModeSelectBehaviour(this, messageMode));
		}else if(arg0.getType()==2){
			this.addBehaviour(new InstrumentSelectBehaviour(this, encodageInstrument(arg0.getParameter(1).toString())));
			this.addBehaviour(new ModeSelectBehaviour(this, arg0.getParameter(0).toString()));
			
		}else if(arg0.getType()==0){
			
			messageDemande.setContent(arg0.getParameter(0).toString());	
			
			this.addBehaviour(new GetListGroupBehaviour(this,messageDemande));
			System.out.println("userAgent envoyer demande\n");

		}else if(arg0.getType()==3){
			messageDemande.setContent(arg0.getParameter(0).toString());	
			
			
			this.addBehaviour(new CreatGroupBehaviour(this,messageDemande));
			
		}
		
	}
	
	
	

	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		changes.addPropertyChangeListener(pcl);
    }
	
	public String encodageInstrument(String message){
		
		String instrument = message.split("/")[1];
		if(instrument.equals("gu.png")){
			return "1";
		}else if(instrument.equals("guitar.png")){
			return "2";
		}else{
			return "0";
		}
		
	}
	
	public void changeToRoomSelectView() {
		if(getDict()!=null){
			room_view.getList_room().setModel(getDict());
			room_view.setVisible(true);
			instrument_view.setVisible(false);
			//menu_view.setVisible(false);
		}
	}
		
	public void changeToRoomWaitView() {
			//if(getDict()!=null){
				//wait_view.getList_player().setModel(getDict());
				wait_view.setVisible(true);
				room_view.setVisible(false);
			//}
			System.out.println("ohhhhhhhhhh");
		
		
	}
	
	public void changeToInstrumentView(){
		instrument_view.setVisible(true);
		menu_view.setVisible(false);
	}
	public void changeToGameView(){
		//System.out.println("okkk");
		game_view.setVisible(true);
		wait_view.setVisible(false);
		instrument_view.setVisible(false);
	}
	
	public void changeToMenuView(){
		menu_view.setVisible(true);
		room_view.setVisible(false);
		wait_view.setVisible(false);
		game_view.setVisible(false);
		wait_view.setVisible(false);
		instrument_view.setVisible(false);		
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

	public GameView getGame_view() {
		return game_view;
	}


	public void setGame_view(GameView game_view) {
		this.game_view = game_view;
	}
	
	public RoomSelectView getRoom_view() {
		return room_view;
	}


	public void setRoom_view(RoomSelectView room_view) {
		this.room_view = room_view;
	}


	public DefaultListModel<String> getDict() {
		return dict;
	}


	public void setDict(DefaultListModel<String> dict) {
		this.dict = dict;
	}
}
