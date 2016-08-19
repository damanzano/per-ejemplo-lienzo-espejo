package squarenode;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Observable;

public class CommunicationManager extends Observable implements Runnable{
	private DatagramSocket socket;
	private final int PORT = 6000;
	
	public CommunicationManager(){
		// Initialization
		try {
			System.out.println("Starting socket at port "+PORT);
			socket = new DatagramSocket(PORT);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMessage(byte[] data, InetAddress destAddress, int destPort){
		DatagramPacket packet = new DatagramPacket(data, data.length, destAddress, destPort);
		try {
			System.out.println("Sending data to "+destAddress.getHostAddress()+":"+destPort);
			socket.send(packet);
			System.out.println("Data was sent");
		} catch (IOException e) {
			// Error sending the packet
			e.printStackTrace();
		}
	}
	
	public DatagramPacket receiveMessage(){
		byte[] buffer = new byte[1024];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		try {
			socket.receive(packet);
			System.out.println("Data received from "+packet.getAddress()+":"+packet.getPort());
			return packet;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void run() {
		while(true){
			// Control that the socket is still listening
			if(socket!=null){
				DatagramPacket data = receiveMessage();
				
				// Validate that there are no errors with the data
				if(data!=null){
					// Notify the observers that new data has arrived and pass the data to them
					setChanged();
					notifyObservers(data);
					clearChanged();
				}
			}
		}
		
	}

}
