package fr.utc.leapband.sma.user;

import java.util.Calendar;

import fr.utc.leapband.utilities.NTPClient;
import jade.core.behaviours.OneShotBehaviour;

@SuppressWarnings("serial")
public class GetNTPDateBehaviour extends OneShotBehaviour{

	@Override
	public void action() {
        // get the current time
        System.out.println("Current time is :" + Calendar.getInstance().getTime());
		Calendar.getInstance().setTime(NTPClient.getRemoteTime());
		// get the current time
		System.out.println("After setting Time:  " + Calendar.getInstance().getTime());
	}
}
