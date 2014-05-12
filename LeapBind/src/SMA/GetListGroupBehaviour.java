package SMA;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

@SuppressWarnings("serial")
public class GetListGroupBehaviour extends OneShotBehaviour{
	
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
			/*String[] list=message.getContent().split(",");
			DefaultListModel<String> dict=new DefaultListModel<String>();
			
			for(String s:list){
				dict.addElement(s);
			}*/
			
			//myAgent.setDict(dict);
			System.out.println("userAgent recu et refresh list\n");
			try {
				myAgent.setDict((DefaultListModel<String>)message.getContentObject());
				//myAgent.getRoom_view().getList_room().setModel((DefaultListModel<String>) message.getContentObject());
				
			} catch (UnreadableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(setReceiver()!=null){
			msg.clearAllReceiver();
			msg.addReceiver(setReceiver());
			myAgent.send(msg);
			System.out.println("getlistgroupbehaviour envoie demande au multiAgent\n");
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
