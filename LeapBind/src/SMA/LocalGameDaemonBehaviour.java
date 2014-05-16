package SMA;

import javax.swing.DefaultListModel;

import Utilities.Constance;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;


public class LocalGameDaemonBehaviour extends CyclicBehaviour{

	private UserAgent myAgent;
	

	public LocalGameDaemonBehaviour(UserAgent myAgent) {
		super();
		this.myAgent = myAgent;
		System.out.println("local multiplay daemon behaviour created");
	}
	
	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), 
				MessageTemplate.MatchConversationId(Constance.MEMBER_CHANGE));
		ACLMessage message= myAgent.receive(mt);

		if(message!=null){
			if (message.getConversationId().equals(Constance.MEMBER_CHANGE)) {
				try {
					myAgent.setDictPlayer((DefaultListModel<String>)message.getContentObject());
				} catch (UnreadableException e) {
					e.printStackTrace();
				}
			}			
		}
		
	}

}
