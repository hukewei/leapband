package Utilities;


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
	
	public static int Gesture_Interval = 600000;
	static public int PLAY_IntervalTimer = 5000;   //T120 120/Min
	static public int click_delay = 1800;
	
	public static String ROOM_CREATED = "ROOM_CREATED";
	public static String ROOM_ENTERED = "ROOM_ENTERED";
	public static String ROOM_QUITTED = "ROOM_QUITTED";
	public static String roomselect_Mode = "104";
	public static String EnterGroupMode = "108";
	public static String ExitGroupMode = "109";
	public static String MEMBER_CHANGE = "memberChangeInform";
	public static String GROUP_CREATED = "groupCreatedInform";
}
