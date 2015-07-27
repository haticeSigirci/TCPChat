import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;


public class UDPMultiServer {

	private static HashMap<DatagramSocket, String> hashMap = new HashMap<DatagramSocket, String>();
	
	public static void main(String[] args) {
		int i= 3;
		
		try {
			System.out.println("Server is working...");
			DatagramSocket serverSocket = new DatagramSocket();
			byte[] receiveData = new byte[100];
			
			while(true) {
				
//				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
//				String sentence = new String(receivePacket.getData());			
				i--;
				getHashMap().put(serverSocket, "Client" + i);
				new UDPServerHelper(serverSocket).start();
			}
		}
		
		catch(Exception e) {
				e.printStackTrace();
		}
	}
	
	
	public static synchronized HashMap<DatagramSocket, String> getHashMap() {
		return hashMap;
	}
	public static synchronized void setHashMap(HashMap<DatagramSocket, String> hashMap) {
		UDPMultiServer.hashMap = hashMap;
	}
	
}
