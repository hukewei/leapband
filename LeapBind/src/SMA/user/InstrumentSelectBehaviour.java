package SMA.user;

import javax.swing.ImageIcon;


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
			myAgent.getGame_view().play.setIcon(new ImageIcon("src/images/play1.png"));
		}else if(msg == UserAgent.guitar){
			myAgent.getGame_view().play.setIcon(new ImageIcon("src/images/guitar.png"));
		}else if(msg == UserAgent.piano){
			myAgent.getGame_view().play.setIcon(new ImageIcon("src/images/pianoniu.png"));
		}
	
	}



	public UserAgent getMyAgent() {
		return myAgent;
	}



	public void setMyAgent(UserAgent myAgent) {
		this.myAgent = myAgent;
	}

}
