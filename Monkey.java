import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A simple model of a Monkey.
 * Monkeys age, move, eat Trees (bananas), and die.
 *
 * @author David J. Barnes, Michael Kölling and Lavish K. Kumar
 * @version 2022.03.02
 */
public class Monkey extends Animal implements Prey
{
    // Characteristics shared by all Monkeys (class variables).

    // The age at which a Monkey can start to breed.
    private static final int breedingAge = 7;

    // The maximum number of births.
    private static final int maxLitterSize = 9;

    // The movement area of the Monkey.
    private static final MovementArea movementArea = MovementArea.GROUND;

    // The max age to which a Monkey can live.
    private static final int maxAge = 150;

    // The likelihood of a Monkey breeding.
    private static final double breedingProbability = 0.9;

    // The food value of a Tree (Banana). In effect, this is the
    // number of steps a Monkey can go before it has to eat again.
    private static final int foodValue = 8;

    // The search distance in the field (for a mate).
    private static final int noiseValue = 4;

    // The Monkey's active hours, represented as an Enum Time.
    private static final Time activeTime = Time.DAY;

    // The chances of a female Monkey being born.
    private static final double femaleChances = 0.6;

    // The prey that the Monkey eats.
    private final Class[] preysOn = {Tree.class};

    /**
     * Constructor for the Monkey object.
     * The Monkey object can have an age that is randomly defined or set at 0.
     *
     * @param randomAge Whether the age of the Animal will be defined randomly.
     * @param field     The field, in which the Animal is placed in.
     * @param location  The location of the Animal in the field.
     */
    public Monkey(Field field, Location location, boolean randomAge)
    {
        super(field, location, randomAge, femaleChances, foodValue, maxAge, breedingAge, breedingProbability, maxLitterSize);
    }

    /**
     * Retrieves the Bounds of the Monkey.
     *
     * @return The bounds of the Monkey.
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
            // Checks if this occurs during the active hours of the Monkey.
            incrementAge();
            incrementHunger();
            if (isAlive()) {
                // Find a mate.
                mate(newActors, this.getClass(), getBounds(), noiseValue);

                // Find a source of food.
                findFood();

                // Move to a free location.
                Location newLocation = getField().freeAdjacentLocation(getLocation(), movementArea.getBounds(), 1);

                // See if it was possible to move.
                if (newLocation != null) {
                    setLocation(newLocation);
                } else {
                    // Overcrowding.
                    setDamage();
                }
            }
        }
        // Else sleep
    }

    /**
     * Look for prey adjacent to the current location.
     * Only the first prey is eaten.
     */
    public void findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation(), movementArea.getBounds(), preysOn, 1);
        Iterator<Location> it = adjacent.iterator();
        // Find Prey.
        if (it.hasNext()) {
            eat(field, it.next());
            // Eat the first instance of prey.
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Actor makeYoung(Field field, Location location)
    {
        return new Monkey(field, location, false);
    }
}