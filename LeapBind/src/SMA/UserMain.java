package SMA;


import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;




public class UserMain {
	
	public static String SECONDARY_PROPERTIES_FILE = "containerUser.cfg";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Runtime rt = Runtime.instance();
		Profile p = null;
		try{
			p = new ProfileImpl(SECONDARY_PROPERTIES_FILE);
			AgentContainer operationContainer = rt.createAgentContainer(p);
			
			AgentController c1 = operationContainer.createNewAgent(
					"MultiPlayAgent",
					"SMA.MultiPlayAgent",
					null);
			c1.start();
			
		    c1 = operationContainer.createNewAgent(
					"User_1",
					"SMA.UserAgent",
					null);
			c1.start();
		
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}


