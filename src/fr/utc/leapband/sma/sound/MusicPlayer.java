package fr.utc.leapband.sma.sound;

public abstract class MusicPlayer {
	protected String filename;
	
	public static MusicPlayer getMusicPlayer(String filename) throws Exception {
		int debutExtension = filename.lastIndexOf('.') + 1;
		
		String extension = filename.substring(debutExtension);
		extension = extension.toLowerCase();
		
		switch(extension) {
			case "mid":
				return new MIDIMusicPlayer(filename);
			case "mp3":
				return new MP3MusicPlayer(filename);
			default:
				throw new Exception("File format not managed.");
		}
	}
	
	public MusicPlayer(String filename) {
		this.filename = filename;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public abstract void Start();
	public abstract void Pause();
	public abstract void Stop();
	public abstract void Restart();
	public abstract void SetVolume(int volume);
}
