package View;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import SMA.user.UserAgent;
import Utilities.CustomImgPanel;
import Utilities.ImageFlowItem;
import Utilities.SongFlowItem;


public class musicview extends JFrame{
	private int i = 0;
	private JLabel label;
	private JLabel labelup;
	private JLabel labeldown;
	private String temptext = null;
	private List<SongFlowItem> songs = new ArrayList<SongFlowItem>();
	private UserAgent myAgent;
	
	public musicview(List<SongFlowItem> loadFromDirectory) {
		songs=loadFromDirectory;
	}
	
	public musicview(File directory,UserAgent agent){
		
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
		label.setBounds(0, 120, 500, 160);
    	label.setFont(new Font("Chalkboard", Font.PLAIN, 50));
    	label.setHorizontalAlignment(SwingConstants.CENTER);
    	label.setForeground(Color.WHITE);
    	//label.getComponent(0).getClass().getTypeName();
    	//System.out.println(label.getComponent(0).getClass().getTypeName());

		labelup = new JLabel(getFileName(songs.get(i+2).getLabel()));
		labelup.setBounds(50, 40, 400, 160);
    	labelup.setFont(new Font("Chalkboard", Font.PLAIN, 20));
    	labelup.setHorizontalAlignment(SwingConstants.CENTER);
    	labelup.setForeground(new Color(255,255,255,150));
    	
		labeldown = new JLabel(getFileName(songs.get(i).getLabel()));
		labeldown.setBounds(50, 200, 400, 160);
    	labeldown.setFont(new Font("Chalkboard", Font.PLAIN, 20));
    	labeldown.setHorizontalAlignment(SwingConstants.CENTER);
    	labeldown.setForeground(new Color(255,255,255,150));
		
		JButton precedent = new JButton("avant");
		precedent.setBounds(30,350,100,40);
		JButton suivant = new JButton("next");
		suivant.setBounds(160,350,100,40);
		JButton valider = new JButton("OK");
		valider.setBounds(300,350,125,50);
		
		suivant.addActionListener(new ActionListener(){			
			public void actionPerformed(ActionEvent e) {
				++i;
				if(i==songs.size()){
					i=0;
					labeldown.setText(getFileName(songs.get(i).getLabel()));
					label.setText(getFileName(songs.get(i+1).getLabel()));
					labelup.setText(getFileName(songs.get(i+2).getLabel()));					
				}
				else if(i+1 == songs.size()){
					labeldown.setText(getFileName(songs.get(i).getLabel()));
					label.setText(getFileName(songs.get(0).getLabel()));
					labelup.setText(getFileName(songs.get(1).getLabel()));
				}
				else if(i+2 == songs.size()){
					labeldown.setText(getFileName(songs.get(i).getLabel()));
					label.setText(getFileName(songs.get(i+1).getLabel()));
					labelup.setText(getFileName(songs.get(0).getLabel()));
				}
				else{
					labeldown.setText(getFileName(songs.get(i).getLabel()));
					label.setText(getFileName(songs.get(i+1).getLabel()));
					labelup.setText(getFileName(songs.get(i+2).getLabel()));
				}
			}
		});
		precedent.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				--i;
				if(i < 0){
					i = songs.size()-1;
					labeldown.setText(getFileName(songs.get(i).getLabel()));
					label.setText(getFileName(songs.get(0).getLabel()));
					labelup.setText(getFileName(songs.get(1).getLabel()));
				}
				else if(i == songs.size()-2){
					labeldown.setText(getFileName(songs.get(i).getLabel()));
					label.setText(getFileName(songs.get(i+1).getLabel()));
					labelup.setText(getFileName(songs.get(0).getLabel()));
				}
				else{
					labeldown.setText(getFileName(songs.get(i).getLabel()));
					label.setText(getFileName(songs.get(i+1).getLabel()));
					labelup.setText(getFileName(songs.get(i+2).getLabel()));
				}
			}
		});
		valider.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		background.add(label);
		background.add(labelup);
		background.add(labeldown);
		background.add(suivant);
		background.add(precedent);
		background.add(valider);
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
