import java.awt.peer.TextComponentPeer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;


public class UDPServerHelper extends Thread{
	
	private DatagramSocket incoming;
	
	public UDPServerHelper(DatagramSocket incoming) {
		this.incoming = incoming;
	}
	
	public void run() {
		try {
			byte[] receiveData = new byte[100];
			byte[] sendData = new byte[100];
			//BufferedReader in = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
			DatagramPacket in = new DatagramPacket(receiveData, receiveData.length);
			incoming.receive(in);
			DatagramPacket out ;
//			PrintWriter out;
			//
				String str = new String(in.getData());
				System.out.println("Server received: " + str);
				int port = in.getPort();
				InetAddress IPAdress = in.getAddress();
				sendData = str.getBytes();
//				if(str == null) {
//					break;
//				}
				//else {
					for(DatagramSocket s: UDPMultiServer.getHashMap().keySet()) {
						if(s.getPort() != incoming.getPort()) {
							
							DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAdress, port);
							incoming.send(sendPacket);
//							out.println(UDPMultiServer.getHashMap().get(incoming) + ":" + str);
//							out.flush();
						}
					}
				//}
			
			incoming.close();
		}catch(Exception exception) {
			exception.printStackTrace();
		}
	}

}
