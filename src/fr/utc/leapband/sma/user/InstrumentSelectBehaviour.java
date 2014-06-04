package fr.utc.leapband.sma.user;

import jade.core.behaviours.OneShotBehaviour;

import javax.swing.ImageIcon;

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

			myAgent.getGame_view().getControl_pane().player.setIcon(new ImageIcon("images/person1.jpg"));
			myAgent.getGame_view().playDrumLeft.loadImage("images/halfDrum.png");
			myAgent.getGame_view().playDrumRight.loadImage("images/halfDrum.png");
			
			myAgent.getGame_view().pianoPane.setVisible(false);
			myAgent.getGame_view().guitarPane.setVisible(false);
			myAgent.getGame_view().playDrumLeft.setVisible(true);
			myAgent.getGame_view().playDrumRight.setVisible(true);

			
		}else if(msg == UserAgent.guitar){
			//myAgent.getGame_view().play.setIcon(new ImageIcon("src/images/guitar.png"));
			//myAgent.getGame_view().play.loadImage("src/images/guitar.png");
			myAgent.getGame_view().getControl_pane().player.setIcon(new ImageIcon("images/person2.jpg"));
			myAgent.getGame_view().pianoPane.setVisible(false);
			myAgent.getGame_view().guitarPane.setVisible(true);
			myAgent.getGame_view().playDrumLeft.setVisible(false);
			myAgent.getGame_view().playDrumRight.setVisible(false);
		}else if(msg == UserAgent.piano){
			//myAgent.getGame_view().play.setIcon(new ImageIcon("src/images/pianoniu.png"));
			//myAgent.getGame_view().play.loadImage("src/images/pianoniu.png");
			myAgent.getGame_view().getControl_pane().player.setIcon(new ImageIcon("images/person3.jpg"));
			myAgent.getGame_view().pianoPane.setVisible(true);
			myAgent.getGame_view().guitarPane.setVisible(false);
			myAgent.getGame_view().playDrumLeft.setVisible(false);
			myAgent.getGame_view().playDrumRight.setVisible(false);
		}
	
	}



	public UserAgent getMyAgent() {
		return myAgent;
	}



	public void setMyAgent(UserAgent myAgent) {
		this.myAgent = myAgent;
	}

}
