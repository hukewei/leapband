package agents;
import org.jfugue.*; 

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.NoteInformData;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


public class SoundPlayAgent extends Agent{
	private ArrayList<String> noteslist;
	private ArrayList<String> instrumentslist;
	private String percussion_notes;
	private Player player;
	private Rhythm rhythm;
	Pattern progression;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void setup() {
		noteslist= new ArrayList<String>();
		instrumentslist= new ArrayList<String>();
		percussion_notes=new String();
		/**Set the sounds of notes**/
		player = new Player(); 
		rhythm = new Rhythm(); 
		progression = new Pattern(); 
		 
		
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Sound");
		sd.setName("SoundPlayer");
		dfd.addServices(sd);
		try {
		DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
		
		//this.addBehaviour(new NoteRecieveBehaviour()); //attend messages INFROM de MoveToSound
		
		ParallelBehaviour comportementParalleleSimulation = new ParallelBehaviour(ParallelBehaviour.WHEN_ANY);
		comportementParalleleSimulation.addSubBehaviour(new EndOfSimulationWaiter());
		comportementParalleleSimulation.addSubBehaviour(new PlayNotesBehaviour(this,Constants.PLAY_IntervalTimer));
		comportementParalleleSimulation.addSubBehaviour(new NoteRecieveBehaviour());
		this.addBehaviour( comportementParalleleSimulation);
       //attend messages Request de User
		
	}
	
	
	private class NoteRecieveBehaviour extends CyclicBehaviour{
		
		public NoteRecieveBehaviour() {
			
		}

	@Override
	public void action() {
		MessageTemplate filtre = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
		ACLMessage message = myAgent.receive(filtre);
		//System.out.println("Recu Inform");
		if(message != null)
		{
			ObjectMapper mapper = new ObjectMapper();
			
			try {
				NoteInformData data = mapper.readValue(message.getContent(), NoteInformData.class);
				addBehaviour(new StockNoteBehaviour(data, message));
				System.out.println("StockNoteBehaviour");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
			block();
	}

 }

	private class PlayNotesBehaviour extends TickerBehaviour {

		public PlayNotesBehaviour(Agent a, long period) {
			super(a, period);
			noteslist.clear();
		}

		@Override
		protected void onTick() {
			if (!percussion_notes.isEmpty()){
				progression.add("V9 "+ percussion_notes);
			}
			if (!noteslist.isEmpty()){
				int voice=0;
				
			for (int i =0;i<noteslist.size();i++){
				
				if(i==9){
					voice++;
				}
				System.out.println(noteslist.get(i));
				//progression.add("V1 T160 I[Piano] " + noteslist.get(i));
				progression.add("V"+String.valueOf(voice)+" T160 I[" + instrumentslist.get(i) + "] "+ noteslist.get(i));
				voice++;
			}

			player.play(progression);
			noteslist.clear();
			instrumentslist.clear();
			percussion_notes="";
			}
			
		}
		
	}

	// Behaviours etat simulation.
	public class EndOfSimulationWaiter extends Behaviour {

		private boolean Done;
		
		EndOfSimulationWaiter() {
			super();
			Done = false;
		}
		
		@Override
		public void action() {
			MessageTemplate filtre = MessageTemplate.MatchPerformative(ACLMessage.CANCEL);
			ACLMessage message = myAgent.receive(filtre);
			
			if(message != null) {
				if(message.getContent().equals("Stop"))
					Done = true;
			}
			else {
				block();
			}
		}

		@Override
		public boolean done() {
			if(Done) {
				//((SoundPlayAgent)myAgent).terminateSimulation();
			}
			
			return Done;
		}
		
	}
	
	
	private class BackgroundMusicBehaviour extends CyclicBehaviour{

		@Override
		public void action() {
			// TODO Auto-generated method stub
			
		}
		
		
	}

	
	private class StockNoteBehaviour extends OneShotBehaviour{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		NoteInformData datas;
		ACLMessage sender;
		boolean Done;
		
		public StockNoteBehaviour(NoteInformData datas, ACLMessage sender) {
			this.datas = new NoteInformData(datas.getNote(), datas.getInstrument(), datas.getID());
			this.sender = sender;
			Done = false;
		}
		@Override
		public void action() {

			if (datas.getInstrument().indexOf("Percussion", 0) >= 0){
				percussion_notes+=datas.getNote();
				System.out.println("add Percussion "+ datas.getNote());
			}
			else {
				if (instrumentslist.isEmpty() || instrumentslist.contains(datas.getInstrument())){
					noteslist.add(datas.getNote());					
					instrumentslist.add(datas.getInstrument());
					System.out.println("add NEW "+ datas.getNote());
				}
				else {
					int index= instrumentslist.indexOf(datas.getInstrument());
					if (noteslist.get(index).indexOf(datas.getNote(), 0) < 0){
						noteslist.set(index, noteslist.get(index) +'+'+ datas.getNote());	
						System.out.println("add "+ datas.getNote());
					}
					
					
				}
				
			}
			
			
			Done =true;
		}


				
	}
	

	

	
	
	
}




