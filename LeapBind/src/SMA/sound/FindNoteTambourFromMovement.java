package SMA.sound;

import javax.vecmath.Point3d;

import com.sun.j3d.utils.geometry.Cylinder;

import Utilities.Constance;
import Utilities.Movement;

public class FindNoteTambourFromMovement  {

	//private Vector<>
	
	Point3d center_left;
	Cylinder cylinder;
	Point3d center_right;// center is the bottom of the circle
	
	
/*	float AXLow = 150; //150
	float BZLow = 50;
	float AXHi = 250;
	float BZHi = 100;
	float hightLowY=(float) (Constance.Windows_height * 0.65);//270;
	float hightHighY=(float) (Constance.Windows_height * 0.72);//420;

	
*/	
	
	double xHi;
	double xLow;
	double xBreak;
	double  yHi;
	int note;
	Movement movement;

	//��x-x1)^2/A^2+(y-y1)^2/B^2 <0 EXICTE
	
	public FindNoteTambourFromMovement(Movement movement) {
		
		 xHi=Constance.Windows_width*0.455;
		 xLow= Constance.Windows_width*0.13;
		 yHi=Constance.Windows_height*0.651;
		this.movement=movement;
	}

public	int matchNote(){
		
		Point3d pos=movement.getPos();
		System.out.println (pos.x);
		System.out.println (pos.y );
		System.out.println (pos.z);
		note= 85;

		if (IsLeft(pos)){
		//	62 Mute Hi Conga, 63 Open Hi Conga
			note = 63;
			if (IsDrumLow(pos)){					
					note = 62;
				}
			
		}
		
		else{
			// 47 Low-Mid Tom, 48HIGH-Mid Tom 
			pos.x-=Constance.Windows_width*0.5;
			note = 48;
			if (IsDrumLow(pos)){
				note = 47;
			}
			
		}


		System.out.println("NOTE de behaviour: " + String.valueOf(note));
	//	int note = matchNote();
		
	//	 volume= matchVolume();
		return note;
		
	}


public boolean IsLeft(Point3d pos){
	if (  pos.x /Constance.Windows_width<= 0.5){
		return true;			
	}				
	return false;		
}

public boolean IsRight(Point3d pos){

	if ( pos.x /Constance.Windows_width> 0.5){
		return true;			
	}
			
	return false;				
	
}
public boolean IsDrumLow(Point3d pos){
	//double volum=( (pos.x-Constance.Windows_width *center_left.x)*(pos.x-Constance.Windows_width *center_left.x) ) /(AXHi*AXHi)+( (pos.z-center_left.z)*(pos.z-center_left.z) ) /(BZLow*BZLow);
	if ( pos.y>yHi && pos.x>xLow&& pos.x<xHi){
		return true;			
	}
			
	return false;
				
}
/*
public boolean IsDrumLow(Point3d pos){
	
	if (pos.y >LowY){
		return true;			
	}
			
	return false;
					
}*/
	
	/*public boolean IsLeftDrumLow(Point3d pos){
		double volum=( (pos.x-Constance.Windows_width *center_left.x)*(pos.x-Constance.Windows_width *center_left.x) ) /(AXLow*AXLow)+( (pos.z-center_left.z)*(pos.z-center_left.z) ) /(BZLow*BZLow);
		if (volum<=1 && pos.y >=Constance.Windows_height *hightLowY&& pos.y <=Constance.Windows_height * hightHighY){
			return true;			
		}				
		return false;		
	}
	
	public boolean IsRightDrumLow(Point3d pos){
		double volum=( (pos.x-Constance.Windows_width *center_right.x)*(pos.x-Constance.Windows_width *center_right.x) ) /(AXLow*AXLow)+( (pos.z-center_right.z)*(pos.z-center_right.z) ) /(BZLow*BZLow);
		if (volum<=1 && pos.y >=Constance.Windows_height *hightLowY&& pos.y <= Constance.Windows_height *hightHighY){
			return true;			
		}
				
		return false;				
		
	}
	public boolean IsLeftDrumHi(Point3d pos){
		double volum=( (pos.x-Constance.Windows_width *center_left.x)*(pos.x-Constance.Windows_width *center_left.x) ) /(AXHi*AXHi)+( (pos.z-center_left.z)*(pos.z-center_left.z) ) /(BZLow*BZLow);
		if (volum<=1 && pos.y >=Constance.Windows_height *hightLowY&& pos.y <=Constance.Windows_height * hightHighY){
			return true;			
		}
				
		return false;
					
	}
	
	public boolean IsRightDrumHi(Point3d pos){
		double volum=( (pos.x-Constance.Windows_width *center_right.x)*(pos.x-Constance.Windows_width *center_right.x) ) /(AXHi*AXHi)+( (pos.z-center_right.z)*(pos.z-center_right.z) ) /(BZLow*BZLow);
		if (volum<=1 && pos.y >=Constance.Windows_height *hightLowY&& pos.y <= Constance.Windows_height *hightHighY){
			return true;			
		}
				
		return false;
						
	}*/
	
	
	public int matchVolume() {
		int volume = 180;
		int current_speed = (int) Math.abs(movement.getSpeed());
		volume = (int) (volume + (current_speed-1200)/80);
		if (volume<100){
			volume=100;
		}
		if (volume>220){
			volume=220;
		}
		if (current_speed <30){
			System.out.println(current_speed + ": 0 volume for this note..");
			volume=0;
		}
		
		return volume;
	}
	
}
