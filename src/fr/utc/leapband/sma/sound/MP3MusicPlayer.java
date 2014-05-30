package fr.utc.leapband.sma.sound;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Timer;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

public class MP3MusicPlayer extends MusicPlayer {
	
	private FileInputStream fis;
	private BufferedInputStream bis;
	private AdvancedPlayer player;
	private int pausedOnFrame = 0;
	private Timer click_task = new Timer();
	
	
	public MP3MusicPlayer(String filename) throws FileNotFoundException, JavaLayerException {
		super(filename);
		/*
		fis = new FileInputStream(filename);
		bis = new BufferedInputStream(fis);
		player = new AdvancedPlayer(bis);
		player.setPlayBackListener(new PlaybackListener() {
		     @Override
		     public void playbackFinished(PlaybackEvent event) {
		         pausedOnFrame = event.getFrame();
		     }
		 });
		 */
	}

	@Override
	public void Start() {
		try {
			fis = new FileInputStream(filename);
			//bis = new BufferedInputStream(fis);
			player = new AdvancedPlayer(fis);
			player.setPlayBackListener(new PlaybackListener() {
			     @Override
			     public void playbackStarted(PlaybackEvent event) {
			    	 event.setFrame(pausedOnFrame);
			     }
				
				 @Override
			     public void playbackFinished(PlaybackEvent event) {
			         pausedOnFrame = event.getFrame();
			     }
			 });
			
			click_task.schedule(
					new java.util.TimerTask() {
						@Override
						public void run() {
							try {
								player.play();
							} catch (JavaLayerException e) {
								e.printStackTrace();
							}
						}
					},0
				);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void Pause() {
		player.stop();
	}

	@Override
	public void Restart() {
		player.stop();
	}
}
