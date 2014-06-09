package fr.utc.leapband.sma.move;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.StringWriter;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.utc.leapband.utilities.NoteInformData;

@SuppressWarnings("serial")
public class SenderInformBehaviour extends OneShotBehaviour {
	private AID aid;
	NoteInformData inform;
	private UUID id;

	public SenderInformBehaviour(NoteInformData inform, AID aid) {
		super();
		this.aid=aid;
		this.inform=inform;
		this.id = UUID.randomUUID();
	}

	@Override
	public void action() {
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		try {			

			mapper.writeValue(sw, inform);
			
			message.addReceiver(aid);
			message.setConversationId(id.toString());
			message.setContent(sw.toString());				
			myAgent.send(message);
		} catch (Exception e) {

			e.printStackTrace();
		}			
	}
}
