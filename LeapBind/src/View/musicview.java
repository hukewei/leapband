 package View;
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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import SMA.user.UserAgent;
import Utilities.Constance;
import Utilities.CustomImgPanel;
import Utilities.ImageFlowItem;
import Utilities.OvalBorder;
import Utilities.SongFlowItem;


public class musicview extends JFrame{
	private int i = 0;
	private JLabel label;
	private JLabel labelup;
	private JLabel labeldown;
	private String temptext = null;
	private List<SongFlowItem> songs = new ArrayList<SongFlowItem>();
	private UserAgent myAgent;
	private int index=0;
	Timer click_task = null;
	private JLabel valider;
	
	public musicview(List<SongFlowItem> loadFromDirectory) {
		songs=loadFromDirectory;
	}
	 
	public musicview(final File directory,UserAgent agent){
		
		this(SongFlowItem.loadFromDirectory(directory));
	    this.myAgent=agent;
		
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
		CustomImgPanel background=new CustomImgPanel(700, 500,"src/images/musicBackground.jpg");
		this.add(background);
		background.setLayout(null);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //arret du programme
		label = new JLabel(getFileName(songs.get(i+1).getLabel()));
		index=i+1;
		label.setBounds(100, 120, 500, 160);
    	label.setFont(new Font("Chalkboard", Font.PLAIN, 50));
    	label.setHorizontalAlignment(SwingConstants.CENTER);
    	label.setForeground(Color.WHITE);
    	//label.getComponent(0).getClass().getTypeName();
    	//System.out.println(label.getComponent(0).getClass().getTypeName());

		labelup = new JLabel(getFileName(songs.get(i+2).getLabel()));
		labelup.setBounds(150, 20, 400, 160);
    	labelup.setFont(new Font("Chalkboard", Font.PLAIN, 20));
    	labelup.setHorizontalAlignment(SwingConstants.CENTER);
    	labelup.setForeground(new Color(255,255,255,150));
    	
		labeldown = new JLabel(getFileName(songs.get(i).getLabel()));
		labeldown.setBounds(150, 220, 400, 160);
    	labeldown.setFont(new Font("Chalkboard", Font.PLAIN, 20));
    	labeldown.setHorizontalAlignment(SwingConstants.CENTER);
    	labeldown.setForeground(new Color(255,255,255,150));
		
//    	ImageIcon avant = new ImageIcon("src/images/avant.jpg");
//		JButton precedent = new JButton(avant);
//		precedent.setBounds(100,360,100,100);
//    	ImageIcon next = new ImageIcon("src/images/next.png");
//		JButton suivant = new JButton(next);
//		suivant.setBounds(510,360,100,100);
    	
		valider = new JLabel(new ImageIcon("src/images/ok.png"));
		valider.setBounds(550,360,100,100);
		valider.setBorder(new OvalBorder(valider.getWidth(), valider.getHeight(), Color.GRAY));
		
//		suivant.addActionListener(new ActionListener(){			
//			public void actionPerformed(ActionEvent e) {
//				++i;
//				if(i==songs.size()){
//					i=0;
//				}
//				labeldown.setText(getFileName(songs.get(i%songs.size()).getLabel()));
//				label.setText(getFileName(songs.get((i+1)%songs.size()).getLabel()));
//				labelup.setText(getFileName(songs.get((i+2)%songs.size()).getLabel()));		
//			}
//		});
//		precedent.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e) {
//				--i;
//				if(i<0){
//					i=songs.size()-1;
//				}
//				labeldown.setText(getFileName(songs.get(i%songs.size()).getLabel()));
//				label.setText(getFileName(songs.get((i+1)%songs.size()).getLabel()));
//				labelup.setText(getFileName(songs.get((i+2)%songs.size()).getLabel()));			
//			}
//		});
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
				Image cursorImage = toolkit.getImage("src/images/cursor.png");
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
				            	GuiEvent ev = new GuiEvent(this,UserAgent.SELECT_MUSIC_EVENT);
								ev.addParameter(songs.get(index).getFile().getAbsolutePath());
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
		Image cursorImage = toolkit.getImage("src/images/cursor.png");
		Point cursorHotSpot = new Point(0,0);
		Cursor customCursor = toolkit.createCustomCursor(cursorImage, cursorHotSpot, "Cursor");
		background.setCursor(customCursor);
	}
	
	private String getFileName(String filePath){
		return filePath.substring(0, filePath.lastIndexOf("."));
	}
	
}
