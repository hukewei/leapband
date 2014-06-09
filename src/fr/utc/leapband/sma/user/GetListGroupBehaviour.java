package fr.utc.leapband.sma.user;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import javax.swing.DefaultListModel;

import fr.utc.leapband.utilities.Constance;

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

	@SuppressWarnings("unchecked")
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
