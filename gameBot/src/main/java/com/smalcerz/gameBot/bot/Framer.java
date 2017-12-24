package com.smalcerz.gameBot.bot;

import java.awt.AWTException;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import com.smalcerz.gameBot.bot.seondWindow.PrintScreenRectangle;

public class Framer extends Thread{
	
	private JFrame mainFrame;
	
	//main buttons
	private Button btnConfiguration;
	private Button btnStartBossClicking;
	private Button btnStartClickingRand;
	
	
	//configurations
	private MouseAdapter mouseListenerForMobs;
	private MouseAdapter mouseListenerForUpgrades;
	private Button btnCheckMobs;
	private Button btnCheckUpgrades;
	private Button btnDoneConfigurations;
	
	private ArrayList<Coords> mobsCoords;
	private ArrayList<Coords> upgradesCoords;
	
	private int sizeConverter;
	
	private PrintScreenRectangle drawer;
	int screenWidth;
	int screenHeight;
	
	private FinalBot finalBot;
	
	public Framer() throws AWTException, IOException {
		
		this.finalBot = FinalBot.getInstance();
		this.mobsCoords = new ArrayList<Coords>(); 
		this.upgradesCoords = new ArrayList<Coords>();
		this.sizeConverter = 2;
		this.drawer = new PrintScreenRectangle();
		this.setVariables();
		this.setFrame();
	}
	
	@Override
	public void run() {
		while(true) {
			
		}
	}
	
