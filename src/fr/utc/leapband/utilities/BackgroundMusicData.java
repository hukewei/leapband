package fr.utc.leapband.utilities;

public class BackgroundMusicData {
	public enum BackgroundMusicActionType {
		START_BACKGROUND,
		PAUSE_BACKGROUND,
		STOP_BACKGROUND,
		CHANGE_BACKGROUND,
		RESTART_BACKGROUND,
		DEFAULT
	}
	
	private BackgroundMusicActionType action;
	private String path;
	
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
}
