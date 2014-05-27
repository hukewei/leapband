package SMA;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class SoundContainer {
	public static String SECOND_PROPERTIES_FILE = "containerSound.cfg";
	
	
	public static void run(String[] args) {
		Runtime rt = Runtime.instance();
		ProfileImpl p = null;
		
		try {
			p = new ProfileImpl(SECOND_PROPERTIES_FILE);
			ContainerController cc = rt.createAgentContainer(p);
			AgentController ac2 = cc.createNewAgent("MoveToSound", "SMA.server.MoveToSoundAgent", null);
			ac2.start();

		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
