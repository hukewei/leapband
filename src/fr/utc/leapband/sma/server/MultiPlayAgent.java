package fr.utc.leapband.sma.server;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;



@SuppressWarnings("serial")
public class MultiPlayAgent extends Agent{
	
	private DefaultListModel<String> dict;
	private List<AID> MultiPlayUsers;
	private int room_ids = 0;

	public int getRoom_ids() {
		return room_ids;
	}

	public void setRoom_ids(int room_ids) {
		this.room_ids = room_ids;
	}
	
	public int generateRoomId() {
		return ++room_ids;
	}

	protected void setup() {
		super.setup();
		MultiPlayUsers= new ArrayList<AID>();
		
		System.out.println(getLocalName()+"--> Installed");
		
		DFAgentDescription dfd=new DFAgentDescription();

		dfd.setName(getAID());

		ServiceDescription sd=new ServiceDescription();

		sd.setType("Organisation");

		sd.setName("Multiplay");

		dfd.addServices(sd);

		try{

			DFService.register(this, dfd);

		}catch(FIPAException fe){

			fe.printStackTrace();

		}
		//addBehaviour(new GetListGameBehaviour(this));
		addBehaviour(new MultiPlayManageBehaviour(this));
		addBehaviour(new GameDaemonBehaviour(this));
		addBehaviour(new SubscribeManageBehaviour(this));
		
	}

	public DefaultListModel<String> getDict(){
		if (dict == null) {
			dict = new DefaultListModel<String>();
//			dict.addElement("room1");
//			dict.addElement("room2");
//			dict.addElement("room3");
//			dict.addElement("room4");
		}
		return dict;
	}
	
	public void setDict(String item){
		dict.addElement(item); 
	}
	
	public List<AID> getMultiPlayUsers(){
		return MultiPlayUsers;
	}
	
	public void addMultiPlayUser(AID user){
		MultiPlayUsers.add(user);	
	}
	
	public void removeMultiPlayUser(AID user){
		if(MultiPlayUsers.contains(user)) MultiPlayUsers.remove(user);	
	}
}