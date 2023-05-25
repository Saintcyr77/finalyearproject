package Jacob.Sorts;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import static Jacob.Sorts.SortingVisualizer.blockWidth;

@SuppressWarnings("serial")
public class VisualizerFrame2 extends JFrame {



    private JLabel[] numberLabels;

    private final int MAX_SPEED = 1000;
    private final int MIN_SPEED = 1;
    private final int MAX_SIZE = 500;
    private final int MIN_SIZE = 1;
    private final int DEFAULT_SPEED = 20;
    private final int DEFAULT_SIZE = 100;

    private final String[] Sorts = {"Bubble", "Selection", "Insertion", "Gnome", "Merge", "Radix LSD", "Radix MSD", "Shell", "Quandrix", "Bubble(fast)", "Selection(fast)", "Insertion(fast)", "Gnome(fast)", "Cyclic","Heap"};

    private int sizeModifier;

    private JPanel wrapper;
    private JPanel arrayWrapper;
    private JPanel buttonWrapper;
    private JPanel[] squarePanels;
    private JButton start;
    private JComboBox<String> selection;
    private JSlider speed;
    private JSlider size;
    private JLabel speedVal;
    private JLabel sizeVal;
    private GridBagConstraints c;
    private JCheckBox stepped;

    private JPanel topBar;


    public VisualizerFrame2() {


        super("Sorting Visualizer");



        topBar = new HoverPanel();
        topBar.setBackground(Color.BLACK);
        add(topBar, BorderLayout.NORTH);

        // Add the "How It Works" button to the top bar
        JButton howItWorksButton = new JButton("How It Works");
        howItWorksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openHowItWorksDialog();
            }
        });
        howItWorksButton.setForeground(Color.BLACK);

// Set the hover foreground color of the button
        howItWorksButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                howItWorksButton.setForeground(Color.blue);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                howItWorksButton.setForeground(Color.BLACK);
            }
        });
        topBar.add(howItWorksButton);

        wrapper = new JPanel();
        wrapper = new JPanel(new FlowLayout());
//        wrapper.setLayout(new BorderLayout());
        buttonWrapper = new JPanel();

        // Create the complexity button
        JButton complexityButton = new JButton("Calculate Complexity");

// Add an action listener to the complexity button
        complexityButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateComplexity();
            }
        });


// Add the buttonWrapper panel to the wrapper panel
        wrapper.add(buttonWrapper, BorderLayout.SOUTH);

// Add the complexity button to the buttonWrapper panel
        buttonWrapper.add(complexityButton);

        add(wrapper);





        // Create a JPanel for the top bar
//        JPanel topBar = new JPanel();
//        topBar.setBackground(Color.gray);

// Create a JLabel with the statement
        JLabel statementLabel = new JLabel("Sorting Visualizer made by Sarthak Bhatt");
        statementLabel.setForeground(Color.WHITE);
        statementLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topBar.add(statementLabel);

// Add the top bar panel to the frame
        add(topBar, BorderLayout.NORTH);


        start = new JButton("Start");
//        buttonWrapper = new JPanel();
        arrayWrapper = new JPanel();
        wrapper = new JPanel();
        selection = new JComboBox<String>();
        speed = new JSlider(MIN_SPEED, MAX_SPEED, DEFAULT_SPEED);
        size = new JSlider(MIN_SIZE, MAX_SIZE, DEFAULT_SIZE);
        speedVal = new JLabel("Speed: 20 ms");
        sizeVal = new JLabel("Size: 100 values");
        stepped = new JCheckBox("Stepped Values");
        c = new GridBagConstraints();

        for (String s : Sorts) selection.addItem(s);

        arrayWrapper.setLayout(new BoxLayout(arrayWrapper, BoxLayout.Y_AXIS));
        wrapper.setLayout(new BorderLayout());

        c.insets = new Insets(0, 1, 0, 1);
        c.anchor = GridBagConstraints.NORTH;

