 /*--------------------------------------------------------------------------
 * Mohammad Khan
 * Perceptron for CI 
 * 10/5/14 - 10/8/14
 * 
 * Takes any amount of inputs from text file, runs them through a summation
 * and generates an output by running through a hard limiter. An error is 
 * computed by taking the difference between the desired and actual outputs.
 * This error, along with a learning rate, is then used to learn weights.
 * ---Able to backpropagate with multiple hidden layer nodes.
 *------------------------------------------------------------------------*/
import java.util.Scanner;

public class PerceptronCI 
{
	int i=-1;
	int limiter;  //establishes that arrays are in order since threshold is also in array
	double add;   //variable for sigma summation
	double desiredOutput; 
	double input[];
	double weight[];
	double output;
	
	public void sizeofStrings(int x)
	{
		input= new double[x+1];
		weight= new double[x+1];
		limiter=x;
	}
	
	//always inputs threshold at 0, the rest come after
	public void input(double x)
	{
		i++;
		if(i==limiter+1)
		{
			i=0;
		}
		input[i]=x;
	}
	
	public void printIn()
	{
		for(int y=1; y<=limiter; y++)
		{
			System.out.print("["+ input[y]+"] " );
		}
	}
	
	public void calcOutput()
	{
		add=0.0;
		for(int x=0;x<=i;x++)
		{
			double a=input[x];
			double b=weight[x];
			
			double k= (a*b);
			add+=k;
		}
	}
	
	//HardLimiter  Function 
	public double getOutput()
	{
		output= 1 / (1 + Math.exp(-add));
		return output;
	}
	
	public void setDesiredOutput(double x)
	{
		desiredOutput= x;
	}
	
	public double getDesiredOutput()
	{
		return desiredOutput;
	}
	
	public void setWeight()
	{
		System.out.println("Set initial  random weight for threshold:    ");
		weight[0]=(double)(Math.random()*1);
		
		System.out.println(weight[0]);
		
		for(int u=1;u<=limiter;u++)
		{
			System.out.println("Set initial  random weight for input:    ");
			weight[u]=(double)(Math.random()*1);
			System.out.println(weight[u]);
		}
	} 

	public double error()
	{
		return (desiredOutput-output);
	}
	
	//updates weights using error from previous iteration
	public void updateWeight()
	{
			double rate=.3;
			
			for(int v=0;v<=limiter;v++)
			{
				weight[v]= weight[v]+ (rate*input[v]*error());
			}
	}
	
	public void printWeight()
	{
		System.out.println("T-weight:  " + weight[0]);
		for(int n=1;n<=limiter;n++)
		{
			System.out.println("weight" + n + ":  " + weight[n]);
		}
	}
	
	public double getWeight(int x)
	{
		return weight[x];
	}	
	
	//updates the weight of the output layer K, using its delta value(computed in the tester)
	public void updateWeightOut(double delta)
	{
		double rate=.3;
		
		for(int v=0;v<=limiter;v++)
		{
			weight[v]= weight[v]+ (rate*input[v]*delta);
		}
	}
	
	//updates the weights of the hidden layer , using the nodes' delta value(computed in the tester)
	public void updateWeightHid(double delta)
	{
		double rate=.3;
		
		for(int v=0;v<=limiter;v++)
		{
			weight[v]= weight[v]+ (rate*input[v]*delta);
		}
	}
}

