package fr.utc.leapband.sma.user;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import fr.utc.leapband.utilities.Constance;
import fr.utc.leapband.utilities.MyAID;

@SuppressWarnings("serial")
public class EnterGroupBehaviour extends Behaviour{
	
	private UserAgent myAgent;
	private ACLMessage msg;
	private boolean first = true;
	private boolean done = false;
	private String msg_conversation = null;
	

	public EnterGroupBehaviour(UserAgent myAgent, String content) {
		super();
		this.myAgent = myAgent;
		this.msg=new ACLMessage(ACLMessage.SUBSCRIBE); 
		msg_conversation = content;
		System.out.println("enter room behaviour created");
	}

	@Override
	public void action() {
		if (first) {
			AID server_name = myAgent.getServerName();
			if (server_name != null){
				msg.addReceiver(server_name);
				msg.setContent(Constance.EnterGroupMode);
				msg.setConversationId(msg_conversation);
				MyAID sound_agent = new MyAID(myAgent.getMySoundAgent().getName(), myAgent.getMySoundAgent().getAddressesArray()[0]);	
				msg.setReplyWith(sound_agent.toJSON());	
				myAgent.send(msg);
				first = false;
				System.out.println("Ask for entring a room sent");
			} else {
				System.out.println("server not found");
			}
		} else {
			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.CONFIRM),
					MessageTemplate.and(MessageTemplate.MatchContent(Constance.ROOM_ENTERED), MessageTemplate.MatchConversationId(msg_conversation)));
			ACLMessage message=myAgent.receive(mt);
			if(message != null){
				myAgent.addBehaviour(new ModeSelectBehaviour(myAgent, Constance.roomselect_Mode));
				done = true;
				System.out.println("room entered, behaviour done");
				myAgent.setRoomId(msg_conversation);
				myAgent.addBehaviour(new LocalGameDaemonBehaviour(myAgent));
			}
		}
		
	}

	@Override
	public boolean done() {
		return done;
	}
	

}
