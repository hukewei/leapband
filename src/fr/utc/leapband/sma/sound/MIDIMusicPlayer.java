package fr.utc.leapband.sma.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;

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
	public void Stop() {
		sequencer.stop();
		sequencer.setTickPosition(0);
	}

	@Override
	public void Restart() {
		sequencer.stop();
		sequencer.setTickPosition(0);
		sequencer.start();	
	}
	
	@Override
	public void SetVolume(int volume) {
		if(volume < 0)
			volume = 0;
		
		if(volume > 255)
			volume = 255;
		
		try {
	            ShortMessage volumeMessage = new ShortMessage();
	            for (int i = 0; i < 16; i++) {
	            	volumeMessage.setMessage(ShortMessage.CONTROL_CHANGE, i, 7, volume);
	                MidiSystem.getReceiver().send(volumeMessage, -1);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
}
