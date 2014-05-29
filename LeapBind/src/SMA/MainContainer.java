package SMA;


import Utilities.Constance;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class MainContainer {
	public static String MAIN_PROPERTIES_FILE = "container.cfg";

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