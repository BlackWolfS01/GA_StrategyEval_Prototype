package sample;

public class GeneAlgorithmConsoleTest {
     public static void main(String[] args){
          GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm ( 100, 0.05, 0.85, 2, 20);

          Population population = geneticAlgorithm.initPopulation ( 120 );

          geneticAlgorithm.evaluatePopulation ( population );

          int generation = 1;
          int maxGenerations = 100;
          System.out.println("Running Gene Test");
          while(true) {
               System.out.println("Best Solution " + population.getFittest(0).toString());
               Individual fittest = population.getFittest(0);

               System.out.println(
                         "G" + generation + " Best solution (" + fittest.getFitness() + "): " + fittest.toString());


               population = geneticAlgorithm.crossoverPopulation ( population );

               population = geneticAlgorithm.mutatePopulation ( population );

               geneticAlgorithm.evaluatePopulation ( population );

               if(geneticAlgorithm.checkTerminationCondition ( generation, maxGenerations )){
                    break;
               }
               generation++;
          }

          System.out.println("Found Solution in " + generation + " generations");
          System.out.println("Best Solution " + population.getFittest(0).toString());
     }
}
