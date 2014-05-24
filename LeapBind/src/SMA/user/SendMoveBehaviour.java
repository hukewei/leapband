package SMA.user;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.StringWriter;

import Utilities.Cordinates;
import Utilities.MoveInformData;
import Utilities.Movement;
import Utilities.Point;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SendMoveBehaviour extends OneShotBehaviour{
	UserAgent myAgent;
	Cordinates position;
	MoveInformData my_data = null;
	Movement move = null;
	Point pos = null;
	
	public SendMoveBehaviour(UserAgent agent, Cordinates pos) {
		myAgent = agent;
		position = pos;
	}

	@Override
	public void action() {
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		message.addReceiver(myAgent.getNoteAgentName());
		move.setDirection(null);
		move.setSpeed(position.speed);
		pos.X = position.x;
		pos.Y = position.y;
		pos.Z = position.z;
		move.setPos(pos);
		my_data.setMove(move);
		my_data.setHost_AID(myAgent.getSoundAgentName());
		my_data.setInstrumentType(myAgent.getSelectedInstrument());

		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		try {			
			mapper.writeValue(sw, my_data);
			message.setContent(sw.toString());				
		} catch (Exception e) {
			e.printStackTrace();
		}
		myAgent.send(message);
	}

}
