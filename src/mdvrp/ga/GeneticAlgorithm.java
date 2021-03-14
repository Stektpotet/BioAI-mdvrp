package mdvrp.ga;

import ga.change.Mutator;
import ga.change.Recombinator;
import ga.data.Chromosome;
import ga.data.Initializer;
import ga.data.Population;
import ga.selection.ParentSelector;
import ga.selection.SurvivorSelector;

import java.util.List;

public class GeneticAlgorithm<C extends Chromosome> {

    private Initializer<Population<C>, C> initializer;
    private Recombinator<C> recombinator;
    private Mutator<C> mutator;
    private ParentSelector<C> parentSelector;
    private SurvivorSelector<Population<C>, C> survivorSelector;


    public GeneticAlgorithm(Initializer<Population<C>, C> initializer,
                            Recombinator<C> recombinator,
                            Mutator<C> mutator,
                            ParentSelector<C> parentSelector,
                            SurvivorSelector<Population<C>, C> survivorSelector) {

        this.initializer = initializer;
        this.recombinator = recombinator;
        this.mutator = mutator;
        this.parentSelector = parentSelector;
        this.survivorSelector = survivorSelector;
    }

    public C run(int populationSize, int numGenerations) {
        Population<C> pop = initializer.breed(populationSize);
        for (int i = 0; i < numGenerations; i++) {
            List<C> parents = parentSelector.select(pop);
            List<C> offspring = mutator.mutateAll(recombinator.recombine(parents));
            pop = survivorSelector.select(pop, parents, offspring);
            Chromosome currentOptimum = pop.getOptimum();
        }
        return null;
    }
}
