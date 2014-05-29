package SMA.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class MIDIMusicPlayer extends MusicPlayer {
	
	private Sequencer sequencer;
	
	public MIDIMusicPlayer(String filename) throws MidiUnavailableException, InvalidMidiDataException, IOException {
		super(filename);
		
		sequencer = MidiSystem.getSequencer();
        sequencer.open();
        
        Sequence sequence = MidiSystem.getSequence(new File(filename));
		sequencer.setSequence(sequence);
	}

	@Override
	public void Start() {
		sequencer.start();
	}

	@Override
	public void Pause() {
		sequencer.stop();
	}

	@Override
	public void Restart() {
		sequencer.stop();
		sequencer.setTickPosition(0);
		sequencer.start();	
	}
}
