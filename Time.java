/**
 * The current Time.
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
public enum Time {
    /**
     * The DAY Time.
     */
    DAY,

    /**
     * The Night Time.
     */
    NIGHT;

    /**
     * Returns the alternate Time.
     *
     * @param time The current time.
     * @return The alternate Time
     */
    public static Time switchTime(Time time)
    {
        if(time == DAY){
            return NIGHT;

        }else{
            return DAY;

        }
    }
}