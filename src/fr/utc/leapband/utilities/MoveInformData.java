package fr.utc.leapband.utilities;


public class MoveInformData {
	private Movement move;
	//private AID host_AID;
	private InstrumentType instrumentType;
	private float velocity_multiplier;
	private long timestamp;
	
	public MoveInformData() {
		move = null;
		//host_AID = null;
		instrumentType = InstrumentType.DEFAULT;
	}
	
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public float getVelocity_multiplier() {
		return velocity_multiplier;
	}

	public void setVelocity_multiplier(float velocity_multiplier) {
		this.velocity_multiplier = velocity_multiplier;
	}
	
	public Movement getMove() {
		return move;
	}
	public void setMove(Movement move) {
		this.move = move;
	}
	public InstrumentType getInstrumentType() {
		return instrumentType;
	}
	public void setInstrumentType(InstrumentType instrumentType) {
		this.instrumentType = instrumentType;
	}
}
