package SMA.user;



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
import javax.swing.JFrame;

import Controller.LeapListener;
import Utilities.Constance;
import Utilities.Cordinates;
import View.GameView;
import View.InstrumentSelectView;
import View.MenuView;
import View.MultiwaitRoom;
import View.RoomSelectView;
import View.JAgentFrame;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Gesture;



@SuppressWarnings("serial")
public class UserAgent extends GuiAgent{
	
	private PropertyChangeSupport changes;
	public static int TEXT_EVENT = 0;
	public static int SELECT_EVENT = 1;
	public static int SELECT_INSTRUMENT_EVENT = 2;
	public static int CREATE_ROOM_EVENT = 3;
	public static int JOINT_ROOM_EVENT = 4;
	public static int CONFIRM_ROOM_EVENT = 5;
	public static int EXIT_ROOM_EVENT = 6;
	public static String Single_Mode = "100";
	public static String Multiple_Mode = "101";
	public static String return_Menu = "102";
	public static String instrument_Mode = "103";
	public static String wait_Mode = "105";
	public static String Exit_Room_Mode = "123";
	public static String piano = "0";
	public static String drum = "1";
	public static String guitar = "2";
	private MenuView menu_view;

	private GameView game_view;
	private InstrumentSelectView instrument_view;
	private RoomSelectView room_view;
	private MultiwaitRoom wait_view;
	private boolean single_mode = false;
	private boolean multiple_mode = false;
	public Cordinates pointer = new Cordinates();
	private Cordinates hand_1 = new Cordinates();
	private Cordinates hand_2 = new Cordinates();
	private AID server_name = null;
	private String selected_instrument = null;
	private String current_room_id = null; //conversation id if in a group
	private JAgentFrame current_frame = null;

	
	private DefaultListModel<String> dict = null;
	private DefaultListModel<String> dict_list_player = null;

	
	private LeapListener listener;
	private Controller controller;
		

	
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
		changeCurrentViewTo(menu_view);
		//game_view.setVisible(true);
		
		listener = new LeapListener(this);
        controller = new Controller();
        
        controller.enableGesture( Gesture.Type.TYPE_KEY_TAP );
        //controller.enableGesture( Gesture.Type.TYPE_CIRCLE);
        controller.enableGesture( Gesture.Type.TYPE_SWIPE);
        //controller.enableGesture( Gesture.Type.TYPE_SCREEN_TAP);
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
	

	public void setRoomId(String id) {
		current_room_id = id;
	}
	
	public String getRoomId() {
		return current_room_id;
	}
	
	@Override
	protected void onGuiEvent(GuiEvent arg0) {
		if(arg0.getType()==1){
			String messageMode = arg0.getParameter(0).toString();
			this.addBehaviour(new ModeSelectBehaviour(this, messageMode));
		}else if(arg0.getType()==2){
			selected_instrument = encodageInstrument(arg0.getParameter(1).toString());
			System.out.println(selected_instrument);
			this.addBehaviour(new InstrumentSelectBehaviour(this, selected_instrument));
			this.addBehaviour(new ModeSelectBehaviour(this, arg0.getParameter(0).toString()));
			
		}else if(arg0.getType()==0){
			//ask for the list of rooms
//			ACLMessage messageDemande = new ACLMessage(ACLMessage.REQUEST);
//			messageDemande.setContent(arg0.getParameter(0).toString());	
			
			this.addBehaviour(new GetListGroupBehaviour(this));
			System.out.println("userAgent envoyer demande\n");


		} else if(arg0.getType() == CREATE_ROOM_EVENT){
			this.addBehaviour(new CreatGroupBehaviour(this));
		} else if(arg0.getType() == JOINT_ROOM_EVENT){
			this.addBehaviour(new EnterGroupBehaviour(this, arg0.getParameter(0).toString()));
		} else if(arg0.getType() == EXIT_ROOM_EVENT){
			if (current_room_id != null)
			this.addBehaviour(new ExitGroupBehaviour(this, current_room_id));
			this.addBehaviour(new GetListGroupBehaviour(this));
		}else if(arg0.getType()==CONFIRM_ROOM_EVENT){
			System.out.println("start game demande");
			this.addBehaviour(new StartGameBehaviour(this));
		}
		
	}
	
