
public class Snippets {
	
	  
	  //public HPBar(int x, int y, int height, int cWidth, int cHeight, Color col)
	  
	  // Sets x and y from original 1600, 1000
	  private int setPosX(int val){
		  return (int)0/val;
	  }
	  
	  private int setPosY(int val){
		  return (int)0/val;
	  }
	  //herohealth.drawHealth(200*scaleX, 100*scaleY, herohealth.getHealth()*scaleX, 100*scaleY, g);
	  /*
    System.out.println("healthVal after hit: " + healthVal);
    System.out.println("newWidth : " + newWidth);
    System.out.println("maxHealth : " + maxHealth);
    System.out.println("border w: " + border.getWidth());
    System.out.println("health w : " + health.getWidth());
    
    System.out.println("In between " + (healthVal >= 0 && healthVal <= maxHealth));
    System.out.println("Full " + (healthVal > maxHealth));
    System.out.println("Empty " + (healthVal <= 0));
    
    System.out.println(" ");    
    */
	//helpText = GameUtilities.getInputFromFile("src/gameInfoText.txt");
	String []helpText = new String[1];
	 /* 
	  //System.out.println("Before x and y: " + health.getWidth());
	  System.out.println("Before w and h: " + health.getWidth() +", "+ +health.getHeight());
	  


	  
	  health.setSize( orgW, (int)(health.getHeight()*scaleY));
	  health.setLocation((int)(health.getX()*scaleX), (int)(health.getY()*scaleY));

	  border.setSize((int)(border.getWidth()*scaleX), (int)(border.getHeight()*scaleY));
	  border.setLocation((int)(border.getX()*scaleX), (int)(border.getY()*scaleY));
	  
	  //System.out.println("After x and y: " + healthVal);
	  System.out.println("After w and h: " + health.getWidth() +", "+ +health.getHeight());
	  System.out.println("       ");
	  */
	
	
	/*
	  String [] input = new String[0];
	  BufferedReader in =	 new BufferedReader(new InputStreamReader(
	            getClass().getResourceAsStream("src/gameInfoText.txt")));
	try {

		String str;

		
		List<String> list = new ArrayList<String>();
		while((str = in.readLine()) != null){
		    list.add(str);
		}

		input = list.toArray(new String[0]);
		in.close();
		
	} catch ( IOException e) {
		
		e.printStackTrace();
	} 
	
	helpText = input;
	*/
	/*
	static public String getFile(String fileName)
	{
	        //Get file from resources folder
	        ClassLoader classLoader = (new A_CLASS()).getClass().getClassLoader();

	        InputStream stream = classLoader.getResourceAsStream(fileName);

	        try
	        {
	            if (stream == null)
	            {
	                throw new Exception("Cannot find file " + fileName);
	            }

	            return IOUtils.toString(stream);
	        }
	        catch (Exception e) {
	            e.printStackTrace();

	            System.exit(1);
	        }

	        return null;
	}	
	*/
	/*
	
		if(card2 == card1){         // Clicked on same card
			  playingCards[card1].switchState();
			  cardTurned = 0;
			}else{
	/*	
		System.out.println("x: " + x + " y: " + y + "  cardspace: " +cardspaceX +" " + cardspaceY);
		System.out.println("perLine: " + perLine + " i % perLine == 0 " + (i % perLine == 0) );
		System.out.println("//////////////");
		
		System.out.println("****************");
	*/
	/*
	playingCards[0] = new Card(red, backdesign, this);
	playingCards[1] = new Card(green, backdesign, this);
	playingCards[2] = new Card(red, backdesign, this);
	playingCards[3] = new Card(green, backdesign, this);
	
	*/	
	/*
	// Resets position of all existing cards
	for(int i = 0; i < playingCards.length && playingCards[i] != null; i++){
		playingCards[i].flipCard(false);
		cardTurned  = 0;
	}
	*/
	//g2.setColor(Color.GREEN);
	//g2.fill(rectArea);
	//g2.draw(rectArea);

	
	/*rectArea = new Rectangle(bounds);
		
		rectArea = new Rectangle((int)playingCards[i].getX(), (int)playingCards[i].getY(),
              (int)playingCards[i].getWidth(), (int)playingCards[i].getHeight()
              );
		*/
	  /*
	  System.out.println("card x" + bounds.getX());
	  System.out.println("double card x" + playingCards[i].getX());
	  */
	//System.out.println("Card width " + playingCards[0].getX());
	//testCard = new Card(red, blue, this);
	
	/*
	 * 
	0
	down vote
	This is what I did for a simple card game I was working on:

	Optional: Make an object to represent your clickable image. It should know it's (x, y), 
	width/height, and how to draw itself on the screen.
	Keep a reference to all of your clickable images in a collection (e.g. List).
	Create a method to determine if the position of the user's click is within the boundaries 
	of your clickable image (I put this in the object created from #1).
	On mouseClicked (or other applicable methods in your listener), traverse your collection of
	 images and call wasClicked(), which might look something like this:
	public boolean wasClicked( int x, int y ) {

	    return( x > getX() && x < ( getX() + getWidth() ) && 
	            y > getY() && y < ( getY() + getHeight() ) );

	}
	Your implementation may vary. Alternatively you can also extend a JComponent and add a MouseListener 
	to that, but for reasons which I can't recall at the moment, the above worked better for me in my case.
	 * 
	 * 
	 * */
	 
}
