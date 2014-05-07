package View;

import jade.gui.GuiEvent;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import SMA.UserAgent;
import Utilities.Constance;

@SuppressWarnings("serial")
public class InstrumentSelectView extends JAgentFrame {
		
		private JLabel label;
		private JLabel test;
		private JLabel labeleft;
		private JLabel labelright;
		private JLabel text;
		private int i=0;
		String[] choix = new String[3];
		String[] choixmini = new String[3];
		private int left = 0;
		private int right = 0;
		
	public InstrumentSelectView(UserAgent agent) {
			super(agent);
			choix[0]= "src/gu.png";
			choix[1]= "src/pianoniu.png";
			choix[2]= "src/guitar.png";
			choixmini[0]= "src/lv.png";
			choixmini[1]= "src/minihuang.png";
			choixmini[2]= "src/test.jpg";
			JButton suivant = new JButton("suivant");
			suivant.setBounds(950,600,250,100);
			JButton precedent = new JButton("precedent");
			precedent.setBounds(300,600,250,100);
			JButton valider = new JButton("Valider");
			valider.setBounds(600,600,300,100);
			this.setTitle("ChooseView");
			this.setSize(Constance.Windows_width, Constance.Windows_height);
			this.setLocationRelativeTo(null);		//������ʾ����Ļ�м�
			this.setLayout(null);
			
			JButton home = new JButton();
			Icon icon = new ImageIcon("src/home.png");
			home.setBounds(140,50,100,100);
			home.setIcon(icon);
			//test = new JLabel(new ImageIcon("/Users/akeharuxiao/Desktop/1.png"));
			//test.setBounds(200, 100, 30, 30);
			//Image src = Toolkit.getDefaultToolkit().getImage("/Users/akeharuxiao/Desktop/1.png");
			//ImageFilter colorfilter = new RedBlueSwapFilter();
			//Image img = createImage(new FilteredImageSource(src.getSource(),colorfilter));
			int size=22;
			text = new JLabel("Choose your instrument");
			text.setFont(new Font("Serif",Font.PLAIN,size));
			text.setBounds(620,50,300,100);
			label = new JLabel(new ImageIcon(choix[0]));
			label.setBounds(520, 130, 450, 450);
			labeleft = new JLabel(new ImageIcon(choixmini[2]));
			labeleft.setBounds(320, 400, 200, 200);
			labelright = new JLabel(new ImageIcon(choixmini[1]));
			labelright.setBounds(950,400,200,200);
			//test.setIcon(new ImageIcon(img));
			suivant.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					selectSuivant();
				}
			});
			
			precedent.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					selectPrecedent();
				}
			});
			valider.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					//new playview();
					GuiEvent ev = new GuiEvent(this,UserAgent.SELECT_EVENT);
					ev.addParameter(UserAgent.instrument_Mode);
					myAgent.postGuiEvent(ev);
				}
			});
			this.add(label);
			this.add(labeleft);
			this.add(labelright);
			this.add(home);
			//this.add(test);
			this.add(suivant);
			this.add(precedent);
			this.add(valider);	
			this.add(text);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //arret du programme
			//this.setVisible(true);
			home.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					GuiEvent ev = new GuiEvent(this,UserAgent.SELECT_EVENT);
					ev.addParameter(UserAgent.return_Menu);
					myAgent.postGuiEvent(ev);
				}
			});
		
			// personnel cursor
			
//			Toolkit toolkit = Toolkit.getDefaultToolkit();
//			Image cursorImage = toolkit.getImage("src/cursor.png");
//			Point cursorHotSpot = new Point(0,0);
//			Cursor customCursor = toolkit.createCustomCursor(cursorImage, cursorHotSpot, "Cursor");
//			this.setCursor(customCursor);
	}
	
	private void selectPrecedent() {
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
		label.setIcon(new ImageIcon(choix[i]));
		labeleft.setIcon(new ImageIcon(choixmini[left]));
		labelright.setIcon(new ImageIcon(choixmini[right]));
	}
	
	private void selectSuivant() {
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
		label.setIcon(new ImageIcon(choix[i]));
		labeleft.setIcon(new ImageIcon(choixmini[left]));
		labelright.setIcon(new ImageIcon(choixmini[right]));
		//System.out.println("suivant" +i);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("swipe")) {
			if ((String)evt.getNewValue() == "LEFT") {
				selectPrecedent();
			} else if ((String)evt.getNewValue() == "RIGHT") {
				selectSuivant();
			}
			
		}
		
	}

}
