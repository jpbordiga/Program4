// JEFF BORDIGA
// PROGRAM04
// 10/9/17

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
 
public class MultiplyMain {

	private static final String OUTPUT = "output.txt"; // output file
	
	public static void main(String[] args) {

		ArrayList<Byte> arr1 = new ArrayList<>();
		ArrayList<Byte> arr2 = new ArrayList<>();
	
		int inputSize = 0;
		
		Scanner inFile = null;
		
		String[] files = new String[9]; // list of input files is used for whole output file
		
		files[0] = "p4_4.txt";
		files[1] = "p4_8.txt";
		files[2] = "p4_16.txt";
		files[3] = "p4_32.txt";
		files[4] = "p4_64.txt";
		files[5] = "p4_128.txt";
		files[6] = "p4_256.txt";
		files[7] = "p4_512.txt";
		files[8] = "p4_1024.txt";	
		
		PrintWriter outputStream = null;
		
		try{
		
			outputStream = new PrintWriter(new FileOutputStream(OUTPUT));

			int iter = 50;
			
			for(int a = 0; a < files.length; a++) { // iterating through each input file for each trial
				
				System.out.println(iter);
				
				try{
					
					inFile = new Scanner(new FileInputStream(files[a])); //
					inputSize = inFile.nextInt();
					arr1 = createByteInts(inFile, inputSize);
					arr2 = createByteInts(inFile, inputSize);
					
				} catch(FileNotFoundException e){
					System.out.println("ERROR: unable to open file " + files[a]);
					System.exit(0);
				}
				
				// Multiply (Brute Force)
						
				Multiply m = new Multiply();
				ArrayList<Byte> result1 = new ArrayList<>();
				long sum1 = 0; // starting first timer
				CpuTimer timer1 = new CpuTimer();
						
				for (long i = 0; i < 100_000_000; ++i){
					sum1 += i;
				}
						
				for(int i = 0; i < iter; i++){ //
					result1 = m.multiply(arr1, arr2, inputSize);
				}
					
				System.out.println(resultToString(result1));
				
				float totalTime1 = (float) timer1.getElapsedCpuTime();  // computing timing values
				float avgTime1 = totalTime1 / iter;
				
				outputStream.println(inputSize + ", M, " + avgTime1 + ", " + resultToString(result1) + "\n"); 
						
				// MultiplyRecursive
						
				MultiplyRecursive mR = new MultiplyRecursive();
				ArrayList<Byte> result2 = new ArrayList<>();
				long sum2 = 0; // starting second timer
				CpuTimer timer2 = new CpuTimer();
						
				for (long i = 0; i < 100_000_000; ++i){
					sum2 += i;
				}
						
				for(int i = 0; i < iter; i++){ //
					result2 = mR.multiplyRecursive(arr1, arr2);
				}
				
				System.out.println(resultToString(result2));
				
				float totalTime2 = (float) timer2.getElapsedCpuTime();  // computing timing values
				float avgTime2 = totalTime2 / iter;
				
				outputStream.println(inputSize + ", R, " + avgTime2 + ", " + resultToString(result2) + "\n");
				
				System.out.println("Brute Force Multiplication: " + totalTime1); // included for the sake of knowing the total times
				System.out.println("Divide and Conquer Multiplication: " + totalTime2);
				System.out.println();
				
				//iter = iter * 2;
				
			}
			
			System.out.println("Time trial session complete.");

			outputStream.close();
			
		} catch(FileNotFoundException e){
			System.out.println("ERROR: unable to open file " + OUTPUT);
			System.exit(0);
		}

	}

	public static ArrayList<Byte> createByteInts(Scanner iF, int n){ // creates input from least significant bytes to most significant bytes
	
		int[] inputArr = new int[n];
		ArrayList<Byte> outputArr = new ArrayList<>(n);
		
		for(int i = 0; i < n; i++) {
			inputArr[i] = iF.nextInt();
		}
		
		for(int i = (n - 1); i >= 0; i--) {
			outputArr.add((byte) inputArr[i]);
		}
		
		return outputArr;
			
	}
	
	public static String resultToString(ArrayList<Byte> arr) { // returns a String of the resulting ArrayList
		
		String result = Integer.toString(arr.get(arr.size() - 1) & 0xFF);
		
		for(int i = arr.size() - 2; i >= 0; i--) {
			result = result + " " + (arr.get(i) & 0xFF);
		}
		
		return result;
		
	}
	
}
