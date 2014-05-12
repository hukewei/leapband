package SMA;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
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
			}else if(message.getContent()=="creatRoom"){
				
				myAgent.addBehaviour(new GameManageBehaviour(myAgent,message));
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