	public AID getServerName() {
		if (server_name != null) {
			return server_name;
		}
		DFAgentDescription template=new DFAgentDescription();
		ServiceDescription sd=new ServiceDescription();
		sd.setType("Organisation");
		sd.setName("Multiplay");
		template.addServices(sd);
		try{
			DFAgentDescription[] result=DFService.search(this, template);
			if(result.length>0){
				server_name = result[0].getName();
			}
		}catch(FIPAException fe){
			fe.printStackTrace();
		}
		return server_name;
	}
	

	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		changes.addPropertyChangeListener(pcl);
    }
	
	public String encodageInstrument(String message){
		
		//String instrument = message.split("/")[1];
		if(message.equals("drum.png")){
			return "1";
		}else if(message.equals("guitar.png")){
			return "2";
		}else{
			return "0";
		}
		
	}
	
	public void changeCurrentViewTo(JAgentFrame frame) {
		frame.setVisible(true);
		if (current_frame != null) {
			current_frame.setVisible(false);
		}
		current_frame = frame;
	}
	
	public void changeToRoomSelectView() {
		if(getDict()!=null){
			//room_view.setVisible(true);
			changeCurrentViewTo(room_view);
			//instrument_view.setVisible(false);
			//menu_view.setVisible(false);
		}
	}
		
	public void changeToRoomWaitView() {
			if(getDict()!=null){
				//wait_view.getList_player().setModel(getDictPlayer());
				//wait_view.setVisible(true);
				//room_view.setVisible(false);
				changeCurrentViewTo(wait_view);
			}
			System.out.println("ohhhhhhhhhh");
		
		
	}
	
	public void changeToInstrumentView(){
//		instrument_view.setVisible(true);
//		menu_view.setVisible(false);
		changeCurrentViewTo(instrument_view);
	}
	public void changeToGameView(){
		//System.out.println("okkk");
//		game_view.setVisible(true);
//		wait_view.setVisible(false);
//		instrument_view.setVisible(false);
		changeCurrentViewTo(game_view);
	}
	
	public void changeToMenuView(){
//		menu_view.setVisible(true);
//		room_view.setVisible(false);
//		wait_view.setVisible(false);
//		game_view.setVisible(false);
//		wait_view.setVisible(false);
//		instrument_view.setVisible(false);	
		changeCurrentViewTo(menu_view);
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
		//double d1 = Math.sqrt((x_1-hand_1.x)*(x_1-hand_1.x) + (y_1-hand_1.y)*(y_1-hand_1.y) + (z_1 - hand_1.z)*(z_1 - hand_1.z));
		
		hand_1.x = x_1;
		hand_1.y = y_1;
		hand_2.x = x_2;
		hand_2.y = y_2;
		hand_1.z = z_1;
		hand_2.z = z_2;
		//if(d1 > Constance.Minimun_Distance)
			changes.firePropertyChange("hand1", null, hand_1);
		//double d2 = Math.sqrt((x_2-hand_2.x)*(x_2-hand_2.x) + (y_2-hand_2.y)*(y_2-hand_2.y) + (z_2 - hand_2.z)*(z_2 - hand_2.z));
		//if (d2 > Constance.Minimun_Distance)
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
	
	public DefaultListModel<String> getDictPlayer() {
		return dict_list_player;
	}


	public void setDict(DefaultListModel<String> dict) {
		this.dict = dict;
		room_view.getList_room().setModel(this.dict);
		room_view.getList_room().setSelectedIndex(0);
		System.out.println("update dict");
	}
	
	public void setDictPlayer(DefaultListModel<String> dict) {
		this.dict_list_player = dict;
		wait_view.getList_player().setModel(this.dict_list_player);
		System.out.println("update dict player");
	}
	public int[] getInstrumentPosition(){
		return new int[]{game_view.instrumentX,game_view.instrumentY};
		
	}
}
