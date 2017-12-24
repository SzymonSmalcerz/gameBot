package com.smalcerz.gameBot.bot.seondWindow;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class PrintScreenRectangle extends JComponent{

	private static final long serialVersionUID = 1L;
	private Robot robot;
	int screenWidth;
	int screenHeight;
	
	
	public PrintScreenRectangle() throws AWTException {

		this.robot = new Robot();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.screenWidth = (int) screenSize.getWidth();
		this.screenHeight = (int) screenSize.getHeight();
	}
	 public void makeScreen(Graphics g, int widthOfFrame, int heightOfFrame)
	   {  
	      
	      Graphics2D g2 = (Graphics2D) g;
	      Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	      BufferedImage capture;
	      
	      
	    	capture = robot.createScreenCapture(screenRect);
			try {
				ImageIO.write(capture, "bmp", new File("asd.jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      
		  double referencedX = 1;
		  double referencedY = 1;
		  int c = 0;
		  Color color;
		  Rectangle box;
		  double sizeConverter = this.screenWidth/widthOfFrame;
	      for(int y=1; y<this.screenHeight/sizeConverter; y++)
	      {
	         for(int x=1; x<this.screenWidth/sizeConverter; x++)
	         {  
	        	
	        	if(referencedX <= this.screenWidth && referencedY <= this.screenHeight) {
	        		c = capture.getRGB((int)referencedX, (int)referencedY);
	        	}
	            color = new Color(c);
	            box = new Rectangle(x, y, 1, 1);
	            g2.setColor(color);
	            g2.fill(box);
	            referencedX += sizeConverter;
	         }
	         	referencedX = 1;
	        	referencedY += sizeConverter;
	      }
	      
	      
	      
	   }
	 
	 
	 public void markPointOnFrame(Graphics g, Color color, int x, int y) {
		 Graphics2D g2 = (Graphics2D) g;
		 
		 Rectangle point = new Rectangle(x, y, 3, 3);
		 g2.setColor(color);
		 g2.fill(point);
		 System.out.println("point drawed");
	 }

}
