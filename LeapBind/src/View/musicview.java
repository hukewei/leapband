package View;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class musicview extends JFrame{
	private int i = 0;
	private JLabel label;
	private JLabel labelup;
	private JLabel labeldown;
	public musicview(){
		final String[] choix = new String[3];
		final String[] choixmini = new String[3];
		choix[0]= "-----------------------Canon----------------------";
		choix[1]= "----------------------Good Life--------------------";
		choix[2]= "------------------How to save a life-----------------";
		choixmini[0]= "                                            Canon";
		choixmini[1]= "                                          Good Life";
		choixmini[2]= "                                    How to save a life";
		this.setTitle("SONGS");
		this.setSize(400,450);
		this.setLocationRelativeTo(null);		//������ʾ����Ļ�м�
		this.setLayout(null);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //arret du programme
		label = new JLabel(choix[0]);
		label.setBounds(0, 80, 400, 160);
		labelup = new JLabel(choixmini[1]);
		labelup.setBounds(0, 0, 400, 160);
		labeldown = new JLabel(choixmini[2]);
		labeldown.setBounds(0, 160, 400, 160);
		
		JButton precedent = new JButton("avant");
		precedent.setBounds(20,300,100,80);
		JButton suivant = new JButton("next");
		suivant.setBounds(140,300,100,80);
		JButton valider = new JButton("OK");
		valider.setBounds(260,290,125,100);
		suivant.addActionListener(new ActionListener(){
			int left = 0;
			int right = 0;
			public void actionPerformed(ActionEvent e) {
				++i;
				if(i<0){
					i=i+1;
				}
				if(i==choix.length){
					i=0;
				}
				if(i-1 < 0){
					left = choix.length-1;
				}
				else{left = i-1;}
				if(i+1 == choix.length){
					right = 0;
				}
				else {right = i+1;}
				label.setText(choix[i]);
				labelup.setText(choixmini[left]);
				labeldown.setText(choixmini[right]);
				//System.out.println("suivant" +i);
			}
		});
		precedent.addActionListener(new ActionListener(){
			int left = 0;
			int right = 0;
			public void actionPerformed(ActionEvent e) {
				--i;
				if(i == choix.length){
					i=i-1;
				}
				if(i < 0){
					i=choix.length-1;
				}
				if(i-1 < 0){
					left = choix.length-1;
				}
				else{left = i-1;}
				if(i+1 == choix.length){
					right = 0;
				}
				else {right = i+1;}
				label.setText(choix[i]);
				labelup.setText(choixmini[left]);
				labeldown.setText(choixmini[right]);
				//System.out.println("precedent "+i);
			}
		});
		valider.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		this.add(label);
		this.add(labelup);
		this.add(labeldown);
		this.add(suivant);
		this.add(precedent);
		this.add(valider);
		this.setVisible(true);
		
		// personnel cursor
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursorImage = toolkit.getImage("src/cursor.png");
		Point cursorHotSpot = new Point(0,0);
		Cursor customCursor = toolkit.createCustomCursor(cursorImage, cursorHotSpot, "Cursor");
		this.setCursor(customCursor);
	}
}
