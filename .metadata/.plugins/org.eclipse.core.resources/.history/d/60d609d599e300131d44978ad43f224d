package SMA.sound;

import java.io.File;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;

import com.fasterxml.jackson.databind.ObjectMapper;

import Utilities.BackgroundMusicData;
import Utilities.NoteInformData;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


@SuppressWarnings("serial")
public class SoundPlayAgent extends Agent{
	private Synthesizer synthesizer;
	MidiChannel[] channels;
	Sequencer sequencer;
	
	public void setup() {
		try {
			synthesizer = MidiSystem.getSynthesizer();
	        synthesizer.open();
	
	        channels = synthesizer.getChannels();
	        
	        sequencer = MidiSystem.getSequencer();
	        sequencer.open();
	        
	        addBehaviour(new WaitNoteRequestBehaviour());	        
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public class WaitNoteRequestBehaviour extends CyclicBehaviour {

		public void action() {
			MessageTemplate filtre = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
			
			ACLMessage request = myAgent.receive(filtre);
			
			if(request != null) {
				ObjectMapper mapper = new ObjectMapper();
				try {
					NoteInformData data = mapper.readValue(request.getContent(), NoteInformData.class);
					
					switch(data.getAction()) {
						case START_NOTE:
							addBehaviour(new BeginNote(data));
							break;
						case STOP_NOTE:
							addBehaviour(new EndNote(data));
							break;
						case CHANGE_INSTRUMENT:
							addBehaviour(new ChangeSound(data));
							break;
						case DEFAULT:
							break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else 
				block();
		}
	}
	
	public class WaitBackgroundRequestBehaviour extends CyclicBehaviour {
		public void action() {
			MessageTemplate filtre = MessageTemplate.MatchPerformative(ACLMessage.PROPAGATE);
			
			ACLMessage request = myAgent.receive(filtre);
			
			if(request != null) {
				ObjectMapper mapper = new ObjectMapper();
				try {
					BackgroundMusicData data = mapper.readValue(request.getContent(), BackgroundMusicData.class);
					
					switch(data.getAction()) {
						case START_BACKGROUND:
							sequencer.start();
							break;
						case PAUSE_BACKGROUND:
							sequencer.stop();
							break;
						case RESTART_BACKGROUND:
							sequencer.stop();
							sequencer.setTickPosition(0);
							sequencer.start();
							break;
						case CHANGE_BACKGROUND:
							sequencer.stop();
							Sequence sequence = MidiSystem.getSequence(new File(data.getPath()));
							sequencer.setSequence(sequence);
							break;
						case DEFAULT:
							
							break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else 
				block();
		}
	}
	
	public class BeginNote extends OneShotBehaviour {
		private int channel;
		private int note;
		private int velocity;
		
		public BeginNote(NoteInformData data) {
			this.channel = data.getChannel();
			this.note = data.getNote();
			this.velocity = data.getVelocity();
		}

		@Override
		public void action() {
			channels[channel].noteOn(note, velocity);
		}
	}
	
	public class EndNote extends OneShotBehaviour {
		private int channel;
		private int note;
		
		public EndNote(NoteInformData data) {
			this.channel = data.getChannel();
			this.note = data.getNote();
		}

		@Override
		public void action() {
			channels[channel].noteOff(note);
		}
	}
	
	public class ChangeSound extends OneShotBehaviour {
		private int channel;
		private int bank;
		private int instrument;
		
		public ChangeSound(NoteInformData data) {
			this.channel = data.getChannel();
			this.bank = data.getBank();
			this.instrument = data.getInstrument();
		}

		@Override
		public void action() {
			channels[channel].programChange(bank, instrument);
		}
	}
}
