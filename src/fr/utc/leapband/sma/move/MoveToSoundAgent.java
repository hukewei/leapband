package fr.utc.leapband.sma.move;




import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;


@SuppressWarnings("serial")
public class MoveToSoundAgent extends Agent{
	
	protected void setup() {
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Organisation");
		sd.setName("MoveToNote");
		dfd.addServices(sd);
		try {
		DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
		fe.printStackTrace();
		}
		
		this.addBehaviour(new SoundMessageDaemonBehaviour() );	
		
	}
	
//	public AID getReceiver() {
//		AID rec = null;
//		DFAgentDescription template =
//		new DFAgentDescription();
//		ServiceDescription sd = new ServiceDescription();
//		sd.setType("Sound");
//		sd.setName("SoundPlay");
//		template.addServices(sd);
//		try {
//			DFAgentDescription[] result = DFService.search(this, template);
//			
//			if (result.length > 0) {
//				System.out.println("Nombre de resultat : " + String.valueOf(result.length));
//				int i = (int)(Math.random() * result.length);
//				System.out.println("Valeur de i : " + String.valueOf(i));
//				rec = result[i].getName();
//			}
//		} catch(FIPAException fe) {
//			fe.printStackTrace();
//		}
//		return rec;
//	}
}