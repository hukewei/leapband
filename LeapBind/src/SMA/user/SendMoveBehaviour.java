package SMA.user;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.StringWriter;

import javax.vecmath.Point3d;

import Utilities.Cordinates;
import Utilities.MoveInformData;
import Utilities.Movement;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SendMoveBehaviour extends OneShotBehaviour{
	UserAgent myAgent;
	Cordinates position;
	MoveInformData my_data = new MoveInformData();
	Movement move = new Movement();
	Point3d pos = new Point3d();
	
	public SendMoveBehaviour(UserAgent agent, Cordinates pos) {
		myAgent = agent;
		position = pos;
	}

	@Override
	public void action() {
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		message.addReceiver(myAgent.getNoteAgentName());
		//move.setDirection(position.direction);
		move.setSpeed(position.speed);
		pos.x = position.x;
		pos.y = position.y;
		pos.z = position.z;
		move.setPos(pos);
		my_data.setMove(move);
		System.out.println("sound agent aid = " + myAgent.getSoundAgentName());
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
