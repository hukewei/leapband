package fr.utc.leapband.sma.sound;

import javax.vecmath.Point3d;

import com.sun.j3d.utils.geometry.Cylinder;

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
		
		Point3d pos=movement.getPos();
		System.out.println (pos.x);
		System.out.println (pos.y );
		System.out.println (pos.z);
		note= 85;

		if (IsLeft(pos)){
		//	62 Mute Hi Conga, 63 Open Hi Conga
			note = 63;
			if (IsDrumLow(pos)){					
					note = 62;
				}
			
		}
		
		else{
			// 47 Low-Mid Tom, 48HIGH-Mid Tom 
			pos.x-=Constance.Windows_width*0.5;
			note = 48;
			if (IsDrumLow(pos)){
				note = 47;
			}
			
		}


		System.out.println("NOTE de behaviour: " + String.valueOf(note));
		return note;
		
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
