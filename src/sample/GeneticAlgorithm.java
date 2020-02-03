package sample;

import java.util.Arrays;
import java.util.Random;

public class GeneticAlgorithm {
     private int populationSize;
     private double mutationRate;
     private double crossoverRate;
     private int elitismCount;

     protected int tournamentSize;

     public GeneticAlgorithm(int populationSize, double mutationRate,
                             double crossoverRate, int elitismCount){
          this.populationSize = populationSize;
          this.mutationRate = mutationRate;
          this.crossoverRate = crossoverRate;
          this.elitismCount = elitismCount;
     }

     public GeneticAlgorithm(int populationSize, double mutationRate,
                             double crossoverRate, int elitismCount, int tournamentSize){
          this.populationSize = populationSize;
          this.mutationRate = mutationRate;
          this.crossoverRate = crossoverRate;
          this.elitismCount = elitismCount;
          this.tournamentSize = tournamentSize;
     }

     public Population initPopulation(int geneLength){
          Population population = new Population(this.populationSize, geneLength);
          return population;
     }

     public double calcFitness(Individual individual){
          int correctGenes = 0;

          for(int geneIndex = 0; geneIndex < individual.getGeneLength (); geneIndex++){
               if(individual.getGeneAt(geneIndex) == 1){
                    correctGenes += 1;
               }
          }

          double fitness = (double) correctGenes /  individual.getGeneLength();
          individual.setFitness(fitness);
          return fitness;
     }

     public void evaluatePopulation(Population population){
          double populationFitness = 0;

          for(Individual ind : population.getPopulation ()){
               populationFitness += calcFitness(ind);
          }

          double averageFitness = populationFitness / population.size();

          population.setPopulationFitness ( averageFitness );
     }

     public boolean checkTerminationCondition(int generation, int maxGenerations){
          if(generation >= maxGenerations)
               return true;
          else return false;
     }

     public Individual selectParent(Population population){
          Population tournament = new Population(tournamentSize);
          population.shuffle();
          for(int i = 0; i < this.tournamentSize; i++){
               Individual tournamentIndividual = population.getIndividual(i);
               tournament.setIndividual ( i, tournamentIndividual );
          }
          return tournament.getFittest ( 0 );
    }

    public Population crossoverPopulation(Population population){
          //Instance new population
          Population newPopulation = new Population(population.size());

          for(int populationIndex = 0; populationIndex < population.size(); populationIndex++){
               Individual parentOne = population.getFittest(populationIndex);

               if(this.crossoverRate > Math.random() && populationIndex >= this.elitismCount){
                    Individual parentTwo = selectParent ( population );

                    int[] offspringChromosome = new int[parentOne.getGeneLength ()];
                    Arrays.fill(offspringChromosome, -1);
                    Individual offspring = new Individual(offspringChromosome);

                    int substringPosOne = (int)(Math.random() * parentOne.getGeneLength ());
                    int substringPosTwo = (int)(Math.random() * parentOne.getGeneLength());

                    final int startSubstring = Math.min(substringPosOne, substringPosTwo);
                    final int endSubstring = Math.max(substringPosOne, substringPosTwo);

                    for(int i = startSubstring; i < endSubstring; i++){
                         offspring.setGeneAt(i, parentOne.getGeneAt(i));
                    }

                    for(int i = 0; i < parentTwo.getGeneLength (); i++){
                         int parentTwoGene = i + endSubstring;

                         if(parentTwoGene >= parentTwo.getGeneLength()){
                              parentTwoGene -= parentTwo.getGeneLength ();
                         }

                         if(!offspring.containsGene ( parentTwo.getGeneAt(parentTwoGene) )){
                              for(int ii = 0; ii < offspring.getGeneLength(); ii++){
                                   if(offspring.getGeneAt(ii) == -1){
                                        if(parentTwo.getGeneAt(ii) !=  -1) {
                                             offspring.setGeneAt ( ii , parentTwo.getGeneAt ( ii ) );
                                             break;
                                        }
                                   }
                              }
                         }
                    }

                    newPopulation.setIndividual(populationIndex, offspring);
               } else {
                    newPopulation.setIndividual ( populationIndex, parentOne);
               }
          }
          return newPopulation;
    }

    public Population mutatePopulation(Population population){
          Population newPopulation = new Population(this.populationSize);
          for(int populationIndex = 0; populationIndex < population.size(); populationIndex++){
               Individual individual = population.getFittest(populationIndex);

               for(int geneIndex = 0; geneIndex < individual.getGeneLength(); geneIndex++){
                    if(populationIndex > this.elitismCount) {
                         if ( this.mutationRate > Math.random ( ) ) {
                              int newGenePos = (int)(Math.random() * individual.getGeneLength());

                              int geneOne = individual.getGeneAt(newGenePos);
                              int geneTwo = individual.getGeneAt(geneIndex);

                              individual.setGeneAt(geneIndex, geneOne);
                              individual.setGeneAt(newGenePos, geneTwo);
                         }
                    }
               }
               newPopulation.setIndividual ( populationIndex, individual );
          }
          return newPopulation;
    }
}