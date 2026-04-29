import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * App is the graphical user interface for the Fortune Teller application.
 * It allows users to view, add, remove, and edit fortunes using a Swing-based GUI.
 * All fortune logic is handled by the FortuneManager class.
 *
 * @author Han Yardimci
 * @author Yushi Kawashima
 * @date 04/21/2026
 */
public class App {

    /** The main application window. */
    private JFrame frame;

    /** Manages the fortune data and operations. */
    private FortuneManager manager;

    /** Displays output messages and fortune results to the user. */
    private JTextArea outputArea;

    /** Input field for entering a new fortune to add. */
    private JTextField inputField;

    /** Input field for entering the index of the fortune to remove. */
    private JTextField removeField;

    /** Input field for entering the index of the fortune to edit. */
    private JTextField editIndexField;

    /** Input field for entering the new text when editing a fortune. */
    private JTextField editTextField;

    /**
     * Constructs the App window, initializes the FortuneManager,
     * and builds the full graphical user interface.
     */
    public App() {

        manager = new FortuneManager();
        frame = new JFrame("Fortune Teller");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //Top: Title + Instructions 
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 1));

        JLabel title = new JLabel("Fortune Teller App", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel instructions = new JLabel(
            "Use the buttons below to get a fortune, add, remove, or edit fortunes.",
            JLabel.CENTER
        );
        instructions.setFont(new Font("Arial", Font.PLAIN, 12));

        topPanel.add(title);
        topPanel.add(instructions);
        frame.add(topPanel, BorderLayout.NORTH);

        //Center: Output Area 
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        frame.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        //Bottom: Controls Panel
        inputField     = new JTextField();
        removeField    = new JTextField();
        editIndexField = new JTextField();
        editTextField  = new JTextField();

        JButton randomBtn  = new JButton("Get Random Fortune");
        JButton addBtn     = new JButton("Add Fortune");
        JButton showAllBtn = new JButton("Show All Fortunes");
        JButton removeBtn  = new JButton("Remove Fortune");
        JButton editBtn    = new JButton("Edit Fortune");

        //Row 1: Random + Show All (full-width buttons)
        JPanel row1 = new JPanel(new GridLayout(1, 2, 5, 0));
        row1.add(randomBtn);
        row1.add(showAllBtn);

        //Row 2: Add Fortune
        JPanel row2 = new JPanel(new BorderLayout(5, 0));
        row2.add(new JLabel("New Fortune:"), BorderLayout.WEST);
        row2.add(inputField, BorderLayout.CENTER);
        row2.add(addBtn, BorderLayout.EAST);

        //Row 3: Remove Fortune
        JPanel row3 = new JPanel(new BorderLayout(5, 0));
        row3.add(new JLabel("Remove Index:"), BorderLayout.WEST);
        row3.add(removeField, BorderLayout.CENTER);
        row3.add(removeBtn, BorderLayout.EAST);

        //Row 4: Edit Fortune (index + new text + button)
        JPanel row4 = new JPanel(new BorderLayout(5, 0));
        JPanel editFields = new JPanel(new GridLayout(1, 2, 5, 0));
        editFields.add(editIndexField);
        editFields.add(editTextField);
        row4.add(new JLabel("Edit Index | New Text:"), BorderLayout.WEST);
        row4.add(editFields, BorderLayout.CENTER);
        row4.add(editBtn, BorderLayout.EAST);

        //Outer bottom panel stacking all rows
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(4, 1, 0, 6));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        bottomPanel.add(row1);
        bottomPanel.add(row2);
        bottomPanel.add(row3);
        bottomPanel.add(row4);

        frame.add(bottomPanel, BorderLayout.SOUTH);


        /**
         * Gets and displays a random fortune.
         * Shows an error if the fortune list is empty.
         */
        randomBtn.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                try {
                    outputArea.setText(manager.getRandomFortune());
                } catch (IllegalStateException ex) {
                    outputArea.setText("Error: No fortunes available. Please add one first.");
                }
            }
        });

        /**
         * Adds the text from inputField as a new fortune.
         * Shows an error if the input field is empty.
         */
        addBtn.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                try {
                    String text = inputField.getText().trim();
                    if (text.isEmpty()) {
                        throw new IllegalArgumentException("Input cannot be empty.");
                    }
                    manager.addFortune(text);
                    outputArea.setText("Fortune added successfully!");
                    inputField.setText("");
                } catch (IllegalArgumentException ex) {
                    outputArea.setText("Error: Please enter a fortune before clicking Add.");
                }
            }
        });

        /**
         * Displays all fortunes with their index numbers.
         * Shows a message if there are no fortunes to display.
         */
        showAllBtn.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                outputArea.setText("");
                java.util.ArrayList<String> all = manager.getAllFortunes();
                if (all.isEmpty()) {
                    outputArea.setText("No fortunes in the list.");
                    return;
                }
                for (int i = 0; i < all.size(); i++) {
                    outputArea.append(i + ": " + all.get(i) + "\n");
                }
            }
        });

        /**
         * Removes the fortune at the index entered in removeField.
         * Shows an error if the input is not a valid number or index.
         */
        removeBtn.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                try {
                    int index = Integer.parseInt(removeField.getText().trim());
                    manager.removeFortune(index);
                    outputArea.setText("Fortune at index " + index + " removed successfully!");
                    removeField.setText("");
                } catch (NumberFormatException ex) {
                    outputArea.setText("Please enter a valid number for the index.");
                } catch (IndexOutOfBoundsException ex) {
                    outputArea.setText("Error: Index not found. Use 'Show All Fortunes' to see valid indices.");
                }
            }
        });

        /**
         * Replaces the fortune at the given index with the new text entered.
         * Shows an error if the index is invalid or the new text is empty.
         */
        editBtn.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                try {
                    int index = Integer.parseInt(editIndexField.getText().trim());
                    String newText = editTextField.getText().trim();
                    if (newText.isEmpty()) {
                        throw new IllegalArgumentException("New fortune text cannot be empty.");
                    }
                    manager.editFortune(index, newText);
                    outputArea.setText("Fortune at index " + index + " updated successfully!");
                    editIndexField.setText("");
                    editTextField.setText("");
                } catch (NumberFormatException ex) {
                    outputArea.setText("Error: Please enter a valid number for the edit index.");
                } catch (IndexOutOfBoundsException ex) {
                    outputArea.setText("Error: Index not found. Use 'Show All Fortunes' to see valid indices.");
                } catch (IllegalArgumentException ex) {
                    outputArea.setText("Error: Please enter new fortune text before clicking Edit.");
                }
            }
        });

        frame.setVisible(true);
    }

    /**
     * 
     * Creates and displays the App window.
     *
     * @param args command-line arguments (no need to use for this program)
     */
    public static void main(String[] args) {
        new App();
    }
}