//        start.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                SortingVisualizer.startSort((String) selection.getSelectedItem());
//            }
//        });
        start.addActionListener(new ActionListener() {
            private Color[] colors = {Color.red, Color.blue, Color.green};
            private int colorIndex = 0;

            public void actionPerformed(ActionEvent e) {
                colorIndex = 0; // Reset color index

                // Iterate over the squarePanels and assign the colors in a cyclic manner
                for (int i = 0; i < squarePanels.length; i++) {
                    squarePanels[i].setBackground(colors[colorIndex]);
                    colorIndex = (colorIndex + 1) % colors.length; // Move to the next color
                }

                SortingVisualizer.startSort((String) selection.getSelectedItem());
            }
        });


        stepped.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SortingVisualizer.stepped = stepped.isSelected();
            }
        });

        speed.setMinorTickSpacing(10);
        speed.setMajorTickSpacing(100);
        speed.setPaintTicks(true);

        speed.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                speedVal.setText(("Speed: " + Integer.toString(speed.getValue()) + "ms"));
                validate();
                SortingVisualizer.sleep = speed.getValue();
            }
        });

        size.setMinorTickSpacing(10);
        size.setMajorTickSpacing(100);
        size.setPaintTicks(true);

        size.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                sizeVal.setText(("Size: " + Integer.toString(size.getValue()) + " values"));
                validate();
                SortingVisualizer.sortDataCount = size.getValue();
            }
        });

        buttonWrapper.add(stepped);
        buttonWrapper.add(speedVal);
        buttonWrapper.add(speed);
        buttonWrapper.add(sizeVal);
        buttonWrapper.add(size);
        buttonWrapper.add(start);
        buttonWrapper.add(selection);

        wrapper.add(buttonWrapper, BorderLayout.SOUTH);
        wrapper.add(arrayWrapper);

        add(wrapper);

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent e) {
                // Reset the sizeModifier
                // 90% of the windows height, divided by the size of the sorted array.
                sizeModifier = (int) ((getHeight() * 0.9) / (squarePanels.length));
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                // Do nothing
            }

            @Override
            public void componentShown(ComponentEvent e) {
                // Do nothing
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                // Do nothing
            }

        });

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    class HoverPanel extends JPanel {
        private boolean isHovered = false;

        public HoverPanel() {
            // Add a mouse listener to track mouse enter and exit events
            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    isHovered = true;
                    repaint();  // Update the panel's appearance
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    isHovered = false;
                    repaint();  // Update the panel's appearance
                }
            });
        }

        // Override the paintComponent method to apply the hover effect
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (isHovered) {
                // Set the background color when the panel is hovered
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    }

    private void openHowItWorksDialog() {
        // Create a JTextArea to display the text content
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        // Add a JScrollPane to enable scrolling if the text content exceeds the dialog box size
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Set the preferred size of the scroll pane to fit the content
        scrollPane.setPreferredSize(new Dimension(500, 400));

        // Set the text content with the explanations of how the sorting algorithms work
        String algorithmExplanations = getAlgorithmExplanations();
        textArea.setText(algorithmExplanations);

        // Show the dialog box with the scroll pane
        JOptionPane.showMessageDialog(this, scrollPane, "How It Works", JOptionPane.PLAIN_MESSAGE);
    }


    // preDrawArray reinitializes the array of panels that represent the values. They are set based on the size of the window.
    public void preDrawArray(Integer[] squares) {
        squarePanels = new JPanel[SortingVisualizer.sortDataCount];
        numberLabels = new JLabel[SortingVisualizer.sortDataCount];

        arrayWrapper.removeAll();

        // 90% of the windows height, divided by the size of the sorted array.
        sizeModifier = (int) ((getHeight() * 0.9) / (squarePanels.length));

        for (int i = 0; i < SortingVisualizer.sortDataCount; i++) {
            squarePanels[i] = new JPanel();
            squarePanels[i].setPreferredSize(new Dimension(blockWidth, squares[i] * sizeModifier));

            numberLabels[i] = new JLabel(String.valueOf(squares[i]));
            numberLabels[i].setHorizontalAlignment(SwingConstants.CENTER);

            JPanel panelWrapper = new JPanel();
            panelWrapper.setLayout(new BorderLayout());
            panelWrapper.add(numberLabels[i], BorderLayout.NORTH);
            panelWrapper.add(squarePanels[i], BorderLayout.CENTER);

            arrayWrapper.add(panelWrapper);

            // Set the background color of the panel based on the value of squares[i]
            Color barColor = getColorForValue(squares[i]);
            squarePanels[i].setBackground(barColor);
        }

        repaint();
        revalidate();
    }

    private Color getColorForValue(int value) {
        // Customize the color mapping based on your requirements
        // This example uses a gradient of blue to green to red
        int red = (int) (255 * (value / (double) MAX_SIZE));
        int green = (int) (255 * (1 - value / (double) MAX_SIZE));
        int blue = (int) (255 * (1 - value / (double) MAX_SIZE));
        return new Color(red, green, blue);
    }



    public void reDrawArray(Integer[] x) {
        reDrawArray(x, -1);
    }

    public void reDrawArray(Integer[] x, int y) {
        reDrawArray(x, y, -1);
    }

    public void reDrawArray(Integer[] x, int y, int z) {
        reDrawArray(x, y, z, -1);
    }

    // reDrawArray does similar to preDrawArray except it does not reinitialize the panel array.
    public void reDrawArray(Integer[] squares, int working, int comparing, int reading) {
        arrayWrapper.removeAll();
        for (int i = 0; i < squarePanels.length; i++) {
            squarePanels[i] = new JPanel();
            squarePanels[i].setPreferredSize(new Dimension(blockWidth, squares[i] * sizeModifier));

            // Set the background color of the panel
            if (i == working) {
                squarePanels[i].setBackground(Color.green);
            } else if (i == comparing) {
                squarePanels[i].setBackground(Color.red);
            } else if (i == reading) {
                squarePanels[i].setBackground(Color.yellow);
            } else {
                squarePanels[i].setBackground(Color.blue);
            }

            // Update the text of the number label
            numberLabels[i].setText(String.valueOf(squares[i]));

            JPanel panelWrapper = new JPanel();
            panelWrapper.setLayout(new BorderLayout());
            panelWrapper.add(numberLabels[i], BorderLayout.NORTH);
            panelWrapper.add(squarePanels[i], BorderLayout.CENTER);

            arrayWrapper.add(panelWrapper);
        }
        repaint();
        revalidate();
    }

    private String getAlgorithmExplanations() {
        String explanations = "Explanation of sorting algorithms:\n\n" +
                "Bubble Sort:\n" +
                "Bubble sort works by repeatedly swapping adjacent elements if they are in the wrong order.\n" +
                "It continues this process until the entire array is sorted.\n\n" +
                "Selection Sort:\n" +
                "Selection sort divides the input array into a sorted and an unsorted region.\n" +
                "It repeatedly selects the smallest element from the unsorted region and swaps it with the leftmost element in the unsorted region.\n" +
                "This expands the sorted region by one element.\n\n" +
                // Include explanations for other sorting algorithms here...
                // Example:
                // "Insertion Sort:\n" +
                // "Insertion sort builds the final sorted array one item at a time.\n" +
                // "It takes each element from the input array and inserts it into its correct position in the sorted array.\n\n" +
                // ... and so on
                "For more detailed explanations and visualizations, please refer to reputable resources and textbooks on sorting algorithms.";

        return explanations;
    }

    private void calculateComplexity() {
        StringBuilder complexityResult = new StringBuilder();

        // Iterate through each sorting algorithm
        for (String algorithm : Sorts) {
            int n = SortingVisualizer.sortDataCount;
            long startTime = System.nanoTime();

            // Call the sorting algorithm
            SortingVisualizer.startSort(algorithm);

            // Measure the elapsed time
            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;

            // Append the algorithm and its complexity to the result string
            complexityResult.append(algorithm).append(": O(").append(elapsedTime).append(")\n");
        }

        // Display the complexity result in a dialog box
        JOptionPane.showMessageDialog(this, complexityResult.toString(), "Algorithm Complexity", JOptionPane.INFORMATION_MESSAGE);
    }

}


