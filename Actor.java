import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An abstract class representing the shared characteristics of the animals and plants.
 * This provides the basis for any object that can act, breed and age within the simulation.
 *
 * @author David J. Barnes, Michael Kölling and Lavish K. Kumar 
 * @version 2022.03.02
 */
public abstract class Actor
{
    // Object of type Randomizer to control randomization of the simulation.
    private static final Random rand = Randomizer.getRandom();

    // The gender of the Actor
    private final Gender gender;

    // The maximum age of the Actor.
    private final int maxAge;

    // The minimum age at which breeding can occur.
    private final int breedingAge;

    // The chance of producing offspring.
    private final double breedingProbability;

    //The maximum number of offspring of the Actor.
    private final int maxLitterSize;

    // The Actor's field.
    private Field field;

    // The Actor's position in the field.
    private Location location;

    // Whether the Actor is alive or not.
    private boolean alive;

    // The current age of the Actor.
    private int age;

    // The disease carried by the actor.
    private Disease disease;

    /**
     * Constructor of the Actor object.
     *
     * @param randomAge Whether the age of the Actor will be defined randomly.
     * @param field     The field, in which the Actor is placed in.
     * @param location  The location of the Actor in the field.
     * @param gender    The gender of the Actor.
     */
    public Actor(Field field, Location location, boolean randomAge, Gender gender, int maxAge, int breedingAge, double breedingProbability, int maxLitterSize)
    {
        if (randomAge) {
            age = rand.nextInt(maxAge);
        } else {
            age = 0;
        }

        this.maxAge = maxAge;
        this.breedingAge = breedingAge;
        this.breedingProbability = breedingProbability;
        this.maxLitterSize = maxLitterSize;
        this.field = field;
        this.gender = gender;
        setLocation(location);
        disease = null;
        alive = true;
    }

    /**
     * Increment the age of the Actor.
     * This could result in the death of the Actor.
     */
    public void incrementAge()
    {
        age++;

        if (age > maxAge) {
            setDead();
        }
    }

    /**
     * Whether the actor can give birth at this stage.
     * This is based on the defined bounds.
     *
     * @param newActors A list to add newborn actors.
     * @param bounds    The predefined bounds for the birth of new actors.
     */
    protected void giveBirth(List<Actor> newActors, ArrayList<Bound> bounds)
    {
        List<Location> free = field.getFreeAdjacentLocations(getLocation(), bounds, 1);
        int births = breed();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Actor young = makeYoung(field, loc);
            newActors.add(young);
            transferDisease(young);
        }
    }

    /**
     * This method returns the number of births, dependent on the Actor's breeding probability and max litter size.
     * If the actor has been affected by a disease, these values may be different.
     * This depends on the disease's mutation.
     *
     * @return The number of births.
     */
    public int breed()
    {
        double breedingProbability = this.breedingProbability;
        int maxLitterSize = this.maxLitterSize;

        if (disease != null && disease.hasMutated()) {
            if (disease.getMutationType() == Mutation.BREEDING_PROBABILITY) {
                breedingProbability = disease.getNewBreedingProbability();
            } else if (disease.getMutationType() == Mutation.MAX_LITTER_SIZE) {
                maxLitterSize = disease.getNewMaxLitterSize();
            }
        }

        int births = 0;
        if (canBreed() && rand.nextDouble() <= breedingProbability) {
            births = rand.nextInt(maxLitterSize) + 1;
        }
        return births;
    }

    /**
     * Return the Actor's location.
     *
     * @return The actor's location.
     */
    protected Location getLocation()
    {
        return location;
    }

    /**
     * Place the Actor at a new location in the field.
     *
     * @param newLocation The Actor's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if (location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * Return the Actor's field.
     *
     * @return The Actor's field.
     */
    protected Field getField()
    {
        return field;
    }

    /**
     * This sets the field of the Actor.
     *
     * @param field The actor's field.
     */
    public void setField(Field field)
    {
        this.field = field;
    }

    /**
     * This causes damage to the Actor.
     */
    public void setDamage()
    {
        setDead();
    }

    /**
     * Check whether the Actor is alive or not.
     *
     * @return True if the Actor is still alive.
     */
    public boolean isAlive()
    {
        return alive;
    }

    /**
     * Sets the disease and configures the Actor.
     *
     * @param disease The disease to be set.
     */
    public void setDisease(Disease disease)
    {
        this.disease = disease;
        this.disease.setActor(this);
        disease.affect();
    }

    /**
     * This method transfers the disease to the Actor based on the disease's spread probability.
     *
     * @param actor The Actor to be transferred the disease.
     */
    public void transferDisease(Actor actor)
    {
        if ((this.disease != null) && (rand.nextDouble() <= this.disease.getSpreadProbability())) {
            actor.setDisease(this.disease);
        }
    }

    /**
     * This sets the Actor dead, clearing the location.
     * This also aids the collection process of the Garbage Collector.
     */
    public void setDead()
    {
        alive = false;
        if (location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Retrieves the gender of the Actor.
     *
     * @return The gender of the Actor.
     */
    public Gender getGender()
    {
        return gender;
    }

    /**
     * An Actor can breed if it has reached the breeding age.
     * If the Actor has been affected by a disease, the value is different.
     * This depends on the disease's mutation.
     *
     * @return Whether the Actor can breed.
     */
    public boolean canBreed()
    {
        if ((disease != null) && (disease.hasMutated()) && (disease.getMutationType() == Mutation.BREEDING_AGE)) {
            return age >= disease.getNewBreedingAge();
        }
        return age >= breedingAge;
    }

    /**
     * Causes the Actors to act - including eat, breed and sleep.
     * Given a pointer to a list to enable the birth of more Actors.
     *
     * @param newActors List to add the new births to.
     */
    abstract public void act(List<Actor> newActors);

    /**
     * Retrieves the age of the actor.
     *
     * @return The age of the actor.
     */
    public int getAge()
    {
        return age;
    }

    /**
     * Retrieves the Breeding age of the Actor.
     *
     * @return The breeding age of the Actor.
     */
    public int getBreedingAge()
    {
        return breedingAge;
    }

    /**
     * Retrieves the Actor's max litter size.
     *
     * @return The Actor's max litter size.
     */
    public int getLitterSize()
    {
        return maxLitterSize;
    }

    /**
     * Retrieves the breeding probability of the Actor.
     *
     * @return The breeding probability of the Actor.
     */
    public double getBreedingProbability()
    {
        return breedingProbability;
    }

    /**
     * Return a newborn actor of this class.
     *
     * @param field    The field for it to be set in.
     * @param location The location within the field to be set
     * @return A newborn Actor.
     */
    abstract public Actor makeYoung(Field field, Location location);
}