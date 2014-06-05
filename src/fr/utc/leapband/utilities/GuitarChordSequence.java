package fr.utc.leapband.utilities;
import javax.sound.midi.*;
import java.util.ArrayList;

public class GuitarChordSequence {   // this is the first one
    
    public void playChords(int instrument, ArrayList<ArrayList<Integer>> chords) {
	
	try {
	    
	    Sequencer player = MidiSystem.getSequencer();         
	    player.open();
	    
	    Sequence seq = new Sequence(Sequence.PPQ, 4);         
	    Track track = seq.createTrack();  
	    
	    MidiEvent event = null;
	    
	    ShortMessage first = new ShortMessage();
	    first.setMessage(192, 1, instrument, 0);
	    MidiEvent changeInstrument = new MidiEvent(first, 1); 
	    track.add(changeInstrument);
	    
	    final int beatDuration = 4; // how many beats to hold each chord 
	    
	    // c is for chord 
	    for (int c=0; c<chords.size(); c++) {
		
		// n is for note
		ArrayList<Integer> chord = chords.get(c);
		
		for (int n=0; n<chord.size(); n++) {
		    
		    ShortMessage a = new ShortMessage();
		    a.setMessage(ShortMessage.NOTE_ON, 1, chord.get(n), 100);
		    MidiEvent noteOn = new MidiEvent(a, 1+(c*beatDuration)); 
		    track.add(noteOn);
		}
		
		for (int n=0; n<chord.size(); n++) {
		    ShortMessage b = new ShortMessage();
		    b.setMessage(ShortMessage.NOTE_OFF, 1, chord.get(n), 100);
		    MidiEvent noteOff = new MidiEvent(b, (c+1)*beatDuration); 
		    track.add(noteOff);
		} // n for loop
	    } // c for loop
	    
	   
	    player.setSequence(seq); 
	    player.start();
	    Thread.sleep(5000);
	    player.close();
	    
	} catch (Exception ex) {ex.printStackTrace();}
    } // close play

} // close class