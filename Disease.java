import java.util.Random;

/**
 * This class represents the Diseases for both Plant and Animal Actors.
 * It affects actors by either selectively terminating them or it mutates to affect
 * certain factors such as breeding age, breeding probability or max litter size in a destructive manner.
 *
 * @author Lavish K. Kumar 
 * @version 2022.03.02
 */
public class Disease
{
    // The strength of the disease.
    private final double strengthOfDisease;

    // The chances of the disease spreading.
    private final double spreadProbability;

    // The chances of the disease mutating.
    private final double mutationProbability;

    // A randomizer, that uses a shared seed to control the randomisation of the simulation.
    private final Random rand = Randomizer.getRandom();

    // The type of the mutation.
    private final Mutation mutationType;

    // The actor that is affected by the disease.
    private Actor actor;

    // Whether the disease has mutated.
    private boolean hasMutated;

    // The actor who is affected by the disease, for example "Plants".
    private final AffectedActor affectedActor;

    /**
     * The constructor for the disease object.
     *
     * @param damage The strength of the disease.
     * @param spread The chances of the disease spreading.
     * @param mutation The chances of the disease mutating.
     * @param mutationType The type of mutation, the disease undergoes.
     * @param affectedActor The actors that are affected by the disease.
     */
    public Disease(Strength damage, Strength spread, Strength mutation, Mutation mutationType, AffectedActor affectedActor)
    {
        strengthOfDisease = damage.getStrength();
        spreadProbability = spread.getStrength();
        mutationProbability = mutation.getStrength();

        hasMutated = false;
        this.mutationType = mutationType;
        this.affectedActor = affectedActor;
    }

    /**
     * The affect of the disease on the actor.
     * This involves terminating the actor or possibly mutating.
     */
    public void affect()
    {
        assert actor != null;
        if (rand.nextDouble() <= strengthOfDisease) {
            actor.setDead();
        } else if (rand.nextDouble() <= mutationProbability) {
            hasMutated = true;
        }
    }

    /**
     * Whether the disease has mutated.
     *
     * @return Whether the disease has mutated.
     */
    public boolean hasMutated()
    {
        return hasMutated;
    }

    /**
     * The type of the mutation.
     *
     * @return The type of the mutation.
     */
    public Mutation getMutationType()
    {
        return mutationType;
    }

    /**
     * Whether this disease affects the plants or the animals.
     *
     * @return An enum representing whether the disease affects the plants or animals.
     */
    public AffectedActor getAffectedActor()
    {
        return affectedActor;
    }

    /**
     * Retrieves the actor, that is affected by the disease.
     *
     * @return The actor that is affected by the disease.
     */
    public Actor getActor()
    {
        return actor;
    }

    /**
     * Sets the actor that will be affected by the disease.
     *
     * @param actor The actor that will be affected by the disease.
     */
    public void setActor(Actor actor)
    {
        this.actor = actor;
    }

    /**
     * Retrieves the spread probability of the disease.
     *
     * @return The spread probability of the disease.
     */
    public double getSpreadProbability()
    {
        return spreadProbability;
    }

    /**
     * Returns the new breeding probability of the actor, given that the disease has mutated.
     * Reduces breeding probability of the Actor, making it harder to breed.
     *
     * @return The new breeding probability of the actor.
     */
    public double getNewBreedingProbability()
    {
        assert actor != null;
        double currentBreedingProbability = actor.getBreedingProbability();
        double newBreedingProbability = currentBreedingProbability * (1 - strengthOfDisease);
        return newBreedingProbability;
    }

    /**
     * Returns the new max litter size of the Actor, given that the disease has mutated.
     * Reduces the max number of offspring.
     *
     * @return The new max litter size of the Actor.
     */
    public int getNewMaxLitterSize()
    {
        assert actor != null;
        int currentLitterSize = actor.getLitterSize();
        if (currentLitterSize <= 1) {
            return currentLitterSize;
        }
        return currentLitterSize - 1;
    }

    /**
     * Returns the new max breeding age of the Actor, given that the disease has mutated.
     * Makes it harder for the actor to breed.
     *
     * @return The new max breeding age of the Actor
     */
    public int getNewBreedingAge()
    {
        assert actor != null;
        int currentBreedingAge = actor.getBreedingAge();
        return currentBreedingAge + 2;
    }
}