package Controller;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import SMA.UserAgent;
import Utilities.Constance;

import com.leapmotion.leap.CircleGesture;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.FingerList;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Pointable;
import com.leapmotion.leap.PointableList;
import com.leapmotion.leap.Screen;
import com.leapmotion.leap.ScreenList;
import com.leapmotion.leap.SwipeGesture;
import com.leapmotion.leap.Vector;

public class LeapListener extends Listener {
	
	private UserAgent myAgent;
	

	//True for Debugging
	boolean DEBUG = false;

	//0 = Key Tap 
	//1 = Finger Tap
	int CLICK_TYPE = 0;


	boolean USE_CALIBRATED_SCREEN = true;

	//Just to control the speed, it can be changed accordingly to needs
	int SLOW = 0;

	//Screen resolution, it should match the current screen resolution for more precise movements
	int SCREEN_X = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
	int SCREEN_Y = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;


	float cur_x = 0, cur_y = 0;

	int fingers_count = 0;
	int prev_fingers_count = 0;

	boolean Lclicked = false;
	boolean Rclicked = false;
	boolean keystroke = false;
	boolean LHold = false;
	boolean ENABLE_MOUSE = false;

	boolean Swype = false;
	boolean Circle = false;
    long last_timestamp = 0;


    public LeapListener(UserAgent userAgent) {
		myAgent = userAgent;
	}

	public void onInit(Controller controller) {
        System.out.println("Initialized");
        System.out.println("Current screen resolution: " + SCREEN_X +"x" + SCREEN_Y);
    }

    public void onConnect(Controller controller) {
        System.out.println("Connected");
    }

    public void onDisconnect(Controller controller) {
        System.out.println("Disconnected");
    }

    public void onExit(Controller controller) {
        System.out.println("Exited");
        System.exit(0);
    }

