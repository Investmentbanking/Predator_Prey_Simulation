import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

/**
 * This class handles all the GUI components such as the JPanels, JComboBox's, JButtons.
 * This will be loaded onto the Simulator.
 * 
 * @author Ayesha Dorani
 * @version 2022.03.02
 */
public class GUILoader 
{
    public static int width = 0;
    public static int height = 0;
    
    /**
     * Main GUI method which loads all the GUI components.
     * Set's the title of the JFrame.
     * Creates the menubar, JPanels, tabbedPane, JButtons, JComboBoxes and container.
     * @param sv The SimulatorView's class object.
     */
    public static void loadMainGUI(SimulatorView sv) 
    {
        // Set title of the window.
        sv.setTitle("Predator Prey Simulation");
        // If the user hits the X on the window, it halts the program.
        sv.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // CREATE MENUBAR. 
        menuBar(sv);
        
        // CREATE PANELS.
        JPanel topPanel = createTopPanel(sv);
        JPanel middlePanel = createMiddlePanel(sv);
        JPanel bottomPanel = createBottomPanel(sv);
        JPanel gridPanel = createGridPanel1(sv, middlePanel);
        JPanel gridPanel2 = createGridPanel2(sv, middlePanel);
        JPanel gridPanel3 = createGridPanel3(sv, middlePanel);
        
        // Create tabbed pane. 
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.addTab("Animals", gridPanel2);
        tabbedPane.addTab("Plants", gridPanel3);
        tabbedPane.setBackground(Color.lightGray);
        tabbedPane.setPreferredSize(new Dimension(50,50));
        tabbedPane.setFocusable(false);

        // CREATE BUTTONS.
        createButtons(sv, bottomPanel);
        
        // CREATE JCOMBOBOX's.
        createAnimalJComboBox(sv, gridPanel2);
        createPlantJComboBox(sv, gridPanel3);
        createSpeedJComboBox(sv, bottomPanel);
        
        // Add JComboBox for simulation speed in bottom panel.
        bottomPanel.add(sv.getSimulationSpeed());
        
        // Bind everything to the container.
        // The main container for the window.
        Container container = sv.getContentPane();
        container.add(topPanel, BorderLayout.PAGE_START);
        container.add(middlePanel, BorderLayout.LINE_START);
        container.add(bottomPanel, BorderLayout.PAGE_END);
        container.add(tabbedPane);
    }
    
    /**
     * Create's bottom panel at the bottom of JFrame and returns it. 
     * @param sv The SimulatorView's class object.
     * @return bottomPanel Bottom panel that was created.
     */
    private static JPanel createBottomPanel(SimulatorView sv) 
    {
        JPanel bottomPanel = new JPanel(new GridLayout(1, 1));
        bottomPanel.setBackground(Color.cyan);
        
        return bottomPanel;
    }
    
    /**
     * Create's a panel inside the middle panel and returns it.
     * @param sv The SimulatorView's class object.
     * @param middlePanel Passes JPanel as parameter.
     */
    private static JPanel createGridPanel1(SimulatorView sv, JPanel middlePanel) 
    {
        // Creates a panel in middle panel.
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(7,1,15,15));
        gridPanel.setBackground(Color.lightGray);
        middlePanel.add(gridPanel);
        
