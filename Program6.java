//********************************************************************
//
//  Author:        Marshal Pfluger
//
//  Program #:     Six
//
//  File Name:     Program6.java
//
//  Course:        COSC 4302 Operating Systems
//
//  Due Date:      11/07/2023
//
//  Instructor:    Prof. Fred Kumi
//
//  Java Version:  17
//
//  Description:   MultiThreaded program with locks to approximate pi
//
//********************************************************************

import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Program6 {
    public static long pointsInCircle = 0;
    private Lock deadbolt = new ReentrantLock();

    public static void main(String[] args) {
    	// instantiate obj of class to call non static methods
    	Program6 obj = new Program6();
    	obj.developerInfo();
    	obj.runDemo();
    }
    
    //**************************************************************
    //
    //  Method:       runDemo (Non Static)
    // 
    //  Description:  The main processor method of the program
    //
    //  Parameters:   N/A
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    public void runDemo() {
    	// Declare variables for operation 
    	String sentinel = "";
    	// Set processors to num of machine minus one for main thread
    	int numThreads = numProcessors() - 1;
    	long totalPoints = 0;
    	Thread[] threads;
    	double piEstimate = 0.0;
    	
    	// Loop through functionality to rerun program operations
    	do {
    		
    		// Reset values for new run
    		pointsInCircle = 0;
    		piEstimate = 0.0;
    		// Receive input from user of number of points
    		printOutput("Please enter the number of points to generate. (lower numbers will result in poor results)");
    		totalPoints = (inputParsing(userChoice())) / numThreads;
    		// Create thread array
            threads = new Thread[numThreads];

            // Start threads for number of threads, split points between threads
            for (int i = 0; i < numThreads; i++) {
                threads[i] = new Thread(new PiTask((totalPoints / numThreads), deadbolt));
                threads[i].start();
            }

            // Join the threads to wait for completion 
            for (int i = 0; i < numThreads; i++) {
                try {
                    threads[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Calculate estimate of pi
            piEstimate = 4.0 * pointsInCircle / (double) totalPoints;
            // Display results
            printOutput("Estimated value of π: " + piEstimate);
            // Allow user to rerun the program
            printOutput("Would you like to run the program again?\n"
            		  + "Press '0' to exit or enter key to re run.");
            // Set sentinel value to the users choice
            sentinel = userChoice();
    	} 
    	while (!sentinel.equals("0"));
    	
    	// Inform user the program has terminated
    	printOutput("The program has terminated, have a good day.");

    }
    
	//***************************************************************
	//
	//  Method:       numProcessors
	// 
	//  Description:  Sets the number of processors for the machine
	//
	//  Parameters:   String output
	//
	//  Returns:      N/A
	//
	//***************************************************************
    public int numProcessors() {
		// Get count of available cores
		return Runtime.getRuntime().availableProcessors();
    }
    
	//***************************************************************
	//
	//  Method:       printOutput (Non Static)
	// 
	//  Description:  handles printing output
	//
	//  Parameters:   String output
	//
	//  Returns:      N/A
	//
	//***************************************************************
	public void printOutput(String output) {
		//Print the output to the terminal
		System.out.print("\n");
		System.out.println(output);
	}//End printOutput
	
	//***************************************************************
	//
	//  Method:       inputParsing
	// 
	//  Description:  method parses client package
	//
	//  Parameters:   String inputLine
	//
	//  Returns:      List<Integer>
	//
	//**************************************************************
	public long inputParsing(String input) {
		
		// Declare variable to hold user string input
		String userInput = input;
		long parsedInput = 0;
		boolean validationState = true;
		
		// Loop through input parsing for each type of input from user
		do {
			validationState = true;
	    	// Try catch to handle non number input
			try {
				// Receive the size of list the user wants to create
	            	parsedInput = Long.parseLong(userInput);
	        	}
	        	catch(NumberFormatException e){
	        		// Prompt user to re-enter number
	        		printOutput("Your Input was invalid.\nPlease try again: ");
	        		userInput = userChoice();
	        		validationState = false;
	        	}
			
		}
		while (!validationState);
		return parsedInput;	  
	}// End inputParsing
	
    //**************************************************************
    //
    //  Method:       userChoice
    //
    //  Description:  gets input from user, closes scanner when program exits 
    //
    //  Parameters:   N/A
    //
    //  Returns:      String file
    //
    //**************************************************************	
    public String userChoice() {
    	String userChoice;
    	// Use Scanner to receive user input
    	Scanner userInput = new Scanner(System.in);
    	// Store user choice
    	userChoice = userInput.nextLine();
    	
    	// close scanner when program exits.
    	if (userChoice.equalsIgnoreCase("0")) {
    		userInput.close();
    		}
    	return userChoice;
    	}
    
    //***************************************************************
    //
    //  Method:       developerInfo (Non Static)
    // 
    //  Description:  The developer information method of the program
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    public void developerInfo(){
       System.out.println("Name:    Marshal Pfluger");
       System.out.println("Course:  COSC 4302 Operating Systems");
       System.out.println("Project: Six\n\n");
    } // End of the developerInfo method
}