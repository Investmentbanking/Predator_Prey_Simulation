import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * A simple model of a Jaguar.
 * Jaguars age, move, eat Fish and Sloths, and die.
 *
 * @author David J. Barnes, Michael Kölling and Lavish K. Kumar
 * @version 2022.02.26
 */
public class Jaguar extends Animal implements Predator
{
    // Characteristics shared by all Jaguars (class variables).

    // The age at which a Jaguar can start to breed.
    private static final int breedingAge = 20;

    // The maximum number of births.
    private static final int maxLitterSize = 6;

    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    // The chances of a female Jaguar being born.
    private static final double femaleChances = 0.5;

    // The movement area of the Jaguar.
    private static final MovementArea movementArea = MovementArea.GROUND_WATER;

    // The attack probability of a Jaguar.
    private static final double attackProbability = 0.05;

    // The max age to which a Jaguar can live.
    private static final int maxAge = 160;

    // The likelihood of a Jaguar breeding.
    private static final double breedingProbability = 0.8;

    // The food value of Sloths or Fish. In effect, this is the
    // number of steps a Jaguar can go before it has to eat again.
    private static final int foodValue = 20;

    // The search distance in the field (for a mate).
    private static final int noiseValue = 3;

    // The Jaguar's active hours, represented as an Enum Time.
    private static final Time activeTime = Time.NIGHT;

    // The prey that the Jaguar eats.
    private static final Class[] preysOn = {Fish.class, Sloth.class};

    /**
     * Constructor for the Jaguar object.
     * The Jaguar object can have an age that is randomly defined or set at 0.
     *
     * @param randomAge Whether the age of the Animal will be defined randomly.
     * @param field     The field, in which the Animal is placed in.
     * @param location  The location of the Animal in the field.
     */
    public Jaguar(Field field, Location location, boolean randomAge)
    {
        super(field, location, randomAge, femaleChances, foodValue, maxAge, breedingAge, breedingProbability, maxLitterSize);
    }

    /**
     * Retrieves the Bounds of the Jaguar.
     *
     * @return The bounds of the Jaguar.
     */
    public static ArrayList<Bound> getBounds()
    {
        return movementArea.getBounds();
    }

    /**
     * {@inheritDoc}
     */
    public void act(List<Actor> newActors)
    {
        if (TimeOfDay.getInstance().getTime() == activeTime) {
            // Checks whether the animal is active at the current time.

            incrementAge();
            incrementHunger();
            // Increments age and hunger.

            if (isAlive()) {
                mate(newActors, this.getClass(), getBounds(), noiseValue);
                // Locate a mate
                Location newLocation = findPrey();
                // Move towards a source of food if available.
                if (newLocation == null) {
                    // No food found - try to move to a free location.
                    newLocation = getField().freeAdjacentLocation(getLocation(), movementArea.getBounds(), 1);
                }
                // See if it was possible to move.
                if (newLocation != null) {
                    setLocation(newLocation);

                } else {
                    // Overcrowding.
                    setDamage();
                }
            }
        }
        // Else sleep.
    }

    /**
     * Look for prey adjacent to the current location.
     * The prey is eaten dependent on the attack probability.
     *
     * @return Where food was found, or null if it wasn't.
     */
    public Location findPrey()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation(), movementArea.getBounds(), preysOn, 1);
        // Get a list of prey.
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            // If the attack probability is high enough, eat the animal.
            Location where = it.next();
            if (rand.nextDouble() <= getAttackProbability()) {
                eat(field, where);
                // Return the location.
                return where;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Actor makeYoung(Field field, Location location)
    {
        return new Jaguar(field, location, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getAttackProbability()
    {
        return attackProbability;
    }
}