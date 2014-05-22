package View;

import jade.gui.GuiEvent;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;

import SMA.user.UserAgent;
import Utilities.Constance;

public class HomeMouseListener implements MouseListener {
	JAgentFrame myFrame = null;
	public HomeMouseListener(JAgentFrame frame) {
		myFrame = frame;
	}
	@Override
	public void mouseExited(MouseEvent e) {
		myFrame.changeCursorImage("src/images/cursor.png");
		if (myFrame.click_task != null) {
			myFrame.click_task.cancel();
			myFrame.click_task = null;
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
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
