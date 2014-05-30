package fr.utc.leapband.sma.user;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import fr.utc.leapband.utilities.Constance;
import fr.utc.leapband.utilities.MyAID;

@SuppressWarnings("serial")
public class CreatGroupBehaviour extends Behaviour{
	
	private UserAgent myAgent;
	private ACLMessage msg;
	private boolean first = true;
	private boolean done = false;
	

	public CreatGroupBehaviour(UserAgent myAgent) {
		super();
		this.myAgent = myAgent;
		this.msg=new ACLMessage(ACLMessage.SUBSCRIBE); //content = 104
		System.out.println("create room behaviour created");
	}

	@Override
	public void action() {
		if (first) {
			AID server_name = myAgent.getServerName();
			if (server_name != null){
				msg.clearAllReceiver();
				msg.addReceiver(server_name);
				msg.setContent(Constance.roomselect_Mode);
				MyAID sound_agent = new MyAID(myAgent.getMySoundAgent().getName(), myAgent.getMySoundAgent().getAddressesArray()[0]);	
				msg.setReplyWith(sound_agent.toJSON());	
				myAgent.send(msg);
				first = false;
				System.out.println("Ask for creating room sent");
			} else {
				System.out.println("server not found");
			}
		} else {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CONFIRM);
			ACLMessage message=myAgent.receive(mt);
			
			if(message != null){
				myAgent.addBehaviour(new ModeSelectBehaviour(myAgent, Constance.roomselect_Mode));
				done = true;
				System.out.println("room created, behaviour done");
				myAgent.setRoomId(message.getContent());
				myAgent.addBehaviour(new LocalGameDaemonBehaviour(myAgent));
			}
		}
		
	}

	@Override
	public boolean done() {
		return done;
	}
	

}
