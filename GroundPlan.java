import java.util.ArrayList;

/**
 * Defines the Ground Plan (map), as a collection of bounds.
 *
 * @author Lavish K. Kumar
 * @version 2022.02.22
 */
public class GroundPlan 
{
    // The length of the field.
    private static int length;

    // The width of the field.
    private static int width;

    // A list of bounds that defines the Map.
    private static ArrayList<Bound> bounds;

    /**
     * Constructor for the Ground Plan object.
     *
     * @param length The length of the field.
     * @param width The width of the field.
     */
    public GroundPlan(int length, int width) 
    {

        this.length = length;
        this.width = width;

        bounds = new ArrayList<>();
    }

    /**
     * Returns the bounds for the whole field.
     *
     * @return The bounds for the whole field.
     */
    public static ArrayList<Bound> wholeArea()
    {
        bounds = new ArrayList<>();

        Location location1 = new Location(0,0);
        Location location2 = new Location(0,width);
        Location location3 = new Location(length,0);
        Location location4 = new Location(length,width);

        Bound bound1 = new Bound(location1,location2,location3,location4);

        bounds.add(bound1);

        return bounds;
    }

    /**
     * Returns the bounds for the ground area within the field.
     *
     * @return The bounds for the ground area within the field.
     */
    public static ArrayList<Bound> groundArea()
    {
        bounds = new ArrayList<>();

        Location location1 = new Location(0,0);
        Location location2 = new Location(0,width);
        Location location3 = new Location((int)(length*0.8),0);
        Location location4 = new Location((int)(length * 0.8),width);

        Bound bound1 = new Bound(location1,location2,location3,location4);

        bounds.add(bound1);

        return bounds;
    }

    /**
     * Returns the bounds for the water area within the field.
     *
     * @return The bounds for the water area within the field.
     */
    public static ArrayList<Bound> waterArea()
    {
        bounds = new ArrayList<>();

        Location location1 = new Location((int) (length*0.8),0);
        Location location2 = new Location((int) (length*0.8),width);
        Location location3 = new Location(length,0);
        Location location4 = new Location(length,width);

        Bound bound1 = new Bound(location1,location2,location3,location4);

        bounds.add(bound1);

        return bounds;
    }

    /**
     * Returns the bounds for the water area and the ground area.
     *
     * @return The bounds for the water area and the ground area.
     */
    public static ArrayList<Bound> groundAndWater()
    {
       ArrayList<Bound> groundWater = new ArrayList<>();
       groundWater.addAll(groundArea());
       groundWater.addAll(waterArea());
       return groundWater;
    }
}