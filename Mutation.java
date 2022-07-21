/**
 * This represents the type of the mutation for the Actors.
 * This was referenced from:
 * Alex Lee, Java Enum- Make Your Own Enum, https://www.youtube.com/watch?v=LYKHxwQ0QH8
 * Accessed on 2022.02.25
 *
 * @author Lavish K. Kumar 
 * @version 2022.03.02
 */
public enum Mutation
{
    /**
     * Affects the breeding probability of the Actor.
     */
    BREEDING_PROBABILITY,

    /**
     * Affects the breeding age of the Actor.
     */
    BREEDING_AGE,

    /**
     * Affects the max litter size of the Actor.
     */
    MAX_LITTER_SIZE;
}