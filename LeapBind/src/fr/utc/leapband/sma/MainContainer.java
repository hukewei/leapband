package fr.utc.leapband.sma;


import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;

public class MainContainer {
	public static String MAIN_PROPERTIES_FILE = "cfg/container.cfg";

	public static void run(String[] args) {
		Runtime rt = Runtime.instance();
		Profile p = null;
		try{
			p = new ProfileImpl(MAIN_PROPERTIES_FILE);
			AgentContainer mainContainer = rt.createMainContainer(p);
			
		}
		catch(Exception ex) {
			
		}
	}
}