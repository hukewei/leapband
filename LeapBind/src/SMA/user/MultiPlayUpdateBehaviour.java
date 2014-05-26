package SMA.user;

import javax.swing.DefaultListModel;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

@SuppressWarnings("serial")
public class MultiPlayUpdateBehaviour extends Behaviour {

	private UserAgent myAgent;
	boolean done = false;
	boolean first = true;

	public MultiPlayUpdateBehaviour (UserAgent myAgent){
		super();
		this.myAgent = myAgent;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void action() {
		// TODO Auto-generated method stub
		if(first){
			ACLMessage multiPlayRegister = new ACLMessage(ACLMessage.SUBSCRIBE);
			multiPlayRegister.setConversationId("register");
			multiPlayRegister.setSender(myAgent.getAID());
			multiPlayRegister.addReceiver(myAgent.getServerName());
			multiPlayRegister.setContent("getting in");
			myAgent.send(multiPlayRegister);
			first = false;
		}
		
		if(!myAgent.isMultiple_mode()){
				ACLMessage multiPlayUnregister = new ACLMessage(ACLMessage.SUBSCRIBE);
				multiPlayUnregister.setConversationId("unregister");
				multiPlayUnregister.setSender(myAgent.getAID());
				multiPlayUnregister.addReceiver(myAgent.getServerName());
				multiPlayUnregister.setContent("getting out");
				myAgent.send(multiPlayUnregister);
				
				done=true;
		}
		
		MessageTemplate mt = 
				MessageTemplate.and(MessageTemplate.MatchConversationId("updateDICT"),
									MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		ACLMessage update_message = myAgent.receive(mt);
		if(update_message!=null){
			System.out.println("receive update dict");
			if(update_message.getPerformative()==ACLMessage.INFORM){
				try {
					 myAgent.setDict((DefaultListModel<String>)update_message.getContentObject());
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		if(done){
			return true;
		}else{
			return false;
		}

	}

}
