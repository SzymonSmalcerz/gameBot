package com.smalcerz.gameBot.bot.helpers;

public class Helper {

	
	public static int getRandomInt(int min, int max) {
		return (int) Math.floor(Math.random()*max - min);
	}
}
