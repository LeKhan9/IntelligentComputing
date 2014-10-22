 /* Mohammad Khan
 * Neural Network MLP-Tester, October 2014
 * Creates a multi-layered network and learn through backpropagation
 */

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



public class MLPCI
{
	public static void main(String[] args) throws FileNotFoundException, IOException 
	{
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
		    
		    double[][] inp=null;
		    try
		    {
		    FileReader readConnectionToFile = new FileReader(filename);
		    BufferedReader reads = new BufferedReader(readConnectionToFile);
		    Scanner scan = new Scanner(reads);

		    inp = new double[a][(b+c)];
		    int counter = 0;
		    try
		    {
		        while(scan.hasNext() && counter < a)
		        {
		            for(int i = 0; i < a; i++)
		            {
		                counter = counter + 1;
		                for(int m = 0; m < (b+c); m++)
		                {
		                    inp[i][m] = scan.nextDouble();
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
		    
		    //creates the output perceptron, to have 3 inputs (from hidden nodes)
		    PerceptronCI per= new PerceptronCI();
			per.sizeofStrings(3);
			
			//creates the 3 hidden nodes
			PerceptronCI hid1= new PerceptronCI();
			PerceptronCI hid2= new PerceptronCI();
			PerceptronCI hid3= new PerceptronCI();
			
			//gives them a set amount of inputs, 5 here 
			hid1.sizeofStrings(b);
			hid2.sizeofStrings(b);
			hid3.sizeofStrings(b);
			
			
			hid1.setWeight();
			hid2.setWeight();
			hid3.setWeight();
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
					//inputs threshold into each node
					hid1.input(-1.0);
					hid2.input(-1.0);
					hid3.input(-1.0);
					per.input(-1.00);
					
					//inputs the rest of the 5 inputs into each hidden layer node
					for(int i=0;i<b;i++)
					{
						hid1.input((double)inp[e][i]);
						hid2.input((double)inp[e][i]);
						hid3.input((double)inp[e][i]);
					}
					System.out.println();
					System.out.println("inputs");
					hid1.printIn();
					
					//calculates outputs at level J (hidden layer)
					hid1.calcOutput();
					hid2.calcOutput();
					hid3.calcOutput();
					
					//inputs the output of each hidden layer node into the output node
					per.input(hid1.getOutput());
					per.input(hid2.getOutput());
					per.input(hid3.getOutput());
					per.calcOutput();
					
					//setting the desired output and showing it
					System.out.print("							Desired Output: " );
					for(int g=b;g<(b+c);g++)
					{
						per.setDesiredOutput((double)inp[e][g]);
						System.out.print("[" +per.getDesiredOutput()+"]");
					}
					
					//calculating error at output node and showing it
					System.out.println();
					System.out.println("output"+":	"+ per.getOutput());
					System.out.println("error: 			             " + Math.abs(per.error()));	
					
					double outputK=per.getOutput();
					
					//calculates the delta for the output node, and uses this to update weights at that level 
					double deltaK=outputK*(1-outputK)*per.error();
					per.updateWeightOut(deltaK);
					
					//calculates delta for each node
					double deltaJ1= hid1.getOutput()*(1-hid1.getOutput())*deltaK*per.getWeight(1);
				    double deltaJ2= hid2.getOutput()*(1-hid2.getOutput())*deltaK*per.getWeight(2);
				    double deltaJ3= hid3.getOutput()*(1-hid3.getOutput())*deltaK*per.getWeight(3);
					
				    //updates the weights of each node
				    hid1.updateWeightHid(deltaJ1);
					hid2.updateWeightHid(deltaJ2);
					hid2.updateWeightHid(deltaJ3);					
					e++;
				}
				s++;
			}
			System.out.println("Final Weights :" );
			System.out.println("Hidden Neuron 1: ");
			hid1.printWeight();
			System.out.println("Hidden Neuron 2: ");
			hid2.printWeight();
			System.out.println("Hidden Neuron 3: ");
			hid3.printWeight();
			System.out.println("Output Neuron: ");
			per.printWeight();
		}    			
	}
		




