package SMA;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class CreatGroupBehaviour extends CyclicBehaviour{
	
	private UserAgent myAgent;
	private ACLMessage msg;
	

	public CreatGroupBehaviour(UserAgent myAgent,ACLMessage messageDemande) {
		super();
		this.myAgent = myAgent;
		this.msg=messageDemande;
	}

	@Override
	public void action() {
		if(setReceiver()!=null){
			msg.clearAllReceiver();
			msg.addReceiver(setReceiver());
			myAgent.send(msg);
		}
		
	}
	private AID setReceiver(){

		DFAgentDescription template=new DFAgentDescription();

		ServiceDescription sd=new ServiceDescription();

		sd.setType("Organisation");

		sd.setName("Multiplay");

		template.addServices(sd);

		try{

			DFAgentDescription[] result=DFService.search(myAgent, template);

			if(result.length>0){
				return result[0].getName();
			}

		}catch(FIPAException fe){

			fe.printStackTrace();

		}

		return null;

	}
	

}
