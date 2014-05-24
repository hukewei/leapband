package SMA.user;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import Utilities.Constance;

@SuppressWarnings("serial")
public class StartGameBehaviour extends OneShotBehaviour{
	
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
		}
	}
}
