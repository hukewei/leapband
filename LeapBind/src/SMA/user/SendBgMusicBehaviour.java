package SMA.user;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.StringWriter;

import Utilities.BackgroundMusicData;
import Utilities.BackgroundMusicData.BackgroundMusicActionType;

import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
public class SendBgMusicBehaviour extends OneShotBehaviour{
	UserAgent myAgent;
	String path;
	BackgroundMusicData my_data = new BackgroundMusicData();
	BackgroundMusicActionType type;
	
	public SendBgMusicBehaviour(UserAgent agent, String pat, BackgroundMusicActionType type) {
		this.myAgent = agent;
		this.path = pat;
		this.type = type;
	}

	@Override
	public void action() {
		ACLMessage message = new ACLMessage(ACLMessage.PROPAGATE);
		message.addReceiver(myAgent.getSoundAgentName());
		my_data.setAction(type);
		my_data.setPath(path);

		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		try {			
			mapper.writeValue(sw, my_data);
			message.setContent(sw.toString());				
		} catch (Exception e) {
			e.printStackTrace();
		}
		myAgent.send(message);
		System.out.println("bg message sent");
	}

}
