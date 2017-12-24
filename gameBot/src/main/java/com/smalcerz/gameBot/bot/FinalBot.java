package com.smalcerz.gameBot.bot;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;

import com.smalcerz.gameBot.bot.helpers.Helper;

public class FinalBot {

	
	private boolean clicking;
	private boolean clickingBoss;
	private static FinalBot finalBot = new FinalBot();
	private Robot robot;
	
	private Coords[] mobsCoords;
	private Coords[] upgradesCoords;

	private int screenWidth; 
	private int screenHeight;
	 
	private FinalBot() {
		this.clicking = false;
		this.clickingBoss = false;
		try {
			this.robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.screenWidth = (int) screenSize.getWidth();
		this.screenHeight = (int) screenSize.getHeight();
	}
	
	public synchronized void stopClicking() {
		
		this.clicking = false;
		
	}
	
	
	public synchronized void startClicking() {
		
		this.clicking = true;
		notifyAll();
	}
	
	public synchronized void startClickingBoss() {
		
		this.clickingBoss = true;
		notifyAll();
		System.out.println("boss clicking");
	}
	
	public synchronized void tryClicking() {
		
		
		while(!this.clicking && !this.clickingBoss) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		int x;
		int y;
		Coords rand;
		if(this.clicking) {
			rand = this.getRandCoords();
			x = rand.getX();
			y = rand.getY();
		}else {
			x = this.screenWidth/2;
			y = this.screenHeight/2;
		}
		
		
		
		
		int currentX;
		int currentY;
		
		robot.mouseMove(x, y);
		robot.delay(100);
		
		currentX = (int) MouseInfo.getPointerInfo().getLocation().getX();
		currentY = (int) MouseInfo.getPointerInfo().getLocation().getY();
		
		if(currentX == x && currentY == y) {
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			
		}else {
			this.clickingBoss = false;
			this.clicking = false;
		}
	}
	
	public static FinalBot getInstance() {
		return finalBot;
	}
	

	
	
	
	public Coords[] getMobsCoords() {
		return mobsCoords;
	}

	public void setMobsCoords(Coords[] mobsCoords) {
		this.mobsCoords = mobsCoords;
	}

	public Coords[] getUpgradesCoords() {
		return upgradesCoords;
	}

	public void setUpgradesCoords(Coords[] upgradesCoords) {
		this.upgradesCoords = upgradesCoords;
	}

	public Coords getRandCoords() {
		Coords temp[] = this.mobsCoords;
		int index = Helper.getRandomInt(0, temp.length);
		return temp[index];
	}
	
	

}
