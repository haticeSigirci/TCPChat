import java.io.BufferedReader;
import java.io.IOException;


public class UDPClientPrinter extends Thread { 
	
	private BufferedReader in;
	
	public UDPClientPrinter(BufferedReader in) {
		this.in = in;
	}
	public void run() {
		while(true) {
			try {
				String str = in.readLine();
				if(str == null) {
					break;
				}else {
					System.out.println(str);
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

}
