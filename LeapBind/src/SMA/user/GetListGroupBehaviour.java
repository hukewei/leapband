package SMA.user;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import Utilities.Constance;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

@SuppressWarnings("serial")
public class GetListGroupBehaviour extends Behaviour{
	
	private UserAgent myAgent;
	private ACLMessage msg;
	private boolean first_run = true;
	private boolean done = false;
	

	public GetListGroupBehaviour(UserAgent myAgent) {
		super();
		this.myAgent = myAgent;
		this.msg= new ACLMessage(ACLMessage.REQUEST);
	}

	@Override
	public void action() {
		if (first_run) {
			AID server_name = myAgent.getServerName();
			if(server_name !=null){
				msg.addReceiver(server_name);
				msg.setContent("listGroup");
				myAgent.send(msg);
				System.out.println("getlistgroupbehaviour envoie demande au multiAgent\n");
				first_run = false;
			} else {
				System.out.println("server not found, retry...");
			}
		} else {

			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), 
					MessageTemplate.MatchConversationId(Constance.GROUP_CREATED));
			ACLMessage message= myAgent.receive(mt);
	
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
					done = true;
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
			
	}


		@Override
		public boolean done() {
			return done;
		}
		
		
	

}
