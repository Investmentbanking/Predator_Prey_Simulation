import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashMap;
import javax.swing.Timer;

/**
 * A graphical view of the simulation grid.
 * The view displays a colored rectangle for each location 
 * representing its contents. It uses a default background color.
 * Colors for each type of species can be defined using the
 * setColor method.
 * 
 * @author David J. Barnes and Michael Kölling and Ayesha Dorani
 * @version 2022.03.02
 */
public class SimulatorView extends JFrame implements ActionListener
{
    // Setting default variable values.
    private static final int DEFAULT_SPEED = 60;
    private static final int DEFAULT_WIDTH = 120;
    private static final int DEFAULT_HEIGHT = 80;
    
    // Setting default colors for specific cases.
    private static final Color EMPTY_COLOR = Color.white;
    private static final Color UNKNOWN_COLOR = Color.gray;
    
    // Setting the current height and width of the window.
    private int height;
    private int width;

    // GUI components.
    private final String STEP_PREFIX = "Step: ";
    private final String POPULATION_PREFIX = "Population: ";
    private JLabel stepLabel, population, infoLabel;
    private JButton goButton, stopButton, resetButton, quitButton, nextStepButton, pieChartButton;
    private JComboBox simulationSpeed;
    private JComboBox animalDiseaseStrength;
    private JComboBox animalDiseaseSpreadRate;
    private JComboBox animalMutationProbability;
    private JComboBox animalMutationType;
    private JComboBox plantDiseaseStrength;
    private JComboBox plantDiseaseSpreadRate;
    private JComboBox plantMutationProbability;
    private JComboBox plantMutationType;
    
    private FieldView fieldView;
    // A map for storing colors for participants in the simulation.
    private Map<Class, Color> colors;
    // A statistics object computing and storing simulation information.
    private FieldStats stats;
    
    // Simulator variables.
    private final Simulator simulator;
    private Timer simulationTimer;
    
    // Pie chart variables.
    private PieChartMaker pieChartDiagram;
    private boolean isPieChartOpen = false;

    /**
     * Constructor for SimulatorView calling second constructor.
     */
    public SimulatorView() 
    {
       this(DEFAULT_HEIGHT, DEFAULT_WIDTH);
    }
    
    /**
     * Constuctor for SimulatorView with paramters.
     * Create a view of the given height and height.
     * @param height The simulator's height.
     * @param width The simulator's width.
     */
    public SimulatorView(int height, int width)
    {
        if (height <= 0 || width <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            this.height = DEFAULT_HEIGHT;
            this.width = DEFAULT_WIDTH;
        } else if ( height < 40 || height > 90 || width < 60 || width > 160) {
            System.out.println("Appropriate dimensions must be used.");
            System.out.println("Using default values.");
            this.height = DEFAULT_HEIGHT;
            this.width = DEFAULT_WIDTH;
        } else {
            this.height = height;
            this.width = width;
        }
        
        // Create a new instance of the simulator class and use the height
        // and width that was passed to the simulator view.
        this.simulator = new Simulator(this.height, this.width);

        // Create instance for timer class controlling Simulation time.
        simulationTimer = new Timer(DEFAULT_SPEED, this);
        
        stats = new FieldStats();
        colors = new LinkedHashMap<Class, Color>();
        
        // Construct and load GUI.
        prepareGUILoader();
        GUILoader.loadMainGUI(this);
        finishGUILoader();
        
        // Create pie chart for showing statistics.
        createPieChart(height, width);

        // Draw the actors on the grid.
        this.showStatus(this.simulator.getStep(), this.simulator.getField());
    }
 
    // Setting up getters and setters for the relevant fields of the SimulatorView.

    /**
     * @param height The height to set of the GUI.
     */
    public void setHeight(int height) 
    {
        this.height = height;
    }
    
    /**
     * @param width The width to set of the GUI.
     */
    public void setWidth(int width) 
    {
        this.width = width;
    }

    /**
     * @return The phrase which is used to show the number of steps. 
     */
    public String getSTEP_PREFIX() 
    {
        return STEP_PREFIX;
    }

    /**
     * @return The phrase which is used to show the current population.
     */
    public String getPOPULATION_PREFIX() 
    {
        return POPULATION_PREFIX;
    }

