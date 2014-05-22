package SMA.server;

import java.util.ArrayList;
import java.util.List;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

@SuppressWarnings("serial")
public class MultiPlayRegisterBehaviour extends CyclicBehaviour{
	
	private MultiPlayAgent myAgent;
	private List<AID> MultiPlayUsers;
	
	public MultiPlayRegisterBehaviour (MultiPlayAgent myAgent) {
		super();
		this.myAgent = myAgent;
		this.MultiPlayUsers = myAgent.getMultiPlayUsers();
	}
	
	@Override
	public void action(){
		MessageTemplate mts = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.SUBSCRIBE),
				MessageTemplate.or(MessageTemplate.MatchConversationId("register"),MessageTemplate.MatchConversationId("unregister")));
		ACLMessage subscribe_message = myAgent.receive(mts);
		if(subscribe_message!=null){
			if(subscribe_message.getConversationId()=="register"){
				MultiPlayUsers.add(subscribe_message.getSender());
			}else if(subscribe_message.getConversationId()=="unregister"){
				if(MultiPlayUsers.contains(subscribe_message.getSender())){
					MultiPlayUsers.remove(subscribe_message.getSender());
				}
			}
		myAgent.setMultiPlayUsers(MultiPlayUsers);
		}
	}
}