    public void onFrame(Controller controller) {
        // Get the most recent frame and report some basic information
        Frame frame = controller.frame();
        int numGestures = frame.gestures().count();
        
        
        	int up_count = 0;
        	int down_count = 0;
        	int left_count = 0;
        	int right_count = 0;
        	int circle_count = 0;
        	int screen_tap_count = 0;
        	if ((frame.timestamp() - last_timestamp > Constance.Gesture_Interval)) {
        	for (int i=0; i < numGestures; i++) {
        	    if(frame.gestures().get(i).type() == Gesture.Type.TYPE_KEY_TAP && !Lclicked) {
        	    	//If Key Tap Mode enabled 
        	    	//Left Click
        	    	if(CLICK_TYPE == 0)
        	    	{	
                		Lclicked = true;
	                    if(DEBUG)
	                    {
	                    	System.out.println("LClicked - Key Tap");
	                    }
        	    	}
        	    	
        	    	
                	if(DEBUG)
                		System.out.println("key tap");
        	    	slow();
        	    } else if (frame.gestures().get(i).type() == Gesture.Type.TYPE_SWIPE && !Swype) {
        	        ENABLE_MOUSE = false;
        	    	SwipeGesture swipe = new SwipeGesture(frame.gestures().get(i));
        	    	if (swipe.speed() > 500) {
        	    		boolean is_horizontal = (Math.abs(swipe.direction().getX()) > Math.abs(swipe.direction().getY()));
        	    		if (!is_horizontal) {
        	    			if(swipe.direction().getY() > 0){
          	                  //swipeDirection = "up";
          	                  up_count++;
          	              	} else {
          	                  //swipeDirection = "down";
          	                  down_count++;
          	              	}          
        	    		} else {
        	    			if(swipe.direction().getX() > 0){
        	                  //swipeDirection = "right";
        	                  right_count++;
        	              	} else {
        	                  //swipeDirection = "left";
        	                  left_count++;
        	              	}  
        	    		}
        	    		       
        	    		
        	    	}
//                    System.out.println("Swipe id: " + swipe.id()
//                               + ", " + swipe.state()
//                               + ", position: " + swipe.position()
//                               + ", direction: " + swipe.direction()
//                               + ", speed: " + swipe.speed());
        	    	
        	    	
        	    	//switchApplication();
        	    	Swype = true;
        	    	if(DEBUG)
        	    		System.out.println("swype");
        	    	
        	    	slow();
        	    } else if (frame.gestures().get(i).type() == Gesture.Type.TYPE_CIRCLE && !Circle) {
      	    		if(DEBUG)
    	    		System.out.println("Circle");
      	    		circle_count++;
        	    	Circle = true; 	
        	    	CircleGesture circle = new CircleGesture(frame.gestures().get(i));
        	        float progress = circle.progress();
        	        if (progress >= 1.0f) {
        	        	
        	        	//copy();
          	    		if(DEBUG)
        	    		System.out.println("Circle - Copy");
        	        }
        	        else
        	        {
        	        	paste();
              	    	if(DEBUG)
            	    		System.out.println("Circle - Paste");
        	        }
        	    	
        	    	
        	    	slow();	
        	    }  else if (frame.gestures().get(i).type() == Gesture.Type.TYPE_SCREEN_TAP) {
        	    	System.out.println("screen tap");
        	    	screen_tap_count++;
        	    } else
        	    {
        	    	Circle = false; 	
        	    	Swype = false;
        	    }
        	    
        	  }
        	}
        	
        	if (Swype || Circle) {
        		int [] direction_count = {left_count, right_count, up_count, down_count, circle_count, screen_tap_count};
        		int maxValue = direction_count[0];  
        	    for(int i=1;i < direction_count.length;i++){  
        	    	if(direction_count[i] > maxValue){  
        	    		maxValue = direction_count[i];  
        	    	}  
        	    }

        		if (maxValue > 0 ) {
        		      if (maxValue == left_count) {
        		    	  myAgent.doSwipe("LEFT");
        		      } else if (maxValue == right_count) {
        		    	  myAgent.doSwipe("RIGHT");
        		      } else if (maxValue == up_count) {
        		    	  myAgent.doSwipe("UP");
        		      } else if (maxValue == down_count) {
        		    	  myAgent.doSwipe("DOWN");
        		      } else if (maxValue == screen_tap_count ) {
        		    	  clickMouse(0);
        		    	  releaseMouse(0);
        		      }
        		      last_timestamp = frame.timestamp();
        		}
        	}
        if (!frame.fingers().isEmpty()) {
          
            // Get fingers
            FingerList fingers = frame.fingers();
            fingers_count = frame.fingers().count();
            
            if(fingers_count != prev_fingers_count)
            {
            	if (DEBUG)System.out.println("Currently " + fingers_count + " fingers visible.\n");
            	prev_fingers_count = fingers_count;
            	if (fingers_count > 1) {
            		ENABLE_MOUSE = false;
            	} else {
            		ENABLE_MOUSE = true;
            	}
            }
            
            
            if (!fingers.isEmpty()) {
                // Calculate the hand's average finger tip position
                Vector avgPos = Vector.zero();
                for (Finger finger : fingers) {
                    avgPos = avgPos.plus(finger.tipPosition());
                }
                avgPos = avgPos.divide(fingers.count());
              
                
                if(USE_CALIBRATED_SCREEN){
	                //New Pointing System using first calibrated screen. Thanks to wooster @ freenode IRC
	                ScreenList screens = controller.locatedScreens();

	                if (screens.isEmpty()) return;
	                Screen s = screens.get(0);
	                PointableList pointables = frame.hands().get(0).pointables();

	                if(pointables.isEmpty()) return;
	                Pointable firstPointable = pointables.get(0);
	                Vector intersection = s.intersect(
	                        firstPointable,
	                        true, // normalize
	                        1.0f // clampRatio
	                        );

			        // if the user is not pointing at the screen all components of
			        // the returned vector will be Not A Number (NaN)
			        // isValid() returns true only if all components are finite
			        if (!intersection.isValid()) return;

			        float x = s.widthPixels() * intersection.getX();
			        // flip y coordinate to standard top-left origin
			        float y = s.heightPixels() * (1.0f - intersection.getY());
			        moveMouse(x, y);

                } else
                {
                    moveMouse(avgPos.getX()*15, SCREEN_Y - avgPos.getY()*5);
                }

                // Left Click hold
                if(fingers.count() == 2 && !LHold && avgPos.getZ()<=-70)
                {
                	clickMouse(0);
                	LHold = true;
                	
                    if(DEBUG)
                    {
                    	System.out.println("LHold");
                    }
                	
                }
                
                else if(fingers.count() != 2 || avgPos.getZ()>0)
                {
                	if(LHold)
                		releaseMouse(0);
                	LHold = false;
                	slow();
                	
                }
                
                
                
                // Right Click
                if(fingers.count() == 3 && !Rclicked && avgPos.getZ()<=-70)
                {
                	clickMouse(1);
                	releaseMouse(1);
                	
                	Rclicked = true;
                	
                    if(DEBUG)
                    {
                    	System.out.println("RClicked");
                    }

                }
                
                else if(fingers.count() != 3 ||  avgPos.getZ()>0)
                {
                	Rclicked = false;
                	slow();
                	
                }

                
                // Place both hands on device
                if(frame.hands().count()>1){
                
                	Hand hand1 = frame.hands().get(0);
//                	Vector normal1 = hand1.palmNormal();
                	Hand hand2 = frame.hands().get(1);
//                	Vector normal2 = hand2.palmNormal();
                	
                	ScreenList screens = controller.locatedScreens();

	                if (screens.isEmpty()) return;
	                Screen s = screens.get(0);
	                

	                PointableList pointables = hand1.pointables();

	                if(pointables.isEmpty()) return;
	                Pointable firstPointable = pointables.get(0);
	                Vector intersection = s.intersect(
	                        firstPointable,
	                        true, // normalize
	                        1.0f // clampRatio
	                        );

			        // if the user is not pointing at the screen all components of
			        // the returned vector will be Not A Number (NaN)
			        // isValid() returns true only if all components are finite
			        if (!intersection.isValid()) return;

			        float x_1 = s.widthPixels() * intersection.getX();
			        // flip y coordinate to standard top-left origin
			        float y_1 = s.heightPixels() * (1.0f - intersection.getY());
			        float z_1 = firstPointable.tipPosition().getZ();
			        System.out.println("z1 = " + z_1);
			        pointables = hand2.pointables();

	                if(pointables.isEmpty()) return;
	                firstPointable = pointables.get(0);
	                intersection = s.intersect(
	                        firstPointable,
	                        true, // normalize
	                        1.0f // clampRatio
	                        );

			        // if the user is not pointing at the screen all components of
			        // the returned vector will be Not A Number (NaN)
			        // isValid() returns true only if all components are finite
			        if (!intersection.isValid()) return;

			        float x_2 = s.widthPixels() * intersection.getX();
			        // flip y coordinate to standard top-left origin
			        float y_2 = s.heightPixels() * (1.0f - intersection.getY());
			        float z_2 = firstPointable.tipPosition().getZ();

			        
			        
			        // track hands position
//			        Vector palm = hand1.palmPosition();
//			        Vector direction = hand1.direction();
//			        Vector intersect = s.intersect(palm, direction, true);
//			              // intersection is in screen coordinates
//
//			       
//			        // test for NaN (not-a-number) result of intersection
//			        if (Float.isNaN(intersect.getX()) || Float.isNaN(intersect.getY()))
//			          return;
//
//			        float xNor_1 = s.widthPixels() * intersect.getX();
//			        float yNorm_1 = (float) (s.heightPixels() * (1.0f - intersect.getY())/1.3);
//			        
//			        palm = hand2.palmPosition();
//			        direction = hand2.direction();
//			        intersect = s.intersect(palm, direction, true);
//			              // intersection is in screen coordinates
//
//			        // test for NaN (not-a-number) result of intersection
//			        if (Float.isNaN(intersect.getX()) || Float.isNaN(intersect.getY()))
//			          return;
//
//			        float xNor_2 = s.widthPixels() * intersect.getX();
//			        float yNorm_2 = (float) (s.heightPixels() * (1.0f - intersect.getY())/1.3);
//
//                	myAgent.updateHands(
//                			xNor_1,
//                			yNorm_1,
//                			xNor_2,
//                			yNorm_2
//                	);
                	
			        myAgent.updateHands(
                			x_1,
                			y_1,
                			x_2,
                			y_2,
                			z_1,
                			z_2
                			);
                	

//	                if(hand1.fingers().count() >= 5 && !keystroke && avgPos.getZ()<=-70 && (normal1.roll() <5 || normal1.roll() > -5) && (normal2.roll() <5 || normal2.roll() > -5))
//	                {
//
//	                	showHideDesktop();
//
//	                    if(DEBUG)
//	                    {
//	                    	System.out.println("Show/Hide Desktop");
//	                    }
//
//	                	keystroke = true;
//
//	                	// To slow down the framerate, I found this would help avoid any sort of incorrect behaviour 
//	                	try {
//							Thread.sleep(200);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//	                }
//	                else
//	                {
//	                	keystroke = false;
//
//	                }




	            }

            }
            
            //slow();
        }
    }
    
    
    // Slows down the frame rate
    private void slow(){
    	try {
			Thread.sleep(SLOW);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    public void moveMouse(float x, float y)
    {
    	if (!ENABLE_MOUSE)
    		return;
    	 Robot mouseHandler;
    	 
    	 if(cur_x != x || cur_y != y){

    		 cur_x = x;
	    	 cur_y = y;

				try {
					//model.updatePosition(x, y);
					mouseHandler = new Robot();
					mouseHandler.mouseMove((int)x, (int)y);

				} catch (Exception e) {
					e.printStackTrace();
				}
	    	 }
    	 
    	 
    }
    
    // 0: Left
    // 1: Right
    // 2: Middle  -not implemented yet-
    public void clickMouse(int value)
    {
    	if (!ENABLE_MOUSE)
    		return;
    	int input;
    	
    	switch(value){
    		case 0: input = InputEvent.BUTTON1_MASK; break;
    		case 1: input = InputEvent.BUTTON3_MASK; break;
    		case 2: input = InputEvent.BUTTON2_MASK; break;
    		default: input = 0;
    	}
    	
    	 Robot mouseHandler;
    	 
    	
				try {

					mouseHandler = new Robot();
					mouseHandler.mousePress(input);

				} catch (AWTException e) {
					e.printStackTrace();
				}

    	 
    	 
    }
 
    // 0: Left
    // 1: Right
    // 2: Middle  -not implemented yet-
    public void releaseMouse(int value)
    {
    	if (!ENABLE_MOUSE)
    		return;
    	int input;
    	
    	switch(value){
    		case 0: input = InputEvent.BUTTON1_MASK; break;
    		case 1: input = InputEvent.BUTTON3_MASK; break;
    		case 2: input = InputEvent.BUTTON2_MASK; break;
    		default: input = 0;
    	}
    	
    	 Robot mouseHandler;
    	 
    	
				try {

					mouseHandler = new Robot();
					mouseHandler.mouseRelease(input);

				} catch (AWTException e) {
					e.printStackTrace();
				}

    	 
    	 
    }   
    
    
    public void showHideDesktop()
    {
    	 Robot keyHandler;
    	 
    	
				try {

					keyHandler = new Robot();
					keyHandler.keyPress(KeyEvent.VK_WINDOWS);
					keyHandler.keyPress(KeyEvent.VK_D);
					keyHandler.keyRelease(KeyEvent.VK_WINDOWS);
					keyHandler.keyRelease(KeyEvent.VK_D);

				} catch (AWTException e) {
					e.printStackTrace();
				}
    	 
    	 
    }

    public void copy()
    {
    	 Robot keyHandler;
    	 
    	
				try {

					keyHandler = new Robot();
					keyHandler.keyPress(KeyEvent.VK_CONTROL);
					keyHandler.keyPress(KeyEvent.VK_C);
					keyHandler.keyRelease(KeyEvent.VK_CONTROL);
					keyHandler.keyRelease(KeyEvent.VK_V);

				} catch (AWTException e) {
					e.printStackTrace();
				}

    	 
    	 
    }

    public void paste()
    {
    	 Robot keyHandler;
    	 
    	
				try {

					keyHandler = new Robot();
					keyHandler.keyPress(KeyEvent.VK_CONTROL);
					keyHandler.keyPress(KeyEvent.VK_V);
					keyHandler.keyRelease(KeyEvent.VK_CONTROL);
					keyHandler.keyRelease(KeyEvent.VK_V);

				} catch (AWTException e) {
					e.printStackTrace();
				}

    	 
    	 
    }
    public void switchApplication()
    {
    	 Robot keyHandler;
    	 
    	
				try {

					keyHandler = new Robot();
					keyHandler.keyPress(KeyEvent.VK_ALT);
					keyHandler.keyPress(KeyEvent.VK_TAB);
					keyHandler.keyRelease(KeyEvent.VK_ALT);
					keyHandler.keyRelease(KeyEvent.VK_TAB);

				} catch (AWTException e) {
					e.printStackTrace();
				}

    	 
    	 
    }
    
    public void setDebug(boolean d){
    	DEBUG = d;
    }
    
    public void setClickType(int i){
    	CLICK_TYPE = i;
    }
    
    public void setCalibratedScren(boolean d){
    	USE_CALIBRATED_SCREEN = d;
    }
}