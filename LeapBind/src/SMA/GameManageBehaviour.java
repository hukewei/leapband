package SMA;

import java.io.IOException;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class GameManageBehaviour extends CyclicBehaviour{
	
	private MultiPlayAgent myAgent;
	private ACLMessage msg;
	private ACLMessage msgResponse;
	
	

	public GameManageBehaviour(MultiPlayAgent myAgent,ACLMessage msg) {
		super();
		this.myAgent = myAgent;
		this.msg=msg;
	}



	@Override
	public void action() {
		if(msg.getContent().equals("creatRoom")){
			//creat a groupe of game
			int countGroup=myAgent.getDict().size();
			countGroup+=1;
			myAgent.setDict("room"+countGroup);
			
			
		}else if(msg.getContent().equals("joinRoom")){
			
		}
		
	}
	
	public void answer(){
		
		ACLMessage reply=msg.createReply();
		
		reply.setPerformative(jade.lang.acl.ACLMessage.INFORM);
		
		//String listString="";
		
		/*for(String s:myAgent.getDict()){
			listString+=s+",";
		}*/

		try {
			reply.setContentObject(myAgent.getDict());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		myAgent.send(reply);
	}

}
