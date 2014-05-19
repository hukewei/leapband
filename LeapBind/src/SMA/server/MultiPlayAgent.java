package SMA.server;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;


import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;



@SuppressWarnings("serial")
public class MultiPlayAgent extends Agent{
	
	private DefaultListModel<String> dict;
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
		addBehaviour(new GameDaemonBehaviour(this));
		
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
		if(!dict.contains(item))dict.addElement(item); 
	}
}