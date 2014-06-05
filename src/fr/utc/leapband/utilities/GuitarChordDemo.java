package fr.utc.leapband.utilities;
import javax.sound.midi.*;
import java.util.ArrayList;

public class GuitarChordDemo {   // this is the first one
       
//     public static void main(String[] args) {
//
//	 GuitarTuning gt = new GuitarTuning();
//	 
//	 ArrayList<Integer> eMajorChordMidiValues = new ArrayList<Integer>();
//
//	 eMajorChordMidiValues.add(gt.midiNum(6,0));
//	 eMajorChordMidiValues.add(gt.midiNum(5,2));
//	 eMajorChordMidiValues.add(gt.midiNum(4,2));
//	 eMajorChordMidiValues.add(gt.midiNum(3,1));
//	 eMajorChordMidiValues.add(gt.midiNum(2,0));
//	 eMajorChordMidiValues.add(gt.midiNum(1,0));
//
//	 ArrayList<Integer> aMajorChordMidiValues = new ArrayList<Integer>();
//
//	 aMajorChordMidiValues.add(gt.midiNum(6,0));
//	 aMajorChordMidiValues.add(gt.midiNum(5,0));
//	 aMajorChordMidiValues.add(gt.midiNum(4,2));
//	 aMajorChordMidiValues.add(gt.midiNum(3,2));
//	 aMajorChordMidiValues.add(gt.midiNum(2,2));
//	 aMajorChordMidiValues.add(gt.midiNum(1,0));
//
//
//	 final int instrument = 25; // SEE http://soundprogramming.net/file_formats/general_midi_instrument_list
//
//	     /* 25 Acoustic Guitar (nylon)
//		26 Acoustic Guitar (steel)
//		27 Electric Guitar (jazz)
//		28 Electric Guitar (clean)
//		29 Electric Guitar (muted)
//		30 Overdriven Guitar
//		31 Distortion Guitar
//		32 Guitar harmonics */
//
//        GuitarChordDemo mini = new GuitarChordDemo();
//	mini.playChord(instrument, eMajorChordMidiValues);
//	mini.playChord(instrument, aMajorChordMidiValues);
//            
//	System.exit(0);        
//     }

    public void playChord(int instrument, ArrayList<Integer> chord, int velocity) {

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

	 final int duration = 16;
        
	 for (int i=0; i<chord.size(); i++) {
            ShortMessage a = new ShortMessage();
            a.setMessage(ShortMessage.NOTE_ON, 1, chord.get(i), velocity);
            MidiEvent noteOn = new MidiEvent(a, i+1); 
            track.add(noteOn);
	 }
	 
	 for (int i=0; i<chord.size(); i++) {
	     ShortMessage b = new ShortMessage();
	     b.setMessage(ShortMessage.NOTE_OFF, 1, chord.get(0), velocity);
	     MidiEvent noteOff = new MidiEvent(b,  duration); 
	     track.add(noteOff);
	 }
         

         player.setSequence(seq); 
         player.start();
	 Thread.sleep(1600);
	 player.close();



      } catch (Exception ex) {ex.printStackTrace();}
  } // close play

} // close class