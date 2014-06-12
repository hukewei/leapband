package fr.utc.leapband.sma.sound;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;
import java.util.Timer;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.utc.leapband.utilities.Constance;
import fr.utc.leapband.utilities.GuitarChordDemo;
import fr.utc.leapband.utilities.GuitarTuning;
import fr.utc.leapband.utilities.NoteInformData;


@SuppressWarnings("serial")
public class SoundPlayAgent extends Agent{
	private Synthesizer synthesizer;
	MidiChannel[] channels;
	Sequencer sequencer;
	
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
					if (Constance.ENABLE_DELAY && System.currentTimeMillis() - data.getTimestamp() < Constance.MAX_MESSAGE_DELAY) {
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
		if (instrument!=Constance.Guitar_Type){
			channels[channel].programChange(bank, instrument);
			channels[channel].noteOn(note, velocity);
		}else
		{
			
			new Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			            	ArrayList<Integer> eMajor =GetSound (note);
			            	ArrayList<ArrayList<Integer>> chords = new ArrayList<ArrayList<Integer>>();
			    			chords.add(eMajor);// chords.add(aMajor);
			    			
			    			final int instrument = Constance.Guitar_Type; // SEE http://soundprogramming.net/file_formats/general_midi_instrument_list
			    			
			    		        //GuitarChordSequence mini = new GuitarChordSequence();
			    			GuitarChordDemo mini = new GuitarChordDemo();
			    		        mini.playChord(instrument, eMajor, velocity);
			    			}
			        }, 
			        0 
			);
		}
			
			
		}

	private ArrayList<Integer> GetSound(int note){
    	GuitarTuning gt = new GuitarTuning();
		ArrayList<Integer> eMajor = new ArrayList<Integer>();
		switch (note){

		case 61: //"EM"
		
			eMajor.add(gt.midiNum(6,0));
			eMajor.add(gt.midiNum(5,2));
			eMajor.add(gt.midiNum(4,2));
			eMajor.add(gt.midiNum(3,0));
			eMajor.add(gt.midiNum(2,0));
			eMajor.add(gt.midiNum(1,0));
			break;
		
		case 62://AM
		
			eMajor.add(gt.midiNum(6,0));
			eMajor.add(gt.midiNum(5,0));
			eMajor.add(gt.midiNum(4,2));
			eMajor.add(gt.midiNum(3,2));
			eMajor.add(gt.midiNum(2,1));
			eMajor.add(gt.midiNum(1,0));
			break;
		case 63://DM
			
			eMajor.add(gt.midiNum(6,0));
			eMajor.add(gt.midiNum(5,0));
			eMajor.add(gt.midiNum(4,0));
			eMajor.add(gt.midiNum(3,2));
			eMajor.add(gt.midiNum(2,3));
			eMajor.add(gt.midiNum(1,2));
			break;
		case 64: //G
			
			eMajor.add(gt.midiNum(6,3));
			eMajor.add(gt.midiNum(5,2));
			eMajor.add(gt.midiNum(4,0));
			eMajor.add(gt.midiNum(3,0));
			eMajor.add(gt.midiNum(2,0));
			eMajor.add(gt.midiNum(1,3));
			break;
		case 65://C"
			
			eMajor.add(gt.midiNum(6,3));
			eMajor.add(gt.midiNum(5,3));
			eMajor.add(gt.midiNum(4,2));
			eMajor.add(gt.midiNum(3,0));
			eMajor.add(gt.midiNum(2,1));
			eMajor.add(gt.midiNum(1,0));
			break;
		case 66://F
			
			eMajor.add(gt.midiNum(6,1));
			eMajor.add(gt.midiNum(5,3));
			eMajor.add(gt.midiNum(4,3));
			eMajor.add(gt.midiNum(3,2));
			eMajor.add(gt.midiNum(2,1));
			eMajor.add(gt.midiNum(1,1));
			break;
		case 67://Bb
			
			eMajor.add(gt.midiNum(6,1));
			eMajor.add(gt.midiNum(5,1));
			eMajor.add(gt.midiNum(4,3));
			eMajor.add(gt.midiNum(3,3));
			eMajor.add(gt.midiNum(2,3));
			eMajor.add(gt.midiNum(1,1));
			break;
		case 68://Bdim
			
			eMajor.add(gt.midiNum(6,2));
			eMajor.add(gt.midiNum(5,2));
			eMajor.add(gt.midiNum(4,4));
			eMajor.add(gt.midiNum(3,4));
			eMajor.add(gt.midiNum(2,3));
			eMajor.add(gt.midiNum(1,2));
			break;
		case 60:
			break;
			
		}
		
		return eMajor;
		
	}
	
	
	}
}