        return gridPanel;
    }
    
    /**
     * Create's a new panel inside middle panel and returns it.
     * @param sv The SimulatorView's class object.
     * @param middlePanel Passes JPanel as parameter.
     * @return gridPanel2 The grid panel that was created.
     */
    private static JPanel createGridPanel2(SimulatorView sv, JPanel middlePanel) 
    {
        // Creates second panel in middle panel.
        JPanel gridPanel2 = new JPanel();
        gridPanel2.setLayout(new GridLayout(10,1,0,0));
        gridPanel2.setBackground(Color.lightGray);
        middlePanel.add(gridPanel2);
        
        return gridPanel2;
    }
    
    /**
     * Create's a new grid panel inside the middle panel.
     * @param sv The SimulatorView's class object.
     * @param middlePanel Passes JPanel as paramter.
     * @return gridPanel3 The grid panel that was made.
     */
    private static JPanel createGridPanel3(SimulatorView sv, JPanel middlePanel) 
    {
        // Creates third panel in middle panel.
        JPanel gridPanel3 = new JPanel();
        gridPanel3.setLayout(new GridLayout(10,1,0,0));
        gridPanel3.setBackground(Color.lightGray);
        middlePanel.add(gridPanel3);
        
        return gridPanel3;
    }
    
    /**
     * Creates middle panel.
     * @param sv The SimulatorView's class object.
     */
    private static JPanel createMiddlePanel(SimulatorView sv) 
    {
        // Creates middle panel.
        JPanel middlePanel = new JPanel();
        middlePanel.setBackground(Color.lightGray);
        sv.setFieldView(new FieldView(height, width));
        // Add's field view to middle panel.
        middlePanel.add(sv.getFieldView());
        
        return middlePanel;
    }
    
    /**
     * Create's top panel.
     * @param sv The SimulatorView's class object.
     */
    private static JPanel createTopPanel(SimulatorView sv) 
    {
        // Creates the top panel.
        JPanel topPanel = new JPanel(new GridLayout(1, 1, 8, 8));
        topPanel.setBackground(Color.lightGray);
        sv.setStepLabel(new JLabel(sv.getSTEP_PREFIX(), SwingConstants.LEFT));
        topPanel.add(sv.getStepLabel(), SwingConstants.CENTER);
        sv.setPopulation(new JLabel(sv.getPOPULATION_PREFIX(), SwingConstants.RIGHT));
        topPanel.add(sv.getPopulation());
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        
        return topPanel;
    }
    
    /**
     * Creates JComboBox which allows the user to change the speed of the Simulation.
     * @param sv The SimulatorView's class object.
     * @param bottomPanel Instance of JPanel.
     */
    private static void createSpeedJComboBox(SimulatorView sv, JPanel bottomPanel) 
    {
        // Drop down button to change the speed of simulation.
        String[] speeds = {"Select Simulation Speed", "Very Slow", "Slow", "Normal", "Fast", "Very Fast"};
        sv.setSimulationSpeed(new JComboBox<>(speeds));
        sv.getSimulationSpeed().setSelectedIndex(0);
        sv.getSimulationSpeed().addActionListener(sv);
    }
    
    /**
     * Create's JCombo boxes and add's them to a grid panel
     * @param sv The SimulatorView's class object.
     * @param gridPanel2 Instance of JPanel.
     */
    private static void createAnimalJComboBox(SimulatorView sv, JPanel gridPanel2)
    {
        // Drop down button to change strengh of disease.
        String[] diseaseStrength = {"Please select one...","High", "Medium", "Low"};
        sv.setAnimalDiseaseStrength(new JComboBox<>(diseaseStrength));
        sv.getAnimalDiseaseStrength().setSelectedIndex(0);
        JLabel label1 = new JLabel("Select Disease Strength: ");
        gridPanel2.add(label1);
        gridPanel2.add(sv.getAnimalDiseaseStrength());
        sv.getAnimalDiseaseStrength().addActionListener(sv);
        
        // Drop down button to change chance of disease spreading.
        String[] diseaseSpread = {"Please select one...","High", "Medium", "Low"};
        sv.setAnimalDiseaseSpreadRate(new JComboBox<>(diseaseSpread));
        sv.getAnimalDiseaseSpreadRate().setSelectedIndex(0);
        JLabel label2 = new JLabel("Select Chance of Disease Spreading: ");
        gridPanel2.add(label2);
        gridPanel2.add(sv.getAnimalDiseaseSpreadRate());
        sv.getAnimalDiseaseSpreadRate().addActionListener(sv);
        
        // Drop down button to change mutation probability.
        String[] mutationProbability = {"Please select one...", "High", "Medium", "Low"};
        sv.setAnimalMutationProbability(new JComboBox<>(mutationProbability));
        sv.getAnimalMutationProbability().setSelectedIndex(0);
        JLabel label3 = new JLabel ("Select Mutation Probability: ");
        gridPanel2.add(label3);
        gridPanel2.add(sv.getAnimalMutationProbability());
        sv.getAnimalMutationProbability().addActionListener(sv);

        // Drop down button to change mutation type.
        String[] mutationType = {"Please select one...", "Breeding Probability", "Breeding Age", "Max Litter Size"};
        sv.setAnimalMutationType(new JComboBox<>(mutationType));
        sv.getAnimalMutationType().setSelectedIndex(0);
        JLabel label4 = new JLabel ("Select Mutation Type: ");
        gridPanel2.add(label4);
        gridPanel2.add(sv.getAnimalMutationType());
        sv.getAnimalMutationType().addActionListener(sv);
    }
    
    /**
     * Create's JComboBox's for the plants which will allow the user to change the strength of disease, disease spread rate,
     * the probability of the disease mutating and the mutation type.
     * @param sv The SimulatorView's class object.
     * @param gridPanel3 Instance of JPanel.
     */
    private static void createPlantJComboBox(SimulatorView sv, JPanel gridPanel3) 
    {
        // Drop down button to change strengh of disease.
        String[] plantDiseaseStrength = {"Please select one...", "High", "Medium", "Low"};
        sv.setPlantDiseaseStrength(new JComboBox<>(plantDiseaseStrength));
        sv.getPlantDiseaseStrength().setSelectedIndex(0);
        JLabel label1 = new JLabel("Select Disease Strength: ");
        gridPanel3.add(label1);
        gridPanel3.add(sv.getPlantDiseaseStrength());
        sv.getPlantDiseaseStrength().addActionListener(sv);
        
        // Drop down button to change chance of disease spreading.
        String[] diseaseSpread = {"Please select one...", "High", "Medium", "Low"};
        sv.setPlantDiseaseSpreadRate(new JComboBox<>(diseaseSpread));
        sv.getPlantDiseaseSpreadRate().setSelectedIndex(0);
        JLabel label2 = new JLabel("Select Chance of Disease Spreading: ");
        gridPanel3.add(label2);
        gridPanel3.add(sv.getPlantDiseaseSpreadRate());
        sv.getPlantDiseaseSpreadRate().addActionListener(sv);
        
        // Drop down button to change mutation probability.
        String[] mutationProbability = {"Please select one...", "High", "Medium", "Low"};
        sv.setPlantMutationProbability(new JComboBox<>(mutationProbability));
        sv.getPlantMutationProbability().setSelectedIndex(0);
        JLabel label3 = new JLabel ("Select Mutation Probability: ");
        gridPanel3.add(label3);
        gridPanel3.add(sv.getPlantMutationProbability());
        sv.getPlantMutationProbability().addActionListener(sv);

        // Drop down button to change mutation type.
        String[] mutationType = {"Please select one...", "Breeding Probability", "Breeding Age", "Max Litter Size"};
        sv.setPlantMutationType(new JComboBox<>(mutationType));
        sv.getPlantMutationType().setSelectedIndex(0);
        JLabel label4 = new JLabel ("Select Mutation Type: ");
        gridPanel3.add(label4);
        gridPanel3.add(sv.getPlantMutationType());
        sv.getPlantMutationType().addActionListener(sv);
    }
    
    /**
     * Create's buttons and add's them to the bottom panel in the JFrame.
     * Removes highlighted box around buttons using built in setFocusPainted() method.
     * @param sv The SimulatorView's class object.
     * @param bottomPanel Instance of JPanel.
     */
    private static void createButtons(SimulatorView sv, JPanel bottomPanel) 
    {
        // Go button, runs Simulation.
        sv.setGoButton(new JButton("Go"));
        bottomPanel.add(sv.getGoButton());
        sv.getGoButton().addActionListener(sv);
        sv.getGoButton().setFocusPainted(false); 

        // Add's stop button to halt the program.
        sv.setStopButton(new JButton("Stop"));
        bottomPanel.add(sv.getStopButton());
        sv.getStopButton().addActionListener(sv);
        sv.getStopButton().setFocusPainted(false);

        // Add's next step button which increments step by 1.
        sv.setNextStepButton(new JButton("Next Step"));
        bottomPanel.add(sv.getNextStepButton());
        sv.getNextStepButton().addActionListener(sv);
        sv.getNextStepButton().setFocusPainted(false);
        
        // Add's reset button to re-start the program.
        sv.setResetButton(new JButton("Reset"));
        bottomPanel.add(sv.getResetButton());
        sv.getResetButton().addActionListener(sv);
        sv.getResetButton().setFocusPainted(false);

        // Add's pie chart button.
        sv.setPieChartButton(new JButton("Pie Chart"));
        bottomPanel.add(sv.getPieChartButton());
        sv.getPieChartButton().addActionListener(sv);
        sv.getPieChartButton().setFocusPainted(false);

        // Add's quit button to stop and exit the program.
        sv.setQuitButton(new JButton("Quit"));
        bottomPanel.add(sv.getQuitButton());
        sv.getQuitButton().addActionListener(sv);
        sv.getQuitButton().setFocusPainted(false);
    }
    
    /**
     * Create's menu bar in the JFrame and adds a View menu so that the user can control what animals,
     * are shown in the simulation.
     * @param sv The SimulatorView's class object.
     */
    private static void menuBar(SimulatorView sv) 
    {
        // Creates new menu bar.
        JMenuBar menubar = new JMenuBar();
        
        // Creates menu in menu bar.
        JMenu viewMenu = new JMenu("View");
        
        // Adds Monkeys to View menu.
        JCheckBoxMenuItem viewMonkeys = new JCheckBoxMenuItem("Show Monkeys");
        viewMonkeys.setSelected(true);
        viewMonkeys.addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                sv.getSimulator().setViewMonkeys(true);
            } else {
                sv.getSimulator().setViewMonkeys(false);
            }
            sv.stopSimulation();
            sv.getSimulator().reset();
            sv.showStatus(sv.getSimulator().getStep(), sv.getSimulator().getField());
        });
        
        // Adds Trees to View menu.
        JCheckBoxMenuItem viewTrees = new JCheckBoxMenuItem("Show Trees");
        viewTrees.setSelected(true);
        viewTrees.addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                sv.getSimulator().setViewTrees(true);
            } else {
                sv.getSimulator().setViewTrees(false);
            }
            sv.stopSimulation();
            sv.getSimulator().reset();
            sv.showStatus(sv.getSimulator().getStep(), sv.getSimulator().getField());
        });

        // Adds Fishes to View menu.
        JCheckBoxMenuItem viewFishes = new JCheckBoxMenuItem("Show Fishes");
        viewFishes.setSelected(true);
        viewFishes.addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                sv.getSimulator().setViewFishes(true);
            } else {
                sv.getSimulator().setViewFishes(false);
            }
            sv.stopSimulation();
            sv.getSimulator().reset();
            sv.showStatus(sv.getSimulator().getStep(), sv.getSimulator().getField());
        });

        // Adds Eagles to View menu.
        JCheckBoxMenuItem viewEagles = new JCheckBoxMenuItem("Show Eagles");
        viewEagles.setSelected(true);
        viewEagles.addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                sv.getSimulator().setViewEagles(true);
            } else {
                sv.getSimulator().setViewEagles(false);
            }
            sv.stopSimulation();
            sv.getSimulator().reset();
            sv.showStatus(sv.getSimulator().getStep(), sv.getSimulator().getField());
        });

        // Adds Algae to the View menu.
        JCheckBoxMenuItem viewAlgae = new JCheckBoxMenuItem("Show Algae");
        viewAlgae.setSelected(true);
        viewAlgae.addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                sv.getSimulator().setViewAlgae(true);
            } else {
                sv.getSimulator().setViewAlgae(false);
            }
            sv.stopSimulation();
            sv.getSimulator().reset();
            sv.showStatus(sv.getSimulator().getStep(), sv.getSimulator().getField());
        });

        // Adds Jaguars to the View menu.
        JCheckBoxMenuItem viewJaguars = new JCheckBoxMenuItem("Show Jaguars");
        viewJaguars.setSelected(true);
        viewJaguars.addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                sv.getSimulator().setViewJaguars(true);
            } else {
                sv.getSimulator().setViewJaguars(false);
            }
            sv.stopSimulation();
            sv.getSimulator().reset();
            sv.showStatus(sv.getSimulator().getStep(), sv.getSimulator().getField());
        });

        // Adds Sloths to the View menu
        JCheckBoxMenuItem viewSloths = new JCheckBoxMenuItem("Show Sloths");
        viewSloths.setSelected(true);
        viewSloths.addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                sv.getSimulator().setViewSloths(true);
            } else {
                sv.getSimulator().setViewSloths(false);
            }
            sv.stopSimulation();
            sv.getSimulator().reset();
            sv.showStatus(sv.getSimulator().getStep(), sv.getSimulator().getField());
        });

        // Adds all of the actors to the View menu.    
        viewMenu.add(viewEagles);
        viewMenu.add(viewJaguars);
        viewMenu.add(viewAlgae);
        viewMenu.add(viewMonkeys);
        viewMenu.add(viewFishes);
        viewMenu.add(viewSloths);
        viewMenu.add(viewTrees);
        
        // Sets menu bar in simulator view.
        sv.setJMenuBar(menubar);
        
        // Adds menu to menu bar.
        menubar.add(viewMenu);
    }
}