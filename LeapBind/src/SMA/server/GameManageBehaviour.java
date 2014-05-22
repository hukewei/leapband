package SMA.server;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

import Utilities.Constance;

@SuppressWarnings("serial")
/**
 * behaviour to manage a game
 * @author hukewei
 *
 */
public class GameManageBehaviour extends Behaviour{
	
	private MultiPlayAgent myAgent;
	private ACLMessage msg = null;
	private AID host_name = null;
	private ACLMessage host_msg = null;
	private ArrayList<AID> list_member = new ArrayList<AID>();
	private DefaultListModel<String> dict_player = new DefaultListModel<>();
	private boolean player_changed = false;
	private int room_id = 0;
	private boolean initialize = true;
	private String conversation_id = null;
	private boolean game_over = false;

	public GameManageBehaviour(MultiPlayAgent myAgent, ACLMessage host_msg) {
		super();
		this.myAgent = myAgent;
		this.room_id = myAgent.generateRoomId();
		this.host_msg = host_msg;
	}

	@Override
	public void action() {
		ACLMessage message=myAgent.receive();
		if (message != null) {
			if(message.getPerformative() == ACLMessage.REQUEST){
				System.out.println("asking for creating a new room");
				if(message.getContent().equals(Constance.roomselect_Mode)){
					conversation_id = "Room"+room_id;
					myAgent.setDict(conversation_id);
					setPlayerDict(host_msg.getSender().getName());
					list_member.add(host_msg.getSender());
					host_name = host_msg.getSender();
					answer_host_ack();
					info_all_player();
					info_all_MPusers();
				}
			}else if (message.getPerformative() == ACLMessage.SUBSCRIBE&&message.getConversationId().equals("Room" + room_id)) {
				System.out.println("asking for entering an existing room");
				if (message.getContent().equals(Constance.EnterGroupMode)){
					setPlayerDict(message.getSender().getName());
					list_member.add(message.getSender());
					answer_guest_ack(message);
					info_all_player();
				}
			} else if (message.getPerformative() == ACLMessage.CANCEL&&message.getConversationId().equals("Room" + room_id)) {
				System.out.println("asking for quitting an existing room");
				if (message.getContent().equals(Constance.ExitGroupMode)){
					removePlayerDict(message.getSender().getName());
					list_member.remove(message.getSender());
					answer_exit_req(message);
					if (list_member.size() == 0) {
						game_over = true;
						myAgent.getDict().removeElement(conversation_id);
						info_all_MPusers();
					} else {
						//change host to the next one
						host_name = list_member.get(0);
					}
					info_all_player();
				}
			}
		}
	}
	
	public void setPlayerDict(String item){
		if(!dict_player.contains(item))dict_player.addElement(item); 
	}
	
	public void removePlayerDict(String item){
		dict_player.removeElement(item);
	}
	
	public void answer_host_ack() {
		ACLMessage reply=host_msg.createReply();
		reply.setPerformative(ACLMessage.CONFIRM);
		reply.setContent(conversation_id);
		myAgent.send(reply);
	}
	
	public void answer_guest_ack(ACLMessage message) {
		ACLMessage reply=message.createReply();
		reply.setPerformative(ACLMessage.CONFIRM);
		reply.setContent(Constance.ROOM_ENTERED);
		myAgent.send(reply);
	}
	
	public void answer_exit_req(ACLMessage message) {
		ACLMessage reply=message.createReply();
		reply.setPerformative(ACLMessage.CONFIRM);
		reply.setContent(Constance.ROOM_QUITTED);
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
	private void info_all_MPusers(){
		ACLMessage info_all_MPusers = new ACLMessage(ACLMessage.INFORM);
		
		for(AID aid:myAgent.getMultiPlayUsers()){
			info_all_MPusers.addReceiver(aid);
		}
		
		info_all_MPusers.setSender(myAgent.getAID());
		info_all_MPusers.setConversationId("updateDICT");
		try {
			info_all_MPusers.setContentObject(myAgent.getDict());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myAgent.send(info_all_MPusers);
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

	@Override
	public boolean done() {
		return game_over;
	}

}
