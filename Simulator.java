import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing eagles, monkeys, sloths, jaguars, fishes, trees and algae.
 *
 * @author David J. Barnes, Michael Kölling, Lavish K. Kumar and Ayesha Dorani
 * @version 2022.02.22 (2)
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;

    // The probability that a Fish will be created in a specified given grid position.
    private static final double FISH_CREATION_PROBABILITY = 0.5;

    // The probability that a Jaguar will be created in a specified given grid position.
    private static final double JAGUAR_CREATION_PROBABILITY = 0.04;

    // The probability that a Monkey will be created in a specified given grid position.
    private static final double MONKEY_CREATION_PROBABILITY = 0.05;

    // The probability that a Sloth will be created in a specified given grid position.
    private static final double SLOTH_CREATION_PROBABILITY = 0.13;

    // The probability that a Eagle will be created in a specified given grid position.
    private static final double EAGLE_CREATION_PROBABILITY = 0.03;

    // The probability that a Tree will be created in a specified given grid position.
    private static final double TREE_CREATION_PROBABILITY = 0.18;

    // The probability that an Algae will be created in a specified given grid position.
    private static final double ALGAE_CREATION_PROBABILITY = 0.4;

    // The initial disease spread probability.
    private static final double INITIAL_DISEASE_SPREAD = 0.15;

    // List of actors in the field.
    private final List<Actor> actors;

    // List of animals in the field.
    private final List<Animal> animals;

    // List of plants in the field.
    private final List<Plant> plants;

    // The current state of the field.
    private final Field field;
    // A randomizer object with a shared SEED.
    private final Random rand = Randomizer.getRandom();
    // The current step of the simulation.
    private int step;
    // Boolean variables which control whether the actors will be shown in the simulation or not.
    private boolean viewSloths = true, viewMonkeys = true, viewFishes = true, viewJaguars = true, viewTrees = true, viewAlgae = true, viewEagles = true;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     *
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if (width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        actors = new ArrayList<>();
        animals = new ArrayList<>();
        plants = new ArrayList<>();
        field = new Field(depth, width);
        
        // Setup a valid starting point.
        reset();
    }

    /**
     * Randomly populate the field with Sloths, Monkeys, Jaguars, Fish, Eagle, Trees and Algae.
     */
    private void populate()
    {
        field.clear();

        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Location location = new Location(row, col);
                Actor actor = null;
                if (this.viewSloths && rand.nextDouble() <= SLOTH_CREATION_PROBABILITY && Bound.isWithinBound(location, Sloth.getBounds())) {
                    actor = new Sloth(field, location, true);
                } else if (this.viewMonkeys && rand.nextDouble() <= MONKEY_CREATION_PROBABILITY && Bound.isWithinBound(location, Monkey.getBounds())) {
                    actor = new Monkey(field, location, true);
                } else if (this.viewJaguars && rand.nextDouble() <= JAGUAR_CREATION_PROBABILITY && Bound.isWithinBound(location, Jaguar.getBounds())) {
                    actor = new Jaguar(field, location, true);
                } else if (this.viewFishes && rand.nextDouble() <= FISH_CREATION_PROBABILITY && Bound.isWithinBound(location, Fish.getBounds())) {
                    actor = new Fish(field, location, true);
                } else if (this.viewEagles && rand.nextDouble() <= EAGLE_CREATION_PROBABILITY && Bound.isWithinBound(location, Eagle.getBounds())) {
                    actor = new Eagle(field, location, true);
                } else if (this.viewTrees && rand.nextDouble() <= TREE_CREATION_PROBABILITY && Bound.isWithinBound(location, Tree.getBounds())) {
                    actor = new Tree(field, location, true);
                } else if (this.viewAlgae && rand.nextDouble() <= ALGAE_CREATION_PROBABILITY && Bound.isWithinBound(location, Algae.getBounds())) {
                    actor = new Algae(field, location, true);
                }
                
                if (actor != null) {
                    // add to the actors list.
                    actors.add(actor);
                }
                if (actor instanceof Animal) {
                    // add to the animals list.
                    animals.add((Animal) actor);

                } else if (actor instanceof Plant) {
                    // add to the plants list.
                    plants.add((Plant) actor);
                }
            }
        }
    }
    
    /**
     * Sets whether sloths should be shown in the simulation.
     * @param viewSloths The boolean variable.
     */
    public void setViewSloths(boolean viewSloths)
    {
        this.viewSloths = viewSloths;
    }
    
    /**
     * Sets whether monkeys should be shown in the simulation.
     * @param viewMonkeys The boolean variable.
     */
    public void setViewMonkeys(boolean viewMonkeys)
    {
        this.viewMonkeys = viewMonkeys;
    }
    
    /**
     * Sets whether fishes should be shown in the simulation.
     * @param viwFishes The boolean variable.
     */
    public void setViewFishes(boolean viewFishes) 
    {
        this.viewFishes = viewFishes;
    }
    
    /**
     * Sets whether eagles should be shown in the simulation.
     * @param viewEagles The boolean variable.
     */
    public void setViewEagles(boolean viewEagles) 
    {
        this.viewEagles = viewEagles;
    }
    
    /**
     * Sets whether trees should be shown in the simulation.
     * @param viewTrees The boolean variable.
     */
    public void setViewTrees(boolean viewTrees) 
    {
        this.viewTrees = viewTrees;
    }
    
    /**
     * Sets whether algae should be shown in the simulation.
     * @param viewAlgae The boolean variable.
     */
    public void setViewAlgae(boolean viewAlgae) 
    {
        this.viewAlgae = viewAlgae;
    }
    
    /**
     * Sets whether jaguars should be shown in the simulation.
     * @param viewJaguars The boolean variable.
     */
    public void setViewJaguars(boolean viewJaguars) 
    {
        this.viewJaguars = viewJaguars;
    }

     /**
     * Populates the field with diseases,
     * depending on whether the disease is an AnimalDisease or
     * a PlantDisease.
     * This is based on the initial disease spread.
     *
     * @param disease The disease to populate the field actors with.
     */
    public void populateDisease(Disease disease)
    {
        // The numbers of actors to affect.
        int numberToAffect = 0;

        if (disease.getAffectedActor() == AffectedActor.ANIMAL) {
            // Whether the disease is an animal disease.

            int animalsSize = animals.size();
            numberToAffect = (int) (animalsSize * INITIAL_DISEASE_SPREAD);
            int incrementValue = animalsSize / numberToAffect;
            // Retrieve the interval to affect.


            for (int i = 0; i < animalsSize; i = i + incrementValue) {
                // Systematically set the disease of animals at regular intervals.
                animals.get(i).setDisease(disease);
            }

        } else if(disease.getAffectedActor() == AffectedActor.PLANT){
            int plantSize = plants.size();
            numberToAffect = (int) (plants.size() * INITIAL_DISEASE_SPREAD);
            int incrementValue = plantSize / numberToAffect;
            // Retrieve the interval to affect.

            for (int i = 0; i < plantSize; i = i + incrementValue) {
                // Systematically set the disease of plants at regular intervals.

                plants.get(i).setDisease(disease);
            }
        }
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(4000);
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     *
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for (int step = 1; step <= numSteps; step++) {
            simulateOneStep();
        }
    }

    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep()
    {
        step++;
        TimeOfDay.getInstance().setCurrentStep(step);

        // Provide space for newborn animals and plants.
        List<Actor> newActors = new ArrayList<>();
        // Let all actors act.
        for (Iterator<Actor> it = actors.iterator(); it.hasNext(); ) {
            Actor animal = it.next();
            animal.act(newActors);
            if (!animal.isAlive()) {
                it.remove();
            }
        }

        // Add the newly animals and plants to the main lists.
        actors.addAll(newActors);
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        actors.clear();
        GroundPlan gp = new GroundPlan(field.getDepth(), field.getWidth());
        populate();
    }
    
    /**
     * @return The current step of simulation.
     */
    public int getStep()
    {
        return step;
    }
    
    /**
     * @return Field.
     */
    public Field getField()
    {
        return field;
    }

    /**
     * Pause for a given time.
     *
     * @param millisec The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        } catch (InterruptedException ie) {
            // wake up
        }
    }
}