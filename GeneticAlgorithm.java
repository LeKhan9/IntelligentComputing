/*----------------------------------------
Matt Burns, Mohammad Khan
Genetic Algorithm
Learns chromosomes of length 64 by taking
fitness as being all 1s in a gene. 
Writes data to a file at the end of the 
program
------------------------------------------*/



import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.*;
class GeneticAlgorithm{
	

	//Generates a random population of 64 bit binary chromosomes base off of user input
	public static int[][] createPopulation(int y){
		int [][] population = new int[y][64];
		for(int i=0;i<y;i++){
			for(int j = 0; j<64;j++){
				double x = java.lang.Math.random();
				if (x<.5){
					population[i][j] = 0;
				}
				else{
					population[i][j]=1;
				}
			}
		}
		return population;
	}
	//returns the fitness value of each chromosome squared in an arraylist
	public static int[] getFitness(int[][] population, int y){
		int[] fitnessList = new int[y];

		for (int i=0;i<y;i++){
			int tempFitness = 0;
			for (int j=0;j<64;j++){
				if (population[i][j]==1){
					tempFitness++;
				}
			}
			tempFitness = tempFitness*tempFitness;
			fitnessList[i]=tempFitness;
		}
		return fitnessList;
	}
	//returns a fitness value of each chromosome in an arraylist
	public static int[] getFitness1(int[][] population, int y){
		int[] fitnessList = new int[y];

		for (int i=0;i<y;i++){
			int tempFitness = 0;
			for (int j=0;j<64;j++){
				if (population[i][j]==1){
					tempFitness++;
				}
			}
			fitnessList[i]=tempFitness;
		}
		return fitnessList;
	}
	//returns the fitness of a given chromosome
	public static int getFit(int[] chromosome){
		int tempFitness = 0;
		for (int i=0; i<64;i++){
			if(chromosome[i]==1){
				tempFitness++;
			}
		}
		tempFitness = tempFitness*tempFitness;
		return tempFitness;
	}
	//returns the highest fitness of the population
	public static int bestFit(int[][] pop, int[] fit,int genpop){
		int tempFitness = 0;
		int bestFitness = 0;

		for (int i = 0; i<genpop;i++){
			tempFitness = fit[i];
			if (tempFitness > bestFitness){
				bestFitness = tempFitness;
			}
		}
		return bestFitness;
	}
	//finds the chromosome with the highest fitness
	public static int[] bestChromosome(int[][] pop, int[] fit, int genpop,int bestfit){
		int[] bestChromosome = new int[64];
		int bestIndex = 0;
		for (int i = 0; i < genpop; i++){
			if (fit[i] == bestfit){
				bestIndex = i;
			}
		}
		for (int i = 0; i < 64; i++){
			bestChromosome[i] = pop[bestIndex][i];
		}
		return bestChromosome;
	}
	//checks to see if the population contains a chromosome of all ones 
	public static boolean isDone(int[] fit,int y){
		boolean check = false;
		for (int i = 0; i<y; i++){
			if (fit[i] == 64){
				check = true;
			}
		}
		return check;
	}
	//randomly selects a chromosome with roulleteSelection
	public static int[] roulleteSelection(int[][] pop,int[] fit, int y){
		int[] tempChromosome = new int[64];
		int totalFit = 0;
		int tempFit = 0;
		Random rand = new Random();
		for (int i = 0;i<y;i++){
			totalFit = totalFit + fit[i];
		}
		int x = rand.nextInt(totalFit);
		for (int i = 0; i<y;i++){
			tempFit = tempFit + fit[i];
			if ((x>=tempFit-fit[i])&& (x<tempFit)){
				for (int j = 0;j<64;j++){
					tempChromosome[j] = pop[i][j];
				}
			}
		}
		return (int[]) tempChromosome.clone();
	}
	//Creates two children from two parents by using crossOver combination
	public static int[][] crossOver(int[][] pop, int y,int[] fit,int[] parent1,int[] parent2,int mutation){
		Random rand = new Random();
		int x = rand.nextInt(64);
		
		int[] child1 = new int[64];
		int[] child2 = new int[64];
		int[][] children = new int[2][64];
		for (int i = 0; i < 64; i++){
			if (i <= x){
				child1[i] = parent1[i];
				child2[i] = parent2[i];
			}
			else{
				child1[i] = parent2[i];
				child2[i] = parent1[i];
			}
		}

		for (int i = 0; i <64; i++){
			int b = rand.nextInt(mutation);
			int z = rand.nextInt(mutation);
			if (b == 1){
				if (child1[i] == 1){
					child1[i] = 0;
				}
				else{
					child1[i] = 1;
				}
			}
			if (z == 1){
				if (child2[i] == 1){
					child2[i] = 0;
				}
				else{
					child2[i] = 1;
				}
			}
		}

		for (int j = 0; j<64;j++){
			children[0][j] = child1[j];
			children[1][j] = child2[j];
		}
		return children;
	}
	public static void main(String args[]) throws IOException {
		Scanner user_input = new Scanner(System.in);

		boolean check = false;
		int n = 0;
		System.out.print("How many chromosomes will the initial population contain?");
		int genpop = (int)user_input.nextInt();

		System.out.println();
		System.out.print("What will the maximum range of the mutation rate be?");
		int mutrate = (int)user_input.nextInt();

		int[][] pop = createPopulation(genpop);
		int[][] newPop = createPopulation(genpop);
		int[] fit = getFitness(pop,genpop);
		int[] newfit;
		int[] temp;
		int[] temp1;
		int[][] children;
		int[] fitness1 = getFitness1(pop,genpop);


		while((n != 10000)&&(check != true)){
			int npcount = 0;
			int bestFitness = bestFit(pop,fit,genpop);
			int[] bestChromo = bestChromosome(pop,fit,genpop,bestFitness);
			for (int i = 0; i < 64; i++){
			}

			for (int i = 0; i < genpop; i = i + 2){

				temp = roulleteSelection(pop,fit,genpop);
				temp1 = roulleteSelection(pop,fit,genpop);
				children = crossOver(pop,genpop,fit,temp,temp1,mutrate);

				for (int j = 0; j<2; j++){
					for (int k = 0; k<64; k++){
						if (npcount == 0){
							newPop[npcount][k] = bestChromo[k];
						}
						else{
							newPop[npcount][k] = children[j][k];
						}
					}
				npcount++;
				}

				if (npcount+2>genpop&& npcount < genpop){
					temp = roulleteSelection(pop,fit,genpop);
					temp1 = roulleteSelection(pop,fit,genpop);
					children = crossOver(pop,genpop,fit,temp,temp1,mutrate);
					int fit1 = getFit(temp);
					int fit2 = getFit(temp1);
					if (fit1 >= fit2){
						for (int l = 0; l < genpop;l++){
							newPop[npcount+1][l]=children[0][l];
						}
					}
					else {
						for (int m = 0; m <genpop; m++){
							newPop[npcount+1][m]=children[1][m];
						}
					}
						}
				for (int b = 0; b < genpop;b++){
					for (int v = 0; v <64; v++){
						pop[b][v]=newPop[b][v];
					}
				}
		}
		fit = getFitness1(pop,genpop);
		check = isDone(fit,genpop);
		n++;
		}
		System.out.println();
		int bestFit = bestFit(pop,fit,genpop);
		int[] bestChromo = bestChromosome(pop,fit,genpop,bestFit);

		fit = getFitness1(pop,genpop);
		for (int s = 0; s<genpop;s++){
			System.out.println(fit[s]);
		}
		File file = new File("finalPopulation.txt");
		file.delete();
		FileWriter writer = new FileWriter(file,true);
		PrintWriter output = new PrintWriter(writer);
		for (int j = 0; j<genpop;j++){
			for (int i = 0; i<64;i++){
				output.print(pop[j][i]);

			}
			output.println();
			output.println();
		}
		output.close();
		System.out.println();
		System.out.println(n);
	}
}







		
	





