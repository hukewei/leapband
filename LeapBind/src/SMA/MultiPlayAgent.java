package SMA;

import java.util.ArrayList;
import java.util.List;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;



@SuppressWarnings("serial")
public class MultiPlayAgent extends Agent{
	
	private List<String> dict;

	protected void setup() {
		
		DFAgentDescription dfd=new DFAgentDescription();

		dfd.setName(getAID());

		ServiceDescription sd=new ServiceDescription();

		sd.setType("Organisation");

		sd.setName("Multiplay");

		dfd.addServices(sd);

		try{

			DFService.register(this, dfd);

		}catch(FIPAException fe){

			fe.printStackTrace();

		}
		//addBehaviour(new GetListGameBehaviour(this));
		addBehaviour(new GameDaemonBehaviour(this));
		
	}

	public List<String> getDict(){
		if (dict == null) {
			dict = new ArrayList<String>();
			dict.add("room1");
			dict.add("room2");
			dict.add("room3");
			dict.add("room4");
		}
		return dict;
	}
	public void setDict(String item){
		dict.add(item);
		
	}

}
