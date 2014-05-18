package SMA;

import Utilities.Constance;
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
			myAgent.changeToInstrumentView();
			//myAgent.changeToRoomSelectView();
		}else if(msg == UserAgent.return_Menu){
			myAgent.setSingle_mode(false);
			myAgent.setMultiple_mode(false);
			myAgent.changeToMenuView();
		}else if(msg == UserAgent.instrument_Mode){
			if(myAgent.isSingle_mode()){
				System.out.println("singlemode---game start");
				myAgent.changeToGameView();
			}
			else if(myAgent.isMultiple_mode()){
				System.out.println("multimode---select room");
				myAgent.changeToRoomSelectView();
			}
			//myAgent.setSingle_mode(false);
			//myAgent.setMultiple_mode(false);
		} else if (msg == Constance.roomselect_Mode){
			System.out.println("enter waiting room");
			myAgent.changeToRoomWaitView();
		} else if (msg == UserAgent.wait_Mode){
			System.out.println("starting game");
			myAgent.changeToGameView();
		} else if (msg == UserAgent.Exit_Room_Mode){
			System.out.println("quiting group");
			myAgent.changeToGameView();
		} 
		
	}

}
