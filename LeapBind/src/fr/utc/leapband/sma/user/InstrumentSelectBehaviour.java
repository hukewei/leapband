package fr.utc.leapband.sma.user;

import jade.core.behaviours.OneShotBehaviour;

@SuppressWarnings("serial")
public class InstrumentSelectBehaviour extends OneShotBehaviour{
	
	private UserAgent myAgent;
	private String msg=null;
	

	public InstrumentSelectBehaviour(UserAgent myAgent, String messageInstrument) {
		super();
		this.setMyAgent(myAgent);
		msg=messageInstrument;
	}



	@Override
	public void action() {
		System.out.println("message is " + msg);
		if(msg == UserAgent.drum){
//			myAgent.getGame_view().play.setIcon(new ImageIcon("src/images/play1.png"));
<<<<<<< HEAD:LeapBind/src/SMA/user/InstrumentSelectBehaviour.java
			myAgent.getGame_view().playDrumLeft.loadImage("src/halfDrum.png");
			myAgent.getGame_view().playDrumRight.loadImage("src/halfDrum.png");
			myAgent.getGame_view().getControl_pane().player.setIcon(new ImageIcon("src/images/person1.jpg"));
=======
			myAgent.getGame_view().playDrumLeft.loadImage("images/halfDrum.png");
			myAgent.getGame_view().playDrumRight.loadImage("images/halfDrum.png");
>>>>>>> keweihu/rename_package:LeapBind/src/fr/utc/leapband/sma/user/InstrumentSelectBehaviour.java
			
		}else if(msg == UserAgent.guitar){
			//myAgent.getGame_view().play.setIcon(new ImageIcon("src/images/guitar.png"));
			//myAgent.getGame_view().play.loadImage("src/images/guitar.png");
			myAgent.getGame_view().getControl_pane().player.setIcon(new ImageIcon("src/images/person2.jpg"));
		}else if(msg == UserAgent.piano){
			//myAgent.getGame_view().play.setIcon(new ImageIcon("src/images/pianoniu.png"));
			//myAgent.getGame_view().play.loadImage("src/images/pianoniu.png");
			myAgent.getGame_view().getControl_pane().player.setIcon(new ImageIcon("src/images/person3.jpg"));
		}
	
	}



	public UserAgent getMyAgent() {
		return myAgent;
	}



	public void setMyAgent(UserAgent myAgent) {
		this.myAgent = myAgent;
	}

}
