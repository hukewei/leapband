package SMA;


import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;




public class UserContainer {
	
	public static String SECONDARY_PROPERTIES_FILE = "containerUser.cfg";

	/**
	 * @param args
	 */
	public static void run(String[] args) {
		Runtime rt = Runtime.instance();
		Profile p = null;
		String sound_agent_name = "SoundPlayer_1";
		String sound_agent_name_2 = "SoundPlayer_2";
		try{
			p = new ProfileImpl(SECONDARY_PROPERTIES_FILE);
			AgentContainer operationContainer = rt.createAgentContainer(p);
			AgentController ac = operationContainer.createNewAgent(sound_agent_name, 
					"SMA.sound.SoundPlayAgent", null);
			ac.start();
/*			AgentController ac2 = operationContainer.createNewAgent(sound_agent_name_2, 
					"SMA.sound.SoundPlayAgent", null);
			ac2.start();*/

			AgentController c1 = operationContainer.createNewAgent(
					"User_1",
					"SMA.user.UserAgent",
					new Object[] {new String(sound_agent_name)});
			c1.start();
//
			/*AgentController c2 = operationContainer.createNewAgent(
					"User_2",
					"SMA.user.UserAgent",
					new Object[] {new String(sound_agent_name_2)});
			c2.start();*/
			
//			AgentController c2 = operationContainer.createNewAgent(
//					"User_2",
//					"SMA.user.UserAgent",
//					new Object[] {new String(sound_agent_name_2)});
//			c2.start();
//			AgentController ac2 = operationContainer.createNewAgent(sound_agent_name_2, 
//					"SMA.sound.SoundPlayAgent", null);
//			ac2.start();
//			AgentController c3 = operationContainer.createNewAgent(
//					"User_3",
//					"SMA.user.UserAgent",
//					null);
//			c3.start();
//		
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}



