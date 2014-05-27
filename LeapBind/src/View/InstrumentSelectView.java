package View;

import jade.gui.GuiEvent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.util.Timer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import SMA.user.UserAgent;
import Utilities.Constance;
import Utilities.ImageFlowItem;
import Utilities.OvalBorder;

@SuppressWarnings("serial")
public class InstrumentSelectView extends JAgentFrame{
	
	private ImageFlow imageFlow = null;
	private JButton home;

	public InstrumentSelectView(UserAgent agent) {
		super(agent);
		this.setTitle("ChooseView");
		this.setSize(Constance.Windows_width, Constance.Windows_height);
	    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    
	    JPanel imageFlowPanel=new JPanel(new BorderLayout());
    	imageFlowPanel.setBackground(new Color(110, 110, 110));
    	
    	home = new JButton();
		Icon icon = new ImageIcon("src/images/home.png");
		home.setBounds(0,0,100,100);
		home.setIcon(icon);
		//home.setBackground(new Color(110, 110, 110));
		imageFlowPanel.add(home);
		
		
    	
    	JLabel choose=new JLabel("Choose your instrument");
    	choose.setBounds(500, 10, 500, 200);
    	choose.setFont(new Font("Chalkboard", Font.PLAIN, 40));
    	choose.setHorizontalAlignment(SwingConstants.CENTER);
    	choose.setForeground(Color.ORANGE);
    	imageFlowPanel.add(choose);
    	
    	imageFlow = new ImageFlow(new File("src/images/instrument/"),agent);
    	imageFlowPanel.add(imageFlow);
    	this.add(imageFlowPanel);
    	
    	home.addMouseListener(new HomeMouseListener(this,home));
    	home.setContentAreaFilled(false);
    	home.setOpaque(false);
    	home.setBorderPainted(false);
    	
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (isVisible()) {
			if (evt.getPropertyName().equals("swipe")) {
	 			if ((String)evt.getNewValue() == "LEFT") {
	 				imageFlow.scrollAndAnimateBy(-1);
	 			} else if ((String)evt.getNewValue() == "RIGHT") {
	 				imageFlow.scrollAndAnimateBy(1);
	 			} else if ((String)evt.getNewValue() == "REAR") {
	 				GuiEvent ev = new GuiEvent(this,UserAgent.SELECT_INSTRUMENT_EVENT);
					ev.addParameter(UserAgent.instrument_Mode);
					ev.addParameter(((ImageFlowItem)imageFlow.getSelectedValue()).getLabel());
					myAgent.postGuiEvent(ev);
	 			}
	 		}
		}
	}

}
