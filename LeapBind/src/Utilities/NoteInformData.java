package Utilities;
///From MoveToSound to SoundPlay message structure
public class NoteInformData {
	
	public enum NoteActionType {
		START_NOTE,
		STOP_NOTE,
		CHANGE_INSTRUMENT,
		DEFAULT
	}
	
	private NoteActionType action;
	private int note;
	private int velocity;
	private int channel;
	private int instrument;
	private int bank;
	
	public NoteInformData() {
		action = NoteActionType.DEFAULT;
		note = -1;
		velocity = -1;
		channel = -1;
		instrument = -1;
		bank = -1;
	}

	public NoteActionType getAction() {
		return action;
	}
	public void setAction(NoteActionType action) {
		this.action = action;
	}
	public int getNote() {
		return note;
	}
	public void setNote(int note) {
		this.note = note;
	}
	public int getVelocity() {
		return velocity;
	}
	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}
	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}
	public int getInstrument() {
		return instrument;
	}
	public void setInstrument(int instrument) {
		this.instrument = instrument;
	}
	public int getBank() {
		return bank;
	}
	public void setBank(int bank) {
		this.bank = bank;
	}
}
