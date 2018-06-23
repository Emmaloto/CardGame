import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class StoryScreen extends JPanel{
	
	private Screen screen;
	private JFrame frame = new JFrame();
	private Image story_bg;
	private JScrollPane pane;

	public StoryScreen(String title, String[] helpText){
		//
		
		try {
			story_bg = ImageIO.read(this.getClass().getResource("pics/bg/forest-315184_1280.jpg"));
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
		setPreferredSize(new Dimension(1000, 1000));
		
		pane = new JScrollPane(this, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//pane.setSize(frame.getWidth(), frame.getHeight());
		
		frame.setTitle(title);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  //Close window on exit
		frame.setSize(1150, 800);			
		

		frame.add(pane);
		System.out.println("Scroll");
		
		
		screen = new Screen(story_bg);
		screen.setTitle(title);
		screen.changeTitlePosition((int)(frame.getWidth()/3.5), (int)(frame.getHeight()/20));
		Font f = getFont();
		Font large = new Font(f.getFontName(), Font.BOLD, 20);
		screen.setTextFont(large);
		
		
		screen.setPhrase(helpText, 23);
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		
		screen.drawScreen(0, 0, (Graphics2D)g);
		// screen.drawScreen(0, 0, (Graphics2D)info.getGraphics());		
	}
	
	
	public void displayWindow(){
		frame.setVisible(true);
	}

	public void changeStory(String [] newSection){
		screen.setPhrase(newSection, 23);
		frame.repaint();
		
		//System.out.println("Changed");
	}
	
	public void setHeading(String heading){
		screen.setTitle(heading);
		frame.repaint();
	}
	
	public void resizeWindow(int width, int height){
		frame.setSize(width, height);
	}
}
