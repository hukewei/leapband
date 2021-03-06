package fr.utc.leapband.sma.sound;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.utc.leapband.utilities.BackgroundMusicData;

@SuppressWarnings("serial")
public class WaitBackgroundRequestBehaviour extends CyclicBehaviour {
	MusicPlayer player = null;
	
	public void action() {
		MessageTemplate filtre = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM),
				MessageTemplate.MatchConversationId("BACKGROUND"));
		
		ACLMessage request = myAgent.receive(filtre);
		
		if(request != null) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				BackgroundMusicData data = mapper.readValue(request.getContent(), BackgroundMusicData.class);
				
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
					case STOP_BACKGROUND:
						player.Stop();
						break;
					case CHANGE_BACKGROUND:
						if (player!=null){
							player.Stop();
							player=null;
						}
							player = MusicPlayer.getMusicPlayer("effect/short.mp3");
							player.Start();
						player = MusicPlayer.getMusicPlayer(data.getPath());
						break;
					case CHANGE_VOLUME:
						player.SetVolume(data.getVolume());
						break;
					case DEFAULT:
						break;
					default:
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
