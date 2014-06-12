package fr.utc.leapband.sma.server;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

import fr.utc.leapband.utilities.Constance;

@SuppressWarnings("serial")
public class GetListGameBehaviour extends OneShotBehaviour{
	
	private MultiPlayAgent myAgent;
	private ACLMessage msg;
	

	public GetListGameBehaviour(MultiPlayAgent a,ACLMessage message) {
		super();
		this.myAgent = a;
		this.msg=message;
		
	}


	@Override
	public void action() {
		ACLMessage reply=msg.createReply();
		
		reply.setPerformative(ACLMessage.INFORM);

		reply.setConversationId(Constance.GROUP_CREATED);
		
		try {
			reply.setContentObject(myAgent.getDict());
			System.out.println("set defautlistmodele of reply\n");
			myAgent.send(reply);
			System.out.println("multiAgent sent reply\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
