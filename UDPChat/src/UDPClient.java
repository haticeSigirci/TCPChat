import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;



public class UDPClient {
	
	public static void main(String[] args) {
		
		try{
			System.out.println("Client is working");

			
			byte[] sendData = new byte[100];		
			byte[] receiveData = new byte[100];
			
			DatagramPacket sendPacket , receivePacket;
			DatagramSocket socket = new DatagramSocket();
			InetAddress IPAddress =  InetAddress.getByName("localhost");
			BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
			
	
			new UDPClientPrinter(inFromUser).start();
			
			System.out.println("Type your nick: ");
			
			String strNew = inFromUser.readLine();
			sendData = strNew.getBytes();
			sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
			socket.send(sendPacket);

			
			System.out.println("Start chatting!");
			
			String str;
			while(true) {
				str = inFromUser.readLine();
				sendData = str.getBytes();
				sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
				socket.send(sendPacket);

			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
