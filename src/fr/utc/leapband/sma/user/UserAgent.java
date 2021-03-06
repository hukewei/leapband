package fr.utc.leapband.sma.user;



import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListModel;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Vector;

import fr.utc.leapband.controller.LeapListener;
import fr.utc.leapband.utilities.BackgroundMusicData.BackgroundMusicActionType;
import fr.utc.leapband.utilities.Constance;
import fr.utc.leapband.utilities.Cordinates;
import fr.utc.leapband.utilities.InstrumentType;
import fr.utc.leapband.utilities.SongFlowItem;
import fr.utc.leapband.view.GameView;
import fr.utc.leapband.view.InstrumentSelectView;
import fr.utc.leapband.view.JAgentFrame;
import fr.utc.leapband.view.MenuView;
import fr.utc.leapband.view.MultiwaitRoom;
import fr.utc.leapband.view.RoomSelectView;



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
	public static int SELECT_MUSIC_EVENT = 7;
	public static int CONTROL_MUSIC_EVENT =8;
	public static int CONTROL_MUSIC_RHYTHM =9;
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
	private AID note_agent_name = null;
	private AID my_sound_name = null;
	private AID host_sound_name = null;
	private String selected_instrument = null;
	private String selected_song = null;
	private boolean isBackGroundMusicOn=false;
	String current_room_id = null; //conversation id if in a group
	private JAgentFrame current_frame = null;
	private long last_fire_left_drum = 0;
	private long last_fire_right_drum = 0;
	private long last_fire_guitar = 0;
	private double current_rotation = 0;//roration for volume
	private List<SongFlowItem> songs = SongFlowItem.loadFromDirectory(new File(Constance.Sound_Directory));

	


	private DefaultListModel<String> dict = null;
	private DefaultListModel<String> dict_list_player = null;
	private String SoundPlayAgentNickName = null;

	
	private LeapListener listener;
	private Controller controller;
	
	public String getNextSong() {
		String next_song = null;
		for (int i = 0; i < songs.size(); i++) {
			if(songs.get(i).getFile().getPath().equals(selected_song)) {
				if(i + 1 < songs.size()) {
					next_song = songs.get(i+1).getFile().getPath(); 
				} else {
					next_song = songs.get(0).getFile().getPath(); 
				}
				break;
			} 
		}
		if (next_song == null) {
			next_song = songs.get(0).getFile().getPath();
		}
		return next_song;
	}
	
	public String getBeforeSong() {
		String before_song = null;
		for (int i = 0; i < songs.size(); i++) {
			if(songs.get(i).getFile().getPath().equals(selected_song)) {
				if(i - 1 >= 0) {
					before_song = songs.get(i-1).getFile().getPath(); 
				} else {
					before_song = songs.get(songs.size()-1).getFile().getPath(); 
				}
				break;
			} 
		}
		if (before_song == null) {
			before_song = songs.get(songs.size()-1).getFile().getPath();
		}
		return before_song;
	}
		

	
	protected void setup() {
		super.setup();
		SoundPlayAgentNickName = (String) getArguments()[0];
		System.out.println("SoundPlayAgentNickName = " + SoundPlayAgentNickName);
		System.out.println(getLocalName()+"--> Installed");
		changes = new PropertyChangeSupport(this);
		menu_view = new MenuView(this);
		instrument_view = new InstrumentSelectView(this);
		game_view = new GameView(this);
		room_view = new RoomSelectView(this);
		wait_view = new MultiwaitRoom(this);
		changeCurrentViewTo(menu_view);
		
		listener = new LeapListener(this);
        controller = new Controller();
        
        //controller.enableGesture( Gesture.Type.TYPE_KEY_TAP );
        controller.enableGesture( Gesture.Type.TYPE_CIRCLE);
        controller.enableGesture( Gesture.Type.TYPE_SWIPE);
        //controller.enableGesture( Gesture.Type.TYPE_SCREEN_TAP);
        //listener.setDebug(true);
        listener.setClickType(1); 
        listener.setCalibratedScren(true);
        controller.addListener(listener);

        // Remove the listener when done
        //controller.removeListener(listener);
	}
	
	public JAgentFrame getCurrent_frame() {
		return current_frame;
	}

	public InstrumentType getSelectedInstrument() {
		InstrumentType instrument = InstrumentType.DEFAULT;
		if (selected_instrument == drum) {
			instrument = InstrumentType.TAMBOUR;
		} else if (selected_instrument == piano) {
			instrument = InstrumentType.PIANO;
		} else if (selected_instrument == guitar) {
			instrument = InstrumentType.GUITAR;
		}
		return instrument;
	}
	
	public boolean isMultipleMode() {
		return multiple_mode;
	}
	
	public AID getSoundAgentName() {
		if (multiple_mode) {
			return host_sound_name;
		} else {
			return getMySoundAgent();
		}
	}
	
	public boolean isHost() {
		return getSoundAgentName().equals(getMySoundAgent());
	}
	
	public void setHostSoundName(AID host) {
		host_sound_name = host;
	}
	
	public void setRoomId(String id) {
		current_room_id = id;
	}
	
	public String getRoomId() {
		return current_room_id;
	}
	
	public double getCurrent_rotation() {
		return current_rotation;
	}

	public void setCurrent_rotation(double current_rotation) {
		this.current_rotation = current_rotation;
	}
	
	public List<SongFlowItem> getSongs() {
		return songs;
	}
	
	@Override
	protected void onGuiEvent(GuiEvent arg0) {
		if(arg0.getType() == SELECT_EVENT){
			String messageMode = arg0.getParameter(0).toString();
			this.addBehaviour(new ModeSelectBehaviour(this, messageMode));
		}else if(arg0.getType()==2){
			selected_instrument = encodageInstrument(arg0.getParameter(1).toString());
			System.out.println("selected instrument = " + selected_instrument);
			this.addBehaviour(new InstrumentSelectBehaviour(this, selected_instrument));
			this.addBehaviour(new ModeSelectBehaviour(this, arg0.getParameter(0).toString()));
			
		}else if(arg0.getType()==0){
			//ask for the list of rooms
			this.addBehaviour(new GetListGroupBehaviour(this));
			System.out.println("userAgent envoyer demande\n");
		} else if(arg0.getType() == CREATE_ROOM_EVENT){
			this.addBehaviour(new CreatGroupBehaviour(this));
		} else if(arg0.getType() == JOINT_ROOM_EVENT){
			this.addBehaviour(new EnterGroupBehaviour(this, arg0.getParameter(0).toString()));
			changeStartVisibility(false);
		} else if(arg0.getType() == EXIT_ROOM_EVENT){
			if (current_room_id != null)
			this.addBehaviour(new ExitGroupBehaviour(this, current_room_id));
		}else if(arg0.getType()==CONFIRM_ROOM_EVENT){
			System.out.println("start game demande");
			this.addBehaviour(new StartGameBehaviour(this));
		}else if(arg0.getType()==SELECT_MUSIC_EVENT){
			System.out.println("selected Song path:"+arg0.getParameter(0));
			if(selected_song==null){
				selected_song=(String) arg0.getParameter(0);
				System.out.println("first select");
				this.addBehaviour(new SendBgMusicBehaviour(this, selected_song, BackgroundMusicActionType.CHANGE_BACKGROUND));
			}else if(!selected_song.equals(arg0.getParameter(0))){
				selected_song=(String) arg0.getParameter(0);
				if (isBackGroundMusicOn) {
					this.addBehaviour(new SendBgMusicBehaviour(this, null, BackgroundMusicActionType.PAUSE_BACKGROUND));
				}
				this.addBehaviour(new SendBgMusicBehaviour(this, selected_song, BackgroundMusicActionType.CHANGE_BACKGROUND));
			}
			if (isBackGroundMusicOn) {
				this.addBehaviour(new SendBgMusicBehaviour(this, null, BackgroundMusicActionType.START_BACKGROUND));
			}
		}else if(arg0.getType()==CONTROL_MUSIC_EVENT){
			isBackGroundMusicOn=(boolean) arg0.getParameter(0);
			if(isBackGroundMusicOn){
				System.out.println("music on");
				if (selected_song == null && getSongs().size() > 0) {
					this.addBehaviour(new SendBgMusicBehaviour(this, getSongs().get(0).getFile().getPath(), BackgroundMusicActionType.CHANGE_BACKGROUND));
					selected_song = getSongs().get(0).getFile().getPath();					
				}
				this.addBehaviour(new SendBgMusicBehaviour(this, null, BackgroundMusicActionType.START_BACKGROUND));
			}else{
				System.out.println("music off");
				this.addBehaviour(new SendBgMusicBehaviour(this, null, BackgroundMusicActionType.PAUSE_BACKGROUND));
			}
		}else if(arg0.getType()==CONTROL_MUSIC_RHYTHM){
			
			System.out.println("here" + arg0.getParameter(0));
			if (arg0.getParameter(0).equals(Constance.Forward)) {
				String next_song = getNextSong();
				System.out.println("next song is " + next_song);
				if(next_song != null) {
					if (isBackGroundMusicOn) {
						this.addBehaviour(new SendBgMusicBehaviour(this, null, BackgroundMusicActionType.PAUSE_BACKGROUND));
					}
					selected_song = next_song;
				}
			} else if (arg0.getParameter(0).equals(Constance.Rewind)) {
				String before_song = getBeforeSong();
				if(before_song != null) {
					if (isBackGroundMusicOn) {
						this.addBehaviour(new SendBgMusicBehaviour(this, null, BackgroundMusicActionType.PAUSE_BACKGROUND));
					}
					selected_song = before_song;
				}
			}
			this.addBehaviour(new SendBgMusicBehaviour(this, selected_song, BackgroundMusicActionType.CHANGE_BACKGROUND));
			if(isBackGroundMusicOn){
				this.addBehaviour(new SendBgMusicBehaviour(this, null, BackgroundMusicActionType.START_BACKGROUND));
				}
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
	
	public AID getNoteAgentName() {
		if (note_agent_name != null) {
			return note_agent_name;
		}
		DFAgentDescription template=new DFAgentDescription();
		ServiceDescription sd=new ServiceDescription();
		sd.setType("Organisation");
		sd.setName("MoveToNote");
		template.addServices(sd);
		try{
			DFAgentDescription[] result=DFService.search(this, template);
			if(result.length>0){
				note_agent_name = result[0].getName();
			}
		}catch(FIPAException fe){
			fe.printStackTrace();
		}
		return note_agent_name;
	}
	
	public AID getMySoundAgent() {
		if (my_sound_name != null) {
			return my_sound_name;
		}
		DFAgentDescription template=new DFAgentDescription();
		ServiceDescription sd=new ServiceDescription();
		sd.setType("Sound");
		sd.setName("SoundPlay");
		template.addServices(sd);
		try{
			DFAgentDescription[] result=DFService.search(this, template);
			if(result.length>0){
				for (int i = 0; i < result.length; i++) {
					System.out.println("possible name = " + result[i].getName().getName());
					if (result[i].getName().getName().contains(SoundPlayAgentNickName)) {
						my_sound_name = result[i].getName();
						break;
					}
				}
			}
		}catch(FIPAException fe){
			fe.printStackTrace();
		}
		return my_sound_name;
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
	
	public void changeCurrentViewTo(final JAgentFrame frame) {
		if (current_frame == frame) {
			return;
		}
		if (current_frame != null) {
			changes.firePropertyChange(Constance.CHANGE_FRAME, null, frame.getName());
			changes.firePropertyChange(Constance.CHANGE_FRAME, current_frame.getName(), null);
		} else {
			changes.firePropertyChange(Constance.CHANGE_FRAME, null, frame.getName());
		}
		current_frame = frame;
	}
	
	public void changeToRoomSelectView() {
		if(getDict()!=null){
			changeCurrentViewTo(room_view);
			System.out.println("change to room select view");
		}
	}
		
	public void changeToRoomWaitView() {
			if(getDict()!=null){
				wait_view.getRoomID().setText(current_room_id);
				changeCurrentViewTo(wait_view);
			}
			System.out.println("ohhhhhhhhhh");
		
		
	}
	
	public void changeToInstrumentView(){
		changeCurrentViewTo(instrument_view);
	}
	public void changeToGameView(){
		changeCurrentViewTo(game_view);
	}
	
	public void changeToMenuView(){
		System.out.println("change to menu view");
		changeCurrentViewTo(menu_view);
	}
	
	public boolean isSingle_mode() {
		return single_mode;
	}
	
	public void setSingle_mode(boolean single_mode) {
		this.single_mode = single_mode;
		this.multiple_mode = !single_mode;
	}
	
	public void setNoMode() {
		this.single_mode = false;
		this.multiple_mode = false;
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
	
	public void updateHands(float x_1, float y_1, float x_2, float y_2, float z_1, float z_2, float speed_1, float speed_2, Vector dir_1, Vector dir_2, boolean two_hand) {
		if(game_view.isCan_fire_change()) {
			if(game_view.isVisible()) {
				hand_1.x = x_1;
				hand_1.y = y_1 - Constance.Control_Pane_height - 50;
				hand_1.z = z_1;
				hand_1.speed = speed_1;
				hand_1.direction = dir_1;
				if (two_hand){
					hand_2.x = x_2;
					hand_2.y = y_2 - Constance.Control_Pane_height - 50;
					hand_2.z = z_2;
					hand_2.speed = speed_2;
					hand_2.direction = dir_2;
				}
				changes.firePropertyChange("hand1", null, hand_1);
				if (two_hand)changes.firePropertyChange("hand2", null, hand_2);
				if (selected_instrument == drum) {
					if(isCollisionForDrumLeft(hand_1) ){
						if(shouldFireChange("drum_left")) {
							changes.firePropertyChange("drum_left", null, null);
							this.addBehaviour(new SendMoveBehaviour(this, hand_1, getVolumeMultiplier()));
						}
					} else if (two_hand && isCollisionForDrumLeft(hand_2)) {
						if(shouldFireChange("drum_left")) {
							changes.firePropertyChange("drum_left", null, null);
							this.addBehaviour(new SendMoveBehaviour(this, hand_2, getVolumeMultiplier()));
						}
					} else if(isCollisionForDrumRight(hand_1) ){
						if(shouldFireChange("drum_right")) {
							changes.firePropertyChange("drum_right", null, null);
							this.addBehaviour(new SendMoveBehaviour(this, hand_1, getVolumeMultiplier()));
						}
					} else if (two_hand && isCollisionForDrumRight(hand_2)) {
						if(shouldFireChange("drum_right")) {
							changes.firePropertyChange("drum_right", null, null);
							this.addBehaviour(new SendMoveBehaviour(this, hand_2, getVolumeMultiplier()));
						}
					}
				} else if (selected_instrument == guitar) {
					int guitar_id = getChord(hand_1.x);
					if(isTriggeredGuitar(hand_1) && shouldFireChange("guitar")) {
						changes.firePropertyChange("chord", null, guitar_id);
						this.addBehaviour(new SendMoveBehaviour(this, hand_1, getVolumeMultiplier()));
					}
					if (two_hand) {
						guitar_id = getChord(hand_2.x);
						if(isTriggeredGuitar(hand_2) && shouldFireChange("guitar")) {
							changes.firePropertyChange("chord", null, guitar_id);
							this.addBehaviour(new SendMoveBehaviour(this, hand_2, getVolumeMultiplier()));
						}
					}
				}
			}
		}
	}
	
	public int getChord(float x) {
		int chord = 0;
		if (x < Constance.Windows_width * 0.14) {
			chord = 1;
		} else if (x < Constance.Windows_width * 0.27) {
			chord = 2;
		} else if (x < Constance.Windows_width * 0.4) {
			chord = 3;
		} else if (x < Constance.Windows_width * 0.53) {
			chord = 4;
		} else if (x < Constance.Windows_width * 0.66) {
			chord = 5;
		} else if (x < Constance.Windows_width * 0.79) {
			chord = 6;
		} else if (x < Constance.Windows_width * 0.9) {
			chord = 7;
		} else if (x < Constance.Windows_width) {
			chord = 8;
		}
		return chord;
	}
	
	public float getVolumeMultiplier() {
		float multiplier = 1;
		if (current_rotation <= -180) {
			multiplier = 0;
		} else if (current_rotation< -120) {
			multiplier = 0.6f;
		} else if (current_rotation< -90) {
			multiplier = 0.7f;
		} else if (current_rotation< -60) {
			multiplier = 0.8f;
		} else if (current_rotation< -30) {
			multiplier = 0.9f;
		} else if (current_rotation< 0) {
			multiplier = 0.95f;
		} else if (current_rotation <= 0) {
			multiplier = 1;
		} else if (current_rotation< 30) {
			multiplier = 1.05f;
		} else if (current_rotation< 60) {
			multiplier = 1.1f;
		} else if (current_rotation< 90) {
			multiplier = 1.2f;
		} else if (current_rotation< 120) {
			multiplier = 1.3f;
		} else if (current_rotation< 180) {
			multiplier = 1.4f;
		} else {
			multiplier = 1.8f;
		}
		return (float) (multiplier * 1.5);
	}
	
	private boolean shouldFireChange(String instrument) {
		boolean fire = false;
		long current_time = new Date().getTime();
		if (instrument.equals("drum_left")) {
			if (current_time - last_fire_left_drum > Constance.Minimun_Fire_interval) {
				last_fire_left_drum = current_time;
				fire = true;
			}
		} else if (instrument.equals("drum_right")) {
			if (current_time - last_fire_right_drum > Constance.Minimun_Fire_interval) {
				last_fire_right_drum = current_time;
				fire = true;
			}
		} else if (instrument.equals("guitar")) {
			if (current_time - last_fire_guitar > Constance.Minimun_Guitar_Fire_interval) {
				last_fire_guitar = current_time;
				fire = true;
			}
		}
		return fire;
	}
	
	public boolean isCollisionForDrumLeft(Cordinates hand) {
		boolean collision = false;
		//System.out.println("direction = " + hand.direction.getY() + " speed = " + hand.speed);
		if ((hand.direction.getY()  < - 0.1) && Math.abs(hand.speed) > 50 ) {
			if (hand.x > Constance.Windows_width * 0.10 && hand.x < Constance.Windows_width * 0.5 && hand.y > Constance.Windows_height * 0.65 && hand.y < Constance.Windows_height * 0.72) {
				return true;
			}
		}
		return collision;
	}
	
	public boolean isCollisionForDrumRight(Cordinates hand) {
		boolean collision = false;
		//System.out.println("direction = " + hand.direction.getY() + " speed = " + hand.speed);
		if ((hand.direction.getY()  < - 0.1) && Math.abs(hand.speed) > 50 ) {
			if (hand.x > Constance.Windows_width * 0.52 && hand.x < Constance.Windows_width * 0.9 && hand.y > Constance.Windows_height * 0.65 && hand.y < Constance.Windows_height * 0.72) {
				return true;
			}
		}
		return collision;
	}
	
	public boolean isTriggeredGuitar(Cordinates hand) {
		boolean trigger = false;
		//System.out.println("direction = " + hand.direction.getY() + " speed = " + hand.speed);
		if(Math.abs(hand.direction.getY()) > Math.abs(hand.direction.getX()) && Math.abs(hand.speed) > 200) {
			if (hand.y > Constance.Windows_height * 0.35 && hand.y < Constance.Windows_height * 0.85) {
				trigger =  true;
			}
		}
		return trigger;
	}
	
	public void doSwipe(String direction) {
		changes.firePropertyChange("swipe", null, direction);
	}
	
	public void doCircle(String direction) {
		if (direction == "clockwise") {
			changes.firePropertyChange("Circle", null, Constance.Volume_Up);
		}
		if (direction == "anticlockwise") {
			changes.firePropertyChange("Circle", null, Constance.Volume_Down);
		}
	}

	public GameView getGame_view() {
		return game_view;
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
	
	public void changeStartVisibility(boolean vis){
		wait_view.getStartButton().setVisible(vis);
	}
}
