package agents;


import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.NoteInformData;
import jade.core.AID;
import jade.core.Agent;
import jade.core.Timer;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


public class MoveToSoundAgent extends Agent{
	private String current_note;
	private String current_instrument;
	private NoteInformData inform_note;
	private NoteInformData inform_note1;
	private NoteInformData inform_note2;
	private NoteInformData inform_note3;
	private NoteInformData inform_note4;
	private NoteInformData inform_note5;
	private NoteInformData inform_note6;
	private NoteInformData inform_note7;
	private NoteInformData inform_note8;
	private ArrayList<NoteInformData> noteslist;
	protected void setup() {
		
		//CDEFGAB 
		inform_note = new NoteInformData("C5W", "Guitar",1);
		inform_note1 = new NoteInformData("D5W", "Guitar",1);
		inform_note2 = new NoteInformData("E5W", "Piano",1);
		inform_note3 = new NoteInformData("F5W", "Piano",1);
		inform_note4 = new NoteInformData("G5W", "Ocarina",1);
		inform_note5 = new NoteInformData("[Crash_Cymbal_1]q", "Percussion",1);
		inform_note6 = new NoteInformData("[Hand_Clap]q", "Percussion",1);
		inform_note7 = new NoteInformData("[COWBELL]q", "Percussion",1);
		//inform_note7 = new NoteInformData("G5W", "Ocarina",1);
		inform_note8 = new NoteInformData("A5W", "Ocarina",1);
		noteslist=new ArrayList<NoteInformData>();
		noteslist.add(inform_note1);
		noteslist.add(inform_note2);
		noteslist.add(inform_note3);
		noteslist.add(inform_note4);
		noteslist.add(inform_note5);
		noteslist.add(inform_note6);
		noteslist.add(inform_note7);
		noteslist.add(inform_note8);
		noteslist.add(inform_note);
		
		this.addBehaviour(new MessageDaemonBehaviour() );	
		
	}
	public class MessageDaemonBehaviour extends CyclicBehaviour{

		@Override
		public void action() {
			MessageTemplate filtre = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
			ACLMessage message = myAgent.receive(filtre);
			
			if(message != null)
			{
				
					

					//AID aid = (AID)message.getAllReplyTo().next();
					AID aid=new AID("SoundPlayer", AID.ISLOCALNAME);
					int index =(int) (Math.random()*9);
					addBehaviour(new SenderInformBehaviour(noteslist.get(index), aid));
					//addBehaviour(new SenderInformBehaviour(inform_note2, aid));
					//addBehaviour(new SenderInformBehaviour(inform_note3, aid));
					//addBehaviour(new SenderInformBehaviour(inform_note4, aid));
					//addBehaviour(new SenderInformBehaviour(inform_note5, aid));
					//addBehaviour(new SenderInformBehaviour(inform_note6, aid));
					//addBehaviour(new SenderInformBehaviour(inform_note7, aid));
					//addBehaviour(new SenderInformBehaviour(inform_note8, aid));
					 
					

			}
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
		private boolean Done;
		private AID aid;
		NoteInformData inform;
		private UUID id;
	
		public SenderInformBehaviour(NoteInformData inform, AID aid) {
			super();
			this.aid=aid;
			this.inform=inform;
			this.id = UUID.randomUUID();
			Done = false;
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
