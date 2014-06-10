package fr.utc.leapband.utilities;

public class BackgroundMusicData {
	public enum BackgroundMusicActionType {
		START_BACKGROUND,
		PAUSE_BACKGROUND,
		CHANGE_BACKGROUND,
		RESTART_BACKGROUND,
		CHANGE_VOLUME,
		DEFAULT
	}
	
	private BackgroundMusicActionType action;
	private String path;
	private int volume;
	
	public BackgroundMusicData() {
		action = BackgroundMusicActionType.DEFAULT;
		path = new String();
	}
	
	public BackgroundMusicActionType getAction() {
		return action;
	}
	public void setAction(BackgroundMusicActionType action) {
		this.action = action;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
}
