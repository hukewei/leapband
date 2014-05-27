package SMA;


public class ServerBoot {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainContainer.run(args);
		ServerContainer.run(args);
		SoundContainer.run(args);
	}


}
