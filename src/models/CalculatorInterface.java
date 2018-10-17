package models;

/**
 * CalculatorInterface.java : Interface for calculator model (back-end)
 *
 * @author Nery Chapeton-Lamas
 * @version 1.0
 */
public interface CalculatorInterface {

    /**
     * Takes an infix expression and, enforcing operator precedence, evaluates it using the stack data structure.
     *
     * @param expression unevaluated mathematical expression containing +, -, *, / and paren (all integer based)
     * @return a String representation of the expression evaluated, using operator precedence and enforcing parens
     */
    public String evaluate(String expression);

}
