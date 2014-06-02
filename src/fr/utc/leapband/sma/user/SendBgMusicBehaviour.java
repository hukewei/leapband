package fr.utc.leapband.sma.user;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.StringWriter;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.utc.leapband.utilities.BackgroundMusicData;
import fr.utc.leapband.utilities.BackgroundMusicData.BackgroundMusicActionType;

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
