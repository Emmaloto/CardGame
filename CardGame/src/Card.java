import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

public class Card {
	
	private Image front, back, state;
	private JComponent canvas;
	private AffineTransform transform;
	

	
	public Card(Image f, Image b, JComponent c){
		front = f;
		back = b;
		canvas = c;
		
		state = back;
		//System.out.println("Height card " + f.getHeight(null));
	}
	
	
	public void drawCard(AffineTransform t, Graphics2D g){
		transform = t;
		if(state != null) g.drawImage(state, t, null);
		
		//System.out.println(getHeight());
	}
	
	public void switchState(){

		if(state == front)
		  state = back;
	    else if(state == back)
		  state = front;

		canvas.repaint();
	}

	public void flipCard(boolean val){

		if(val)  state = front;
	    else     state = back;

		canvas.repaint();
	}	
	
	public void hideCard(){
		state = null;	
		canvas.repaint();
	}
	
	public boolean itExists(){
		return state != null;
	}
	
	public boolean isRemoved(){
		return state == null;
	}	
	
	public Image getFace(){
		return front;		
	}
	
	public void setFace(Image newfront){
		front = newfront;
	}
	
	public double getX(){
		return transform.getTranslateX();
		
	}

	public double getY(){
		return transform.getTranslateY();
		
	}

	public double getWidth(){
		return transform.getScaleX() * front.getWidth(canvas);
		
	}
	
	public double getHeight(){
		return transform.getScaleY() * front.getHeight(canvas);
		
	}


}
