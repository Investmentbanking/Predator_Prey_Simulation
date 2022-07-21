import java.util.ArrayList;
/**
 * The movement bounds, defined by an arraylist of Bounds.
 *
 * This was referenced from:
 * Baeldung,Attaching Values to Java Enum, https://www.baeldung.com/java-enum-values,
 * Accessed on 2022.02.25, section about constructors and methods.
 *
 * Alex Lee, Java Enum- Make Your Own Enum, https://www.youtube.com/watch?v=LYKHxwQ0QH8
 * Accessed on 2022.02.25
 *
 * @author Lavish K. Kumar 
 * @version 2022.02.25
 */
public enum MovementArea {
    /**
     * The designated WATER bound.
     */
    WATER(GroundPlan.waterArea()),

    /**
     * The designated GROUND bound.
     */
    GROUND(GroundPlan.groundArea()),

    /**
     * The designated AIR bound.
     */
    AIR(GroundPlan.wholeArea()),

    /**
     * The designated GROUND & WATER bound.
     */
    GROUND_WATER(GroundPlan.groundAndWater());

    // The bounds that the actor will move within.
    private ArrayList<Bound> bounds;

    /**
     * The constructor for the MovementArea ENUM.
     *
     * @param bounds The specified locations the actors can travel within.
     */
    MovementArea(ArrayList<Bound> bounds){
        this.bounds = bounds;
    }

    /**
     * Retrieves a list of bounds the actor can move within.
     *
     * @return A list of bounds the actor can move within.
     */
    public ArrayList<Bound> getBounds(){
        return bounds;
    }
}