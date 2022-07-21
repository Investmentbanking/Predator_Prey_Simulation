/**
 * The Strength of the Disease.
 *
 * This was referenced from:
 * Baeldung,Attaching Values to Java Enum, https://www.baeldung.com/java-enum-values,
 * Accessed on 2022.02.25, section about constructors and methods.
 *
 * Alex Lee, Java Enum- Make Your Own Enum, https://www.youtube.com/watch?v=LYKHxwQ0QH8
 * Accessed on 2022.02.25
 *
 * @author Lavish Kamal Kumar 
 * @version 2022.02.22
 */
public enum Strength 
{
    /**
     * The LOW strength.
     */
    LOW(0.03),

    /**
     * The MEDIUM strength.
     */
    MEDIUM(0.08),

    /**
     * The HIGH strength.
     */
    HIGH(0.15);

    private double strength;

    Strength(double strength)
    {
        this.strength = strength;
    }

    public double getStrength()
    {
        return strength;
    }
}