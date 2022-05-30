import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.util.stream.Collectors;


public class ScanFile 
{
	
	List<String> result = new ArrayList<String>();
	
	List<String> checkpointList = new ArrayList<>();

	String theSameFiles = "";
	
	Key p = new Key();
	
	HashMap<String,String> files = new HashMap<>();
	HashMap<String,String> sameFiles = new HashMap<>();
	
	String goTopath = "";
	String goTopath2 = "";
	
	int changedFileCnt = 0;
	String changedFiles = "";

	public String isChangedCheckpoint() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException
	{
		String dcrp = p.decryptedFile("keykeykeykey.com",goTopath2);
		
		return dcrp;
	}

	public void isChanged() throws NoSuchAlgorithmException, IOException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
	{
		int counter = 0;
		boolean flag;
		MessageDigest md = MessageDigest.getInstance("MD5");
		try  
		{  
			for(int i = 0; i<result.size(); i++)
			{
				String checksum = getFileChecksum(result.get(i),md);

				files.put(result.get(i),checksum);
			}
			
			
			File file=new File(goTopath);    
			FileReader fr=new FileReader(file); 
			BufferedReader br = new BufferedReader(fr);
			StringBuffer sb = new StringBuffer();
			String line;
			String total = "";

			
			
			
			
			while((line=br.readLine())!=null)  
			{
				int last = line.lastIndexOf(" ");
				total = total + line+"\n";
				
				if(files.get(line.substring(0,last)).equals(line.substring(last+1)))
				{
					flag = true;
				}
				else
				{
					changedFileCnt++;
					changedFiles += line.substring(0,last) + " is changed. !!!"+"\n"; 
					flag = false;
					System.out.println(line.substring(0,last)+" is changed.");
				}
				if(flag)
				{
				}
				
				
			}  
			fr.close();   
			System.out.println("total : "+total);
			if(isChangedCheckpoint().equals(total))
			{
				System.out.println("Well");
			}

		}  
		catch(IOException e)  
		{  
			e.printStackTrace();  
		}  
		
	}
		
	
	
	
	public void write2file(String path,String text)
	{
		 try {
		      FileWriter myWriter = new FileWriter(path);
		      myWriter.write(text);
		      myWriter.close();
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	
	private static String getFileChecksum(String file,MessageDigest digest) throws IOException
	{
		  File x = new File(file);
		  FileInputStream fis = new FileInputStream(x);
		   
		 	
		  byte[] byteArray = new byte[1024];
		  int bytesCount = 0; 
		    
		  	
		  while ((bytesCount = fis.read(byteArray)) != -1) {
		    digest.update(byteArray, 0, bytesCount);
		  };
		   
		 	
		  fis.close();
		   
		  byte[] bytes = digest.digest();
		 
		  StringBuilder sb = new StringBuilder();
		  for(int i=0; i< bytes.length ;i++)
		  {
		    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		  }
		 
		   return sb.toString();
	}


	public void takeSnapshot() throws NoSuchAlgorithmException, IOException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
	{
			String total = "";
			int counter = 0;
			MessageDigest md = MessageDigest.getInstance("MD5");
			for(int i = 0; i<result.size(); i++)
			{
				
				String checksum = getFileChecksum(result.get(i),md);
				files.put(result.get(i),checksum);	
			
			}
			
			
			for (String key: files.keySet()){
				total = total + key +" "+files.get(key)+"\n";
	            counter++;
	        }
			write2file(goTopath, total);
			Key.encryptedFile("keykeykeykey.com",goTopath, goTopath2);
	}

	
	
	
	boolean sameContent(String file1, String file2) throws IOException 
	{
		
		 BufferedReader bfr2 = new BufferedReader(new InputStreamReader(System.in));
		 String s1 = "";
		 String s2 = "", s3 = "", s4 = "";
		 String y = "", z = "";

		    

		 BufferedReader bfr = new BufferedReader(new FileReader(file1.toString()));
		 BufferedReader bfr1 = new BufferedReader(new FileReader(file2.toString()));

		 while ((z = bfr1.readLine()) != null)
		        s3 += z;

		 while ((y = bfr.readLine()) != null)
		        s1 += y;

		
		 if (s3.equals(s1)) 
		 {
			 return true;
		 } 
		 else 
		 {

			 return false;
		 }
	}
	
	public void detectSameFiles() throws IOException
	{
		System.out.println("-------- Path List start -------\n");
		for(int i = 0; i<result.size(); i++)
		{
			
			for(int j = 0; j<result.size(); j++)
			{
				boolean check = sameContent(result.get(i),result.get(j));
				if(check & i !=j)
				{
					
					System.out.println("Path 1 : "+result.get(i)+" ---------- "+"Path 2 : "+result.get(j)+" || are same file !");
					theSameFiles += "Path 1 : "+result.get(i)+" ---------- "+"Path 2 : "+result.get(j)+" || are same file !"+"\n";
					sameFiles.put(result.get(i),result.get(j));
				}
			}
		}
		System.out.println("\n-------- Path List end -------\n");
	}
	
	public void readFile(String absPath) throws IOException
	{
		
		
		int index = -1;
		int index2 = -1;
		int counter = 0;
		try (Stream<Path> walk = Files.walk(Paths.get(absPath))) 
		{
            result = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString())
                    .collect(Collectors.toList());
           for(String x : result)
           {
        	   if(x.contains("checkpoint.txt"))
        	   {
        		   index = counter;
        		   System.out.println(index);
        	   }
        	   if(x.contains("checkpoint-en.txt"))
        	   {
        		   index2 = counter;
        	   }
        	   counter++;
           }
           if(index != -1)
           {
        	   result.remove(index);
           }
           if(index2 != -1)
           {
        	   result.remove(index2);
           }
           
            
        } 
		catch (IOException e) 
		{
            e.printStackTrace();
        }
	
		detectSameFiles();

	}
	
	
	
	
}
