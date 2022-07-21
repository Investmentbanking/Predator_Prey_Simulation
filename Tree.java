import java.util.ArrayList;
import java.util.List;

/**
 * A simple model of Tree.
 * A Tree can grow, support animal ecosystems and breed.
 *
 * @author David J. Barnes, Michael Kölling and Lavish K. Kumar
 * @version 2022.03.02
 */
public class Tree extends Plant
{
    // The defined movement restrictions.
    private static final MovementArea movementArea = MovementArea.GROUND;

    // The active Time of the day for the Tree.
    private static final Time activeTime = Time.NIGHT;

    // The gender of the Tree.
    private static final Gender gender = Gender.ASEXUAL;

    // The breeding age of the Tree.
    private static final int breedingAge = 8;

    // The maximum age of the Tree.
    private static final int maxAge = 19;

    // The breeding probability of the Tree.
    private static final double breedingProbability = 0.055;

    // The max litter size of the Tree.
    private static final int maxLitterSize = 3;

    // The amount of food that can be provided to actors from this plant
    private static int nutritionalValue = 1000;

    /**
     * Constructor for the Tree object.
     * The Tree object can have an age that is randomly defined or set at 0.
     *
     * @param field     The field, in which the Plant is placed in.
     * @param location  The location of the Plant in the field.
     * @param randomAge Whether the age of the Plant will be defined randomly.
     */
    public Tree(Field field, Location location, boolean randomAge)
    {
        super(field, location, randomAge, gender, nutritionalValue, maxAge, breedingAge, breedingProbability, maxLitterSize);
    }

    /**
     * Retrieves the Bounds of the Tree.
     *
     * @return The bounds of the Tree.
     */
    public static ArrayList<Bound> getBounds()
    {
        return movementArea.getBounds();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void act(List<Actor> newActors)
    {
        if (TimeOfDay.getInstance().getTime() == activeTime) {
            // Checks if it is currently the active hours of the Tree.

            incrementAge();

            if (isAlive()) {
                giveBirth(newActors, getBounds());
            }
        } else {
            nutritionalValue++;
            // Increment its food value.
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Actor makeYoung(Field field, Location location)
    {
        return new Tree(field, location, false);
    }
}