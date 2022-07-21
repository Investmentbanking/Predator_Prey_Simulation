import java.util.Random;

/**
 * An abstract class representing the shared characteristics of plants.
 *
 * @author David J. Barnes, Michael Kölling and Lavish K. Kumar 
 * @version 2022.03.02
 */
public abstract class Plant extends Actor
{
    // Object of type Randomizer to control randomization of the simulation.
    private static final Random rand = Randomizer.getRandom();

    // The amount of food that can be provided to actors from this plant
    private int nutritionalValue;

    /**
     * Constructor for an object of type plant.
     *
     * @param field               The field in which the Plant is placed.
     * @param location            The location of the Plant in the field.
     * @param randomAge           Whether the Plant will be assigned a random age.
     * @param gender              The gender of the Plant.
     * @param nutritionalValue    The nutritional value the Plant provides to other actors.
     * @param maxAge              The maximum age of the Plant.
     * @param breedingAge         The age at which the Plant can breed.
     * @param breedingProbability The chances of the Plant breeding.
     * @param maxLitterSize       The maximum number of offspring the Plant can have.
     */
    public Plant(Field field, Location location, boolean randomAge, Gender gender, int nutritionalValue, int maxAge, int breedingAge, double breedingProbability, int maxLitterSize)
    {
        super(field, location, randomAge, gender, maxAge, breedingAge, breedingProbability, maxLitterSize);
        this.nutritionalValue = nutritionalValue;
    }

    /**
     * This decrements the nutritional value for the Plant Object.
     * If the nutritional value of the object drops to zero, the Plant Object dies.
     */
    @Override
    public void setDamage()
    {
        nutritionalValue--;
        if (nutritionalValue <= 0) {
            setDead();
        }
    }
}