package circlenode;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.shape.Ellipse;
import processing.core.PApplet;

public class Logic implements Observer{
	private PApplet app;
	private CommunicationManager comm;
	private InetAddress destAddress;
	private int destPort;
	private int x;
	private int y;
	
	public Logic(PApplet app){
		this.app = app;
		this.x = app.width/2;
		this.y = app.height/2;
		
		this.comm = new CommunicationManager();
		this.comm.addObserver(this);
		(new Thread(this.comm)).start();
		
		this.destPort = 6000;
		try {
			this.destAddress = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void draw() {
		app.background(200);
		app.ellipse(x, y, 50, 50);
		
	}

	@Override
	public void update(Observable observed, Object data) {
		/*
		 *  Transform data to an understandable object
		 *  and do somethig with it.
		 */
		DatagramPacket receivedData = (DatagramPacket) data;
		String realData = new String(receivedData.getData(), 0, receivedData.getLength());
		
		String[] parts = realData.split(":");
		x = Integer.parseInt(parts[0]);
		y = Integer.parseInt(parts[1]);
		
	}

	public void mouseDragged() {
		// Send a message with the coordinates
		String message = app.mouseX+":"+app.mouseY;
		byte[] data = message.getBytes();
		comm.sendMessage(data, destAddress, destPort);
	}
	
}
