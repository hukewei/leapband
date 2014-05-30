package fr.utc.leapband.sma;


import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import fr.utc.leapband.utilities.Constance;

public class MainContainer {
	public static String MAIN_PROPERTIES_FILE = "cfg/container.cfg";

	public static void run(String[] args) {
		Runtime rt = Runtime.instance();
		Profile p = null;
		try{
			p = new ProfileImpl(MAIN_PROPERTIES_FILE);
			AgentContainer mainContainer = rt.createMainContainer(p);
			System.out.println(Constance.Windows_height);
			System.out.println(Constance.Windows_width);
			
		}
		catch(Exception ex) {
			
		}
	}
}