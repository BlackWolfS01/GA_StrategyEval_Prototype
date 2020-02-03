package sample;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Population {

     private Individual[] population;
     private double populationFitness;

     public Population(int _PopulationSize){
          this.population = new Individual[_PopulationSize];
     }

     public Population(int _PopulationSize, int geneLength){
          this.population = new Individual[_PopulationSize];

          for(int indCount = 0; indCount < _PopulationSize; indCount++){
               Individual individual = new Individual(geneLength);
               this.population[indCount] = individual;
          }
     }

     public Individual getFittest(int offset){
          GeneArraySort geneArraySort = new GeneArraySort ();
          Arrays.sort ( this.population , new Comparator< Individual > ( ) {
               @Override
               public int compare ( Individual o1 , Individual o2 ) {
                    return Double.compare ( o2.getFitness ( ) , o1.getFitness ( ) );
               }
          } );
          return this.population[offset];
     }

     public Individual[] getPopulation(){
          return this.population;
     }

     public int size(){
          return this.population.length;
     }

     public void setPopulationFitness(double fitness){
          this.populationFitness = fitness;
     }

     public double getPopulationFitness(){
          return this.populationFitness;
     }

     public void setIndividual(int offset, Individual individual){
          this.population[offset] = individual;
     }

     public Individual getIndividual(int offset){
          return this.population[offset];
     }
     public void shuffle(){
          Random random = new Random();
          for(int i = population.length - 1; i > 0; i--){
               int index = random.nextInt(i + 1);
               Individual a = population[index];
               population[index] = population[i];
               population[i] = a;
          }
     }
}
