package fr.utc.leapband.utilities;
/**
 * 
 * 
 * @author Matthew Garcia and Ben McCurdy
 * @version Guitar tuning choice points for CS56, Spring 2011
 */


public class GuitarTuning {
    


private int[] tuning;
    /** Construct a Guitar Tuning object with standard tuning
*/
public GuitarTuning(){

int []tuning2= {64,59,55,50,45,40};
tuning=tuning2;
}

/** Constructs a Guitar Tuning object from the elements of the array. The array is of size 6,
and represents the midi notes of the strings from highest to lowest, 

If the array is not of size 6, or if and of the values in the array are not in the range 21 to 108, a IllegalArgumentException is thrown.
@param strings -an array that holds the midi values of the strings
*/
public GuitarTuning(int [] strings){
    if (strings.length != 6){
  throw new IllegalArgumentException("You must have 6 strings tuned!");
    }
 
   
for(int i=0; i<6; i++){
     if (strings[i]>108 || strings[i]<21){
        throw new IllegalArgumentException("You must have 6 strings    tuned!");
      } 
    }
tuning=strings;


}


/** converts a string number (1-6) and a fret number (0-24) to a MIDI note number. Throws
     an IllegalArgumentException if the parameter values are out of range.

  @param string an integer from 1-6, where 1 is the highest string, and 6 is the lowest string
  @param a fret number from 0-24 where 0 is the open string, 1 is the first fret, etc.

*/

public int midiNum(int string, int fret){
int temp=0;
int temp2=0;
int midi=0;
if (string > 6 || string < 1){
  throw new IllegalArgumentException("You must pick one of the 6 strings!");
}

if (fret >24 || string <0){
  throw new IllegalArgumentException("You must pick one of the 24 frets or none at all!");
}

temp= string -1;
temp2= tuning[temp];
midi= temp2 + fret;
if(midi>108 || midi<21)
  throw new IllegalArgumentException("midi out of range");
 else
   return midi;
}
/** Overridden toString method to print out the array correctly
*/

public String toString(){
    return "Tuning: " + tuning[0] + " " + tuning[1] + " " + tuning[2] + " " + tuning[3] + " " + tuning[4] + " " + tuning[5];
}
}








