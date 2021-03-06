package fr.utc.leapband.sma.user;

import jade.core.behaviours.OneShotBehaviour;
import fr.utc.leapband.utilities.Constance;
import fr.utc.leapband.view.GameView;


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
		} else if (msg == UserAgent.Multiple_Mode) {
			myAgent.setMultiple_mode(true);
			myAgent.changeToInstrumentView();
			myAgent.addBehaviour(new MultiPlayUpdateBehaviour(myAgent));
		}else if(msg == UserAgent.return_Menu){
			if (myAgent.isMultiple_mode() && myAgent.getCurrent_frame().getClass() == GameView.class) {
				//if some one exit from a group game, we have to create a ExitGroupBehaviour.
				// ExitGroupBehaviour va s'occuper de la changement de vue
				if (myAgent.current_room_id != null) {
					System.out.println("quitting from current room : " + myAgent.current_room_id);
					myAgent.addBehaviour(new ExitGroupBehaviour(myAgent, myAgent.current_room_id));
				}
			} else {
				myAgent.setNoMode();
				myAgent.changeToMenuView();
			}
		}else if(msg == UserAgent.instrument_Mode){
			if(myAgent.isSingle_mode()){
				System.out.println("singlemode---game start");
				myAgent.changeToGameView();
			}
			else if(myAgent.isMultiple_mode()){
				System.out.println("multimode---select room");
				myAgent.changeToRoomSelectView();
			}
		} else if (msg == Constance.roomselect_Mode){
			System.out.println("enter waiting room");
			myAgent.changeToRoomWaitView();
		} else if (msg == UserAgent.wait_Mode){
			System.out.println("starting game");
			myAgent.changeToGameView();
		} else if (msg == UserAgent.Exit_Room_Mode){
			System.out.println("quiting group");
			myAgent.changeToRoomSelectView();
		} 
		
	}

}
