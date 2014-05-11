package model;
///From MoveToSound to SoundPlay message structure
public class NoteInformData {
	
		private String note;
		private String instrument;
		private int id;
		
		NoteInformData() {
			note = new String();
			instrument=new String();
			id = -1;
		}
		

		public NoteInformData (String note, String instrument,int id) {
			this.note=note;
			this.instrument=instrument;
			this.id=id;;
		}

		public void setNote(String value) {
			note = value;
		}
		
		public String getNote() {
			return note;
		}
		public void setInstrument(String value) {
			instrument = value;
		}
		
		public String getInstrument() {
			return instrument;
		}
		
		public void setID(int id) {
			this.id=id;
		}
		
		public int getID() {
			return id;
		}
		

	
}
