/**
 * CalculatorView.java : View for calculator display and buttons
 *
 * @author Nery Chapeton-Lamas
 * @version 1.0
 *
 */

package views;

import models.CalculatorInterface;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class CalculatorView extends JPanel implements ActionListener {
    public static final int EVAL_OP = 0, ADD_OP = 1, SUB_OP = 2, MULT_OP = 3, DIV_OP = 4, LEFT_PAREN = 5, RIGHT_PAREN = 6;
    public static final char[] OPERATORS = {'=','+','-','*','/','(',')'};
    public static final String CLEAR = "CLEAR";
    public static final int BUTTONS_PANEL_ROWS = 4, BUTTONS_PANEL_COLS = 5;
    public static final String DISPLAY_START = "CS113 CALC ^_^";

    private JLabel displayLabel;
    private JButton[] digitButtons;
    private JButton[] operatorButtons;
    private JButton clearButton;

    private CalculatorInterface calc;

    /**
     * View constructor that initializes all GUI objects and builds view
     *
     * @param calc object that implements CalculatorInterface to use for evaluation of expressions (model)
     */
    public CalculatorView(CalculatorInterface calc) {
        super(); //instantiate super class JPanel stuff

        this.calc = calc;

        //instantiate GUI objects
        this.displayLabel = new JLabel(DISPLAY_START);
        this.clearButton = new JButton(CLEAR);
        this.clearButton.addActionListener(this);

        this.digitButtons = new JButton[10];
        for(int i = 0; i < this.digitButtons.length; i++) {
            this.digitButtons[i] = new JButton(""+i);
            this.digitButtons[i].addActionListener(this);
        }

        this.operatorButtons = new JButton[OPERATORS.length];
        for(int i = 0; i < this.operatorButtons.length; i++) {
            this.operatorButtons[i] = new JButton(""+OPERATORS[i]);
            this.operatorButtons[i].addActionListener(this);
        }

        this.buildPanelLook();
    }

    /**
     * Getter for display contents
     *
     * @return display contents
     */
    public String getDisplay() {
        return this.displayLabel.getText();
    }

    /**
     * Setter for display contents
     *
     * @param display set calculator display to this value
     */
    public void setDisplay(String display) {
        this.displayLabel.setText(display);
    }

    /**
     * Helper sets up JPanel overall layout, adding top display and bottom button panels
     */
    private void buildPanelLook()
    {
        this.setLayout( new BorderLayout() );
        this.add( this.buildDisplayPanel(), BorderLayout.NORTH );
        this.add( this.buildButtonsPanel(), BorderLayout.SOUTH );
    }

    /**
     * Helper sets up top display panel
     *
     * @return JPanel object with custom font and look
     */
    private JPanel buildDisplayPanel() {
        JPanel displayPanel = new JPanel();

        displayPanel.setLayout( new FlowLayout() );
        displayPanel.setBackground( new Color(248,216,0) );
        displayPanel.add(this.displayLabel);

        //add custom font
        try {
            Font calcFont = Font.createFont(Font.TRUETYPE_FONT, new File("resources/digital7mono.ttf"));
            this.displayLabel.setFont(calcFont.deriveFont(32f));
            this.displayLabel.setBorder(new CompoundBorder( this.displayLabel.getBorder(),
                    new EmptyBorder(5, 5, 10, 5)));
        } catch(IOException ioe) {
            System.err.println("ERROR: font file not found. did you mess with the resources folder? Did you import correctly? Shutting down...");
            System.exit(0);
        } catch(FontFormatException ffe) {
            System.err.println("ERROR: bad font. did you replace the file? corrupted in clone? Shutting down...");
            System.exit(0);
        }

        return displayPanel;
    }

    /**
     * Helper sets up bottom button panel
     *
     * @return JPanel object with digit buttons, operator buttons, and clear button.
     */
    private JPanel buildButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout( new GridLayout(BUTTONS_PANEL_ROWS,BUTTONS_PANEL_COLS));

        //first row
        for(int i = 7; i <= 9; i++) {
            buttonsPanel.add(this.digitButtons[i]);
        }
        buttonsPanel.add(this.operatorButtons[ADD_OP]);
        buttonsPanel.add(this.operatorButtons[SUB_OP]);

        //second row
        for(int i = 4; i <= 6; i++) {
            buttonsPanel.add(this.digitButtons[i]);
        }
        buttonsPanel.add(this.operatorButtons[MULT_OP]);
        buttonsPanel.add(this.operatorButtons[DIV_OP]);

        //third row
        for(int i = 1; i <= 3; i++) {
            buttonsPanel.add(this.digitButtons[i]);
        }
        buttonsPanel.add(this.operatorButtons[LEFT_PAREN]);
        buttonsPanel.add(this.operatorButtons[RIGHT_PAREN]);

        //fourth row
        buttonsPanel.add(this.clearButton);
        buttonsPanel.add(this.operatorButtons[0]);
        buttonsPanel.add(this.operatorButtons[EVAL_OP]);

        return buttonsPanel;
    }

    /**
     * Helper to concatenate string to display based on current contents (new/empty display,
     * adding spaces in between numbers and operators)
     *
     * @param value digit/operator to concatenate
     */
    private void concatDisplay(String value) {
        String currentDisplay = this.getDisplay();
        //handle brand new or empty display
        if(currentDisplay.equals(DISPLAY_START) || currentDisplay.length() == 0 || currentDisplay.equals("0")) {
            this.setDisplay(value);
        }
        //value is digit and end of display is a digit too, don't add space in between
        else if(value.length() == 1 && Character.isDigit(value.charAt(0)) &&
                Character.isDigit(currentDisplay.charAt(currentDisplay.length()-1)) ) {
            this.setDisplay(this.getDisplay() + value);
        }
        else {
            this.setDisplay(this.getDisplay() + " " + value);
        }
    }

    /**
     * Overriden ActionListener method to handle button presses appropriately
     *
     * @param e ActionEvent object to respond to ActionListener (button click)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        char actionChar = actionCommand.charAt(0);
        int value;

        if(actionCommand.equals(CLEAR)) {
            this.setDisplay("");
        }
        else if(actionChar == OPERATORS[EVAL_OP]) {
            value = calc.evaluate(this.getDisplay());
            //TODO: handle errors that may get thrown
            this.setDisplay("" + value);
        }
        else {
            //digit or op, so just concatenate
            this.concatDisplay(actionCommand);
        }
    }
}
