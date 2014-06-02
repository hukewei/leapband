package fr.utc.leapband.main;

import fr.utc.leapband.sma.MainContainer;
import fr.utc.leapband.sma.ServerContainer;
import fr.utc.leapband.sma.SoundContainer;




public class ServerBoot {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainContainer.run(args);
		ServerContainer.run(args);
		SoundContainer.run(args);
	}


}
