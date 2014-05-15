package SMA;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.IOException;
import java.util.ArrayList;

import Utilities.Constance;

@SuppressWarnings("serial")
/**
 * behaviour to manage a game
 * @author hukewei
 *
 */
public class GameManageBehaviour extends CyclicBehaviour{
	
	private MultiPlayAgent myAgent;
	private ACLMessage msg;
	private ACLMessage msgResponse;
	private AID host_name = null;
	private ArrayList<AID> list_member = new ArrayList<AID>();	
	

	public GameManageBehaviour(MultiPlayAgent myAgent,ACLMessage msg) {
		super();
		this.myAgent = myAgent;
		this.msg = msg;
	}

	@Override
	public void action() {
		if(msg.getContent().equals("104")){
			//creat a groupe of game
			int countGroup=myAgent.getDict().size();
			countGroup+=1;
			//update room list
			myAgent.setDict("room"+countGroup);
			list_member.add(msg.getSender());
			host_name = msg.getSender();
			answer_host_ack();
			
		} else if(msg.getContent().equals("joinRoom")){
			
		}
		
	}
	
	public void answer_host_ack() {
		ACLMessage reply=msg.createReply();
		reply.setPerformative(ACLMessage.CONFIRM);
		reply.setContent(Constance.ROOM_CREATED);
		myAgent.send(reply);
	}
	
	
	public void answer(){
		
		ACLMessage reply=msg.createReply();
		
		reply.setPerformative(ACLMessage.INFORM);
		
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
