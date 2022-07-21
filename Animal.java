import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An abstract class representing the shared characteristics of animals.
 *
 * @author David J. Barnes, Michael Kölling and Lavish K. Kumar
 * @version 2022.03.02
 */
public abstract class Animal extends Actor
{
    // Object of type Randomizer to control randomization of the simulation.
    private static final Random rand = Randomizer.getRandom();

    // The hunger level of the animal (zero hunger level equates to death)
    private int foodLevel;

    // This defines whether the animal is currently breeding.
    private boolean isCurrentlyBreeding;

    // The food value of prey. In effect, this is the
    // number of steps an Animal can go before it has to eat again.
    private final int maxFoodValue;

    /**
     * The constructor for the Animal object.
     *
     * @param field               The field, in which the Animal will be placed.
     * @param location            The location in the field of the Animal.
     * @param randomAge           Whether the animal will be assigned a random age.
     * @param femaleProbability   The chances of the animal being of the female gender.
     * @param maxFoodValue        The maximum number of steps an animal can go without eating food.
     * @param maxAge              The maximum age of the animal.
     * @param breedingAge         The age at which, the animal can start to breed.
     * @param breedingProbability The probability of producing offspring.
     * @param maxLitterSize       The maximum number that can be produced by an animal at one time.
     */
    public Animal(Field field, Location location, boolean randomAge, double femaleProbability, int maxFoodValue, int maxAge, int breedingAge, double breedingProbability, int maxLitterSize)
    {
        super(field, location, randomAge, setGender(femaleProbability), maxAge, breedingAge, breedingProbability, maxLitterSize);

        if (randomAge) {
            foodLevel = rand.nextInt(maxFoodValue);
        } else {
            foodLevel = maxFoodValue;
        }

        this.maxFoodValue = maxFoodValue;

        isCurrentlyBreeding = false;
    }

    /**
     * Sets the gender based on the female probability.
     *
     * @return The gender.
     */
    private static Gender setGender(double femaleProbability)
    {
        if (rand.nextDouble() <= femaleProbability) {
            return Gender.FEMALE;
        } else {
            return Gender.MALE;
        }
    }

    /**
     * This increments the animal's hunger.
     * This may lead to the death of the animal.
     */
    public void incrementHunger()
    {
        foodLevel--;
        if (foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * This enables animals to "eat" their prey, which can be of type Actor.
     * In consequence, sets the food level to the maximum and clears the location.
     *
     * @param field    This is the field, from which the animal is cleared.
     * @param location This is the location in the field, to retrieve the animal.
     */
    public void eat(Field field, Location location)
    {
        Actor animal = (Actor) field.getObjectAt(location);
        animal.setDamage();
        foodLevel = maxFoodValue;
    }

    /**
     * @param newActors      The list to hold the reference to the newborn animals.
     * @param interestedIn   The class the animal is interested in.
     * @param bounds         The defined area for the birth of newborn animals.
     * @param searchDistance The maximum search distance within the field to search for mates.
     */
    public void mate(List<Actor> newActors, Class interestedIn, ArrayList<Bound> bounds, int searchDistance)
    {
        Field field = getField();

        if (getGender() == Gender.MALE && canBreed()) {
            // A male animal will search for a female within the field using a noise-value/search-distance.
            List<Location> adjacentFemales = field.adjacentLocationOfGender(getLocation(), bounds, interestedIn, Gender.FEMALE, searchDistance);
            // Retrieves a list of all the adjacent females, based off the current location in the field.

            for (Location location : adjacentFemales) {
                Animal animal = (Animal) field.getObjectAt(location);
                if (animal.canBreed() && !animal.getCurrentlyBreeding()) {
                    // The first female that is not currently breeding and can breed, will be set to breed.
                    animal.setCurrentlyBreeding(true);
                    transferDisease(animal);
                    // Transfer any diseases based on spread probability.
                    break;
                }
            }
        } else {
            if (getCurrentlyBreeding()) {
                // If it is a female it will be set to give birth (based on the breeding probability).
                giveBirth(newActors, bounds);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void giveBirth(List<Actor> newActors, ArrayList<Bound> bounds)
    {
        super.giveBirth(newActors, bounds);

        setCurrentlyBreeding(false);
    }

    /**
     * Retrieves whether the animal is currently breeding.
     *
     * @return Whether the animal is currently breeding.
     */
    public boolean getCurrentlyBreeding()
    {
        return isCurrentlyBreeding;
    }

    /**
     * Sets whether the animal is currently breeding.
     *
     * @param currentlyBreeding Whether the animal is currently breeding.
     */
    public void setCurrentlyBreeding(boolean currentlyBreeding)
    {
        this.isCurrentlyBreeding = currentlyBreeding;
    }
}