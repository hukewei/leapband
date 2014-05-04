import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class playview extends JFrame{
	public playview(){
		
		this.setTitle("PlayView");
		this.setSize(600,450);
		this.setLocationRelativeTo(null);		//窗口显示在屏幕中间
		this.setLayout(null);
		
		JButton home = new JButton();
		Icon icon = new ImageIcon("/Users/akeharuxiao/Dropbox/image/fangzi.png");
		home.setBounds(15,15,35,35);
		home.setIcon(icon);
		home.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			setVisible(false);
			}
		});
		
		JLabel player = new JLabel(new ImageIcon("/Users/akeharuxiao/Dropbox/image/player.png"));
		player.setBounds(80, 15, 70, 70);
		JButton music = new JButton("Choose a music");
		music.setBounds(220,40,150,40);
		music.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new musicview();
			}
		});
		JButton stop = new JButton();
		Icon icone = new ImageIcon("/Users/akeharuxiao/Dropbox/image/stop.png");
		stop.setBounds(420,40,40,40);
		stop.setIcon(icone);
		JLabel volume = new JLabel(new ImageIcon("/Users/akeharuxiao/Dropbox/image/volume.png"));
		volume.setBounds(500, 30, 70, 60);
		JLabel play = new JLabel(new ImageIcon("/Users/akeharuxiao/Dropbox/image/play.png"));
		play.setBounds(0, 170, 600, 256);
		
		this.add(home);
		this.add(player);
		this.add(music);
		this.add(stop);
		this.add(volume);
		this.add(play);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //arret du programme
		this.setVisible(true);
		
	}

}
