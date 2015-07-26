import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;



public class ReadFile
 {
	public String readingString  = "";
	
    public  String read(String fileName) 
    {
       System.out.println("Reading File from Java code");
       //Name of the file
       
       
       try{

          //Create object of FileReader
          FileReader inputFile = new FileReader(fileName);

          //Instantiate the BufferedReader Class
          BufferedReader bufferReader = new BufferedReader(inputFile);

          //Variable to hold the one line data
          String line;
          
          // Read file line by line and print on the console
          while ((line = bufferReader.readLine()) != null)   {
        	  readingString = readingString + line + "\n";
        	  
          }
         // System.out.println(readingString);
          
          //Close the buffer reader
          bufferReader.close();
          
         
       }catch(Exception e){
          System.out.println("Error while reading file line by line:" + e.getMessage());                      
       }
       
       return readingString;
     }
    
    public void write(String writeString , String fileName) {
    	
    	PrintWriter writer;
		try {
			writer = new PrintWriter(fileName, "UTF-8");
			writer.write(writeString);
	    	writer.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
  }