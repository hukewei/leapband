package fr.utc.leapband.sma.sound;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.utc.leapband.utilities.BackgroundMusicData;

public class WaitBackgroundRequestBehaviour extends CyclicBehaviour {
	MusicPlayer player = null;
	
	public void action() {
		MessageTemplate filtre = MessageTemplate.MatchPerformative(ACLMessage.PROPAGATE);
		
		ACLMessage request = myAgent.receive(filtre);
		
		if(request != null) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				BackgroundMusicData data = mapper.readValue(request.getContent(), BackgroundMusicData.class);
				/*BackgroundMusicData data = new BackgroundMusicData();
				System.out.println(step);
				if(step == 0) {
					data.setAction(BackgroundMusicData.BackgroundMusicActionType.CHANGE_BACKGROUND);
					data.setPath("/Users/Alexandre/Desktop/Life Is Like A Song.mp3");
					step++;
				} else if(step == 1) {
					data.setAction(BackgroundMusicData.BackgroundMusicActionType.START_BACKGROUND);
					step++;
				} else {
					data.setAction(BackgroundMusicData.BackgroundMusicActionType.PAUSE_BACKGROUND);
					step = 1;
				}*/
				
				switch(data.getAction()) {
					case START_BACKGROUND:
						player.Start();
						break;
					case PAUSE_BACKGROUND:
						player.Pause();
						break;
					case RESTART_BACKGROUND:
						player.Restart();
						break;
					case CHANGE_BACKGROUND:
						player = MusicPlayer.getMusicPlayer(data.getPath());
						break;
					case DEFAULT:
						
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else 
			block();
	}
}