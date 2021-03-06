package fr.utc.leapband.sma.user;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import javax.swing.DefaultListModel;

import fr.utc.leapband.utilities.Constance;
import fr.utc.leapband.utilities.MyAID;


@SuppressWarnings("serial")
public class LocalGameDaemonBehaviour extends Behaviour{

	private UserAgent myAgent;
	private boolean done = false;
	

	public LocalGameDaemonBehaviour(UserAgent myAgent) {
		super();
		this.myAgent = myAgent;
		System.out.println("local multiplay daemon behaviour created");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.or(
												MessageTemplate.and(
																	MessageTemplate.and(
																						MessageTemplate.MatchPerformative(ACLMessage.CONFIRM), 
																						MessageTemplate.MatchConversationId(myAgent.getRoomId())), 
																	(MessageTemplate.or(MessageTemplate.MatchContent(Constance.CONFIRM_START), 
																			MessageTemplate.MatchContent(Constance.Sound_Change)))),
												MessageTemplate.and(
																	MessageTemplate.MatchPerformative(ACLMessage.INFORM),
																	MessageTemplate.or(
																						MessageTemplate.MatchConversationId(Constance.MEMBER_CHANGE),
																						MessageTemplate.MatchConversationId("StartVisibility"))));
		ACLMessage message= myAgent.receive(mt);

		if(message!=null){
			if (message.getPerformative() == ACLMessage.INFORM) {
				if(message.getConversationId().equals("StartVisibility")){
					if(message.getContent().equals("true")){
						myAgent.changeStartVisibility(true);
					}
				}else{
					try {
						myAgent.setDictPlayer((DefaultListModel<String>)message.getContentObject());
						if (!myAgent.getDictPlayer().contains(myAgent.getName())) {
							System.out.println("Agent name not found after member change, localGameBehaviour done");
							done = true;
						}
					} catch (UnreadableException e) {
						e.printStackTrace();
					}
				}
			} else if(message.getPerformative() == ACLMessage.CONFIRM){
				if(message.getContent().equals(Constance.Sound_Change)) {
					myAgent.setHostSoundName(MyAID.toAID(message.getReplyWith()));
					System.out.println("my local name is " + myAgent.getLocalName());
					System.out.println("sound play agent change to "+ MyAID.toAID(message.getReplyWith()));
				} else if (message.getContent().equals(Constance.CONFIRM_START)) {
					myAgent.setHostSoundName(MyAID.toAID(message.getReplyWith()));
					myAgent.changeToGameView();
				}
			}
		}
		
	}

	@Override
	public boolean done() {
		return done;
	}

}
