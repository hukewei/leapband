package fr.utc.leapband.utilities;


public class Constance {
	public static int Windows_width = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
	public static int Windows_height = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;

	public static int Nb_Instrument_Supported = 3;
	public static int Swipe_Up = 200;
	public static int Swipe_Down = 201;
	public static int Swipe_Left = 202;
	public static int Swipe_Right = 203;
	
	public static int Hand_width = 80;
	public static int Hand_height = 45;
	public static boolean ENABLE_DELAY = true;
	public static long MAX_MESSAGE_DELAY = 1000000; //1000000 == 1 second
	public static int Gesture_Interval = 750000;   //100000 == 0.1 second
	static public int PLAY_IntervalTimer = 5000;   //T120 120/Min
	static public int click_delay = 1600;
	static public int Minimun_Distance = 3;
	static public int Minimun_Fire_interval = 200;
	static public int Minimun_Guitar_Fire_interval = 750;
	public static int Control_Pane_height = 120;
	
	public static String ROOM_CREATED = "ROOM_CREATED";
	public static String ROOM_ENTERED = "ROOM_ENTERED";
	public static String ROOM_QUITTED = "ROOM_QUITTED";
	public static String Sound_Change = "Sound_Change";
	public static String roomselect_Mode = "104";
	public static String EnterGroupMode = "108";
	public static String ExitGroupMode = "109";
	public static String MEMBER_CHANGE = "memberChangeInform";
	public static String GROUP_CREATED = "groupCreatedInform";
	public static String START_GAME = "Game_Start_Request";
	public static String CONFIRM_START = "Game_Start_Confirmed";
	public static String Volume_Up = "Volume_Up";
	public static String Volume_Down = "Volume_Down";
	public static int Guitar_Type = 25;
	public static String Forward = "Forward";
	public static String Rewind = "Rewind";
	public static String Sound_Directory = "songs/";
	public static String CHANGE_FRAME = "CHANGE_FRAME";
}
