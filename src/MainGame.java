import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class MainGame extends JComponent implements MouseListener, ActionListener,KeyListener{


	private JFrame fr;
	private Image red, blue, green, yellow, purple, backdesign, branch, screenbg, endimg;
	private Image[] cardfaces;
	
	//private Card testCard ;
	private Card[] playingCards = new Card[2];
	private Screen info;
	private Font helpFont, endFont, buttonFont;
	private JLabel statusBar;
	private JButton restart;
	private JComboBox<String> typeOfGame;
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


	public static void main(String args[]){
		  new MainGame();
					
	} 

	public MainGame(){
		
		loadImages();
		
		cardfaces = GameUtilities.placeImages(red, blue, green, yellow, purple, branch);
	    
		info = new Screen(screenbg);
		//endScreen = new Screen(endimg);
		//info.changeTextPosition(50,50);
		
		noOfCards = 8;
		perLine = 3;

		fr = new JFrame();
		fr.setTitle("Pick a Card");
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //Close window on exit
		fr.setSize(1200,900);	    
		fr.getContentPane().add(this);	
			
		
		statusBar = new JLabel("0,0");
		restart = new JButton("Restart");
		restart.setPreferredSize(new Dimension(120, 40));
		restart.setFont(buttonFont);
		
		
		String[] options = { "", "6 Cards", "8 Cards", "12 Cards", "16 Cards", 
				               "20 Cards", "Surprise Me" };
		typeOfGame = new JComboBox<String>(options);
		typeOfGame.setSelectedIndex(0);
		typeOfGame.setPreferredSize(new Dimension(200, 40));
		typeOfGame.setFont(helpFont);
		
		JPanel buttonPanel = new JPanel();
		//buttonPanel.setSize(40,100);
		buttonPanel.setBackground(Color.LIGHT_GRAY);
		buttonPanel.add(restart);
		buttonPanel.add(typeOfGame);
		
		//fr.add(statusBar, BorderLayout.NORTH);
		fr.add(buttonPanel, BorderLayout.SOUTH);
			
	    fr.setFocusable(true);
	    fr.requestFocusInWindow();	  
	    fr.addKeyListener(this);
	    //this.addKeyListener(this);
	    
	    fr.addMouseListener(this);
		restart.addActionListener(this);
		typeOfGame.addActionListener(this);
		
		fr.setVisible(true);
		
		setup();
		
	}
	
	public void setup(){

		playingCards = new Card[noOfCards];
		
		// Set cards
		for(int i = 0, j = 0; i < playingCards.length; i++){
			if(i % 2 == 0) j++;                        // if a second card is selected
			 
			// If there are no more new colors
			if(j >= cardfaces.length) j = GameUtilities.getRandomInteger(0, cardfaces.length - 1);          
			  
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
	  	  	  cardTurned++;
	  	  	  clickCounter++;
	  	  	  
	  	  	  if(cardTurned >= 2 ) {       // 2 cards are turned, freeze cards and disable card clicking
	  			card2 = i; 
	  			if(card2 == card1) cardTurned = 0;         // clicked on same card twice, reflip & ignore
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
		
	  }	


	private void checkCards() {
		// If cards have same face remove them, else re-flip them
		if(playingCards[card1].getFace() == playingCards[card2].getFace()){
			playingCards[card1].hideCard();
			playingCards[card2].hideCard();				
		}else{
			playingCards[card1].switchState();
			playingCards[card2].switchState();			
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
		
		try {
			blue = ImageIO.read(this.getClass().getResource("pics/bluecard.png"));
			red = ImageIO.read(this.getClass().getResource("pics/redcard.png"));
			green = ImageIO.read(this.getClass().getResource("pics/green.png"));
			yellow = ImageIO.read(this.getClass().getResource("pics/yellowcard.png"));
			purple = ImageIO.read(this.getClass().getResource("pics/purplecard.png"));
			branch = ImageIO.read(this.getClass().getResource("pics/leafcard.png"));
			
			backdesign = ImageIO.read(this.getClass().getResource("pics/cardback.png"));
			
			screenbg = ImageIO.read(this.getClass().getResource("pics/infoscreen.png"));
			endimg = ImageIO.read(this.getClass().getResource("pics/gamescreen_2.png"));
			
		} catch (IOException e) {

			e.printStackTrace();
		}

		helpText = GameUtilities.getInputFromFile("gameInfoText.txt", this);
		helpFont = GameUtilities.getFont("fonts/Philosopher-Italic.ttf", 27f, this);
		
		endText = GameUtilities.getInputFromFile("endtxt.txt", this);
		endFont = GameUtilities.getFont("fonts/TypoGraphica.otf", 40f, this);
		
		buttonFont  = GameUtilities.getFont("fonts/AmaliaMutia.ttf", 15f, this);	
		
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

