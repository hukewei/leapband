package SMA.server;




import java.io.StringWriter;
import java.util.UUID;

import javax.vecmath.Point3d;

import com.fasterxml.jackson.databind.ObjectMapper;

import SMA.sound.FindNoteTambourFromMovement;
import Utilities.InstrumentType;
import Utilities.MoveInformData;
import Utilities.Movement;
import Utilities.NoteInformData;
import Utilities.NoteInformData.NoteActionType;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


@SuppressWarnings("serial")
public class MoveToSoundAgent extends Agent{
	
	protected void setup() {
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Organisation");
		sd.setName("MoveToNote");
		dfd.addServices(sd);
		try {
		DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
		fe.printStackTrace();
		}
		
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
					NoteInformData data = new NoteInformData();
					try {
						MoveInformData moveData = mapper.readValue(message.getContent(), MoveInformData.class);
	
 				/*		//TEST POUR AGENT
						MoveInformData moveData= new MoveInformData();
						Point3d p3d= new Point3d(375,270,0);
						Movement move= new Movement();
						move.setPos(p3d);
						move.setSpeed(2000);
						moveData.setMove(move);
						moveData.setInstrumentType(InstrumentType.TAMBOUR);*/
						
						
						switch(moveData.getInstrumentType()) {
						case TAMBOUR:
							
							System.out.println("Tambour");
							
							data.setAction(NoteActionType.START_NOTE);
							
							data.setChannel(9);
							 FindNoteTambourFromMovement drum=new FindNoteTambourFromMovement(moveData.getMove());
							// addBehaviour(be);
							int volume =drum.matchVolume();
							data.setVelocity(volume);
							int i= drum.matchNote();
							System.out.println("NOTE " + String.valueOf(i));
							data.setNote(i);
							
							break;
						case PIANO:
							break;
						case GUITAR:
							break;
						case DEFAULT:
							break;
					}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					

					AID aid = (AID)message.getAllReplyTo().next();
					
					//AID aid=new AID("SoundPlayer", AID.ISLOCALNAME);		
					//AID aid=getReceiver();
					addBehaviour(new SenderInformBehaviour(data, aid));
					
				
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

	public AID getReceiver() {
		AID rec = null;
		DFAgentDescription template =
		new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Sound");
		sd.setName("SoundPlay");
		template.addServices(sd);
		try {
			DFAgentDescription[] result = DFService.search(this, template);
			
			if (result.length > 0) {
				System.out.println("Nombre de resultat : " + String.valueOf(result.length));
				int i = (int)(Math.random() * result.length);
				System.out.println("Valeur de i : " + String.valueOf(i));
				rec = result[i].getName();
			}
		} catch(FIPAException fe) {
			fe.printStackTrace();
		}
		return rec;
	}
}