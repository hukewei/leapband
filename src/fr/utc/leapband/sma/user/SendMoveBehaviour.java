package fr.utc.leapband.sma.user;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.StringWriter;

import javax.vecmath.Point3d;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.utc.leapband.utilities.Cordinates;
import fr.utc.leapband.utilities.MoveInformData;
import fr.utc.leapband.utilities.Movement;

public class SendMoveBehaviour extends OneShotBehaviour{
	UserAgent myAgent;
	Cordinates position;
	MoveInformData my_data = new MoveInformData();
	Movement move = new Movement();
	Point3d pos = new Point3d();
	float velocity;
	
	public SendMoveBehaviour(UserAgent agent, Cordinates pos, float vel) {
		myAgent = agent;
		position = pos;
		velocity = vel;
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
		my_data.setVelocity_multiplier(velocity);
		message.clearAllReplyTo();
		message.addReplyTo(myAgent.getSoundAgentName());
		//my_data.setHost_AID(myAgent.getSoundAgentName());
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
