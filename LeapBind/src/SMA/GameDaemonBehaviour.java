package SMA;
import Utilities.Constance;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;


@SuppressWarnings("serial")
public class GameDaemonBehaviour extends CyclicBehaviour{
	
	private MultiPlayAgent myAgent;
	private boolean isPrint=false;
	
	public GameDaemonBehaviour(MultiPlayAgent myAgent) {
		super();
		this.myAgent = myAgent;
		
	}

	@Override
	public void action() {
		
		ACLMessage message=myAgent.receive();
		
		if(message!=null && message.getPerformative()==ACLMessage.REQUEST){
			//isPrint=true;
			System.out.println(message.getContent()+"\n");
			if(message.getContent().equals("listGroup")){
				System.out.println("oklistGruop\n");
				myAgent.addBehaviour(new GetListGameBehaviour(myAgent,message));
			}
		} else if (message != null && message.getPerformative() == ACLMessage.SUBSCRIBE) {
			System.out.println("subscrib received");
			System.out.println(message.getContent());
			if (message.getContent().equals(Constance.roomselect_Mode)) {
				System.out.println("code 104 matched");
				System.out.println("receive userAgent subscription");
				//UserAgent ask for creating a new room
				myAgent.addBehaviour(new GameManageBehaviour(myAgent, message));
			}
			
		}
		
		
	}

	/*@Override
	public boolean done() {
		if(isPrint){
			return true;
		}else{
			return false;
		}
	}*/

}
