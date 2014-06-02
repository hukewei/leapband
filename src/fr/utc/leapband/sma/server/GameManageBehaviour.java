package fr.utc.leapband.sma.server;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;

import fr.utc.leapband.utilities.Constance;
import fr.utc.leapband.utilities.MyAID;

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
	private AID host_sound_name = null;
	private ACLMessage host_msg = null;
	private ArrayList<AID> list_member = new ArrayList<AID>();	
	private Map<AID,AID> list_member_map = new HashMap<AID,AID>();
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
		MessageTemplate mt = MessageTemplate.and(MessageTemplate.or(MessageTemplate.or(
				MessageTemplate.MatchPerformative(ACLMessage.REQUEST), 
				MessageTemplate.MatchPerformative(ACLMessage.SUBSCRIBE)),
				MessageTemplate.MatchPerformative(ACLMessage.CANCEL)
				), 
				MessageTemplate.MatchConversationId("Room" + room_id));
		ACLMessage message = myAgent.receive(mt);
		if (message != null) {
			if (message.getPerformative() == ACLMessage.SUBSCRIBE) {
				System.out.println("asking for entering a existed room");
				if (message.getContent().equals(Constance.EnterGroupMode)){
					setPlayerDict(message.getSender().getName());
					list_member.add(message.getSender());
					list_member_map.put(message.getSender(),MyAID.toAID(message.getReplyWith()));
					answer_guest_ack(message);
					player_changed = true;
				}
			} else if (message.getPerformative() == ACLMessage.CANCEL) {
				System.out.println("asking for quiting a existed room");
				if (message.getContent().equals(Constance.ExitGroupMode)){
					removePlayerDict(message.getSender().getName());
					list_member.remove(message.getSender());
					list_member_map.remove(message.getSender());
					answer_exit_req(message);
					player_changed = true;
					if (list_member.size() == 0) {
						game_over = true;
						myAgent.getDict().removeElement(conversation_id);
					} else if(message.getSender().equals(host_name)){
						info_multiplay_users();
						//change host to the next one
						host_name = list_member.get(0);
						
						ACLMessage inform_host = new ACLMessage(ACLMessage.INFORM);
						inform_host.addReceiver(host_name);
						inform_host.setConversationId("StartVisibility");
						inform_host.setContent("true");
						myAgent.send(inform_host);
						
					}
				}
			} else if(message.getPerformative()==ACLMessage.REQUEST){
				if(message.getContent().equals(Constance.START_GAME) && message.getSender().equals(host_name)){
					info_all_player_start_game(message);
					myAgent.getDict().removeElement(conversation_id);
					info_multiplay_users();
				}
			}
		}
		if(initialize){
			//creat a groupe of game
//			int countGroup=myAgent.getDict().size();
//			countGroup+=1;
			//update room list
			conversation_id = "Room"+room_id;
			myAgent.setDict(conversation_id);
			info_multiplay_users();
			setPlayerDict(host_msg.getSender().getName());
			//info_all_player();
			list_member.add(host_msg.getSender());
			list_member_map.put(host_msg.getSender(),MyAID.toAID(host_msg.getReplyWith()));
			host_name = host_msg.getSender();
			System.out.println(host_msg.getReplyWith());
			host_sound_name = MyAID.toAID(host_msg.getReplyWith());
			answer_host_ack();
			player_changed = true;
			initialize = false;	
		}
		if (player_changed) {
			info_all_player();
			player_changed = false;
		}	
	}
	
	
	public void setPlayerDict(String item){
		dict_player.addElement(item); 
	}
	
	public void removePlayerDict(String item){
		dict_player.removeElement(item);
	}
	
	public void answer_host_ack() {
		ACLMessage reply=host_msg.createReply();
		reply.setPerformative(ACLMessage.CONFIRM);
		reply.setContent(conversation_id);
		System.out.println("send host ack");
		myAgent.send(reply);
	}
	
	public void answer_guest_ack(ACLMessage message) {
		ACLMessage reply=message.createReply();
		reply.clearAllReceiver();
		reply.addReceiver(message.getSender());
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
	
	private void info_multiplay_users(){
		ACLMessage info_multiplay_users = new ACLMessage(ACLMessage.INFORM);
		for(int i=0; i<myAgent.getMultiPlayUsers().size();i++){
			info_multiplay_users.addReceiver(myAgent.getMultiPlayUsers().get(i));
		}
		info_multiplay_users.setSender(myAgent.getAID());
		try {
			info_multiplay_users.setContentObject(myAgent.getDict());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		info_multiplay_users.setConversationId("updateDICT");
		myAgent.send(info_multiplay_users);
	}
	
	private void info_all_player_start_game(ACLMessage m) {
		ACLMessage info_game_start = new ACLMessage(ACLMessage.CONFIRM);
		for (int i = 0; i < list_member.size(); i++) {
			info_game_start.addReceiver(list_member.get(i));
		}
		info_game_start.setConversationId(m.getConversationId());
		info_game_start.setSender(myAgent.getAID());
		MyAID sound_agent = new MyAID(host_sound_name.getName(), host_sound_name.getAddressesArray()[0]);
		info_game_start.setReplyWith(sound_agent.toJSON());
		info_game_start.setContent(Constance.CONFIRM_START);
		myAgent.send(info_game_start);
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
