package SMA.sound;

import javax.vecmath.Point3d;

import com.sun.j3d.utils.geometry.Cylinder;

import Utilities.Movement;

public class FindNoteTambourFromMovement  {

	//private Vector<>
	
	Point3d center_left;
	Cylinder cylinder;
	Point3d center_right;// center is the bottom of the circle
	
	float AXLow = 150;
	float BZLow = 50;
	float AXHi = 250;
	float BZHi = 100;
	float hightLowY=270;
	float hightHighY=420;
	int note;
	Movement movement;

	//��x-x1)^2/A^2+(y-y1)^2/B^2 <0 EXICTE
	
	public FindNoteTambourFromMovement(Movement movement) {
		
		center_left=new Point3d(375,270,0); //270-420 high //left point top:130x y350, right: x620 y350
		center_right=new Point3d(970,270,0);//  z== -100 100 // // LEFTx 720 RIGHT x 1220 

		cylinder=new Cylinder(150,245);   //Cylinder(float height, float radius)
		this.movement=movement;
	}

public	int matchNote(){
		
		Point3d pos=movement.getPos();

		if (IsLeftDrumHi(pos)){
		//	62 Mute Hi Conga, 63 Open Hi Conga
			if (IsLeftDrumLow(pos)){					
					note = 62;
				}
			note = 63;
		}
		
		if (IsRightDrumHi(pos)){
			// 47 Low-Mid Tom, 48HIGH-Mid Tom 
			
			if (IsRightDrumLow(pos)){
				note = 47;
			}
			note = 48;
		}


		System.out.println("NOTE de behaviour: " + String.valueOf(note));
	//	int note = matchNote();
		
	//	 volume= matchVolume();
		return note;
		
	}
	
	public boolean IsLeftDrumLow(Point3d pos){
		double volum=( (pos.x-center_left.x)*(pos.x-center_left.x) ) /(AXLow*AXLow)+( (pos.z-center_left.z)*(pos.z-center_left.z) ) /(BZLow*BZLow);
		if (volum<=1 && pos.y >=hightLowY&& pos.y <= hightHighY){
			return true;			
		}				
		return false;		
	}
	
	public boolean IsRightDrumLow(Point3d pos){
		double volum=( (pos.x-center_right.x)*(pos.x-center_right.x) ) /(AXLow*AXLow)+( (pos.z-center_right.z)*(pos.z-center_right.z) ) /(BZLow*BZLow);
		if (volum<=1 && pos.y >=hightLowY&& pos.y <= hightHighY){
			return true;			
		}
				
		return false;				
		
	}
	public boolean IsLeftDrumHi(Point3d pos){
		double volum=( (pos.x-center_left.x)*(pos.x-center_left.x) ) /(AXHi*AXHi)+( (pos.z-center_left.z)*(pos.z-center_left.z) ) /(BZLow*BZLow);
		if (volum<=1 && pos.y >=hightLowY&& pos.y <= hightHighY){
			return true;			
		}
				
		return false;
					
	}
	
	public boolean IsRightDrumHi(Point3d pos){
		double volum=( (pos.x-center_right.x)*(pos.x-center_right.x) ) /(AXHi*AXHi)+( (pos.z-center_right.z)*(pos.z-center_right.z) ) /(BZLow*BZLow);
		if (volum<=1 && pos.y >=hightLowY&& pos.y <= hightHighY){
			return true;			
		}
				
		return false;
						
	}
	
	
	public int matchVolume() {
		int volume = 80;

		volume = (int) (volume + (movement.getSpeed()-1200)/80);
		if (volume<60){
			volume=60;
		}
		if (volume>100){
			volume=100;
		}
		if (movement.getSpeed() <50){
			volume=0;
		}
		
		return volume;
	}
	
}
/*public class FindNoteTambourFromMovement extends FindNoteFromMovement {

	//private Vector<>
	
	Point3d center_left;
	Cylinder cylinder;
	Point3d center_right;// center is the bottom of the circle
	
	float AXLow = 150;
	float BZLow = 50;
	float AXHi = 250;
	float BZHi = 100;
	float hightLowY=270;
	float hightHighY=420;
	int note;

	//��x-x1)^2/A^2+(y-y1)^2/B^2 <0 EXICTE
	
	public FindNoteTambourFromMovement(Movement movement) {
		super(movement);
		center_left=new Point3d(375,270,0); //270-420 high //left point top:130x y350, right: x620 y350
		center_right=new Point3d(970,270,0);//  z== -100 100 // // LEFTx 720 RIGHT x 1220 

		cylinder=new Cylinder(150,245);   //Cylinder(float height, float radius)
		
=======
import Utilities.Movement;

public class FindNoteTambourFromMovement extends FindNoteFromMovement {

	//private Vector<>
	
	
	public FindNoteTambourFromMovement(Movement movement) {
		super(movement);
>>>>>>> keweihu/SendMoveBehaviour
	}

	@Override
	public void action() {
<<<<<<< HEAD
		
		Point3d pos=movement.getPos();

		if (IsLeftDrumHi(pos)){
		//	62 Mute Hi Conga, 63 Open Hi Conga
			if (IsLeftDrumLow(pos)){					
					note = 62;
				}
			note = 63;
		}
		
		if (IsRightDrumHi(pos)){
			// 47 Low-Mid Tom, 48HIGH-Mid Tom 
			
			if (IsRightDrumLow(pos)){
				note = 47;
			}
			note = 48;
		}


		System.out.println("NOTE de behaviour: " + String.valueOf(note));
		int note = matchNote();
		
		int volume= matchVolume();
		
		
	}
	
	public boolean IsLeftDrumLow(Point3d pos){
		double volum=( (pos.x-center_left.x)*(pos.x-center_left.x) ) /(AXLow*AXLow)+( (pos.z-center_left.z)*(pos.z-center_left.z) ) /(BZLow*BZLow);
		if (volum<=1 && pos.y >=hightLowY&& pos.y <= hightHighY){
			return true;			
		}				
		return false;		
	}
	
	public boolean IsRightDrumLow(Point3d pos){
		double volum=( (pos.x-center_right.x)*(pos.x-center_right.x) ) /(AXLow*AXLow)+( (pos.z-center_right.z)*(pos.z-center_right.z) ) /(BZLow*BZLow);
		if (volum<=1 && pos.y >=hightLowY&& pos.y <= hightHighY){
			return true;			
		}
				
		return false;				
		
	}
	public boolean IsLeftDrumHi(Point3d pos){
		double volum=( (pos.x-center_left.x)*(pos.x-center_left.x) ) /(AXHi*AXHi)+( (pos.z-center_left.z)*(pos.z-center_left.z) ) /(BZLow*BZLow);
		if (volum<=1 && pos.y >=hightLowY&& pos.y <= hightHighY){
			return true;			
		}
				
		return false;
					
	}
	
	public boolean IsRightDrumHi(Point3d pos){
		double volum=( (pos.x-center_right.x)*(pos.x-center_right.x) ) /(AXHi*AXHi)+( (pos.z-center_right.z)*(pos.z-center_right.z) ) /(BZLow*BZLow);
		if (volum<=1 && pos.y >=hightLowY&& pos.y <= hightHighY){
			return true;			
		}
				
		return false;
						
	}
	
}*/
