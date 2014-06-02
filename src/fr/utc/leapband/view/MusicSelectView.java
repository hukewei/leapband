 package fr.utc.leapband.view;
import jade.gui.GuiEvent;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import fr.utc.leapband.sma.user.UserAgent;
import fr.utc.leapband.utilities.Constance;
import fr.utc.leapband.utilities.CustomImgPanel;
import fr.utc.leapband.utilities.ImageTimerTask;
import fr.utc.leapband.utilities.OvalBorder;
import fr.utc.leapband.utilities.SongFlowItem;


public class MusicSelectView extends JAgentFrame{
	private int i=1;
	private JLabel label;
	private JLabel labelup;
	private JLabel labeldown;
//	private JButton precedent;
//	private JButton suivant;	
	private List<SongFlowItem> songs = new ArrayList<SongFlowItem>();
	Timer click_task = null;
	private JLabel valider;
	
	public MusicSelectView(UserAgent agent){
		super(agent);
		songs = myAgent.getSongs();
		/*String path = "src/songs.txt";
		BufferedReader br = null;
		String temp = null;
		try {
			br = new BufferedReader(new FileReader(path));
			while ((temp = br.readLine()) != null) {
				songs.add(temp);
			}
		} catch (FileNotFoundException e) {
			System.out.println("file not exite");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(br!=null) {
					br.close();
					br = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
		this.setTitle("SONGS");
		this.setSize(700,500);
		this.setLocationRelativeTo(null);		
		this.setLayout(null);
		CustomImgPanel background=new CustomImgPanel(700, 500,"images/musicBackground.jpg");
		this.add(background);
		background.setLayout(null);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //arret du programme
		label = new JLabel(getFileName(songs.get(i).getLabel()));
		label.setBounds(100, 120, 500, 160);
    	label.setFont(new Font("Chalkboard", Font.PLAIN, 50));
    	label.setHorizontalAlignment(SwingConstants.CENTER);
    	label.setForeground(Color.WHITE);
    	//label.getComponent(0).getClass().getTypeName();
    	//System.out.println(label.getComponent(0).getClass().getTypeName());

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

//		JButton suivant = new JButton(next);
//		suivant.setBounds(510,360,100,100);
    	
		valider = new JLabel(new ImageIcon("images/ok.png"));
		valider.setBounds(550,360,100,100);
		valider.setBorder(new OvalBorder(valider.getWidth(), valider.getHeight(), Color.GRAY));
		
		valider.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				valider.setBorder(new OvalBorder(valider.getWidth(), valider.getHeight(), Color.GRAY));

				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Image cursorImage = toolkit.getImage("images/cursor.png");
				Point cursorHotSpot = new Point(0,0);
				Cursor customCursor = toolkit.createCustomCursor(cursorImage, cursorHotSpot, "Cursor");
				setCursor(customCursor);
				if (click_task != null) {
					click_task.cancel();
					click_task = null;
				}
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				valider.setBorder(new OvalBorder(valider.getWidth(), valider.getHeight(), new Color(153,153,255)));
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				click_task = new Timer();
				click_task.schedule( 
				        new java.util.TimerTask() {
				            @Override
				            public void run() {
				            	if(i==songs.size()){
				            		i=0;
				            	}
				            	System.out.println(i);
				            	GuiEvent ev = new GuiEvent(this,UserAgent.SELECT_MUSIC_EVENT);
								ev.addParameter(songs.get(i).getFile().getAbsolutePath());
								myAgent.postGuiEvent(ev);
								setVisible(false);
				            }
				        }, 
				        Constance.click_delay 
				);
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
//		valider.addActionListener(new ActionListener(){
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				GuiEvent ev = new GuiEvent(this,UserAgent.SELECT_MUSIC_EVENT);
//				ev.addParameter(songs.get(index).getFile().getAbsolutePath());
//				myAgent.postGuiEvent(ev);
//				setVisible(false);
//			}
//		});
		background.add(label);
		background.add(labelup);
		background.add(labeldown);
//		background.add(suivant);
//		background.add(precedent);
		background.add(valider);
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
		if (isVisible()) {
			if (evt.getPropertyName().equals("swipe")) {
				if ((String)evt.getNewValue() == "UP") {
					selectLastMusic();
				} else if ((String)evt.getNewValue() == "DOWN") {
					selectNextMusic();
				}				
			}
		}
		
	}
	
}
