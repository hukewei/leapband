package SMA;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

@SuppressWarnings("serial")
public class GetListGroupBehaviour extends Behaviour{
	
	private UserAgent myAgent;
	private ACLMessage msg;
	private boolean first_run = true;
	private boolean done = false;
	

	public GetListGroupBehaviour(UserAgent myAgent,ACLMessage messageDemande) {
		super();
		this.myAgent = myAgent;
		this.msg=messageDemande;
	}


	@Override
	public void action() {
		if (first_run) {
			AID server_name = myAgent.getServerName();
			if(server_name !=null){
				msg.clearAllReceiver();
				msg.addReceiver(server_name);
				myAgent.send(msg);
				System.out.println("getlistgroupbehaviour envoie demande au multiAgent\n");
				first_run = false;
			} else {
				System.out.println("server not found, retry...");
			}
		} else {
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
