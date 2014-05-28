package fr.utc.leapband.sma;


import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;



public class ServerContainer {
	
	public static String SECONDARY_PROPERTIES_FILE = "cfg/containerServer.cfg";

	/**
	 * @param args
	 */
	public static void run(String[] args) {
		Runtime rt = Runtime.instance();
		Profile p = null;
		try{
			p = new ProfileImpl(SECONDARY_PROPERTIES_FILE);
			AgentContainer operationContainer = rt.createAgentContainer(p);
			AgentController c1 = operationContainer.createNewAgent(
					"MultiPlayAgent",
					"fr.utc.leapband.sma.server.MultiPlayAgent",
					null);
			c1.start();
		} catch(Exception ex) {
			ex.printStackTrace();
		}

	}
}