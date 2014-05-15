package SMA;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

import Utilities.Constance;

@SuppressWarnings("serial")
/**
 * behaviour to manage a game
 * @author hukewei
 *
 */
public class GameManageBehaviour extends CyclicBehaviour{
	
	private MultiPlayAgent myAgent;
	private ACLMessage msg = null;
	private AID host_name = null;
	private ArrayList<AID> list_member = new ArrayList<AID>();	
	private DefaultListModel<String> dict_player = new DefaultListModel<>();
	private boolean player_changed = false;
	

	public GameManageBehaviour(MultiPlayAgent myAgent,ACLMessage msg) {
		super();
		this.myAgent = myAgent;
		this.msg = msg;
	}

	@Override
	public void action() {	
		if(msg.getContent().equals(Constance.roomselect_Mode)){
			//creat a groupe of game
			int countGroup=myAgent.getDict().size();
			countGroup+=1;
			//update room list
			myAgent.setDict("room"+countGroup);
			setDictPlayer(msg.getSender().getName());
			info_all_player();
			list_member.add(msg.getSender());
			host_name = msg.getSender();
			answer_host_ack();
			msg.setContent("");
			player_changed = true;
			
		} else if (msg.getContent().equals("joinRoom")){
			
		}
		if (player_changed) {
			info_all_player();
			player_changed = false;
		}
		
	}
	
	
	public void setDictPlayer(String item){
		dict_player.addElement(item); 
	}
	
	public void answer_host_ack() {
		ACLMessage reply=msg.createReply();
		reply.setPerformative(ACLMessage.CONFIRM);
		reply.setContent(Constance.ROOM_CREATED);
		myAgent.send(reply);
	}
	
	private void info_all_player() {
		ACLMessage info_player_change = new ACLMessage(ACLMessage.INFORM);
		for (int i = 0; i < list_member.size(); i++) {
			info_player_change.addReceiver(list_member.get(i));
		}
		info_player_change.setSender(myAgent.getAID());
		try {
			info_player_change.setContentObject(dict_player);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		info_player_change.setConversationId(Constance.MEMBER_CHANGE);
		myAgent.send(info_player_change);
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
