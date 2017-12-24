package com.smalcerz.gameBot;

import java.awt.AWTException;
import java.io.IOException;

import com.smalcerz.gameBot.bot.Clicker;
import com.smalcerz.gameBot.bot.Framer;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	
		
		try {
			Framer framer = new Framer();
			framer.start();
			Clicker clicker = new Clicker();
			clicker.start();
			
			
			framer.join();
			clicker.join();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
    }
}
