package SMA.server;
import Utilities.Constance;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.tools.gui.ACLMessageNode;


@SuppressWarnings("serial")
public class GameDaemonBehaviour extends CyclicBehaviour{
	
	private MultiPlayAgent myAgent;
	//private boolean isPrint=false;
	
	public GameDaemonBehaviour(MultiPlayAgent myAgent) {
		super();
		this.myAgent = myAgent;
		
	}

	@Override
	public void action() {
		
		ACLMessage message=myAgent.receive();
		
		if(message!=null && message.getPerformative()==ACLMessage.REQUEST){//GETLISTGROUPBEHAVIOUR or CREATEGROUPBEHAVIOUR
			System.out.println(message.getContent()+"\n");
			if(message.getContent().equals(Constance.roomselect_Mode)){
				System.out.println("code 104 matched");
				System.out.println("receive userAgent create room request");
				myAgent.addBehaviour(new GameManageBehaviour(myAgent, message));
			}else if(message.getContent().equals("listGroup")){
				System.out.println("oklistGroup\n");
				myAgent.addBehaviour(new GetListGameBehaviour(myAgent,message));
			}
		} else if (message != null && message.getPerformative() == ACLMessage.SUBSCRIBE) {//ENTERGROUPBEHAVIOUR
			if (message.getContent().equals(Constance.roomselect_Mode)) {
				System.out.println("code 104 matched");
				System.out.println("receive userAgent subscription");
				//UserAgent ask for creating a new room
				myAgent.addBehaviour(new GameManageBehaviour(myAgent, message));
			}
		} else if (message != null && message.getPerformative() == ACLMessage.CANCEL){//EXITGROUPBEHAVIOUR
			if(message.getContent().equals(Constance.ExitGroupMode)){
				System.out.println("code 109 matched");
				System.out.println("receive userAgent cancel subscription");
				myAgent.addBehaviour(new GameManageBehaviour(myAgent, message));
			}
		}
	}
}
