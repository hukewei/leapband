package fr.utc.leapband.sma.move;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.utc.leapband.sma.sound.FindNoteGuitarFromMovement;
import fr.utc.leapband.sma.sound.FindNoteTambourFromMovement;
import fr.utc.leapband.utilities.Constance;
import fr.utc.leapband.utilities.MoveInformData;
import fr.utc.leapband.utilities.NoteInformData;
import fr.utc.leapband.utilities.NoteInformData.NoteActionType;

public class SoundMessageDaemonBehaviour extends CyclicBehaviour{
	
	public SoundMessageDaemonBehaviour() {
		// TODO Auto-generated constructor stub
	}

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
					
					int i;
					int volume;
					switch(moveData.getInstrumentType()) {
					case TAMBOUR:
						
						System.out.println("Tambour");
						
						data.setAction(NoteActionType.START_NOTE);
						
						data.setChannel(9);
						 FindNoteTambourFromMovement drum=new FindNoteTambourFromMovement(moveData.getMove());
						// addBehaviour(be);
						volume =(int) (drum.matchVolume() * moveData.getVelocity_multiplier());
						System.out.println("inside volume = " + drum.matchVolume());
						System.out.println("final volume = " + volume);
						data.setVelocity(volume);
						i= drum.matchNote();
						System.out.println("NOTE " + String.valueOf(i));
						data.setNote(i);
						
						break;
					case PIANO:					
						break;
					case GUITAR:
						System.out.println("Guitar");
						
						data.setAction(NoteActionType.CHANGE_INSTRUMENT);
						
						data.setChannel(0);
						FindNoteGuitarFromMovement guitar=new FindNoteGuitarFromMovement(moveData.getMove());
						volume =(int) (guitar.matchVolume() * moveData.getVelocity_multiplier());
						System.out.println("inside volume = " + guitar.matchVolume());
						System.out.println("final volume = " + volume);
						data.setVelocity(volume);
						i= guitar.matchNote();
						System.out.println("NOTE " + String.valueOf(i));
						data.setNote(i);
						data.setInstrument(Constance.Guitar_Type);
						data.setBank(0);
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
				myAgent.addBehaviour(new SenderInformBehaviour(data, aid));
				
			
		}
		else block();
	}	
}
