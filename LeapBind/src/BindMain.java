import java.io.IOException;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Gesture;


public class BindMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//new MenuView();
		//new GameView();
		new RoomSelectView();
		
		LeapListener listener = new LeapListener();
        Controller controller = new Controller();
        controller.enableGesture( Gesture.Type.TYPE_KEY_TAP );
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

}
