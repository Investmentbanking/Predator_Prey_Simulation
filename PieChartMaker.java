import java.awt.Color;
import java.util.HashMap;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * This class uses the population stats to paint the pie chart.
 * 
 * @author Ayesha Dorani
 * @version 2022.02.22 
 */
public class PieChartMaker extends JPanel
{
    // panel width.
    private int width;
    // panel height.
    private int height;
    // HashMap keeps count of the population per color.
    private HashMap<Color, Counter> stats;
    
    /**    
     * Constructor.
     */
    public PieChartMaker()
    {
    }
    
    /**
     * Sets the stats.
     * @param stats Simulation stats.
     */
    public void setStats(HashMap<Color, Counter> stats)
    {
        this.stats = stats;
    }
    
    /**
     * Sets the size of the pie chart frame.
     * @param width The width of the frame.
     * @param height The height of the frame.
     */
    public void setSize(int width, int height) 
    {
        this.width = width;
        this.height = height;
    }
    
    /**
     * Creating and updating pie chart graphics when data is being changed. 
     * @param g Graphic component.
     */
    @Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        // The total amount of all the colors.
        int total = 0;
        
        int startAngle = 0;
        int arcAngle = 0;
        for (Color color: stats.keySet())
        {
            // Count of all colors added together.
            total += stats.get(color).getCount();
        }
        
        // Colors the pie chart.
        for (Color color : stats.keySet())
        {
            if (stats.get(color).getCount() > 0)
            {
                // Multiples color count by 360 and divides by total.
                arcAngle = (stats.get(color).getCount() * 360/ total) ;
                g.setColor(color);
                
                // Draw pie chart.
                g.fillArc(50, 50, width, height, startAngle, arcAngle);
                
                // Start the angle of the next color.
                startAngle += arcAngle + 1;
            }
        }
        
        // Paints the outline of the pie chart.
        g.setColor(Color.BLACK);
        g.drawArc(50, 50, width, height, 0, 360);
    }
}