package Utilities;

import jade.core.AID;

public class MoveInformData {
	private Movement move;
	//private AID host_AID;
	private InstrumentType instrumentType;
	

	public MoveInformData() {
		move = null;
		//host_AID = null;
		instrumentType = InstrumentType.DEFAULT;
	}
	
	public Movement getMove() {
		return move;
	}
	public void setMove(Movement move) {
		this.move = move;
	}
//	public AID getHost_AID() {
//		return host_AID;
//	}
//	public void setHost_AID(AID host_AID) {
//		this.host_AID = host_AID;
//	}
	public InstrumentType getInstrumentType() {
		return instrumentType;
	}
	public void setInstrumentType(InstrumentType instrumentType) {
		this.instrumentType = instrumentType;
	}
}