	public void setVariables() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.screenWidth = (int) screenSize.getWidth();
		this.screenHeight = (int) screenSize.getHeight();
		
		
//		Rectangle rec = new Rectangle(0, 0, this.screenWidth, this.screenHeight);
//		BufferedImage image =this.robot.createScreenCapture(rec);		
	}
	
	public void setFrame() throws IOException {
		
		
		this.mainFrame = new JFrame("szymcio clickerBot");
		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainFrame.setSize(screenWidth/sizeConverter,(int) (screenHeight/sizeConverter * 1.2));
		this.mainFrame.setMinimumSize(new Dimension(screenWidth/sizeConverter,(int) (screenHeight/sizeConverter * 1.2)));
		this.mainFrame.setMaximumSize(new Dimension(screenWidth/sizeConverter,(int) (screenHeight/sizeConverter * 1.2)));
		this.mainFrame.setResizable(false);
		this.mainFrame.setLayout(null);
		this.mainFrame.pack();
		this.mainFrame.setVisible(true);
		this.mainFrame.setState(Frame.ICONIFIED);
		this.mainFrame.setState(Frame.NORMAL);
		
		this.setBackgroundImage();
		
		this.btnStartClickingRand = new Button("rand ride");
		this.btnStartClickingRand.setSize(new Dimension(mainFrame.getWidth()/6, mainFrame.getHeight()/8));
		this.btnStartClickingRand.setLocation(4 * mainFrame.getWidth()/6,  11* mainFrame.getHeight()/16);
		this.btnStartClickingRand.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				finalBot.startClicking();
			}
		});
		
		
		this.btnStartBossClicking = new Button("boss ride");
		this.btnStartBossClicking.setSize(new Dimension(mainFrame.getWidth()/6, mainFrame.getHeight()/8));
		this.btnStartBossClicking.setLocation(mainFrame.getWidth()/6, 11* mainFrame.getHeight()/16);
		this.btnStartBossClicking.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				finalBot.startClickingBoss();
			}
		});
		
		
		this.btnConfiguration = new Button("configure");
		this.btnConfiguration.setSize(new Dimension(mainFrame.getWidth()/4, mainFrame.getHeight()/8));
		this.btnConfiguration.setLocation(3 * mainFrame.getWidth()/8, 3 * mainFrame.getHeight()/16);
		this.btnConfiguration.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					startConfigurating();
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		this.btnCheckMobs = new Button("mobs");
		this.btnCheckMobs.setSize(new Dimension(mainFrame.getWidth()/10, mainFrame.getHeight()/12));
		this.btnCheckMobs.setLocation(2*mainFrame.getWidth()/10, (int) (0.875*mainFrame.getHeight()));
		this.btnCheckMobs.addActionListener(new ActionListener() { 
			
			public void actionPerformed(ActionEvent e) {
				mainFrame.addMouseListener(mouseListenerForMobs);
				mainFrame.removeMouseListener(mouseListenerForUpgrades);
			} 
		});
		
		this.btnCheckUpgrades = new Button("upgrades");
		this.btnCheckUpgrades.setSize(new Dimension(mainFrame.getWidth()/10, mainFrame.getHeight()/12));
		this.btnCheckUpgrades.setLocation(7*mainFrame.getWidth()/10, (int) (0.875*mainFrame.getHeight()));
		this.btnCheckUpgrades.addActionListener(new ActionListener() { 
			
			public void actionPerformed(ActionEvent e) {
				mainFrame.addMouseListener(mouseListenerForUpgrades);
				mainFrame.removeMouseListener(mouseListenerForMobs);
			}
		});
		 
		
		this.btnDoneConfigurations = new Button("done");
		this.btnDoneConfigurations.setSize(new Dimension(mainFrame.getWidth()/10, mainFrame.getHeight()/12));
		this.btnDoneConfigurations.setLocation((int) (4.5*mainFrame.getWidth()/10), (int) (0.875*mainFrame.getHeight()));
		this.btnDoneConfigurations.addActionListener(new ActionListener() { 
			
			public void actionPerformed(ActionEvent e) {
				mainFrame.remove(drawer);
				mainFrame.remove(btnDoneConfigurations);
				mainFrame.remove(btnCheckMobs);
				mainFrame.remove(btnCheckUpgrades);
				mainFrame.repaint();
				try {
					setBackgroundImage();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				mainFrame.add(btnConfiguration);
				mainFrame.add(btnStartBossClicking);
				mainFrame.add(btnStartClickingRand);
				sendCoordsToBot();
			}
		});
		
		
		this.mouseListenerForMobs = new MouseAdapter() {
		     @Override
		     public void mousePressed(MouseEvent e) {
		    	 if(e.getY() * sizeConverter <= screenHeight) {
			    	 mobsCoords.add(new Coords(e.getX() * sizeConverter, e.getY() * sizeConverter));
			    	 drawer.markPointOnFrame(mainFrame.getGraphics(), Color.RED, e.getX(), e.getY());
		    	 }
		     }
		  };
		
		
		this.mouseListenerForUpgrades = new MouseAdapter() {
		     @Override
		     public void mousePressed(MouseEvent e) {
		    	 if(e.getY() * sizeConverter <= screenHeight) {
		    		 upgradesCoords.add(new Coords(e.getX() * sizeConverter, e.getY() * sizeConverter));
		    		 drawer.markPointOnFrame(mainFrame.getGraphics(), Color.BLUE, e.getX(), e.getY());
		    	 }
		     }
		  };
		
		this.mainFrame.add(this.btnConfiguration);
	}
	


	private void sendCoordsToBot() {
		
		int index;
		Iterator<Coords> iteratorMobs = this.mobsCoords.iterator();
		Coords [] mobsCoordsArray = new Coords[this.mobsCoords.size()];
		
		index = 0;
		while(iteratorMobs.hasNext()) {
			mobsCoordsArray[index] = iteratorMobs.next();
			index+=1;
		}
		
		Iterator<Coords> iteratorUpgrades = this.upgradesCoords.iterator();
		Coords [] upgradesCoordsArray = new Coords[this.upgradesCoords.size()];
		
		index = 0;
		while(iteratorUpgrades.hasNext()) {
			upgradesCoordsArray[index] = iteratorUpgrades.next();
			index+=1;
		}
		
		this.finalBot.setMobsCoords(mobsCoordsArray);
		this.finalBot.setUpgradesCoords(upgradesCoordsArray);
	} 
	
	
	private void startConfigurating() throws AWTException {

		this.mobsCoords = new ArrayList<Coords>();
		this.upgradesCoords = new ArrayList<Coords>();
		this.mainFrame.remove(btnConfiguration);
		this.mainFrame.remove(btnStartBossClicking);
		this.mainFrame.remove(btnStartClickingRand);
		this.mainFrame.add(drawer);
		drawer.makeScreen(this.mainFrame.getGraphics(), this.mainFrame.getWidth(), this.mainFrame.getHeight());	

		this.mainFrame.add(btnCheckMobs);
		this.mainFrame.add(btnCheckUpgrades);
		this.mainFrame.add(btnDoneConfigurations);
	}
	
	public void setBackgroundImage() throws IOException {
//		 ImageIcon image = new ImageIcon(ImageIO.read(new File("/home/szymcio/eclipse-workspace/gameBot/src/main/java/com/smalcerz/gameBot/resources/clicker.jpg")));
//		 this.mainFrame.getGraphics().drawImage(image.getImage(), 0, 0, null);
	}
}
