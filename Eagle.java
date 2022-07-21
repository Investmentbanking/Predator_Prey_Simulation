import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * A simple model of a Eagle
 * Eagles age, move, eat Sloths and Monkeys, and die.
 *
 * @author David J. Barnes, Michael Kölling and Lavish K. Kumar 
 * @version 2022.03.02
 */
public class Eagle extends Animal implements Predator
{
    // Characteristics shared by all Eagles (class variables).

    // The age at which an Eagle can start to breed.
    private static final int breedingAge = 20;

    // The maximum number of births.
    private static final int maxLitterSize = 5;

    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    // The prey that the Eagle eats.
    private static final Class[] preysOn = {Sloth.class, Monkey.class};

    // The chances of a female Eagle being born.
    private static final double femaleChances = 0.7;

    // The movement area of the Eagle.
    private static final MovementArea movementArea = MovementArea.AIR;

    // The attack probability of an Eagle.
    private static final double attackProbability = 0.08;

    // The max age to which an Eagle can live.
    private static final int maxAge = 144;

    // The likelihood of an Eagle breeding.
    private static final double breedingProbability = 0.49;

    // The food value of prey. In effect, this is the
    // number of steps an Eagle can go before it has to eat again.
    private static final int foodValue = 15;

    // The search distance in the field (for a mate).
    private static final int noiseValue = 2;

    // The Eagle's active hours, represented as an Enum Time.
    private static final Time activeTime = Time.DAY;

    /**
     * Constructor for the Eagle object.
     * The Eagle object can have an age that is randomly defined or set at 0.
     *
     * @param randomAge Whether the age of the Animal will be defined randomly.
     * @param field     The field, in which the Animal is placed in.
     * @param location  The location of the Animal in the field.
     */
    public Eagle(Field field, Location location, boolean randomAge)
    {
        super(field, location, randomAge, femaleChances, foodValue, maxAge, breedingAge, breedingProbability, maxLitterSize);
    }

    /**
     * Retrieves the Bounds of the Eagle.
     *
     * @return The bounds of the Eagle.
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
                // Move towards food.
                Location newLocation = findPrey();

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

        // Get a list of locations of the prey.
        List<Location> adjacent = field.adjacentLocations(getLocation(), movementArea.getBounds(), preysOn, 1);
        Iterator<Location> it = adjacent.iterator();

        while (it.hasNext()) {
            Location where = it.next();
            if (rand.nextDouble() <= getAttackProbability()) {
                // If the attack probability is high enough, eat the animal.
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
        return new Eagle(field, location, false);
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