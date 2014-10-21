 /*--------------------------------------------------------------------------
 * Mohammad Khan
 * Perceptron for CI 
 * due 10/8/14
 * 
 * Tester for the PerceptroCI class. Takes input from text file. 
 *------------------------------------------------------------------------*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class PerceptronCITest
{
	public static void main(String[] args) throws FileNotFoundException, IOException 
	{
			//Obtains entry and desired data from this file, replace with generic text file 
		    String filename = "/Users/Owais/Desktop/NeuronMK/Perceptron/input2.txt"; 
		 
		    Scanner read= new Scanner(System.in);
		    System.out.println("How many test sets?");
		    int a= read.nextInt();
		    System.out.println("What is the length of the inputs?");
		    int b= read.nextInt();
		    System.out.println("What is the length of the outputs?");
		    int c= read.nextInt();
		    System.out.println("How many cycles do you want to run? ");
			int r= read.nextInt();
		    read.close();
		    
		    int[][] inp=null;
		    try
		    {
		    FileReader readConnectionToFile = new FileReader(filename);
		    BufferedReader reads = new BufferedReader(readConnectionToFile);
		    Scanner scan = new Scanner(reads);

		    inp = new int[a][(b+c)];
		    int counter = 0;
		    try
		    {
		    	//scans into 2D array, each of the inputs and desired output from text file
		        while(scan.hasNext() && counter < a)
		        {
		            for(int i = 0; i < a; i++)
		            {
		                counter = counter + 1;
		                for(int m = 0; m < (b+c); m++)
		                {
		                    inp[i][m] = scan.nextInt();
		                }
		            }
		        }
			    int z=0;
			    for(int i = 0; i < a; i++)
			    {
			       System.out.print("Set " + (i + 1) + " is: " );
			       z=0;
			       while(z<(b+c))
			       {
			          System.out.print(inp[i][z] + " ");
			          z++;
			       }
			       System.out.println();
			    }

		    } catch(InputMismatchException e)
		    {
		        System.out.println("Error converting number");
		    }
		    scan.close();
		    reads.close();
		    } catch (FileNotFoundException e)
		    {
		        System.out.println("File not found" + filename);
		    } catch (IOException e)
		    {
		        System.out.println("IO-Error open/close of file" + filename);
		    }
		    
		    PerceptronCI per= new PerceptronCI();
		    per.sizeofStrings(b);
		    per.setWeight();
	
			int s=0;//iterations to r
			int y=0;//track cycles
			while(s<r)
			{
				y++;
				System.out.println();
				System.out.println();
				System.out.println(" Cycle: " + y);

				int e=0; 
				while(e<a)//goes line by line from user inputs
				{
					per.input(-1.00);
					
					for(int i=0;i<b;i++)
					{
						per.input((double)inp[e][i]);
					}
					
					System.out.println();
					System.out.println("inputs");
					
					per.printIn();
					
					System.out.print("							Desired Output: " );
					for(int g=b;g<(b+c);g++)
					{
						per.setDesiredOutput((double)inp[e][g]);
						System.out.print("[" +per.getDesiredOutput()+"]");
					}
					
					System.out.print("	Actual Output:  [");
					per.calcOutput();
					System.out.println(per.getOutput()+"]");
					System.out.println("	Error:   "   + per.error());
					per.updateWeight();
					
					e++;
				}
				s++;
			}
		}    			
	}
		




