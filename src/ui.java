import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;

public class ui {



	static boolean flag = true;
	static boolean wait = true;
	static boolean forMenu;
	static String path = "";
    public static void main(String[] args)
    {
    	
    	ScanFile run = new ScanFile();
    	
    	JFrame frame = new JFrame("METCNR - Security Term");
        JLabel label = new JLabel("Program", JLabel.CENTER) ;
        JFrame frame2 = new JFrame("Reports");
        JButton b=new JButton("Browse");  
    	JMenuBar menubar = new JMenuBar();
    	JMenu m1 = new JMenu("File");
    	JMenuItem m1_1 = new JMenuItem("Start");
    	JMenuItem m1_2 = new JMenuItem("Stop");
    	JMenuItem m1_3 = new JMenuItem("Same Files");
    	JMenuItem m1_6 = new JMenuItem("Exit");


        JButton b2 = new JButton("Detect same file(s)");  
        JButton b3 = new JButton("Detect changed file(s)");  
        JButton b4 = new JButton("Take Snapshot");  
 


    	menubar.add(m1);

    	
    	m1.add(m1_1);
    	m1.add(m1_2);
    	m1.add(m1_3);
    	m1.add(m1_6);
		JTextField t2 = new JTextField("Choose Your Path");  

    	frame.getContentPane().add(BorderLayout.NORTH,menubar);
        b.setBounds(1220,50,130,30);  
        b2.setBounds(610,900,160,30);  
        b3.setBounds(1100,900,160,30);  
        b4.setBounds(850,900,160,30);  
        
	    t2.setBounds(520,50, 700,30); 
		JTextArea t1 = new JTextArea(run.theSameFiles);  
		JScrollPane scroll = new JScrollPane (t1, 
				   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    scroll.setBounds(520,200,845,600); 
	    t1.setEditable(false);
	    
	    b2.setEnabled(false);
	    b3.setEnabled(false);
	    b4.setEnabled(false);
	    
        frame.add(t2);
    	frame.add(b);
    	frame.add(b2);
    	frame.add(b3);
    	frame.add(b4);
	    frame.add(scroll);
    	frame.setLayout(null);  

    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(1920,1080);
    	frame.setVisible(true);
        t2.setEditable(false);

    	b.addActionListener(new ActionListener(){  
    		public void actionPerformed(ActionEvent e){  
    			JFileChooser fileChooser = new JFileChooser();
    			 
    	        // For Directory
    	        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    	 
    	       
    	 
    	        fileChooser.setAcceptAllFileFilterUsed(false);
    	 
    	        int rVal = fileChooser.showOpenDialog(null);
    	        if (rVal == JFileChooser.APPROVE_OPTION) {
    	          path = fileChooser.getSelectedFile().toString();
    	          run.goTopath = path+"/checkpoint.txt";
    	          run.goTopath2 = path+"/checkpoint-en.txt";
    	          b2.setEnabled(true);
    	          b3.setEnabled(true);
    	          b4.setEnabled(true);
    	        }
    	        t2.setText(path);
    		}  
    		    });  
    	b2.addActionListener(new ActionListener(){  
    		public void actionPerformed(ActionEvent e)
    		{  
    			try {
					run.readFile(path);
					for(String content:run.theSameFiles.split("!"))
					{
						t1.append(content);
					}
					
    			} catch (IOException e1) {
					
					e1.printStackTrace();
				}
    			
    		}  
    		    });  
    	b3.addActionListener(new ActionListener(){  
    		public void actionPerformed(ActionEvent e)
    		{  
    			try {
					run.isChanged();
					t1.append("\n"+run.changedFiles+"\n");
					t1.append("Changed File Count : "+run.changedFileCnt);
					run.changedFileCnt = 0;
					run.changedFiles = "";
				} catch (NoSuchAlgorithmException | IOException e1) {
					e1.printStackTrace();
				} catch (InvalidKeyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchPaddingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalBlockSizeException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (BadPaddingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			
    		}  
    		    });  
    	
    	b4.addActionListener(new ActionListener(){  
    		public void actionPerformed(ActionEvent e)
    		{  
				try {
					run.takeSnapshot();
					t1.append("\n\nSnapshot taken !\n\n -> Path: "+run.goTopath); 
						try {
					      File myObj = new File(run.goTopath);
					      Scanner myReader = new Scanner(myObj);
					      while (myReader.hasNextLine()) {
					        String data = myReader.nextLine();
					        t1.append("\n"+data+"\n");
					      }
					      myReader.close();
					    } catch (FileNotFoundException e1) {
					      System.out.println("An error occurred.");
					      e1.printStackTrace();
					    }
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (InvalidKeyException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (NoSuchPaddingException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (IllegalBlockSizeException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (BadPaddingException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

    			
    		}  
    		    });  
    	


    }
}
