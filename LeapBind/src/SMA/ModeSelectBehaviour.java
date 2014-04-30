package SMA;
import Model.Constance;
import jade.core.Agent;
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
		if (msg == UserAgent.Single_Mode)
		myAgent.setSingle_mode(true);
		myAgent.changeToRoomSelectView();
		
	}

}
