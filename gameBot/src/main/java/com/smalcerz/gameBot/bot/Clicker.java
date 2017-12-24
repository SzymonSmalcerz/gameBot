package com.smalcerz.gameBot.bot;

import java.awt.AWTException;

public class Clicker extends Thread{

	private FinalBot finalBot;
	
	public Clicker() throws AWTException {
		
		this.finalBot = FinalBot.getInstance();
	}
	
	public void run() {

		while(true) {
			finalBot.tryClicking();
		}

	}
}
