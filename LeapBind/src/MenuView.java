import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class MenuView extends JFrame implements PropertyChangeListener {
	private JPanel buttonPane;

	public MenuView() {

		this.setTitle("Image View");
		this.setSize(Constance.Windows_width, Constance.Windows_height);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		SimpleModel.getInstance().addPropertyChangeListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//mouse = new MousePane();
		buttonPane = new JPanel();
		buttonPane.setLayout(null);
		this.add(buttonPane,BorderLayout.CENTER);
		buttonPane.setSize(new Dimension(Constance.Windows_width, Constance.Windows_height));

		//*************** button
		//mouse.setLayout(null);
		JButton start = new JButton("start");
		JButton stop = new JButton("stop");
		JButton exit = new JButton("exit");
		exit.addActionListener(new java.awt.event.ActionListener() {  
            public void actionPerformed(java.awt.event.ActionEvent evt) {  
                System.exit(1);  
            }  
        });
		start.setBounds(500, 50,500, 150);
		stop.setBounds(500,250,500,150);
		exit.setBounds(500,450,500,150);
				
//		start.setBackground(Color.yellow);
//		start.setOpaque(true);
//		start.setBorderPainted(false);
//		
//		stop.setBackground(Color.pink);
//		stop.setOpaque(true);
//		stop.setBorderPainted(false);
				
		
		buttonPane.add(start);
		buttonPane.add(stop);
		buttonPane.add(exit);
		
		this.setVisible(true);
		//this.getGraphicsConfiguration().getDevice().setFullScreenWindow(this);
		
		// personnel cursor
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursorImage = toolkit.getImage("src/cursor.png");
		Point cursorHotSpot = new Point(0,0);
		Cursor customCursor = toolkit.createCustomCursor(cursorImage, cursorHotSpot, "Cursor");
		this.setCursor(customCursor);
		
		//this.hideCursor();
	}

	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
//		if (evt.getPropertyName().equals("pos")) {
//			System.out.println("x = " + ((Cordinates) evt.getNewValue()).x
//					+ " y = " + ((Cordinates) evt.getNewValue()).y);
//			mouse.setPos(((Cordinates) evt.getNewValue()).x,
//					((Cordinates) evt.getNewValue()).y);
//		}

	}

}
