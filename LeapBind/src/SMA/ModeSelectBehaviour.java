package SMA;

import jade.core.behaviours.OneShotBehaviour;


@SuppressWarnings("serial")
public class ModeSelectBehaviour extends OneShotBehaviour{
	
	private String msg=null;
	private UserAgent myAgent;
	
	public ModeSelectBehaviour(UserAgent a, String message) {
		myAgent = a;
		msg = message;
	}

	@Override
	public void action() {
		System.out.println("message is " + msg);
		if (msg == UserAgent.Single_Mode) {
			myAgent.setSingle_mode(true);
			myAgent.changeToInstrumentView();
			//myAgent.changeToRoomSelectView();
		} else if (msg == UserAgent.Multiple_Mode) {
			myAgent.setMultiple_mode(true);
			myAgent.changeToRoomSelectView();
		}else if(msg == UserAgent.return_Menu){
			myAgent.setSingle_mode(false);
			myAgent.setMultiple_mode(false);
			myAgent.changeToMenuView();
		}else if(msg == UserAgent.instrument_Mode){
			//myAgent.setSingle_mode(false);
			//myAgent.setMultiple_mode(false);
			myAgent.changeToGameView();
		}
		
	}

}
