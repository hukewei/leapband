package fr.utc.leapband.sma.server;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
/*EST-CE QUE JE PEUX METTRE LE CODE DE GAMEDAEMONBEHAVIOUR ICI? */
@SuppressWarnings("serial")
public class MultiPlayManageBehaviour extends CyclicBehaviour {
	
	private MultiPlayAgent myAgent;
	
	public MultiPlayManageBehaviour (MultiPlayAgent myAgent){
		this.myAgent = myAgent;
	}
	
	@Override
	public void action() {
		// TODO Auto-generated method stub
		MessageTemplate mts = 
				MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.SUBSCRIBE),
									MessageTemplate.or(
											MessageTemplate.MatchConversationId("register"),
											MessageTemplate.MatchConversationId("unregister")));
		ACLMessage subscribe_message = myAgent.receive(mts);
		if(subscribe_message!=null){
			if(subscribe_message.getConversationId().equals("register")){
				myAgent.addMultiPlayUser(subscribe_message.getSender());
			}else if(subscribe_message.getConversationId().equals("unregister")){
				myAgent.removeMultiPlayUser(subscribe_message.getSender());
			}
		}
	}

}