    /**
     * @return The label to show the current step.
     */
    public JLabel getStepLabel()
    {
        return stepLabel;
    }

    /**
     * @param stepLabel Creates a new JLabel.
     */
    public void setStepLabel(JLabel stepLabel) 
    {
        this.stepLabel = stepLabel;
    }

    /**
     * @return A JLabel containing the population.
     */
    public JLabel getPopulation() 
    {
        return population;
    }

    /**
     * @param population Creates a new JLabel to be set.
     */
    public void setPopulation(JLabel population)
    {
        this.population = population;
    }

    /**
     * @return The JButton go button which runs the Simulation. 
     */
    public JButton getGoButton() 
    {
        return goButton;
    }

    /**
     * @param goButton Create's a new JButton called goButton.
     */
    public void setGoButton(JButton goButton)
    {
        this.goButton = goButton;
    }

    /**
     * @return The JButton stop button which stops the simulation.
     */
    public JButton getStopButton() 
    {
        return stopButton;
    }

    /**
     * @param stopButton Create's a new JButton called stopButton.
     */
    public void setStopButton(JButton stopButton) 
    {
        this.stopButton = stopButton;
    }
    
    /**
     * @return The JButton reset button which resets the simulation.
     */
    public JButton getResetButton() 
    {
        return resetButton;
    }

    /**
     * @param resetButton Create's a new JButton called resetButton.
     */
    public void setResetButton(JButton resetButton) 
    {
        this.resetButton = resetButton;
    }

    /**
     * @return The JButton quit button which halts the simulation.
     */
    public JButton getQuitButton() 
    {
        return quitButton;
    }

    /**
     * @param quitButton Create's a new JButton called quitButton.
     */
    public void setQuitButton(JButton quitButton) 
    {
        this.quitButton = quitButton;
    }

    /**
     * @return The Jbutton nextStep button which increments the step by 1 each time it is clicked.
     */
    public JButton getNextStepButton() 
    {
        return nextStepButton;
    }

    /**
     * @param nextStepButton Create's a new JButton called nextStepButton.
     */
    public void setNextStepButton(JButton nextStepButton) 
    {
        this.nextStepButton = nextStepButton;
    }

    /**
     * @return The JButton pieChartButton which opens up a pie chart when it is clicked. 
     */
    public JButton getPieChartButton() 
    {
        return pieChartButton;
    }

    /**
     * @param pieChartButton Create's a new JButton called pieChartButton.
     */
    public void setPieChartButton(JButton pieChartButton)
    {
        this.pieChartButton = pieChartButton;
    }

    /**
     * @return The JComboBox simulationSpeed which will change the speed of the Simulation.
     */
    public JComboBox getSimulationSpeed()
    {
        return simulationSpeed;
    }

    /**
     * @param simulationSpeed Create's a new JComboBox.
     */
    public void setSimulationSpeed(JComboBox simulationSpeed) 
    {
        this.simulationSpeed = simulationSpeed;
    }
    
    /**
     * @return The JComboBox animalDiseaseStrength which changes the strength of the disease for animals.
     */
    public JComboBox getAnimalDiseaseStrength()
    {
        return animalDiseaseStrength;
    }
    
    /**
     * @param animalDiseaseStrength Create's a new JComboBox.
     */
    public void setAnimalDiseaseStrength(JComboBox animalDiseaseStrength) 
    {
        this.animalDiseaseStrength = animalDiseaseStrength;
    }
    
    /**
     * @return The JComboBox animalDiseaseSpreadRate which changes the chance of disease spreading for animals.
     */
    public JComboBox getAnimalDiseaseSpreadRate() 
    {
        return animalDiseaseSpreadRate;
    }

    /**
     * @param animalDiseaseSpreadRate Create's a new JComboBox.
     */
    public void setAnimalDiseaseSpreadRate(JComboBox animalDiseaseSpreadRate) 
    {
        this.animalDiseaseSpreadRate = animalDiseaseSpreadRate;
    }

    /**
     * @return The JComboBox animalMutationProbability which changes the probability of the mutation spreading for animals.
     */
    public JComboBox getAnimalMutationProbability() 
    {
        return animalMutationProbability;
    }

    /**
     * @param animalMutationProbability Create's a new JComboBox.
     */
    public void setAnimalMutationProbability(JComboBox animalMutationProbability)
    {
        this.animalMutationProbability = animalMutationProbability;
    }
    
