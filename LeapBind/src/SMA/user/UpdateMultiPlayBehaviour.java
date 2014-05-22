package SMA.user;

import javax.swing.DefaultListModel;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

@SuppressWarnings("serial")
public class UpdateMultiPlayBehaviour extends Behaviour {
	private UserAgent myAgent;
	boolean done = false;
	boolean first = true;
	
	public UpdateMultiPlayBehaviour (UserAgent myAgent) {
		super();
		this.myAgent = myAgent;
	}
	
	@Override
	public void action() {
		if(first){
			ACLMessage multiPlayRegister = new ACLMessage(ACLMessage.SUBSCRIBE);
			multiPlayRegister.setConversationId("register");
			multiPlayRegister.setSender(myAgent.getAID());
			multiPlayRegister.addReceiver(myAgent.getServerName());
			multiPlayRegister.setContent("getting in");
			myAgent.send(multiPlayRegister);
			first = false;
		}
		
		MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM),
				MessageTemplate.MatchConversationId("updateDICT"));
		ACLMessage update_message = myAgent.receive(mt);
		if(update_message!=null){
			try {
				 myAgent.setDict((DefaultListModel<String>)update_message.getContentObject());
			} catch (UnreadableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		MessageTemplate mtc = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.CANCEL),
				MessageTemplate.MatchConversationId("updateDICT"));
		ACLMessage cancel_message = myAgent.receive(mtc);
		if(cancel_message!=null){
			done=true;
		}
	}

	@Override
	public boolean done() {
		if(done){
			return true;
		}else{
			return false;
		}
	}
}
