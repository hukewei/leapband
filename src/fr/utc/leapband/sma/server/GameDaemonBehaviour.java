package fr.utc.leapband.sma.server;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


@SuppressWarnings("serial")
public class GameDaemonBehaviour extends CyclicBehaviour{
	
	private MultiPlayAgent myAgent;
	
	public GameDaemonBehaviour(MultiPlayAgent myAgent) {
		super();
		this.myAgent = myAgent;
		
	}

	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), 
				 		MessageTemplate.MatchContent("listGroup"));
		
		ACLMessage message=myAgent.receive(mt);
		
		if(message!=null && message.getPerformative()==ACLMessage.REQUEST){
			System.out.println(message.getContent()+"\n");
			if(message.getContent().equals("listGroup")){
				System.out.println("oklistGruop\n");
				myAgent.addBehaviour(new GetListGameBehaviour(myAgent,message));
			}
		}
			
	}

}