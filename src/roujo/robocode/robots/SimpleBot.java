package roujo.robocode.robots;

import java.awt.Color;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

public class SimpleBot extends AdvancedRobot {
	private boolean lockedOn = false;
	
	public void run() {
		// Set colors
		setBodyColor(Color.green);
		setGunColor(Color.red);
		setRadarColor(Color.black);
		setScanColor(Color.yellow);

        findTarget();
        //*
        while(true) {
        	scan();
        }//*/
		/*
		while (true) {
			// Tell the game that when we take move,
			// we'll also want to turn right... a lot.
			setTurnLeft(10000);
			// Limit our speed to 5
			setMaxVelocity(5);
			// Start moving (and turning)
			ahead(10000);
			// Repeat.
		}//*/
		//System.out.println("H = " + getHeadingRadians());
	}
	
	public void findTarget() {
		setAdjustRadarForGunTurn(true);
		setAdjustRadarForRobotTurn(true);
		setAdjustGunForRobotTurn(true);
		while(!isLockedOn()) {
			turnRadarLeft(10);			
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		if(!isLockedOn()) {
			lockOn();
		}
		
		double bearing = e.getBearingRadians();
		double heading = getHeadingRadians();
		double gun = getGunHeadingRadians();
		double radar = getRadarHeadingRadians();
		double absEnemyBearing = bearing + heading;
		System.out.println("B=" + bearing + "/H=" + heading);
		System.out.println("R=" + radar + "/G=" + gun);
		System.out.println("AEB=" + absEnemyBearing);
		
		if(absEnemyBearing > radar) {
			turnRadarRightRadians(absEnemyBearing - radar);
		} else if(absEnemyBearing < radar) {
			turnRadarLeftRadians(radar - absEnemyBearing);
		}
		if(radar > gun) {
			turnGunRightRadians(radar - gun);
		} else if(radar < gun) {
			turnGunLeftRadians(gun - radar);
		}
		
		//turnGunLeftRadians(offset);
		fire(3);
	}
	
	public boolean isLockedOn() {
		return lockedOn;
	}
	
	public void lockOn() {
		this.lockedOn = true;
	}
}
