/**
 * A singleton class to represent the current time of the day {DAY, NIGHT} in the simulation.
 * This was referenced from:
 * David J. Barnes & Michael Kölling Objects First with Java, A practical introduction using BlueJ (Sixth Edition)
 * Accessed on 2022.02.24 from page 544, singleton pattern
 *
 * @author Lavish K. Kumar 
 * @version 2022.02.25
 */
public class TimeOfDay {
    // The Singleton instance of the Time Of Day Object.
    private static final TimeOfDay timeOfDay = new TimeOfDay();

    // The number of steps before a change in the time of day.
    private static final int STEPS_PER_CHANGE = 1;

    // The current time of the day.
    private Time time;

    /**
     * Constructor for the TimeOfDay object.
     * Initialises the time as DAY.
     */
    private TimeOfDay()
    {
        time = Time.DAY;
    }

    /**
     * Returns the singleton instance for the Time of Day object.
     *
     * @return The singleton instance for the Time of Day object.
     */
    public static TimeOfDay getInstance() 
    {
        return timeOfDay;
    }

    /**
     * Switch the Time of the Day.
     * Set this in the instance.
     */
    private void switchTime() 
    {
        if (time == Time.DAY) {

            time = Time.NIGHT;
        } else {
            time = Time.DAY;
        }
    }

    /**
     * Retrieves the current instance of the Time.
     *
     * @return The current instance of the Time.
     */
    public Time getTime() 
    {
        return time;
    }

    /**
     * Checks if the time of the day has changed.
     * This depends on the current step in the simulation.
     * Sets the new Time.
     *
     * @param currentStep The current step in the simulation.
     */
    public void setCurrentStep(int currentStep) 
    {
        if (currentStep % STEPS_PER_CHANGE == 0) {
            switchTime();
        }
    }
}