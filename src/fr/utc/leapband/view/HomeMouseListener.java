package fr.utc.leapband.view;

import jade.gui.GuiEvent;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;

import javax.swing.JButton;

import fr.utc.leapband.sma.user.UserAgent;
import fr.utc.leapband.utilities.Constance;
import fr.utc.leapband.utilities.ImageTimerTask;
import fr.utc.leapband.utilities.OvalBorder;

public class HomeMouseListener implements MouseListener {
	JAgentFrame myFrame = null;
	JButton home = null;
	public HomeMouseListener(JAgentFrame frame, JButton c) {
		myFrame = frame;
		this.home = c;
	}
	@Override
	public void mouseExited(MouseEvent e) {
		if(home!=null){
			home.setBorder(null);
		}
		myFrame.changeCursorImage("images/cursor.png");
		if (myFrame.click_task != null) {
			myFrame.click_task.cancel();
			myFrame.click_task = null;
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		if(home!=null){
			home.setBorder(new OvalBorder(home.getWidth(), home.getHeight(), new Color(153,153,255)));
			home.setBorderPainted(true);
		}
		myFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		myFrame.click_task = new Timer();
		myFrame.click_task.schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	GuiEvent ev = new GuiEvent(this,UserAgent.SELECT_EVENT);
						ev.addParameter(UserAgent.return_Menu);
						myFrame.myAgent.postGuiEvent(ev);
		            }
		        }, 
		        Constance.click_delay 
		);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
