package fr.utc.leapband.sma.sound;

import java.util.ArrayList;
import java.util.Timer;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.utc.leapband.utilities.GuitarChordSequence;
import fr.utc.leapband.utilities.GuitarTuning;
import fr.utc.leapband.utilities.NoteInformData;


@SuppressWarnings("serial")
public class SoundPlayAgent extends Agent{
	private Synthesizer synthesizer;
	MidiChannel[] channels;
	Sequencer sequencer;
	private MusicPlayer player = null;
	private int step = 0;
	
	public void setup() {
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Sound");
		sd.setName("SoundPlay");
		dfd.addServices(sd);
		try {
		DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
		fe.printStackTrace();
		}
		
		try {
			synthesizer = MidiSystem.getSynthesizer();
	        synthesizer.open();
	
	        channels = synthesizer.getChannels();
	        
	        sequencer = MidiSystem.getSequencer();
	        sequencer.open();
	        
	        addBehaviour(new WaitNoteRequestBehaviour());	
	        addBehaviour(new WaitBackgroundRequestBehaviour());
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
		private int note;
		private int velocity;
		
		public ChangeSound(NoteInformData data) {
			this.channel = data.getChannel();
			this.bank = data.getBank();
			this.instrument = data.getInstrument();
			this.note = data.getNote();
			this.velocity = data.getVelocity();
		}

		@Override
		public void action() {
			channels[channel].programChange(bank, instrument);
			channels[channel].noteOn(note, velocity);
			new Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			            	GuitarTuning gt = new GuitarTuning();
			    			
			    			ArrayList<Integer> eMajor = new ArrayList<Integer>();
			    			
			    			eMajor.add(gt.midiNum(6,0));
			    			eMajor.add(gt.midiNum(5,2));
			    			eMajor.add(gt.midiNum(4,2));
			    			eMajor.add(gt.midiNum(3,1));
			    			eMajor.add(gt.midiNum(2,0));
			    			eMajor.add(gt.midiNum(1,0));
			    			
			    			ArrayList<Integer> aMajor = new ArrayList<Integer>();
			    			
			    			aMajor.add(gt.midiNum(6,0));
			    			aMajor.add(gt.midiNum(5,0));
			    			aMajor.add(gt.midiNum(4,2));
			    			aMajor.add(gt.midiNum(3,2));
			    			aMajor.add(gt.midiNum(2,2));
			    			aMajor.add(gt.midiNum(1,0));
			    			
			    			
			    			ArrayList<ArrayList<Integer>> chords = new ArrayList<ArrayList<Integer>>();
			    			
			    			chords.add(eMajor);// chords.add(aMajor);
			    			
			    			final int instrument = 25; // SEE http://soundprogramming.net/file_formats/general_midi_instrument_list
			    			
			    		        GuitarChordSequence mini = new GuitarChordSequence();
			    		        mini.playChords(instrument, chords);
			    			}
			        }, 
			        0 
			);
			
		}
	}
}
