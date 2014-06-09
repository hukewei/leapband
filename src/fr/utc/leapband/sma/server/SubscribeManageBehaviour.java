package fr.utc.leapband.sma.server;

import fr.utc.leapband.utilities.Constance;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

@SuppressWarnings("serial")
public class SubscribeManageBehaviour extends CyclicBehaviour{
	MultiPlayAgent myAgent;
	
	public SubscribeManageBehaviour(MultiPlayAgent myAgent) {
		super();
		this.myAgent = myAgent;	
	}

	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.SUBSCRIBE), 
		 						MessageTemplate.MatchContent(Constance.roomselect_Mode));

		ACLMessage message=myAgent.receive(mt);
		
		if (message != null) {
			System.out.println("subscrib received");
			System.out.println(message.getContent());
			if (message.getContent().equals(Constance.roomselect_Mode)) {
				System.out.println("code 104 matched");
				System.out.println("receive userAgent subscription");
				//UserAgent ask for creating a new room
				myAgent.addBehaviour(new GameManageBehaviour(myAgent, message));
			}
		}
	}

}
