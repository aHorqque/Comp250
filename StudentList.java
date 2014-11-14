import java.io.*;    
import java.util.*;

class studentList {
    int studentID[];
    int numberOfStudents;
    String courseName;
    
    // A constructor that reads a studentList from the given fileName and assigns it the given courseName
    public studentList(String fileName, String course) {
	String line;
	int tempID[]=new int[4000000]; // this will only work if the number of students is less than 4000000.
	numberOfStudents=0;
	courseName=course;
	BufferedReader myFile;
	try {
	    myFile = new BufferedReader(new FileReader( fileName ) );	

	    while ( (line=myFile.readLine())!=null ) {
		tempID[numberOfStudents]=Integer.parseInt(line);
		numberOfStudents++;
	    }
	    studentID=new int[numberOfStudents];
	    for (int i=0;i<numberOfStudents;i++) {
		studentID[i]=tempID[i];
	    }
	} catch (Exception e) {System.out.println("Can't find file "+fileName);}
	
    }
    

    // A constructor that generates a random student list of the given size and assigns it the given courseName
    public studentList(int size, String course) {
	int IDrange=2*size;
	studentID=new int[size];
	boolean[] usedID=new boolean[IDrange];
	for (int i=0;i<IDrange;i++) usedID[i]=false;
	for (int i=0;i<size;i++) {
	    int t;
	    do {
		t=(int)(Math.random()*IDrange);
	    } while (usedID[t]);
	    usedID[t]=true;
	    studentID[i]=t;
	}
	courseName=course;
	numberOfStudents=size;
    }

    
    // Returns the number of students present in both lists L1 and L2
    public static int intersectionSizeNestedLoops(studentList L1, studentList L2) 
    {
    	int intersection=0;
    	//nested for loops, going to size of the list
    	for(int i=0; i<L1.numberOfStudents; i++) 
    	{
    		for(int j=0; j<L2.numberOfStudents; j++)
    		{
    			if(L1.studentID[i]==L2.studentID[j])
    			{	
    				//add one if the ids are the same at that given point
    				intersection++;
    			}
    		}
    	}
    	//return number of cross IDs
    	return intersection;    	
    }
    
    
    // This algorithm takes as input a sorted array of integers called mySortedArray, the number of elements it contains,
    // and the student ID number to look for
    // It returns true if the array contains an element equal to ID, and false otherwise.
    public static boolean myBinarySearch(int mySortedArray[], int numberOfStudents, int ID) {
	
    	
    	int left = 0;
		int right = mySortedArray.length-1;

		boolean isIntPresent = false;

		while(left<=right)//create a loop s.t. it keeps going until we reached the last integer(the farmost right or left)
		{
			int middle = (int)((left+right)/2);

			if(ID == mySortedArray[middle])// if the target is the value stored at the middle, then we are done
			{
				isIntPresent = true; 
				break;
			}
			else if(ID > mySortedArray[middle])//we check if the target is in the first half of the array
				left = middle + 1;//change the value of the left to become the middle so we discard the first half
            else if(ID < mySortedArray[middle])//check if the target is in the second half of the array
            	right = middle-1;//we change the value of right to become the middle so we discard the left half
            else
            	{
					isIntPresent = false;// if the loop checked all the numbers and it wasnt present, we return false.
            		break;
            	}
		}
		return isIntPresent;

    }
    
    
    public static int  intersectionSizeBinarySearch(studentList L1, studentList L2) {
	/* Write your code for question 2 here */
    	int intersection = 0 ;
    	Arrays.sort(L2.studentID);

    	for(int i=0; i<L1.numberOfStudents; i++)
    	{
        	if ( myBinarySearch(L2.studentID, L2.numberOfStudents, L1.studentID[i]) == true)
        			intersection++;
    	}
    	
    	return intersection; 
    	
    }
    
    
    public static int intersectionSizeSortAndParallelPointers(studentList L1, studentList L2) {
    	int intersection = 0;
    	Arrays.sort(L1.studentID);
    	Arrays.sort(L2.studentID);
    	int A = 0; 
    	int B = 0; 
    	while (A < L1.numberOfStudents && B < L2.numberOfStudents)
    	{
    		if (L1.studentID[A] == L2.studentID[B])
    		{
    			intersection++;
    			A++;
    			B++;
    		}
    		else if(L1.studentID[A] < L2.studentID[B])
    			A++; 
    		else 
    			B++;    			
    	}
    	return intersection;
    }
    
    
    public static int intersectionSizeMergeAndSort(studentList L1, studentList L2) {

    	int intersection = 0; 
    	int point = 0;
    	
    	int[] merge = new int[L1.numberOfStudents + L2.numberOfStudents];  // new array of merged arrays
    	for(int i=0; i<L1.numberOfStudents; i++) //put info in the array
    		merge[i] = L1.studentID[i];
    	for(int j=0; j<L2.numberOfStudents; j++) // put info in the array without overwriting previously stored info ^
    		merge[j+L1.numberOfStudents] = L2.studentID[j];
    	Arrays.sort(merge);
    	
    	while( point < (L1.numberOfStudents + L2.numberOfStudents)-1 ) // we only go to length - 1, otherwise we are out of bounds
    	{
    		if(merge[point] == merge[point + 1]) // compare if the number and the next are the same
    		{
    			intersection++;
    			point= point+2;	// if they are, we move on to the next number (not the one we just checked) which is why we do +2
    		}
    		else
    			point++; // otherwise, we will check if the number we compared is equal to the number to its right.
    		
    	}
    	return intersection;
    	
    }
    
    
    
    /* The main method */
    /* Write code here to test your methods, and evaluate the running time of each. 2014 */
    /* This method will not be marked */
    public static void main(String args[]) throws Exception {
	
	studentList firstList;
	studentList secondList;
	
	// This is how to read lists from files. Useful for debugging.
	
	//firstList=new studentList("COMP250.txt", "COMP250 - Introduction to Computer Science");
	//secondList=new studentList("MATH240.txt", "MATH240 - Discrete Mathematics");
	
	// get the time before starting the intersections
	long startTime = System.nanoTime();

	
	// repeat the process a certain number of times, to make more accurate average measurements.
	for (int rep=0;rep<10;rep++) {
	    
	    // This is how to generate lists of random IDs. 
	    
		firstList=new studentList(1024000, "COMP250 - Introduction to Computer Science"); 
	  	secondList=new studentList(32000 , "MATH240 - Discrete Mathematics"); 
	  	

	    // run the intersection method
	  int intersection=studentList.intersectionSizeSortAndParallelPointers(firstList,secondList);
	  System.out.println("The intersection size is: "+ intersection);
	}
	
	// get the time after the intersection
	long endTime = System.nanoTime();
	
	
	System.out.println("Running time: "+ (endTime-startTime)/10.0 + " nanoseconds");
    }
    
}

