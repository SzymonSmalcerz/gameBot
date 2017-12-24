package com.smalcerz.gameBot.bot;

import java.awt.AWTException;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

import javax.swing.JFrame;



public class Bot{
	
	private Robot robot;

	long currentTime;
	long lastTime;
	int fps;
	
	private Thread clickThread;
	private Thread frameThread;
	
	private JFrame frame;
	int screenWidth;
	int screenHeight;
	
	boolean clicking;
	
	private static Bot bot = new Bot();
	
	private Bot(){
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void setVariables() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.screenWidth = (int) screenSize.getWidth();
		this.screenHeight = (int) screenSize.getHeight();
		this.fps = 30;
		this.currentTime = System.currentTimeMillis();
		this.lastTime = System.currentTimeMillis();
		this.clicking = false;
		
		
//		Rectangle rec = new Rectangle(0, 0, this.screenWidth, this.screenHeight);
//		BufferedImage image =this.robot.createScreenCapture(rec);		
	}
	
	public void setFrame() {
		
		frameThread = new Thread() {
			public void run() {
				frame = new JFrame("szymcio clickerBot");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(screenWidth/2,screenHeight/2);
				frame.setMinimumSize(new Dimension(screenWidth/2,screenHeight/2));
				frame.setMaximumSize(new Dimension(screenWidth/2,screenHeight/2));
				frame.setResizable(false);
				frame.setLayout(null);
				frame.pack();
				frame.setVisible(true);
				frame.setState(Frame.ICONIFIED);
				frame.setState(Frame.NORMAL);
				
				
				
				Button btnStart = new Button("start");
				btnStart.setSize(new Dimension(screenWidth/15, screenHeight/15));
				btnStart.setLocation(screenWidth/20, screenHeight/20);
				btnStart.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						System.out.println("CLICKERD");
						Bot bot;
						bot = Bot.getInstance();
						bot.setClicking();
						notify();
						System.out.println(bot.isClicking());
					}
				});
				frame.add(btnStart);
			}
		};
		
		
		frameThread.start();
		
	}
	

	public void start() {
		this.setVariables();
		this.setFrame();
		this.run();
	}
	
 
	public void run() {
		

		
		clickThread = new Thread() {
			public void run() {
				
				while(true) {
					
					
						click();
					
				}
			}
		};
		
		clickThread.start();
		
	}
	
	private synchronized void click() {
		
		int x;
		int y;
		
		int currentX;
		int currentY;
		if(!this.isClicking()) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		robot.delay(100);
		System.out.println("IMHEEERE");
		x = getRandXPos();
		y = getRandYPos();
		
		
		currentX = (int) MouseInfo.getPointerInfo().getLocation().getX();
		currentY = (int) MouseInfo.getPointerInfo().getLocation().getY();
		if(currentX != x || currentY != y) {
			clicking = false;
		}else {
			robot.mouseMove(x, y);
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		}
	}
	
	public int getRandXPos() {
		return (int) (this.screenWidth/2 + Math.floor((Math.random()*2 -1 ) * this.screenWidth/15));
	}
	
	
	public int getRandYPos() {
		return (int) (this.screenHeight/2 + Math.floor((Math.random()*2 -1 ) * this.screenHeight/15));
	}
	
	public synchronized void stopClicking() {
		this.clicking = false;
	}
	
	public synchronized void setClicking() {
		
		this.clicking = true;
	}
	
	
		
		public boolean isClicking() {
		return this.clicking;
	}
	
	public synchronized static Bot getInstance() {
		return bot;
	}
}
