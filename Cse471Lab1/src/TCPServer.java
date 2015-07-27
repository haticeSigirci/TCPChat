import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class TCPServer {

	public static void main(String[] args) throws IOException {
		
		String clientSentence;
		String capitalizedSentence;
		
		File file = new File("HelloTxt");
		file.createNewFile();
		
		FileWriter writer = new FileWriter(file);
		
		ServerSocket welcomeSocket = new ServerSocket(6789); //new serverSocket object to 
				//listen a specific port
		
		while(true) {
			
			Socket connectionSocket = welcomeSocket.accept();
			System.out.println("Incoming from: " + connectionSocket.getInetAddress().getHostAddress());
			
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			
			while(true) {
				
				clientSentence = inFromClient.readLine();
				System.out.println("Received : " + clientSentence);
				capitalizedSentence = clientSentence.toUpperCase() + "\n";
				
				writer.write("Received : " + clientSentence + " " + connectionSocket.getInetAddress().getHostAddress() + "\r\n");
				writer.write("FROM SERVER : " + capitalizedSentence + " " + connectionSocket.getInetAddress().getHostAddress() + "\r\n");
				writer.flush();
				
				outToClient.writeBytes(capitalizedSentence);
				
				
			}
			
		}
		
		
	}
}
