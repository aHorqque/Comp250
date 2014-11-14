import java.util.*;
import java.io.*;


class ProgramFrameMS {
    int start;
    int stop;
    int PC;
    
    public ProgramFrameMS(int myStart, int myStop, int myPC) {
	start=myStart;
	stop=myStop;
	PC=myPC;
    }

    // returns a String describing the content of the object
    public String toString() {
	return "ProgramFrame: start = " + start + " stop = " + stop + " PC = " + PC;
    }
}

class MergeSortNonRecursive {

	   // This is the stack we will use to store the set of ProgramFrame mimicking the recursive calls
    static Stack<ProgramFrameMS> callStack;

    // Our non-recursive version of the mergeSort method, using a stack to mimic recursion
    static void mergeSort(int A[]) {

    	int start = 0;
    	int stop = A.length-1;
    	int mid;

		// the stack will use
		callStack = new Stack<ProgramFrameMS>();

		// the initial program frame
		ProgramFrameMS current = new ProgramFrameMS(start, stop, 1); 

		// put that frame on the stack
		callStack.push(current);

		while (!callStack.empty()){

			// for debugging purposes
			 System.out.println(callStack);

			// base case
			if (callStack.peek().PC == 1){

				if (callStack.peek().start==callStack.peek().stop){
					// we are done with that frame
				    callStack.pop();
				    callStack.peek().PC++; 	// increase PC of the parent-array 
				    						// so if we checked left (PC==2)
				    						// we will now check PC==3, i.e. the right part
				    						// once  it has checked both left and right, 
				    						// PC will increase to 4, to Sort that subarray
				} 
				else 
					callStack.peek().PC++;
				
			}

			if (callStack.peek().PC == 2){
				// midpoint of the current top stack
				mid = (callStack.peek().start + callStack.peek().stop) / 2;

				// create a new program frame, corresponding to the recursive call
				current = new ProgramFrameMS(callStack.peek().start, mid, 1);
				// using only the left half of the array
				//this will mimic the recursion since the PC = 1
				callStack.push(current);
			
			}

			if (callStack.peek().PC == 3){

				// midpoint of the current top stack
				mid = (callStack.peek().start + callStack.peek().stop) / 2;

				// create a new program frame, corresponding to the recursive call
				current = new ProgramFrameMS(mid+1, callStack.peek().stop, 1);
				//using the right side of the array
				callStack.push(current);

			}

			if (callStack.peek().PC == 4){
				//once we have reached PC == 4, it means that subArray
				//needs to be sorted
				
				if (!callStack.empty()){
					// midpoint of the current top stack
					mid = (callStack.peek().start + callStack.peek().stop) / 2;

					merge(A, callStack.peek().start, mid, callStack.peek().stop);
					//that array has been sorted now,
					callStack.pop();
					if (callStack.empty()) 
						break;
					//If the whole array has been sorted, then it was popped, so we stop
					callStack.peek().PC++;
				}
	
			}
		}

    }

    public static void merge(int A[], int start, int mid, int stop) 
    {
	int index1=start;
	int index2=mid+1;
	int tmp[]=new int[A.length];
	int indexTmp=start;
	
	while (indexTmp<=stop) {
	    if (index1<=mid && (index2>stop || A[index1]<=A[index2])) {
	    	tmp[indexTmp]=A[index1];
	    	index1++;
	    }
	    else {
	    	if (index2<=stop && (index1>mid || A[index2]<=A[index1])) {
	    		tmp[indexTmp]=A[index2];
	    		index2++;
	    	}
	    }
	    indexTmp++;
	}
	for (int i=start;i<=stop;i++) A[i]=tmp[i];
    }
    
    public static void main (String args[]) throws Exception {
	
	// just for testing purposes
	int myArray[] = {4,3,1,5};
	mergeSort(myArray);
	System.out.println("Sorted array is:");
	for (int i=0;i<myArray.length;i++) {
	    System.out.print(myArray[i]+" ");
	}
    }
}

	