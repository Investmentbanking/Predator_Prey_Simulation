import java.util.ArrayList;

/**
 * The bound class defines a certain area of the field.
 * This is defined by 4 locations, as defined by the diagram below.
 *
 *       1------------2
 *       |   Bound    |
 *       |   Area     |
 *       3------------4
 *
 * 1,2,3,4 are Location objects.
 *
 * @author Lavish K. Kumar
 * @version 2022.02.26
 */
public class Bound {
    // These are the locations that make up the bounding box as ascribed above.
    private final Location location1;
    private final Location location2;
    private final Location location3;
    private final Location location4;

    /**
     * Constructor of the Bound object.
     * Made up of 4 Locations.
     *
     * @param location1 The Upper-Left location.
     * @param location2 The Upper-Right location.
     * @param location3 The Lower-Left location.
     * @param location4 The Lower-Right location.
     */
    public Bound(Location location1, Location location2, Location location3, Location location4) 
    {
        this.location1 = location1;
        this.location2 = location2;
        this.location3 = location3;
        this.location4 = location4;
    }

    /**
     * @return The Upper-Left location.
     */
    public Location getLocation1()
    {
        return location1;
    }

    /**
     * @return The Upper-Right location.
     */
    public Location getLocation2()
    {
        return location2;
    }

    /**
     * @return The Lower-Left location.
     */
    public Location getLocation3() 
    {
        return location3;
    }

    /**
     * @return The Lower-Right location.
     */
    public Location getLocation4() 
    {
        return location4;
    }

    /**
     * Checks whether a location is within a list of bounds.
     *
     * @param location The location to be checked.
     * @param bounds A list of the bounds to be considered.
     * @return true if the location is within the defined bounds, false otherwise.
     */
    public static boolean isWithinBound(Location location, ArrayList<Bound> bounds)
    {
        for (Bound bound: bounds){
            if(isWithinBound(location, bound)){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether a location is within a bound.
     *
     * @param location The location to be checked.
     * @param bound The bound to be considered.
     * @return true if the location is within the bound, false otherwise.
     */
    public static boolean isWithinBound(Location location, Bound bound) 
    {
        boolean isWithin = false;
        boolean isWithinRow = false;
        boolean isWithinCol = false;

        int row = location.getRow();
        int col = location.getCol();

        if ((bound.getLocation1().getRow() <= row) && (row < bound.getLocation3().getRow())) {
            // Check whether the location's row is within the bound's row.
            isWithinRow = true;
        }
        
        if ((bound.getLocation1().getCol() <= col) && (col < bound.getLocation4().getCol())) {
            // Check whether the location's column is within the bound's column.
            isWithinCol = true;
        }

        if (isWithinRow && isWithinCol) {
            // Whether the location is within the bound's row and column.
            isWithin = true;
        }

        return isWithin;
    }
}