package SMA.sound;

import javax.vecmath.Point3d;
import com.sun.j3d.utils.geometry.Cylinder;

import Utilities.Movement;
import jade.core.behaviours.OneShotBehaviour;

@SuppressWarnings("serial")
public abstract class FindNoteFromMovement extends OneShotBehaviour {
	protected Movement movement;
	protected int note;
	// speed 400-2000

	
	public FindNoteFromMovement(Movement movement) {
		this.movement = movement;
	}
	
	public int matchNote() {
		//TODO
		return note;

	}
	protected int matchVolume() {
		int volume = 80;

		volume = (int) (volume + (movement.getSpeed()-1200)/80);
		if (volume<60){
			volume=60;
		}
		if (volume>127){
			volume=127;
		}
		if (movement.getSpeed() <50){
			volume=0;
		}
		
		return volume;
	}
		
	
	

}
