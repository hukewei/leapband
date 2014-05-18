package View;

import jade.gui.GuiEvent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import SMA.UserAgent;
import Utilities.Constance;

@SuppressWarnings("serial")
public class InstrumentSelectView extends JAgentFrame{
	
	private ImageFlow imageFlow = null;

	public InstrumentSelectView(UserAgent agent) {
		super(agent);
		this.setTitle("ChooseView");
		this.setSize(Constance.Windows_width, Constance.Windows_height);
	    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    
	    JPanel imageFlowPanel=new JPanel(new BorderLayout());
    	imageFlowPanel.setBackground(new Color(110, 110, 110));
    	
    	JButton home = new JButton();
		Icon icon = new ImageIcon("src/home.png");
		home.setBounds(140,50,100,100);
		home.setIcon(icon);
		imageFlowPanel.add(home);
		
    	
    	JLabel choose=new JLabel("Choose your instrument");
    	choose.setBounds(500, 30, 500, 150);
    	choose.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 50));
    	choose.setHorizontalAlignment(SwingConstants.CENTER);
    	choose.setForeground(Color.WHITE);
    	imageFlowPanel.add(choose,BorderLayout.NORTH);
    	
    	imageFlow = new ImageFlow(new File("src/instrument/"),agent);
    	imageFlowPanel.add(imageFlow);
    	this.add(imageFlowPanel);
    	
    	home.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				GuiEvent ev = new GuiEvent(this,UserAgent.SELECT_EVENT);
				ev.addParameter(UserAgent.return_Menu);
				myAgent.postGuiEvent(ev);
			}
		});
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("swipe")) {
 			if ((String)evt.getNewValue() == "LEFT") {
 				imageFlow.scrollAndAnimateBy(-1);
 			} else if ((String)evt.getNewValue() == "RIGHT") {
 				imageFlow.scrollAndAnimateBy(1);
 			}
 			
 		}
	}

}
