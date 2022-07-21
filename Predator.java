/**
 * An interface for Actors who are predators (Carnivores).
 *
 * @author Lavish K. Kumar
 * @version 2022.02.25
 */
public interface Predator 
{
    /**
     * The attack probability of the predator.
     *
     * @return The attack probability of the actor.
     */
    double getAttackProbability();

    /**
     * Retrieves the location of the prey, or null if it is not found.
     *
     * @return The location of the prey, or null, if not found.
     */
    Location findPrey();
}