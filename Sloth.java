import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A simple model of a Sloth.
 * Sloths age, move, eat Trees (leaves), and die.
 *
 * @author David J. Barnes, Michael Kölling and Lavish K. Kumar
 * @version 2022.03.02
 */
public class Sloth extends Animal implements Prey
{
    // Characteristics shared by all Sloths (class variables).

    // The age at which a Sloth can start to breed.
    private static final int breedingAge = 2;

    // The maximum number of births.
    private static final int maxLitterSize = 8;

    // The movement area of the Sloth.
    private static final MovementArea movementArea = MovementArea.GROUND;

    // The chances of a female Sloth being born.
    private static final double femaleChances = 0.8;

    // The age to which a Sloth can live.
    private static final int maxAge = 130;

    // The likelihood of a Sloth breeding.
    private static final double breedingProbability = 0.4;

    // The food value of a Tree (leaves). In effect, this is the
    // number of steps a Sloth can go before it has to eat again.
    private static final int foodValue = 9;

    // The search distance in the field (for a mate).
    private static final int noiseValue = 2;

    // The Sloth's active hours, represented as an Enum Time.
    private static final Time activeTime = Time.NIGHT;

    // The prey that the Sloth eats.
    private final Class[] preysOn = {Tree.class};

    /**
     * Constructor for the Sloth object.
     * The Sloth object can have an age that is randomly defined or set at 0.
     *
     * @param randomAge Whether the age of the Animal will be defined randomly.
     * @param field     The field, in which the Animal is placed in.
     * @param location  The location of the Animal in the field.
     */
    public Sloth(Field field, Location location, boolean randomAge)
    {
        super(field, location, randomAge, femaleChances, foodValue, maxAge, breedingAge, breedingProbability, maxLitterSize);
    }

    /**
     * Retrieves the Bounds of the Sloth.
     *
     * @return The bounds of the Sloth.
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
            // Checks if the current time matches the active hours of the sloth.
            incrementAge();
            incrementHunger();
            if (isAlive()) {
                mate(newActors, this.getClass(), getBounds(), noiseValue);
                // Move towards a source of food if found.
                findFood();

                // No food found - try to move to a free location.
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
        //sleep
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
        if (it.hasNext()) {
            // Eat the first instance of food.
            eat(field, it.next());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Actor makeYoung(Field field, Location location)
    {
        return new Sloth(field, location, false);
    }
}