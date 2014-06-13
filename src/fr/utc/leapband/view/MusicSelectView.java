 package fr.utc.leapband.view;
import jade.gui.GuiEvent;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import fr.utc.leapband.sma.user.UserAgent;
import fr.utc.leapband.utilities.CustomImgPanel;
import fr.utc.leapband.utilities.SongFlowItem;


@SuppressWarnings("serial")
public class MusicSelectView extends JAgentFrame{
	private int i=1;
	private JLabel label;
	private JLabel labelup;
	private JLabel labeldown;	
	private List<SongFlowItem> songs = new ArrayList<SongFlowItem>();
	Timer click_task = null;
	
	public MusicSelectView(UserAgent agent){
		super(agent);
		songs = myAgent.getSongs();
		myAgent.getGame_view().disableChange();
		this.setTitle("SONGS");
		this.setSize(700,500);
		this.setLocationRelativeTo(null);		
		this.setLayout(null);
		CustomImgPanel background=new CustomImgPanel(700, 500);
		background.setImagePath("images/musicBackground.jpg");
		this.add(background);
		background.setLayout(null);
		label = new JLabel(getFileName(songs.get(i).getLabel()));
		label.setBounds(100, 120, 500, 160);
    	label.setFont(new Font("Chalkboard", Font.PLAIN, 50));
    	label.setHorizontalAlignment(SwingConstants.CENTER);
    	label.setForeground(Color.WHITE);

		labelup = new JLabel(getFileName(songs.get(i+1).getLabel()));
		labelup.setBounds(150, 20, 400, 160);
    	labelup.setFont(new Font("Chalkboard", Font.PLAIN, 20));
    	labelup.setHorizontalAlignment(SwingConstants.CENTER);
    	labelup.setForeground(new Color(255,255,255,150));
    	
		labeldown = new JLabel(getFileName(songs.get(i-1).getLabel()));
		labeldown.setBounds(150, 220, 400, 160);
    	labeldown.setFont(new Font("Chalkboard", Font.PLAIN, 20));
    	labeldown.setHorizontalAlignment(SwingConstants.CENTER);
    	labeldown.setForeground(new Color(255,255,255,150));
		background.add(label);
		background.add(labelup);
		background.add(labeldown);
		this.setResizable(false);
		this.setVisible(true);
		
		// personnel cursor
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursorImage = toolkit.getImage("images/cursor.png");
		Point cursorHotSpot = new Point(0,0);
		Cursor customCursor = toolkit.createCustomCursor(cursorImage, cursorHotSpot, "Cursor");
		background.setCursor(customCursor);
	}
	
	private String getFileName(String filePath){
		return filePath.substring(0, filePath.lastIndexOf("."));
	}
	
	public void selectNextMusic() {
		++i;
		if(i-1==songs.size()){
			i=1;
		}
		labeldown.setText(getFileName(songs.get(i-1%songs.size()).getLabel()));
		label.setText(getFileName(songs.get((i)%songs.size()).getLabel()));
		labelup.setText(getFileName(songs.get((i+1)%songs.size()).getLabel()));		
	}
	
	public void selectLastMusic() {
		--i;
		if(i<1){
			i=songs.size();
		}
		labeldown.setText(getFileName(songs.get((i-1)%songs.size()).getLabel()));
		label.setText(getFileName(songs.get((i)%songs.size()).getLabel()));
		labelup.setText(getFileName(songs.get((i+1)%songs.size()).getLabel()));			
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);
		if (isVisible()) {
			if (evt.getPropertyName().equals("swipe")) {
				if ((String)evt.getNewValue() == "UP") {
					selectLastMusic();
				} else if ((String)evt.getNewValue() == "DOWN") {
					selectNextMusic();
				} else if ((String)evt.getNewValue() == "GRAB") {
					if(i==songs.size()){
	            		i=0;
	            	}
	            	System.out.println(i);
					GuiEvent ev = new GuiEvent(this,UserAgent.SELECT_MUSIC_EVENT);
					ev.addParameter(songs.get(i).getFile().getAbsolutePath());
					myAgent.postGuiEvent(ev);
					setVisible(false);
					myAgent.getGame_view().enableChange();
	 			}			
			}
		}
		
	}
	
}
