package squarenode;
import processing.core.PApplet;

public class Main extends PApplet {
	private Logic log;
	
	public static void main(String[] args) {
		System.out.println("Starting square node");
		PApplet.main("squarenode.Main");
	}
	public void settings(){
		size(500, 500);
	}
	
	public void setup(){
		rectMode(PApplet.CENTER);
		log = new Logic(this);
	}
	
	public void draw(){
		log.draw();
	}
	
	@Override
	public void mouseDragged(){
		log.mouseDragged();
	}

}
