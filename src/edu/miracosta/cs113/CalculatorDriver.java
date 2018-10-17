package edu.miracosta.cs113;

import controllers.CalculatorController;
import models.CalculatorInterface;
import models.CalculatorModel;

/**
 * CalculatorDriver.java : Used to create JFrame object and show for user.
 *
 * @author Nery Chapeton-Lamas
 * @version 1.0
 */
public class CalculatorDriver {

    /**
     * - Creates model (using concrete class implementing CalculatorInterface)
     * - Creates controller connected to model
     * - Sets controller (JFrame) to visible
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        CalculatorInterface calcInt = new CalculatorModel();
        CalculatorController calc = new CalculatorController(calcInt);
        calc.setVisible(true);
    }
}
