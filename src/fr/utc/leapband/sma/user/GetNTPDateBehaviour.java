package fr.utc.leapband.sma.user;

import java.util.Calendar;
import java.util.Timer;

import fr.utc.leapband.utilities.Constance;
import fr.utc.leapband.utilities.ImageTimerTask;
import fr.utc.leapband.utilities.NTPClient;
import jade.core.behaviours.OneShotBehaviour;

@SuppressWarnings("serial")
public class GetNTPDateBehaviour extends OneShotBehaviour{

	@Override
	public void action() {
        // get the current time
        System.out.println("Current time is :" + Calendar.getInstance().getTime());
        // get NTP time in another Thread
        new Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	Calendar.getInstance().setTime(NTPClient.getRemoteTime());
		            }
		        }, 
		        0
		);
		// get the current time
		System.out.println("After setting Time:  " + Calendar.getInstance().getTime());
	}
}
