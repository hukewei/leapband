package agents;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class MainContainer {
	public static String SECOND_PROPERTIES_FILE = "MusicConfig";
	
	
	public static void main(String[] args) {
		Runtime rt = Runtime.instance();
		ProfileImpl p = null;
		
		try {
			p = new ProfileImpl(SECOND_PROPERTIES_FILE);
			ContainerController cc = rt.createAgentContainer(p);
			AgentController ac = cc.createNewAgent("SoundPlayer", "agents.SoundPlayAgent", null);
			AgentController ac2 = cc.createNewAgent("MoveToSound", "agents.MoveToSoundAgent", null);
			ac.start();
			ac2.start();

		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
