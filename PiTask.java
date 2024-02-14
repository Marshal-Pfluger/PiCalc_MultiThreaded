//********************************************************************
//
//  Author:        Marshal Pfluger
//
//  Program #:     Six
//
//  File Name:     PiTask.java
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
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;


public class PiTask implements Runnable {
	// Declare class private variables 
    private long numPoints;
    private Lock deadbolt;

	//***************************************************************
	//
	//  Method:       PiTask constructor
	// 
	//  Description:  constructor for PiTask Class
	//
	//  Parameters:   long numPoints, Lock lock
	//
	//  Returns:      N/A
	//
	//**************************************************************
    PiTask(long numPoints, Lock lock) {
        this.numPoints = numPoints;
        this.deadbolt = lock;
    }

	//***************************************************************
	//
	//  Method:       run override
	// 
	//  Description:  overrides the run method in the runnable interface
	//
	//  Parameters:   N/A
	//
	//  Returns:      N/A
	//
	//**************************************************************
    @Override
    public void run() {
    	// Declare variable to hold num of points in circle
        long localPointsInCircle = 0;
        // Loop through the number of points and check if they are in circle
        for (int i = 0; i < numPoints; i++) {
        	// Use ThreadLocalRandom to generate random values from -1,1
            double x = ThreadLocalRandom.current().nextDouble(-1.0, 1.0);
            double y = ThreadLocalRandom.current().nextDouble(-1.0, 1.0);
            if(y < -1 || y > 1) {
            	System.out.println("here");
            	
            }
            if(x < -1 || x > 1) {
            	System.out.println("here");
            	
            }

            // Check if point is in circle
            if (Math.sqrt(x * x + y * y) < 1.0) {
                localPointsInCircle++;
            }
        }

        // Lock access to the global variable pointsInCircle to stop race conditions
        deadbolt.lock();
        try {
            // Add points to global variable
            Program6.pointsInCircle += localPointsInCircle;
        } finally {
            // Unlock variable to allow other threads to access
            deadbolt.unlock();
        }


    }
}
