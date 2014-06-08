package fr.utc.leapband.sma.sound;

import maryb.player.Player;

public class MP3MusicPlayer extends MusicPlayer {

    private Player player;
    private float volume;
    /**
     * Constructs a new MusicPlayer object, to use the specified music file.
     * @param filePath - path to the music file.
     * @param volume - volume the file should be played at.
     */
    
	public  MP3MusicPlayer(String filename) {
		super(filename);
		 try {
	            player = new Player();
	          //  player.setCurrentVolume(volume);
	            player.setSourceLocation(filename);
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	}

    /**
     * Gets the location of the file being played.
     * @return
     */
    public String getFileLocation() {
        return filename;
    }

    /**
     * Gets the volume at which the music file is being played.
     * @return
     */
    public float getVolume() {
        return volume;
    }

    /**
     * Sets the current volume of the music file being played.
     * @param volume
     */
    public void setVolume(float volume) {
        this.player.setCurrentVolume(volume);
    }



/**
 * Plays the music file.
 */
public void Start() {
    if (player != null && !player.getSourceLocation().equals(null) || !player.getSourceLocation().equals("")) {
        player.play();
    }
}

/**
 * Pauses the music file. 
 */
public void Pause() {
    if (player != null && !player.getSourceLocation().equals(null) || !player.getSourceLocation().equals("")) {
        player.pause();
    }
}

/**
 * Stops the music file.
 */
public void Stop() {
    if (player != null && !player.getSourceLocation().equals(null) || !player.getSourceLocation().equals("")) {
        player.stop();
    }
}
public void Restart() {
    if (player != null && !player.getSourceLocation().equals(null) || !player.getSourceLocation().equals("")) {
        player.stop();
        player.play();
    }
}


}
