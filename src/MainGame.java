import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class MainGame extends JComponent implements MouseListener, ActionListener,KeyListener{


	private JFrame fr;
//	private Image red, blue, green, yellow, purple, branch;
	private Image  backdesign, screenbg, endimg;	
	private Image[] cardfaces;
	private Image gameBG, bgList[], skyBG, bokehBG, milkyBG, lineBG, blocksBG,
	              sunriseBG, treeBG, forestBG, blueBG;
	
	//private Card testCard ;
	private Card[] playingCards = new Card[2];
	private Screen info;
	private Font helpFont, endFont, buttonFont;
	private JLabel statusBar;
	private JButton restart;
	private JComboBox<String> typeOfGame;
	
	private JMenuBar menu;
	private JMenu file, bgs;
	private JMenuItem helpSel, close, noSel, skySel, bowSel, milkSel, lineSel, blockSel, 
	                  sunSel, treeSel, forestSel, blueSel;
	
	private Timer timer = new Timer(5, this);
	
	private String endText[], helpText[];
	
	private int x, y, cardspaceX, cardspaceY, perLine;
	private double scaling;
	private int cardTurned = 0, clickCounter, noOfCards;
	private int card1, card2;
	private long startTime = 0;
	private boolean showHelp, gameHasEnded;
	
	//private Rectangle rectArea;
	private boolean cardDisabled = false;
	
	private Sound flipClip, victoryClip, matchClip;
	

	public static void main(String args[]){
		  new MainGame();
					
	} 

	public MainGame(){
		
		// Load images to be used
		loadImages();		
				
		// Set up infoscreen background
		info = new Screen(screenbg);
		//endScreen = new Screen(endimg);
		//info.changeTextPosition(50,50);		
		noOfCards = 8;
		perLine = 3;

		// Set up game frame
		fr = new JFrame();
		fr.setTitle("Pick a Card");
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //Close window on exit
		fr.setSize(1200,900);	    
		fr.getContentPane().add(this);	
			
		// Set up status bar and restart button
		statusBar = new JLabel("0,0");
		restart = new JButton("Restart");
		restart.setPreferredSize(new Dimension(120, 40));
		restart.setFont(buttonFont);
		
		// Sets up JComboBox of Game Options
		String[] options = { "", "6 Cards", "8 Cards", "12 Cards", "16 Cards", 
				               "20 Cards", "Surprise Me" };
		typeOfGame = new JComboBox<String>(options);
		typeOfGame.setSelectedIndex(0);
		typeOfGame.setPreferredSize(new Dimension(200, 40));
		typeOfGame.setFont(helpFont);
		
		// Setting up all the game GUIs and listeners
		JPanel buttonPanel = new JPanel();
		//buttonPanel.setSize(40,100);
		buttonPanel.setBackground(Color.LIGHT_GRAY);
		buttonPanel.add(restart);
		buttonPanel.add(typeOfGame);
		
		//fr.add(statusBar, BorderLayout.NORTH);
		fr.add(buttonPanel, BorderLayout.SOUTH);

		// Menu Setup
		menu = new JMenuBar();	
		
	    file = new JMenu("File");	    
	    bgs  = new JMenu("Backgrounds");
	    
	    helpSel = new JMenuItem("Help");
		close = new JMenuItem("Close Game");	
		file.add(helpSel);
		file.add(close);
		
		noSel = new JMenuItem("None");
		skySel = new JMenuItem("Landscape");
		bowSel = new JMenuItem("Spectrum"); 
		milkSel = new JMenuItem("Milky Way"); 
		lineSel = new JMenuItem("Wavy Lines");
		blockSel = new JMenuItem("Blocks");
        sunSel = new JMenuItem("Sunrise");
        treeSel = new JMenuItem("Tree");
        forestSel = new JMenuItem("Forest");
        blueSel = new JMenuItem("Blue");
		bgs.add(noSel);
		bgs.add(skySel);
		bgs.add(bowSel);
		bgs.add(milkSel);
		bgs.add(lineSel);
		bgs.add(blockSel);
		bgs.add(sunSel);
		bgs.add(treeSel);
		bgs.add(forestSel);
		bgs.add(blueSel);
			
		menu.add(file);
		menu.add(bgs);
		
		fr.setJMenuBar(menu);
		
	    fr.setFocusable(true);
	    fr.requestFocusInWindow();	  
	    fr.addKeyListener(this);
	    //this.addKeyListener(this);
	    
	    fr.addMouseListener(this);
		restart.addActionListener(this);
		typeOfGame.addActionListener(this);
		
		helpSel.addActionListener(this);
		close.addActionListener(this);
		noSel.addActionListener(this);
		skySel.addActionListener(this);
		bowSel.addActionListener(this); 
		milkSel.addActionListener(this);
		lineSel.addActionListener(this);
		blockSel.addActionListener(this);
        sunSel.addActionListener(this);
        treeSel.addActionListener(this);
        forestSel.addActionListener(this);
        blueSel.addActionListener(this);
		
		fr.setVisible(true);
		
		setup();
		
	}
	
	public void setup(){

		playingCards = new Card[noOfCards];
		
		// Set cards
		for(int i = 0, j = 0; i < playingCards.length; i++){
			if(i % 2 == 0) j = GameUtilities.getRandomInteger(0, cardfaces.length - 1);                        // if a second card is selected
			 
			// If there are no more new colors
			//if(j >= cardfaces.length) j = GameUtilities.getRandomInteger(0, cardfaces.length - 1);          
			  
			playingCards[i] = new Card(cardfaces[j], backdesign, this);
		
		}
		
		// Swaps face of 2 random cards
		for(int i = 0; i < playingCards.length; i++){
			int index = GameUtilities.getRandomInteger(0, playingCards.length - 1);
			int index2 = GameUtilities.getRandomInteger(0, playingCards.length - 1);
			
			Image swapface = playingCards[index].getFace(); 
			playingCards[index].setFace(playingCards[index2].getFace());
			playingCards[index2].setFace(swapface);
		}
		x = 50;
		y = 50;
		positionCards();
		
		// Set up help screen and game variables
		info.changeScreenBackground(screenbg);
		info.changeTextPosition(70, 110);
		info.setTitle("BETA TEST v 1.0");
		info.changeTitlePosition(60, 100);
		info.setTextFont(helpFont);		
		info.setPhrase(helpText, 40);

		//rectArea = new Rectangle();
		
	    fr.setFocusable(true);
	    fr.requestFocusInWindow();
	    //fr.addKeyListener(this);
		
		showHelp = false;
		cardTurned = 0;
		clickCounter = 0;
		cardDisabled = false;
		gameHasEnded = false;		
		
		//instantEnd();
	}
	
	  // Scales and rotates to fit into window for different cards	
	public void positionCards(){
		if(playingCards.length == 6){
			scaling = .9;
			cardspaceX = 300;
			cardspaceY = cardspaceX;
			perLine = 3;

		}else if(playingCards.length == 8){
			scaling = .9;
			cardspaceX = 300;
			cardspaceY = cardspaceX;
			perLine = 4;

	    }else if(playingCards.length == 12){
			scaling = .8;
			cardspaceX = 250;
			cardspaceY = cardspaceX;
			perLine = 4;
	    }else if(playingCards.length == 16){
			scaling = .6;
			cardspaceX = 300;
			cardspaceY = 200;
			perLine = 4;						
	    }else if(playingCards.length == 20){
			scaling = .55;
			cardspaceX = 250;
			cardspaceY = 200;
			perLine = 5;
	    }
			
	}
	
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;	
		
		if(gameBG != null) g2.drawImage(gameBG, 0, 0, fr.getWidth(), fr.getHeight(), this);
		
		g2.setFont(new Font("TimesRoman", Font.BOLD, 30));
		g2.drawString("Press h for more game info.", 0, 20 );
		
		//-------------- Draw Cards				
		for(int i = 0; i < playingCards.length; i++){
			if(i % perLine == 0 && i != 0){  // if card will go Offscreen
				x = 50;
				y += cardspaceY;
			}
			
			AffineTransform testT = GameUtilities.getTransform(x, y, 0, scaling, scaling);
			playingCards[i].drawCard(testT, g2);		
			
			x += cardspaceX;

		}

		// reset coordinates
		x = 50;
		y = 50;
		
		//--------------
		
		if(showHelp){
		    info.drawScreen(0, 0, fr.getWidth()-20, fr.getHeight()-20, g2);		  
		}

	}
	

	public void mousePressed(MouseEvent mouse) {
	    fr.setFocusable(true);
	    fr.requestFocusInWindow();
		
		if(!cardDisabled){                 // Disable cards when 2 are clicked

		  Point clickPoint = mouse.getPoint();		
		  for(int i = 0; i < playingCards.length && playingCards[i] != null; i++){
		  // Bounding rectangle ** might move to Card class
	  	    Rectangle bounds = new Rectangle((int)playingCards[i].getX(), (int)playingCards[i].getY(),
				                  (int)playingCards[i].getWidth(), (int)playingCards[i].getHeight());

		    if (bounds.contains(clickPoint)){         //point is inside given image, flip card	    
	  		  playingCards[i].switchState();
	  		  flipClip.play();
	  		  
	  	  	  cardTurned++;
	  	  	  clickCounter++;
	  	  	  
	  	  	  if(cardTurned >= 2 ) {       // 2 cards are turned, freeze cards and disable card clicking
	  			card2 = i; 
	  			if(card2 == card1) cardTurned = 0;         // clicked on same card twice, re-flip & ignore
	  			else{
	  				timer.start(); 
	  				startTime = System.currentTimeMillis(); 
	  				cardDisabled = true;
	  			}
	  		  }else card1 = i;
	  	  	  
	  	  	  break;                // Stop going through all cards
		    }
		  
		  
		  }
		
		}
	}
	

	  public void actionPerformed(ActionEvent action) {
		if(action.getSource()==timer){
			
			//System.out.println(System.currentTimeMillis() - startTime);
			if(System.currentTimeMillis() - startTime >= 1500){
				timer.stop();
				checkCards();
			}	
			
		}else if(action.getSource() == restart){
		    String selectedGame = (String) typeOfGame.getSelectedItem();
			     if(selectedGame.equals("6 Cards")  ) noOfCards = 6;
			else if(selectedGame.equals("8 Cards")  ) noOfCards = 8;
			else if(selectedGame.equals("12 Cards") ) noOfCards = 12;
			else if(selectedGame.equals("16 Cards") ) noOfCards = 16;
			else if(selectedGame.equals("20 Cards") ) noOfCards = 20;
			else if(selectedGame.equals("Surprise Me") ){ 
				int cardNo[] = {6, 8, 12, 16, 20};  
				noOfCards = cardNo[GameUtilities.getRandomInteger(0, cardNo.length - 2)];
			}
			// Resets everything     
			setup();
			timer.stop();
			repaint();
		}
		
		// Change from Menu
		if(action.getSource().getClass() == JMenuItem.class){
			//System.out.println("BGS CHANGED");
		
			if(action.getSource() == close){
				System.exit(0);
			}else if(action.getSource() == helpSel){
				showHelp = !showHelp;
			}else if(action.getSource() == skySel){
				gameBG = skyBG;
			}else if(action.getSource() == bowSel){
				gameBG = bokehBG;
			}else if(action.getSource() == milkSel){
				gameBG = milkyBG;			
			}else if(action.getSource() == lineSel){
				gameBG = lineBG;		
			}else if(action.getSource() == blockSel){
				gameBG = blocksBG;
			}else if(action.getSource() == sunSel){
				gameBG = sunriseBG;
			}else if(action.getSource() == treeSel){
				gameBG = treeBG;
			}else if(action.getSource() == forestSel){
				gameBG = forestBG;
			}else if(action.getSource() == blueSel){
				gameBG = blueBG;
			}
			
			else if(action.getSource() == noSel){
				gameBG = null;
			}
		
		repaint();
		}
		
	  }	


	private void checkCards() {
		// If cards have same face remove them, else re-flip them
		if(playingCards[card1].getFace() == playingCards[card2].getFace()){
			playingCards[card1].hideCard();
			playingCards[card2].hideCard();		
			matchClip.play();
		}else{
			playingCards[card1].switchState();
			playingCards[card2].switchState();	
			flipClip.play();
		}
		
		cardDisabled = false;
		cardTurned = 0;
		
		gameEnded();
	}
	
	public void gameEnded(){
		gameHasEnded = true;
		
		  for(int i = 0; i < playingCards.length;i++){
			  gameHasEnded = gameHasEnded && playingCards[i].isRemoved();
			  //System.out.println(playingCards[i].isRemoved());
		  }
		  //System.out.println("\\\\\\");
		  
		  if(gameHasEnded){ 
			  // Leaves un-removable end screen 
			  info.changeScreenBackground(endimg);
			  info.setTitle("You're done! That took " + clickCounter +" clicks!");
			  info.changeTitlePosition(108, 350);
			  info.setPhrase(endText, 40);
			  info.changeTextPosition(100, 470);
			  info.setTextFont(endFont);
			  
			  victoryClip.play();
			  showHelp = true;
			  //fr.removeKeyListener(this);
		  }
	}	

	public void mouseReleased(MouseEvent e) {}

	public void mouseClicked(MouseEvent e) {
		statusBar.setText(String.format("Click at %d, %d", e.getX(), e.getY()));
	}

	public void mouseEntered(MouseEvent arg0) {}


	public void mouseExited(MouseEvent arg0) {}	
	
	private void loadImages(){
		Image car = null, clock = null, diamond = null, hippo = null, house = null, roses = null, target = null;
		
		try {

			car     = ImageIO.read(this.getClass().getResource("pics/card_car.png"));
			clock   = ImageIO.read(this.getClass().getResource("pics/card_clock.png"));
			diamond = ImageIO.read(this.getClass().getResource("pics/card_diamond.png"));
			hippo   = ImageIO.read(this.getClass().getResource("pics/card_hippo.png"));
			house   = ImageIO.read(this.getClass().getResource("pics/card_house.png"));
			roses   = ImageIO.read(this.getClass().getResource("pics/card_roses.png"));
			target  = ImageIO.read(this.getClass().getResource("pics/card_target.png"));
			
			backdesign = ImageIO.read(this.getClass().getResource("pics/cardback.png"));
			
			screenbg = ImageIO.read(this.getClass().getResource("pics/infoscreen.png"));
			endimg = ImageIO.read(this.getClass().getResource("pics/gamescreen_2.png"));
			
			skyBG   = ImageIO.read(this.getClass().getResource("pics/bg/turkey-3048299_1920.jpg"));
			bokehBG = ImageIO.read(this.getClass().getResource("pics/bg/bokeh-3249883_1920.jpg")); 
			milkyBG = ImageIO.read(this.getClass().getResource("pics/bg/milky-way-2695569_1920.jpg")); 
			lineBG  = ImageIO.read(this.getClass().getResource("pics/bg/wallpaper-3419273_1920.jpg"));
			blocksBG = ImageIO.read(this.getClass().getResource("pics/bg/the-background-3418637_1920.jpg"));
            sunriseBG  = ImageIO.read(this.getClass().getResource("pics/bg/sunrise-1949939_1920.jpg"));
            treeBG  = ImageIO.read(this.getClass().getResource("pics/bg/background-3390802_1920.jpg"));
            forestBG  = ImageIO.read(this.getClass().getResource("pics/bg/pattern-3119825_1920.jpg"));
            blueBG  = ImageIO.read(this.getClass().getResource("pics/bg/desktop-3246124_1920.jpg"));
			
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		cardfaces = GameUtilities.placeImages(car, clock, diamond, hippo, house, roses, target);
		//cardfaces = GameUtilities.placeImages(red, blue, green, yellow, purple, branch);

		bgList = GameUtilities.placeImages(skyBG, bokehBG, milkyBG, lineBG, blocksBG,
	              sunriseBG, treeBG, forestBG, blueBG);
		gameBG = null;
		
		helpText = GameUtilities.getInputFromFile("gameInfoText.txt", this);
		helpFont = GameUtilities.getFont("fonts/Philosopher-Italic.ttf", 27f, this);
		
		endText = GameUtilities.getInputFromFile("endtxt.txt", this);
		endFont = GameUtilities.getFont("fonts/TypoGraphica.otf", 40f, this);
		
		buttonFont  = GameUtilities.getFont("fonts/AmaliaMutia.ttf", 15f, this);	
		
		flipClip = new Sound("audio/card-flip.wav");
		victoryClip = new Sound("audio/goodresult.wav");
		matchClip = new Sound("audio/good.wav");
		
	}

	/*private void instantEnd(){
		for(int i = 0; i < playingCards.length; i++){       			  
			playingCards[i].hideCard();
		}
		gameHasEnded = true;
		showHelp = true;
		gameEnded();
				
	}
	
*/
	
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_H){
			showHelp = !showHelp;
			fr.repaint();
		}else if(e.getKeyCode() == KeyEvent.VK_R){
			  gameBG = bgList[GameUtilities.getRandomInteger(0, bgList.length - 1)];
			  fr.repaint();
		}else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			  fr.dispose();
			  System.exit(0);
		}
	
	}


public void keyReleased(KeyEvent arg0) {
	// TODO Auto-generated method stub
	
}


public void keyTyped(KeyEvent arg0) {
	// TODO Auto-generated method stub
	
}

}

