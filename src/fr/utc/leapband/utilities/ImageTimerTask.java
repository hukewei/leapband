package fr.utc.leapband.utilities;


//import java.util.Timer;
import java.util.TimerTask;

import fr.utc.leapband.sma.user.UserAgent;
import fr.utc.leapband.view.JAgentFrame;

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
		String image = "images/loading"+ String.valueOf(imagenow) + ".gif";
		current_frame.changeCursorImage(image);
		imagenow++;
		if (imagenow == 12) {
			return;
		}
	}
}
