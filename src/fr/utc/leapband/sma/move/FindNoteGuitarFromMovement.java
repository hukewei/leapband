package fr.utc.leapband.sma.move;

import javax.vecmath.Point3d;

import fr.utc.leapband.utilities.Constance;
import fr.utc.leapband.utilities.Movement;

public class FindNoteGuitarFromMovement  {


	
	double xHi;
	double xLow;
	double xBreak;
	double  yHi;
	int note;
	Movement movement;


	
	public FindNoteGuitarFromMovement(Movement movement) {
		
		 xHi=Constance.Windows_width*0.455;
		 xLow= Constance.Windows_width*0.13;
		 yHi=Constance.Windows_height*0.651;
		 this.movement=movement;
	}

public	int matchNote(){
		
		double x=movement.getPos().x;

		int chord = 60;
		if (x < Constance.Windows_width * 0.14) {
			chord = 61;
		} else if (x < Constance.Windows_width * 0.27) {
			chord = 62;
		} else if (x < Constance.Windows_width * 0.4) {
 			chord = 63;
 		} else if (x < Constance.Windows_width * 0.53) {
 			chord = 64;
 		} else if (x < Constance.Windows_width * 0.66) {
 			chord = 65;
 		} else if (x < Constance.Windows_width * 0.79) {
 			chord = 66;
 		} else if (x < Constance.Windows_width * 0.9) {
 			chord = 67;
 		} else if (x < Constance.Windows_width) {
 			chord = 68;
 		}
 		return chord;
 	}

		



public boolean IsLeft(Point3d pos){
	if (  pos.x /Constance.Windows_width<= 0.5){
		return true;			
	}				
	return false;		
}

public boolean IsRight(Point3d pos){

	if ( pos.x /Constance.Windows_width> 0.5){
		return true;			
	}
			
	return false;				
	
}
public boolean IsDrumLow(Point3d pos){
	if ( pos.y>yHi && pos.x>xLow&& pos.x<xHi){
		return true;			
	}
			
	return false;
				
}

	
	
	public int matchVolume() {
		int volume = 30;
		int current_speed = (int) Math.abs(movement.getSpeed());
		volume = (int) (volume + (current_speed-400)/2);
		if (volume<70){
			volume=70;
		}
		if (volume>240){
			volume=240;
		}
		if (current_speed <30){
			System.out.println(current_speed + ": 0 volume for this note..");
			volume=0;
		}
		return volume;
	}
	
}
