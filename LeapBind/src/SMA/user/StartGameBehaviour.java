package SMA.user;

import Utilities.Constance;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

@SuppressWarnings("serial")
public class StartGameBehaviour extends Behaviour{
	
	private ACLMessage msg;
	private UserAgent myAgent;
	private boolean first=true;
	private boolean done=false;


	public StartGameBehaviour(UserAgent myAgent) {
		super();
		this.myAgent = myAgent;
		this.msg=new ACLMessage(ACLMessage.REQUEST); 
		System.out.println("start game behaviour created");

	}



	@Override
	public void action() {
		AID server_name = myAgent.getServerName();
		if(first){
			if (server_name != null){
				msg.clearAllReceiver();
				msg.addReceiver(server_name);
				msg.setContent(Constance.START_GAME);
				msg.setConversationId(myAgent.getRoomId());
				myAgent.send(msg);
				System.out.println("Ask for start game sent");
				first=false;
			} else {
				System.out.println("server not found");
			}
		}else{
			MessageTemplate mt = MessageTemplate.and(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.CONFIRM), MessageTemplate.MatchConversationId(myAgent.getRoomId())), 
					MessageTemplate.MatchContent(Constance.CONFIRM_START));
			
			ACLMessage message=myAgent.receive(mt);
			
			if(message != null){
				System.out.println("game will start");
				myAgent.addBehaviour(new LocalGameDaemonBehaviour(myAgent));
				done=true;
				
			}
		}
		
	}



	@Override
	public boolean done() {
		return done;
	}
}