    /**
     * @return The JComboBox animalMutationType which changes the type of mutation for animals and what it affects.
     */
    public JComboBox getAnimalMutationType() 
    {
        return animalMutationType;
    }

    /**
     * @param animalMutationType Create's a new JComboBox.
     */
    public void setAnimalMutationType(JComboBox animalMutationType) 
    {
        this.animalMutationType = animalMutationType;
    }
    
    /**
     * @return The JComboBox plantDiseaseStrength which changes the strength of plant disease.
     */
    public JComboBox getPlantDiseaseStrength()
    {
        return plantDiseaseStrength;
    }

    /**
     * @param plantDiseaseStrength Create's a new JComboBox.
     */
    public void setPlantDiseaseStrength(JComboBox plantDiseaseStrength)
    {
        this.plantDiseaseStrength = plantDiseaseStrength;
    }
    
    /**
     * @return The JComboBox plantDiseaseSpreadRate which changes chance of the diease spreading for plants.
     */
    public JComboBox getPlantDiseaseSpreadRate() 
    {
        return plantDiseaseSpreadRate;
    }

    /**
     * @param plantDiseaseSpreadRate Create's a new JComboBox.
     */
    public void setPlantDiseaseSpreadRate(JComboBox plantDiseaseSpreadRate) 
    {
        this.plantDiseaseSpreadRate = plantDiseaseSpreadRate;
    }
    
    /**
     * @return The JComboBox plantMutationProbability which changes the probability of a mutation spreading for plants.
     */
    public JComboBox getPlantMutationProbability() 
    {
        return plantMutationProbability;
    }

    /**
     * @param plantMutationProbability Create's a new JComboBox.
     */
    public void setPlantMutationProbability(JComboBox plantMutationProbability) 
    {
        this.plantMutationProbability = plantMutationProbability;
    }
    
    /**
     * @return The JComboBox plantMutationType which changes the mutation type for plants.
     */
    public JComboBox getPlantMutationType() 
    {
        return plantMutationType;
    }
    
    /**
     * @param plantMutationType Create's a new JComboBox.
     */
    public void setPlantMutationType(JComboBox plantMutationType) 
    {
        this.plantMutationType = plantMutationType;
    }

    /**
     * @return graphical view of rectangular field.
     */
    public FieldView getFieldView() 
    {
        return fieldView;
    }

    /**
     * @param fieldView Object of FieldView.
     */
    public void setFieldView(FieldView fieldView) 
    {
       this.fieldView = fieldView;
    }
    
    /**
     * Define a color to be used for a given class of animal.
     * @param actorClass The animal's Class object.
     * @param color The color to be used for the given class.
     */
    public void setColor(Class actorClass, Color color)
    {
        colors.put(actorClass, color);
    }

    /**
     * Display a short information label at the top of the window.
     */
    public void setInfoText(String text)
    {
        infoLabel.setText(text);
    }

    /**
     * @return The color to be used for a given class of actor.
     */
    private Color getColor(Class actorClass)
    {
        Color col = colors.get(actorClass);
        if(col == null) {
            // no color defined for this class
            return UNKNOWN_COLOR;
        }
        else {
            return col;
        }
    }
    
    /**
     * Set the colors for the different actors that will be viewed on the grid during simulation.
     */
    private void setColorsForActors()
    {  
        this.setColor(Sloth.class, Color.ORANGE);
        this.setColor(Monkey.class, Color.GREEN);
        this.setColor(Jaguar.class, Color.RED);
        this.setColor(Eagle.class, Color.PINK);
        this.setColor(Fish.class, Color.BLUE);
        this.setColor(Tree.class, Color.YELLOW);
        this.setColor(Algae.class, Color.BLACK);
    }
    
    /**
     * Return the current simulator.
     * @return Simulator.
     */
    public Simulator getSimulator()
    {
        return this.simulator;
    }
    
    /**
     * Sets the height and width of the GUILoader class.
     */
    private void prepareGUILoader() 
    {
        GUILoader.height = this.height;
        GUILoader.width = this.width;
    }
    
    /**
     * Finishes loading the GUI.
     */
    private void finishGUILoader()
    {
        pack();
        // Set the window not to be resizable.
        setResizable(false);
        
        // Show the window in the middle of the screen, it is more user friendly.
        setLocationRelativeTo(null);
        // Set the window to be visible.
        setVisible(true);
        
        // Set the colors for all organisms.
        setColorsForActors();
    }
    
