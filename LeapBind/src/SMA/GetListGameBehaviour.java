package SMA;

import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

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
		
//		ACLMessage message=myAgent.receive();
//		
//		if(message!=null && message.getPerformative()==ACLMessage.REQUEST){
		System.out.println("****"+msg.getContent());
			
			ACLMessage reply=msg.createReply();
			
			reply.setPerformative(jade.lang.acl.ACLMessage.INFORM);
			
			String listString="";
			
			for(String s:myAgent.getDict()){
				listString+=s+",";
			}

			reply.setContent(listString);
			
			myAgent.send(reply);
		

	
		
		
		
		
	}

}
