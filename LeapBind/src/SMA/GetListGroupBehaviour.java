package SMA;

import javax.swing.DefaultListModel;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class GetListGroupBehaviour extends CyclicBehaviour{
	
	private UserAgent myAgent;
	private ACLMessage msg;
	

	public GetListGroupBehaviour(UserAgent myAgent,ACLMessage messageDemande) {
		super();
		this.myAgent = myAgent;
		this.msg=messageDemande;
	}


	@Override
	public void action() {
		ACLMessage message=myAgent.receive();

		if(message!=null){
			String[] list=message.getContent().split(",");
			DefaultListModel<String> dict=new DefaultListModel<String>();
			for(String s:list){
				dict.addElement(s);
			}
			myAgent.setDict(dict);
			myAgent.getRoom_view().getList_room().setModel(dict);
		}
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
