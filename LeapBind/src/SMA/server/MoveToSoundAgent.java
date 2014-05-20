package SMA.server;




import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Utilities.MoveInformData;
import Utilities.NoteInformData;
import Utilities.NoteInformData.NoteActionType;
import jade.core.AID;
import jade.core.Agent;
import jade.core.Timer;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


public class MoveToSoundAgent extends Agent{
	
	protected void setup() {
		
		
		this.addBehaviour(new SoundMessageDaemonBehaviour() );	
		
	}

	public class SoundMessageDaemonBehaviour extends CyclicBehaviour{

		@Override
		public void action() {
			MessageTemplate filtre = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
			ACLMessage message = myAgent.receive(filtre);
			
			if(message != null)
			{
					ObjectMapper mapper = new ObjectMapper();
					try {
						MoveInformData moveData = mapper.readValue(message.getContent(), MoveInformData.class);
						
						switch(moveData.getInstrumentType()) {
							case TAMBOUR:
								break;
							case PIANO:
								break;
							case GUITAR:
								break;
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					

					//AID aid = (AID)message.getAllReplyTo().next();
					/*
					AID aid=new AID("SoundPlayer", AID.ISLOCALNAME);
					int index =(int) ((Math.random()*9)+60);
					
					NoteInformData data = new NoteInformData();
					data.setAction(NoteActionType.START_NOTE);
					data.setVelocity(255);
					data.setChannel(0);
					data.setNote(index);
					
					addBehaviour(new SenderInformBehaviour(data, aid));
					*/
				
			}
			else block();
		}	
	}


	/*

	public class MessageDaemonBehaviour extends CyclicBehaviour {

		@Override
		public void action() {
			MessageTemplate filtre = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
			ACLMessage message = myAgent.receive(filtre);
			
			if(message != null)
			{
				ObjectMapper mapper = new ObjectMapper();
				
				try {

					
					AID aid = (AID)message.getAllReplyTo().next();
					//MoveToSound ();
					
					addBehaviour(new SenderInformBehaviour(current_note, aid));

				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			else
				block();
			
			
		}
		
		
	}
	
		*/
	
	
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

}