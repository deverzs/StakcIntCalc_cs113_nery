/**
 * CalculatorController.java : Controller to connect model (CalculatorInterface implementer) and view (CalculatorView)
 *
 * @author Nery Chapeton-Lamas
 * @version 1.0
 *
 */

package controllers;

import models.CalculatorInterface;
import views.CalculatorView;
import javax.swing.JFrame;
import java.awt.FlowLayout;

public class CalculatorController extends JFrame {

    //default window size, can be resized but starts at these dimensions
    public static final int WIDTH = 450, HEIGHT = 200;

    /**
     * Builds frame details and adds CalculatorView
     *
     * @param calc object that implements CalculatorInterface, used to build view
     */
    public CalculatorController(CalculatorInterface calc) {
        super();

        this.setSize(WIDTH, HEIGHT);
        this.setTitle("CS113 Stack Calculator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout( new FlowLayout() );
        this.add( new CalculatorView(calc) );
    }
}
