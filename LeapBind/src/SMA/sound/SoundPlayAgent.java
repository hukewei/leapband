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
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


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
	
public class WaitBackgroundRequestBehaviour extends CyclicBehaviour {
		public void action() {
			MessageTemplate filtre = MessageTemplate.MatchPerformative(ACLMessage.PROPAGATE);
			
			ACLMessage request = myAgent.receive(filtre);
			
			if(request != null) {
				ObjectMapper mapper = new ObjectMapper();
				try {
					BackgroundMusicData data = mapper.readValue(request.getContent(), BackgroundMusicData.class);
					/*BackgroundMusicData data = new BackgroundMusicData();
					System.out.println(step);
					if(step == 0) {
						data.setAction(BackgroundMusicData.BackgroundMusicActionType.CHANGE_BACKGROUND);
						data.setPath("/Users/Alexandre/Desktop/Life Is Like A Song.mp3");
						step++;
					} else if(step == 1) {
						data.setAction(BackgroundMusicData.BackgroundMusicActionType.START_BACKGROUND);
						step++;
					} else {
						data.setAction(BackgroundMusicData.BackgroundMusicActionType.PAUSE_BACKGROUND);
						step = 1;
					}*/
					
					switch(data.getAction()) {
						case START_BACKGROUND:
							player.Start();
							break;
						case PAUSE_BACKGROUND:
							player.Pause();
							break;
						case RESTART_BACKGROUND:
							player.Restart();
							break;
						case CHANGE_BACKGROUND:
							player = MusicPlayer.getMusicPlayer(data.getPath());
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
















/*package SMA.sound;

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
	private MidiChannel[] channels;
	private MusicPlayer player = null;
	
	private int step = 0;
	
	public void setup() {
		try {
			synthesizer = MidiSystem.getSynthesizer();
	        synthesizer.open();
	
	        channels = synthesizer.getChannels();
	        
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
	
	public class WaitBackgroundRequestBehaviour extends CyclicBehaviour {
		public void action() {
			MessageTemplate filtre = MessageTemplate.MatchPerformative(ACLMessage.PROPAGATE);
			
			ACLMessage request = myAgent.receive(filtre);
			
			if(request != null) {
				System.out.println("bg message received");
				ObjectMapper mapper = new ObjectMapper();
				try {
					BackgroundMusicData data = mapper.readValue(request.getContent(), BackgroundMusicData.class);
					BackgroundMusicData data = new BackgroundMusicData();
					System.out.println(step);
					if(step == 0) {
						data.setAction(BackgroundMusicData.BackgroundMusicActionType.CHANGE_BACKGROUND);
						data.setPath("/Users/Alexandre/Desktop/Life Is Like A Song.mp3");
						step++;
					} else if(step == 1) {
						data.setAction(BackgroundMusicData.BackgroundMusicActionType.START_BACKGROUND);
						step++;
					} else {
						data.setAction(BackgroundMusicData.BackgroundMusicActionType.PAUSE_BACKGROUND);
						step = 1;
					}
					
					switch(data.getAction()) {
						case START_BACKGROUND:
							player.Start();
							break;
						case PAUSE_BACKGROUND:
							player.Pause();
							break;
						case RESTART_BACKGROUND:
							player.Restart();
							break;
						case CHANGE_BACKGROUND:
							player = MusicPlayer.getMusicPlayer(data.getPath());
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
*/
