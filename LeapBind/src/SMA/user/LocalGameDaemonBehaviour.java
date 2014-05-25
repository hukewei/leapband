package SMA.user;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import javax.swing.DefaultListModel;

import Utilities.Constance;
import Utilities.MyAID;


public class LocalGameDaemonBehaviour extends Behaviour{

	private UserAgent myAgent;
	private boolean done = false;
	

	public LocalGameDaemonBehaviour(UserAgent myAgent) {
		super();
		this.myAgent = myAgent;
		System.out.println("local multiplay daemon behaviour created");
	}
	
	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.or(MessageTemplate.and(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.CONFIRM), MessageTemplate.MatchConversationId(myAgent.getRoomId())), 
				MessageTemplate.MatchContent(Constance.CONFIRM_START)),
				MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), 
				MessageTemplate.MatchConversationId(Constance.MEMBER_CHANGE)));
		ACLMessage message= myAgent.receive(mt);

		if(message!=null){
			if (message.getPerformative() == ACLMessage.INFORM) {
				try {
					myAgent.setDictPlayer((DefaultListModel<String>)message.getContentObject());
					if (!myAgent.getDictPlayer().contains(myAgent.getName())) {
						System.out.println("Agent name not found after member change, localGameBehaviour done");
						done = true;
					}
				} catch (UnreadableException e) {
					e.printStackTrace();
				}
			} else if(message.getPerformative() == ACLMessage.CONFIRM){
				myAgent.setHostSoundName(MyAID.toAID(message.getReplyWith()));
				myAgent.changeToGameView();
			}
		}
		
	}

	@Override
	public boolean done() {
		return done;
	}

}
