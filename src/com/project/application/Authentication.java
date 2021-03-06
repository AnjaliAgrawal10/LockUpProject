package com.project.application;
import com.project.model.Users;
import com.project.model.fileNames;
import com.project.model.UserCredentials;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class Authentication {
	//input data
	private static Scanner keyboard;
	private static Scanner input;
	private static Scanner lockerInput;
	private static Scanner fileInput;
	//output data 
	private static PrintWriter output;
	private static PrintWriter lockerOutput;
	private static PrintWriter fileOutput;
	//model to store data.
	private static Users users;
	private static UserCredentials userCredentials;
	private static fileNames filename;
	private static File fileObj;
	
	private static String path ="C:\\FSD2\\VirtualKeyProject\\src\\";
	
	public static void main(String[] args) {
		System.out.println("Welcome to VirtualKey!");
        System.out.println("Developer: Anjali Kumari Agrawal");
        System.out.println("\n");
		initApp();
		welcomeScreen();
		signInOptions();

	}
	
	public static void signInOptions() {
		System.out.println("1 . Registration ");
		System.out.println("2 . Login ");
		System.out.println("3.  Exist");
		int option = keyboard.nextInt();
		switch(option) {
			case 1 : 
				registerUser();
				break;
			case 2 :
				loginUser();
				break;
			case 3 :
				System.out.println("Application is closed successfully!");
				exit(0);
				break;
			default :
				System.out.println("Please select 1 Or 2");
				break;
		}
		keyboard.close();
		input.close();
	}
	
	private static void exit(int i) {
		// TODO Auto-generated method stub
		
	}

	public static void lockerOptions(String inpUsername) {
		System.out.println("1 . FETCH ALL STORED CREDENTIALS ");
		System.out.println("2 . STORED CREDENTIALS ");
		System.out.println("3 . DELETE CREDENTIAL FILE");
		int option = keyboard.nextInt();
		switch(option) {
			case 1 : 
				fetchCredentials(inpUsername);
				break;
			case 2 :
				storeCredentials(inpUsername);
				break;
			case 3:
				deleteCredentials(inpUsername);
				break;
			default :
				System.out.println("Please select 1 Or 2");
				break;
		}
		lockerInput.close();
	}
	
	public static void registerUser() {
		System.out.println("==========================================");
		System.out.println("*					*");
		System.out.println("*   WELCOME TO REGISTRATION PAGE	*");
		System.out.println("*					*");
		System.out.println("==========================================");
		
		
		System.out.println("Enter Username :");
		String username = keyboard.next();
		users.setUsername(username);
		
		//check if user is already registered
		boolean found = false;
		while(input.hasNext() && !found) {
			if(input.next().equals(username)) {
					found = true;
					break;
				}
			else {
				input.next();
			}
		}
		
		
		if(!found)
		{
		System.out.println("Enter Password :");
		String password = keyboard.next();
		users.setPassword(password);
		
		output.println(users.getUsername());
		output.println(users.getPassword());
		System.out.println("User Registration Suscessful !");
		}
		else {
			System.out.println("User already exists !");
		}
		
		output.close();
		
	}
	
	public static void loginUser() {
		System.out.println("==========================================");
		System.out.println("*					*");
		System.out.println("*   WELCOME TO LOGIN PAGE	 *");
		System.out.println("*					*");
		System.out.println("==========================================");
		System.out.println("Enter Username :");
		String inpUsername = keyboard.next();
		boolean found = false;
		while(input.hasNext() && !found) {
			if(input.next().equals(inpUsername)) {
				System.out.println("Enter Password :");
				String inpPassword = keyboard.next();
				if(input.next().equals(inpPassword)) {
					System.out.println("Login Successful ! 200OK");
					found = true;
					lockerOptions(inpUsername);
					break;
				}
			}
		}
		if(!found) {
			System.out.println("User Not Found : Login Failure : 404");
		}
		
	}
	
	public static void welcomeScreen() {
		System.out.println("==========================================");
		System.out.println("*					*");
		System.out.println("*   Welcome To LockMe.com		*");
		System.out.println("*   Your Personal Digital Locaker	*");
		System.out.println("*					*");
		System.out.println("==========================================");
		
	}
	
	//store credentails
	public static void storeCredentials(String loggedInUser) {
		System.out.println("==========================================");
		System.out.println("*					*");
		System.out.println("*   WELCOME TO DIGITAL LOCKER STORE YOUR CREDS HERE	 *");
		System.out.println("*					*");
		System.out.println("==========================================");
		boolean found = false;
		while(lockerInput.hasNext() && !found) {
			if(lockerInput.next().equals(loggedInUser)) {
				System.out.println("File exists for :" +loggedInUser);
					found = true;
					break;
				}
			}
		
		if(!found) {
			System.out.println("New User ");
		    fileObj= file(loggedInUser);
		}
		
		userCredentials.setLoggedInUser(loggedInUser);
		
		System.out.println("Enter Site Name :");
		String siteName = keyboard.next();
		userCredentials.setSiteName(siteName);
		
		//check if user credential for specific site present or not
		boolean present = false;
		File userFile = new File(path+loggedInUser);
		try {
			fileInput = new Scanner(userFile);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		while(fileInput.hasNext() && !present) {
			if(fileInput.next().equals(siteName) )
			{
			System.out.println("Site Name: "+ siteName +" is already present!");
			present = true;
			}
			
		}
		
		    
		if(!present)
		{
		System.out.println("Enter Username :");
		String username = keyboard.next();
		userCredentials.setUsername(username);
		
		System.out.println("Enter Password :");
		String password = keyboard.next();
		userCredentials.setPassword(password);
		
		try {
			fileOutput = new PrintWriter( new FileWriter(path+loggedInUser,true));
    		fileOutput.println(userCredentials.getSiteName());
    		fileOutput.println(userCredentials.getUsername());
    		fileOutput.println(userCredentials.getPassword());
           
    		System.out.println("YOUR CREDS ARE STORED AND SECURED!");
 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		fileOutput.close();	
		}
		fileInput.close();
		
	}
	
	//creating locker file for new users
	 public static File file(String inpUsername)
	  {
		
	    File file = new File(path+inpUsername);
	    filename.setFilename(inpUsername);
	    lockerOutput.println(filename.getFilename());
	    try {
			if(file.createNewFile())
			{
				System.out.println("File created: " + file.getName());  
			    System.out.println("Absolute path: " + file.getAbsolutePath()); 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    lockerOutput.close();
	    return file;
	  }
	 
	//fetch credentials
	public static void fetchCredentials(String inpUsername) {
		System.out.println("==========================================");
		System.out.println("*					*");
		System.out.println("*   WELCOME TO DIGITAL LOCKER 	 *");
		System.out.println("*   YOUR CREDS ARE 	 *");
		System.out.println("*					*");
		System.out.println("==========================================");
		System.out.println(inpUsername);
		
		boolean present = false;
		while(lockerInput.hasNext()) {
			if(lockerInput.next().equals(inpUsername) && !present)
			{
			File  userFile = new File(path+inpUsername);
			try {
				fileInput = new Scanner(userFile);
				 while(fileInput.hasNext()){
						System.out.println("Site Name: "+fileInput.next());
						System.out.println("User Name: "+fileInput.next());
						System.out.println("User Password: "+fileInput.next());
						
						present = true;	
					}	
			} catch (FileNotFoundException e) {
			
				e.printStackTrace();
			}
			break;
			}
		}
		   
		
		if(!present)
		{
			System.out.println("No existing credentials for user: "+inpUsername);
		}
		
	}
	
	//deleting credential file
    public static void deleteCredentials(String inpUsername) {
    	boolean present = false;
    	while(lockerInput.hasNext()) {
			if(lockerInput.next().equals(inpUsername) && !present)
			{
			  File  userFile = new File(path+inpUsername);
			  userFile.delete();
			
				lockerOutput.toString().trim();
			present = true;
			System.out.println("UserName: "+inpUsername+" file has been deleted");
			break;
			}
    	}
    	
    	if(!present)
    	{
    		System.out.println("Credential file for userName: "+inpUsername+" doesn't exist" );
    	}
	}
	
	public static void initApp() {

		File  dbFile = new File("database.txt");
		File  lockerFile = new File("locker-data.txt");
		
		try {
			//read data from db file
			input = new Scanner(dbFile);
			
			//read data from locker file
			lockerInput = new Scanner(lockerFile);
			
			//read data from keyboard
			keyboard = new Scanner(System.in);
			
			//out put 
			output = new PrintWriter( new FileWriter(dbFile,true));
			lockerOutput = new PrintWriter( new FileWriter(lockerFile,true));
			
			users = new Users();
			userCredentials  = new UserCredentials();
			filename = new fileNames();
			
		} catch (IOException e) {
			System.out.println("404 : File Not Found ");
		}
		
	}

}
