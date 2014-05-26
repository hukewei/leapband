package Utilities;


//import java.util.Timer;
import java.util.TimerTask;
import SMA.user.UserAgent;
import View.JAgentFrame;

public class ImageTimerTask extends TimerTask{
	
	public UserAgent myAgent;
	public static int imagenow = 0;
	public JAgentFrame current_frame;
	
	public ImageTimerTask(UserAgent agent){
		myAgent = agent;
		imagenow = 0;
		current_frame = myAgent.getCurrent_frame();
	}
	
	public ImageTimerTask(JAgentFrame myFrame) {
		imagenow = 0;
		current_frame = myFrame;
	}

	@Override
	public void run(){
		String image = "src/images/loading"+ String.valueOf(imagenow) + ".gif";
		System.out.println(image);
		current_frame.changeCursorImage(image);
		imagenow++;
		if (imagenow == 12) {
			return;
		}
	}
}
