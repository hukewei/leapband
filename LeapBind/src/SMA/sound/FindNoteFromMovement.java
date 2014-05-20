package SMA.sound;

import Utilities.Movement;
import jade.core.behaviours.OneShotBehaviour;

@SuppressWarnings("serial")
public abstract class FindNoteFromMovement extends OneShotBehaviour {
	protected Movement movement;
	
	public FindNoteFromMovement(Movement movement) {
		this.movement = movement;
	}
	
	protected int matchNote() {
		//TODO
		return 0;
	}
}
