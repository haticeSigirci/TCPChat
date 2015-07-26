
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.MessageDigest;
import java.security.Security;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * Basic class to confirm the Bouncy Castle provider is installed.
 */
public class Main {
	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		try {
			Security.addProvider(new BouncyCastleProvider());

			ReadFile file = new ReadFile();
			//file.read();
			//file.write();
			
			Scanner input = new Scanner(System.in);

			System.out.println("Enter the your choice:\n1)Plaint Text to Cipher Text \n2)Cipher Text to Plain Text\n");

			int selection = input.nextInt();

			System.out.println("Enter your choice:\n1)Authentication\n2)Confidentiality\n3)Authentication and Confidentiality\n");

			int selection2 = input.nextInt();

			// Declarations
			byte[] ivBytes = new byte[] { 0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00 };
			
			Cipher cipher = Cipher.getInstance("DES", "BC");

			String inputMessage = file.read("new.txt");
			
			
			byte[] inputMessageBytes = inputMessage.getBytes();
			Mac mac = Mac.getInstance("DES", "BC");
			IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);


			byte[] keyByte = new byte[] { 0x01, 0x23, 0x45, 0x67, (byte) 0x89,	(byte) 0xab, (byte) 0xcd, (byte) 0xef };

			byte[] macByte = new byte[] { 0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00 };
			
			// Key Generate
			Key key = new SecretKeySpec(keyByte, "DES");
			Key macKey = new SecretKeySpec(macByte, "DES");
			int ctLength = 0;
			byte[] cipherText = null;

			while (true) {
				// Menu Selection Part
				if (selection == 1) {

					if (selection2 == 1) {

						mac.init(macKey);
						mac.update(Utils.toByteArray(inputMessage));
						
					//	String macString = Utils.toString(mac.doFinal());
						Writer writer = null;
						try {
							writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Mac.txt"), "utf-8"));
							writer.write(Utils.toHex(mac.doFinal()));
					    	writer.close();
							
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						Writer writer2 = null;
						try {
							writer2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("MacMessage.txt"), "utf-8"));
							writer2.write(inputMessage);
					    	writer2.close();
							
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    	
						
						selection = 2;
						//file.write(macString, "Mac.txt");
						
						

					} else if (selection2 == 2) {

						System.out.println("input : " + Utils.toString(inputMessageBytes));

						cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

						cipherText = new byte[cipher
								.getOutputSize(inputMessageBytes.length)];

						ctLength = cipher.update(inputMessageBytes, 0,
								inputMessageBytes.length, cipherText, 0);

						ctLength += cipher.doFinal(cipherText, ctLength);

						System.out.println("cipher: "
								+ Utils.toHex(cipherText, ctLength)
								+ " bytes: " + ctLength);
						
						String writeFile = "cipherText" + Utils.toString(cipherText);
						
						file.write(writeFile, "Encrypt.txt");
						
						

						selection = 2;
					} else if (selection2 == 3) {

						cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

						cipherText = new byte[cipher.getOutputSize(inputMessage
								.length() + mac.getMacLength())];

						ctLength = cipher.update(
								Utils.toByteArray(inputMessage), 0,
								inputMessage.length(), cipherText, 0);

						mac.init(macKey);
						mac.update(Utils.toByteArray(inputMessage));

						ctLength += cipher.doFinal(mac.doFinal(), 0,
								mac.getMacLength(), cipherText, ctLength);
						
						String writeFile = "cipherText" + Utils.toString(cipherText);
						
						file.write(writeFile, "AuthenticationConfidentiality.txt");
						
						selection = 2;

					}

				} else if (selection == 2) {

					if (selection2 == 1) {
						
						ReadFile inputFile = new ReadFile();
						String macFile = inputFile.read("Mac.txt");
						String messageFile = inputFile.read("MacMessage.txt");
						
						
						mac.init(macKey);
						mac.update(Utils.toByteArray(messageFile));
						
						
						if(macFile.equals(mac)) {
							System.out.println("True");
						}else {
							System.out.println("False");
						}
						
						break;

					} else if (selection2 == 2) {

						cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

						byte[] plainText = new byte[cipher
								.getOutputSize(inputMessageBytes.length)];

						int ptLength = cipher.update(cipherText, 0, ctLength,
								plainText, 0);

						ptLength += cipher.doFinal(plainText, ptLength);

						System.out.println("plain : "
								+ Utils.toHex(plainText, ptLength) + " bytes: "
								+ ptLength);
						

						break;
					} else if (selection2 == 3) {

						cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

						byte[] plainText = cipher.doFinal(cipherText, 0,
								ctLength);
						int messageLength = plainText.length
								- mac.getMacLength();

						mac.init(macKey);
						mac.update(plainText, 0, messageLength);

						byte[] messageHash = new byte[mac.getMacLength()];
						System.arraycopy(plainText, messageLength, messageHash,
								0, messageHash.length);

						System.out.println("plain : "
								+ Utils.toString(plainText, messageLength)
								+ " verified: "
								+ MessageDigest.isEqual(mac.doFinal(),
										messageHash));

						break;
					}

				}

			}

		} catch (ShortBufferException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalBlockSizeException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		} catch (BadPaddingException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		} catch (DataLengthException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalStateException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}

		System.out.println("Test done !");
	}
	
	

}
