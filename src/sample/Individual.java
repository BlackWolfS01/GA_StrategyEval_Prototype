package sample;
import java.util.Arrays;
import java.util.Random;

public class Individual {
     private int geneLength;
     private double fitness;
     private int[] genes;

     public Individual(int[] genes){
          this.genes = genes;
     }

     public Individual(int geneLength){
          Random random = new Random();

          this.geneLength = geneLength;
          this.genes = new int[this.geneLength];

          for(int i = 0; i < genes.length; i++){
               genes[i] = Math.abs(random.nextInt() % 2);
          }
          fitness = 0;
     }

     public void calcFitness(){
          fitness = 0;
          for(Integer gene : genes){
               if(gene == 1){
                    fitness += 1;
               }
          }
     }

     public boolean containsGene(int gene){
          for(int i = 0; i < this.genes.length; i++){
               if(this.genes[i] == gene){
                    return true;
               }
          }
          return false;
     }

     public int getGeneLength(){
          return this.geneLength;
     }

     public void setGeneLength(int _GeneLength){
          this.geneLength = _GeneLength;
     }

     public double getFitness(){
          return this.fitness;
     }

     public void setFitness(double fitness) { this.fitness = fitness; }

     public int[] getGenes(){ return this.genes; }

     public void setGenes(int[] genes){ this.genes = genes; }

     public int getGeneAt(int offset){ return this.genes[offset]; }

     public void setGeneAt(int offset, int gene){ this.genes[offset] = gene;}

     public String toString(){
          String newOutput = "";
          for(int gene = 0; gene < this.genes.length; gene++){
               newOutput += this.genes[gene];
          }
          return newOutput;
     }
}
