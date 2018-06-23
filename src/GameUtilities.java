
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/** Generate random integers in a certain range. */
public class GameUtilities {
  
  public static int getRandomInteger(int min, int max){
	  
	Random rand = new Random();
	int random = rand.nextInt((max - min) + 1) + min;
	return random;
  }
  
  
  public static void fileExists(String location){
    File f = new File(location);
		 
	 if(f.isFile()){
	  System.out.println("File exists ! File is " + location);
	 }else{
	  System.out.println("File not found at " + location + "!");
	 }	  
  }

  public static AffineTransform getTransform(double x, double y, int rot, double scaleX, double scaleY){
	  AffineTransform specialTrans = new AffineTransform();
      
	  // Rotation
	  AffineTransform rotation = new AffineTransform();
	  rotation.setTransform(new AffineTransform()); // identity
	  rotation.rotate( Math.toRadians(rot), x, y );

	  // Translation
	  AffineTransform trans = new AffineTransform();
	  trans.translate(x, y);
	  
	  // Scale
	  AffineTransform scaleVal = new AffineTransform();
	  scaleVal.scale(scaleX, scaleY);		  

	  specialTrans.concatenate(rotation);
	  specialTrans.concatenate(trans);
	  specialTrans.concatenate(scaleVal);	
	  
	  return specialTrans;
  }
  
  // Return an array made of images of variable size
  public static Image[] placeImages(Image ...pics){
	Image []img = new Image[pics.length];
	
	for(int i = 0; i < pics.length; i++)
		img[i] = pics[i];
	return img;
	  
  }
  
  public static Font getFont(String location, float size, Object cl){
	  Font font = new Font(null);
	  InputStream i = cl.getClass().getClassLoader().getResourceAsStream(location);	
	try{
		
	    		
      Font f = Font.createFont(Font.TRUETYPE_FONT, i);
      font = f.deriveFont(Font.BOLD, size);
	  
	}catch(FontFormatException | IOException e){}
	
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    ge.registerFont(font);	
	
	return font;
  }
  
  public static String[] getInputFromFile(String location, Object cl){
	  String [] input = new String[0];
	  InputStream i = cl.getClass().getClassLoader().getResourceAsStream(location);
	  //System.out.println(cl);
	  //System.out.println(input != null);
	  
	try {
		
		BufferedReader in = new BufferedReader(new InputStreamReader(i));
		String str;
		
		List<String> list = new ArrayList<String>();
		while((str = in.readLine()) != null){
		    list.add(str);
		}

		input = list.toArray(new String[0]);
		in.close();
		
	} catch ( IOException | NullPointerException e) {
		
		e.printStackTrace();
	}      
	 /*
	for(int r = 0; r < input.length;r++) 
	   System.out.println("["+ r +"]" +input[r]);
	   
	   */
	  return input;
  }

  
} 

