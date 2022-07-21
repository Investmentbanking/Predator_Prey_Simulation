import java.util.ArrayList;
import java.util.List;

/**
 * A simple model of Algae.
 * Algae can grow, support animal ecosystems and breed.
 *
 * @author David J. Barnes, Michael Kölling and Lavish K. Kumar 
 * @version 2022.03.02
 */
public class Algae extends Plant
{
    // The defined movement restrictions.
    private static final MovementArea movementArea = MovementArea.WATER;

    // The active Time of the day for the Algae.
    private static final Time activeTime = Time.DAY;

    // The gender of the Algae.
    private static final Gender gender = Gender.ASEXUAL;

    // The breeding age of the Algae.
    private static final int breedingAge = 10;

    // The maximum age of the Algae.
    private static final int maxAge = 47;

    // The breeding probability of the Algae.
    private static final double breedingProbability = 0.02;

    // The max litter size of the Algae.
    private static final int maxLitterSize = 3;

    // The amount of food that can be provided to actors from this plant
    private static int nutritionalValue = 20;

    /**
     * Constructor for the Algae object.
     * The Algae object can have an age that is randomly defined or set at 0.
     *
     * @param field     The field, in which the Plant is placed in.
     * @param location  The location of the Plant in the field.
     * @param randomAge Whether the age of the Plant will be defined randomly.
     */
    public Algae(Field field, Location location, boolean randomAge)
    {
        super(field, location, randomAge, gender, nutritionalValue, maxAge, breedingAge, breedingProbability, maxLitterSize);
    }

    /**
     * Retrieves the Bounds of the Algae.
     *
     * @return The bounds of the Algae.
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
            // Checks if it is currently the active hours of the Algae.

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
        return new Algae(field, location, false);
    }
}