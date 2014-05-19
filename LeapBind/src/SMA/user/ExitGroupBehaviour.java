package SMA.user;

import Utilities.Constance;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

@SuppressWarnings("serial")
public class ExitGroupBehaviour extends Behaviour{
	
	private UserAgent myAgent;
	private ACLMessage msg;
	private boolean first = true;
	private boolean done = false;
	private String msg_conversation = null;

	public ExitGroupBehaviour(UserAgent myAgent, String content) {
		super();
		this.myAgent = myAgent;
		this.msg=new ACLMessage(ACLMessage.CANCEL); 
		msg_conversation = content;
		System.out.println("Exit room behaviour created");
	}

	@Override
	public void action() {
		if (first) {
			AID server_name = myAgent.getServerName();
			if (server_name != null){
				msg.addReceiver(server_name);
				msg.setContent(Constance.ExitGroupMode);
				msg.setConversationId(msg_conversation);
				myAgent.send(msg);
				first = false;
				System.out.println("Ask for quiting a room sent");
			} else {
				System.out.println("server not found");
			}
		}else {
			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.CONFIRM),
					MessageTemplate.and(MessageTemplate.MatchContent(Constance.ROOM_QUITTED), MessageTemplate.MatchConversationId(msg_conversation)));
			ACLMessage message=myAgent.receive(mt);
			
			if(message != null){
				myAgent.addBehaviour(new ModeSelectBehaviour(myAgent, UserAgent.Exit_Room_Mode));
				done = true;
				System.out.println("room quitted, behaviour done");
				myAgent.setRoomId(null);
			}
		}
	}

	@Override
	public boolean done() {
		return done;
	}

}
