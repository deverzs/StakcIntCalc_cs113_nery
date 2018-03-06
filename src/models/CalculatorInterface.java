/**
 * CalculatorInterface.java : Interface for calculator model (back-end)
 *
 * @author Nery Chapeton-Lamas
 * @version 1.0
 *
 */

package models;

public interface CalculatorInterface {

    /**
     * takes in an infix expression and, enforcing operator precedence, evaluates it using stack data structure
     *
     * @param expression unevaluated mathematical expression containing +, -, *, / and paren (all integer based)
     * @return int of the expression evaluated using operator precedence and enforcing parens
     */
    public int evaluate(String expression);

}