    /**
     * A listener for events triggered by the GUI components.
     * @param event An event which indicates a component-defined action has occured.
     */
    public void actionPerformed(ActionEvent event) 
    {
        if (event.getSource() == this.quitButton) {
            // Halt the program and exit.
            dispose();
            System.exit(0);
        } else if (event.getSource() == this.goButton) { 
            this.runSimulation();
        } else if (event.getSource() == this.resetButton) {
            this.resetSimulation();
        } else if (event.getSource() == this.pieChartButton && isPieChartOpen == false) { 
            // If pie chart is not already opened new pie chart is created. 
            this.createPieChart(100,100);
            makePieVisible();
            isPieChartOpen = true; 
        } else if (event.getSource() == this.stopButton) {
            this.stopSimulation();
        } else if (event.getSource() == this.simulationTimer) {
            // Determine if the simulation should continue to go on...
            if (this.isViable(this.simulator.getField())) {
                this.simulator.simulateOneStep();
            } else {
                // Otherwise, stop the timer, reset and show a message with information about the population.
                JOptionPane.showMessageDialog(this, "The simulation has ended. \n" + stats.getPopulationDetails(this.simulator.getField()), "Results of Simulation:", JOptionPane.PLAIN_MESSAGE);
                
                this.simulationTimer.stop();
                this.simulator.reset();

                // Enable the goButton, resetButton, nextStepButton and all the JComboBox's 
                // but disable the stopButton.
                this.stopButton.setEnabled(false);
                this.goButton.setEnabled(true);
                this.resetButton.setEnabled(true);
                this.nextStepButton.setEnabled(true);
                
                this.simulationSpeed.setEnabled(true);
                
                this.animalDiseaseStrength.setEnabled(true);
                this.animalMutationProbability.setEnabled(true);
                this.animalDiseaseSpreadRate.setEnabled(true);
                this.animalMutationType.setEnabled(true);
                
                this.plantDiseaseStrength.setEnabled(true);
                this.plantMutationProbability.setEnabled(true);
                this.plantDiseaseSpreadRate.setEnabled(true);
                this.plantMutationType.setEnabled(true);
            }
            // On every step, grid is redrawn.
            this.showStatus(this.simulator.getStep(), this.simulator.getField());
        } else if (event.getSource() == this.nextStepButton) {
            // Simulates only one step.
            this.simulator.simulateOneStep();
            this.showStatus(this.simulator.getStep(), this.simulator.getField());
        } else if (event.getSource() == this.simulationSpeed) {
            this.changeSimulationSpeed();
        } else if (event.getSource() == this.animalDiseaseStrength) {
            this.changeAnimalStats();
        } else if (event.getSource() == this.animalDiseaseSpreadRate) {
            this.changeAnimalStats();
        } else if (event.getSource() == this.animalMutationProbability) {
            this.changeAnimalStats();
        } else if (event.getSource() == this.animalMutationType) {
            this.changeAnimalStats();
        } else if (event.getSource() == this.plantDiseaseStrength) {
            this.changePlantStats();
        } else if (event.getSource() == this.plantDiseaseSpreadRate) {
            this.changePlantStats();
        } else if (event.getSource() == this.plantMutationProbability) {
            this.changePlantStats();
        }else if (event.getSource() == this.plantMutationType) {
            this.changePlantStats();
        }
    }
    
    /**
     * Run the simulation, but first disable the nextStepButton, resetButton, goButton,
     * and JComboBox's and enable the stopButton.
     * Start the timer.
     */
    private void runSimulation() 
    {
        this.nextStepButton.setEnabled(false);
        this.resetButton.setEnabled(false);
        this.goButton.setEnabled(false);
        this.simulationSpeed.setEnabled(false);
        
        this.animalDiseaseStrength.setEnabled(false);
        this.animalMutationProbability.setEnabled(false);
        this.animalDiseaseSpreadRate.setEnabled(false);
        this.animalMutationType.setEnabled(false);
        
        this.plantDiseaseStrength.setEnabled(false);
        this.plantMutationProbability.setEnabled(false);
        this.plantDiseaseSpreadRate.setEnabled(false);
        this.plantMutationType.setEnabled(false);
        
        this.simulationTimer.start();
        this.stopButton.setEnabled(true);
    }
    
