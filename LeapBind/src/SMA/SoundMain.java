package SMA;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class SoundMain {
	public static String SECOND_PROPERTIES_FILE = "containerSound.cfg";
	
	
	public static void main(String[] args) {
		Runtime rt = Runtime.instance();
		ProfileImpl p = null;
		
		try {
			p = new ProfileImpl(SECOND_PROPERTIES_FILE);
			ContainerController cc = rt.createAgentContainer(p);
			AgentController ac = cc.createNewAgent("SoundPlayer", "SMA.sound.SoundPlayAgent", null);
			AgentController ac2 = cc.createNewAgent("MoveToSound", "SMA.server.MoveToSoundAgent", null);
			ac.start();
			ac2.start();

		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
