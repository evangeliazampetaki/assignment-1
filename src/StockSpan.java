import java.io.*;
import java.util.Stack;
public class StockSpan
 {
	public static void main(String[] args) throws IOException 
    {
		//Input argument from the command line which
		//Here args[1] = Filename
		String fileName=args[1];
        //Create object of FileReader
        FileReader inputFile = new FileReader(fileName);

        //Instantiate the BufferedReader Class
        BufferedReader bufferReader = new BufferedReader(inputFile);
        //I create a string array which hold all Dates
        String [] Dates ;
        //I know that the total number of records are 32098
        Dates = new String[32098];
        
        //Variable to hold the one line data
        String line;
        //This is my counter
        int i = 0;
        //I skip first line(DATE,VALUE)
        bufferReader.readLine();
        //quote(value) for every day
        float [] quote = new float [32098];
        //Read and store while not EOF(End Of File)
        while ((line = bufferReader.readLine()) != null)   {
        		//I split every line with ','
        		String [] parts = line.split(",");
        		//First part is split
        		Dates [i] = parts[0];
        		//Quotes are floats so I change string to float
        		float f = Float.parseFloat(parts[1]);
        		
        		quote[i] = f;
        		i = i+1;                     
        }	
        bufferReader.close();
    
        /*Until here i Have 2 arrays. 
         * quotes[] with stock span for every day
         * Dates[] with all dates
         */
     //args[0] is the first arguement after the program name
     //I have 3 possible inputs, so i have an "if - else if clause
     //for every one of them("-n","-s","-b")
    if(args[0].equals("-n")){
        //Simple stock span algorithm
        int [] span = new int [Dates.length];
        for (int j = 0; j<Dates.length-1;j++){
        	int k = 1;
        	boolean span_end = false;
        	while (j-k>=0 && !span_end){
        		if(quote[j-k]<=quote[j]){
        			k = k+1;
        		}else {
        			span_end = true;
        		}
        		
        	}
        	span[j] = k;
        }
        
        for (int j=0;j<Dates.length-1;j++){
        	System.out.println(Dates[j]+","+span[j]);
        	}
        
    //In case of "-s" I implement stack algorithm    
    }else if(args[0].equals("-s")){
    		int [] span = new int [Dates.length];
    	 	Stack<Integer> s = new Stack<Integer>();
    	    s.push(0);   	   
    	    span[0] = 1;
    	    for(int j =1;j<Dates.length-1;j++){
    	    	while(!s.empty() && quote[s.peek()]<=quote[j]){
    	    		s.pop();
    	    	}
    	    	if (s.empty()){
    	    		span[j] = j+1;
    	    	}else{
    	    		span[j] = j - s.peek();
    	    	}
    	    	s.push(j);
    	    }	
    	    
    	for (int j=0;j<Dates.length-1;j++){
    	System.out.println(Dates[j]+","+span[j]);
    	}
    	
    }else if(args[0].equals("-b")){
    	//Before start 100*naive process I store system time in Milliseconds
    	long startTimeNaive = System.currentTimeMillis();
    	int [] span = new int [Dates.length];
    	for(int times=0;times<100;times++){
    		for (int j = 0; j<Dates.length-1;j++){
            	int k = 1;
            	boolean span_end = false;
            	while (j-k>=0 && !span_end){
            		if(quote[j-k]<=quote[j]){
            			k = k+1;
            		}else {
            			span_end = true;
            		}
            		
            	}
            	span[j] = k;
            }
    	}
    	//After finish the process I store system time in Millis.
    	long endTimeNaive = System.currentTimeMillis();
    	//So execution time is the difference between these times
    	System.out.println("Naive implementation took: " + (endTimeNaive - startTimeNaive) + " millis");
    	//The same thing as before
    	long startTimeStack = System.currentTimeMillis();
    	Stack<Integer> s = new Stack<Integer>();
	    s.push(0);   
	    span[0] = 1;
    	for(int times=0;times<100;times++){
    		for(int j =1;j<Dates.length-1;j++){
    	    	while(!s.empty() && quote[s.peek()]<=quote[j]){
    	    		s.pop();
    	    	}
    	    	if (s.empty()){
    	    		span[j] = j+1;
    	    	}else{
    	    		span[j] = j - s.peek();
    	    	}
    	    	s.push(j);
    	    }	
    	}
    	long endTimeStack = System.currentTimeMillis();
    	System.out.println("Stack implementation took: " + (endTimeStack - startTimeStack) + " millis");
    }
    }
	}