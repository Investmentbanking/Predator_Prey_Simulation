import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A simple model of a Fish.
 * Fishes age, move, eat Algae, and die.
 *
 * @author David J. Barnes, Michael Kölling and Lavish K. Kumar
 * @version 2022.03.02
 */
public class Fish extends Animal implements Prey
{
    // Characteristics shared by all Fishes (class variables).

    // The chances of a female Fish being born.
    private static final double femaleChances = 0.5;

    // The age at which a Fish can start to breed.
    private static final int breedingAge = 1;

    // The maximum number of births.
    private static final int maxLitterSize = 10;

    // The movement area of the Fish.
    private static final MovementArea movementArea = MovementArea.WATER;

    // The likelihood of a Fish breeding.
    private static final double breedingProbability = 0.95;

    // The food value of Algae. In effect, this is the
    // number of steps a Fish can go before it has to eat again.
    private static final int foodValue = 40;

    // The Fish's active hours, represented as an Enum Time.
    private static final Time activeTime = Time.NIGHT;

    // The search distance in the field (for a mate).
    private static final int noiseValue = 2;

    // The prey that the Fish eats.
    private static final Class[] preysOn = {Algae.class};

    // The max age to which a Fish can live.
    private static final int maxAge = 100;

    /**
     * Constructor for the Fish object.
     * The Fish object can have an age that is randomly defined or set at 0.
     *
     * @param randomAge Whether the age of the Animal will be defined randomly.
     * @param field     The field, in which the Animal is placed in.
     * @param location  The location of the Animal in the field.
     */
    public Fish(Field field, Location location, boolean randomAge)
    {
        super(field, location, randomAge, femaleChances, foodValue, maxAge, breedingAge, breedingProbability, maxLitterSize);
    }

    /**
     * Retrieves the Bounds of the Fish.
     *
     * @return The bounds of the Fish.
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
            // Checks if the current time matches the active hours of the Fish.

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
        // Find Prey.
        Iterator<Location> it = adjacent.iterator();
        if (it.hasNext()) {
            eat(field, it.next());
            // Eat first instance of prey.
        }
    }

    /**
     * {@inheritDoc
     */
    @Override
    public Actor makeYoung(Field field, Location location)
    {
        return new Fish(field, location, false);
    }
}