    /**
     * Reset simulation and redraw positions.
     */
    private void resetSimulation() 
    {
        this.simulationTimer.stop();
        this.simulator.reset();
        this.showStatus(this.simulator.getStep(), this.simulator.getField());
    }
    
    /**
     * @param height The height of the pie chart.
     * @param width The width of the pie chart.
     * Create's a pie chart with certain height and width and populates it with Simulation stats.
     */
    private void createPieChart(int height, int width) 
    {
        this.pieChartDiagram = new PieChartMaker();
        
        this.pieChartDiagram.setSize(height * 3, width * 2);
        this.pieChartDiagram.setStats(this.getPopulationDetails());
    }
    
    /**
     * Create's new JFrame and add's pie chart diagram to it.
     * Add's a window listener to implement custom window-closing behaviour.
     */
    private void makePieVisible()
    {
        JFrame pie = new JFrame("Population Pie Chart");
        JPanel panel = new JPanel(new BorderLayout());        
        JLabel label = new JLabel();
        
        pie.setSize(600, 600);
        label.setText("TREE: Yellow, FISH: Blue, ALGAE: Black, EAGLE: Pink, SLOTH: Orange, MONKEY: Green");
        panel.add(label, BorderLayout.PAGE_START);
        panel.add(this.pieChartDiagram, BorderLayout.CENTER);
        pie.add(panel);
        pie.setVisible(true);
        pie.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        pie.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                pie.setVisible(false);
                pie.dispose();
                isPieChartOpen = false;
            }
        });
    }
    
    /**
     * Enable the buttons except the stopButton.
     * Enable all the JComboBox's and stop the timer.
     */
    public void stopSimulation() 
    {
        this.resetButton.setEnabled(true);
        this.goButton.setEnabled(true);
        this.nextStepButton.setEnabled(true);
        this.simulationSpeed.setEnabled(true);
        
        this.animalDiseaseStrength.setEnabled(true);
        this.animalMutationProbability.setEnabled(true);
        this.animalDiseaseSpreadRate.setEnabled(true);
        this.animalMutationType.setEnabled(true);
        
        this.plantDiseaseStrength.setEnabled(true);
        this.plantMutationProbability.setEnabled(true);
        this.plantDiseaseSpreadRate.setEnabled(true);
        this.plantMutationType.setEnabled(true);
        
        this.stopButton.setEnabled(false);
        this.simulationTimer.stop();
    }
    
    /**
     * Get's the population.
     * Convert's a Class Counter HashMap to a Color Counter HashMap.
     * @return the HashMap containing population per color.
     */
    public HashMap<Color, Counter> getPopulationDetails() 
    {
        HashMap<Class, Counter> classData = this.stats.getPopulation();
        HashMap<Color, Counter> colorData = new HashMap();
        
        for (Class c : classData.keySet()) {
            colorData.put(this.getColor(c), classData.get(c));
        }
        
        return colorData;
    }

    /**
     * Change the delay of the timer, which will change the simulation's speed.
     */
    private void changeSimulationSpeed() 
    {
        switch (this.simulationSpeed.getSelectedIndex()) {
            case 1:
                this.simulationTimer.setDelay(DEFAULT_SPEED * 10); // make 10 times slower.
                break;
            case 2:
                this.simulationTimer.setDelay(DEFAULT_SPEED * 2); // make 2 times slower.
                break;
            case 4:
                this.simulationTimer.setDelay(DEFAULT_SPEED / 2); // make 2 times faster.
                break;
            case 5:
                this.simulationTimer.setDelay(DEFAULT_SPEED / 100); // make 100 times faster.
                break;
            default:
                this.simulationTimer.setDelay(DEFAULT_SPEED); // set to default speed.
                break;
        }
    }
    
    /**
     * Change the animal stats.
     * Change the strengh of the disease, the disease spread rate, the chance of mutation spreading and the mutation type.
     * Depending on what option is selected in the JComboBox drop down for plants, the effects will be applied to the simulation.
     */
    private void changeAnimalStats() 
    {
        Strength strength;
        switch (this.animalDiseaseStrength.getSelectedIndex()) {
            case 1:
                strength = Strength.HIGH;
                break;
            case 2:
                strength = Strength.MEDIUM;
                break;
            case 3:
                strength = Strength.LOW;
                break;
            default:
               return;
        }
        
        Strength spread;
        switch (this.animalDiseaseSpreadRate.getSelectedIndex()) {
            case 1:
                spread = Strength.HIGH;
                break;
            case 2:
                spread = Strength.MEDIUM;
                break;
            case 3:
                spread = Strength.LOW;
                break;
            default:
                return;
        }
        
        Strength mutationStrength;
        switch (this.animalMutationProbability.getSelectedIndex()) {
            case 1:
                mutationStrength = Strength.HIGH;
                break;
            case 2:
                mutationStrength = Strength.MEDIUM;
                break;
            case 3:
                mutationStrength = Strength.LOW;
                break;
            default:
                return;
        }
        
        Mutation mutationType;
        switch (this.animalMutationType.getSelectedIndex()) {
            case 1:
                mutationType = Mutation.BREEDING_PROBABILITY;
                break;
            case 2:
                mutationType = Mutation.BREEDING_AGE;
                break;
            case 3:
                mutationType = Mutation.MAX_LITTER_SIZE;
                break;
            default:
                return;
        }
        
        Disease disease = new Disease(strength, spread, mutationStrength, mutationType, AffectedActor.ANIMAL);
        this.simulator.populateDisease(disease);
    }
    
    /**
     * Change the plant stats.
     * Change the strengh of the disease, the disease spread rate, the chance of mutation spreading and the mutation type.
     * Depending on what option is selected in the JComboBox drop down for plants, the effects will be applied to the simulation.
     */
    private void changePlantStats() 
    {
        Strength strength;
        switch (this.plantDiseaseStrength.getSelectedIndex()) {
            case 1:
                strength = Strength.HIGH;
                break;
            case 2:
                strength = Strength.MEDIUM;
                break;
            case 3:
                strength = Strength.LOW;
                break;
            default:
               return;
        }
        
        Strength spread;
        switch (this.plantDiseaseSpreadRate.getSelectedIndex()) {
            case 1:
                spread = Strength.HIGH;
                break;
            case 2:
                spread = Strength.MEDIUM;
                break;
            case 3:
                spread = Strength.LOW;
                break;
            default:
                return;
        }
        
        Strength mutationStrength;
        switch (this.plantMutationProbability.getSelectedIndex()) {
            case 1:
                mutationStrength = Strength.HIGH;
                break;
            case 2:
                mutationStrength = Strength.MEDIUM;
                break;
            case 3:
                mutationStrength = Strength.LOW;
                break;
            default:
                return;
        }
        
        Mutation mutationType;
        switch (this.plantMutationType.getSelectedIndex()) {
            case 1:
                mutationType = Mutation.BREEDING_PROBABILITY;
                break;
            case 2:
                mutationType = Mutation.BREEDING_AGE;
                break;
            case 3:
                mutationType = Mutation.MAX_LITTER_SIZE;
                break;
            default:
                return;
        }
        
        Disease disease = new Disease(strength, spread, mutationStrength, mutationType, AffectedActor.PLANT);
        this.simulator.populateDisease(disease);
    }
    
    /**
     * Show the current status of the field.
     * @param step Which iteration step it is.
     * @param field The field whose status is to be displayed.
     */
    public void showStatus(int step, Field field)
    {
        if(!isVisible()) {
            setVisible(true);
        }
            
        stepLabel.setText(STEP_PREFIX + step);
        stats.reset();
        
        fieldView.preparePaint();

        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Object animal = field.getObjectAt(row, col);
                if(animal != null) {
                    stats.incrementCount(animal.getClass());
                    fieldView.drawMark(col, row, getColor(animal.getClass()));
                }
                else {
                    fieldView.drawMark(col, row, EMPTY_COLOR);
                }
            }
        }
        stats.countFinished();
        
        // Populates and repaints the pie chart with new stats. 
        this.pieChartDiagram.setStats(this.getPopulationDetails());
        this.pieChartDiagram.repaint();

        population.setText(POPULATION_PREFIX + stats.getPopulationDetails(field));
        fieldView.repaint();
    }

    /**
     * Determine whether the simulation should continue to run.
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Field field)
    {
        return stats.isViable(field);
    }
}