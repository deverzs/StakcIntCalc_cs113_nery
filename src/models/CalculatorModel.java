package models;

import jdk.nashorn.internal.ir.ReturnNode;

import java.util.Scanner;
import java.util.Stack;

/**
 * CalculatorModel.java : Concrete class using the stack data structure to evaluate infix math expressions.
 *
 * TODO: This file given just to get code to compile (method stubbed). Make sure to implement appropriately (and remove this).
 *
 * @author Nery Chapeton-Lamas
 * @version 1.0
 */
public class CalculatorModel implements CalculatorInterface {
    public static final String OPERATORS = "+-*/()Xx";
    public static final int[] PRECEDENCE = {1, 1, 2, 2, -1, -1};
    private Stack<Integer> operands;
    private int firstTerm ;
    private int secondTerm ;
    private int total ;
    private boolean deriv ;

    /**
     * Default constructor for CalculatorModel. All instance variables are set to default.
     */
    public  CalculatorModel() {
        firstTerm = 0 ;
        secondTerm = 0 ;
        total = 0 ;
        operands = new Stack<Integer>();
        deriv = false ;
    }

    /**
     * Constructor for debugging
     * @param term1  firstTerm
     * @param term2  secondTerm
     */
    public CalculatorModel(int term1, int term2) {
        firstTerm = term1 ;
        secondTerm = term2 ;
        total = 0 ;
        operands = new Stack<Integer>() ;
        deriv = false ;
    }

    /**
     * Constructor that takes in another stack as its main operational stack.
     * @param stack  Reference to new stack
     */
    public CalculatorModel(Stack<Integer> stack) {
        this.operands = stack ;
        firstTerm = 0 ;
        secondTerm = 0 ;
        total = 0 ;
        deriv = false ;
    }

    /**
     * Copy constructor that takes in another model to copy.
     * @param model The CalculatorModel to be copied. Creates a shallow copy.
     */
    public CalculatorModel(CalculatorModel model) {
        operands = model.operands ;
        firstTerm = model.firstTerm ;
        secondTerm = model.secondTerm ;
        total = model.total ;
        deriv = false ;

    }

    /**
     * Adds a new term of int to the stack.
     * @param term Term of int to be added to the stack.
     */
    public void pushToStack(int term) {
        operands.push(term) ;
    }

    /**
     * Removes the top item from the stack.
     * @return an int that represents the item at the top of the stack
     */
    public int popFromStack() {
        int temp ;
        if (operands.isEmpty()) {
            temp = -1 ;
        }
        else {
            temp =  operands.pop();
        }
        return temp;
    }

    /**
     * Adds firstTerm and secondTerm and pushes result to stack
     */
    public void add() {
        operands.push(this.firstTerm + this.secondTerm) ;
    }

    /**
     * Subtracts firstTerm from secondTerm and pushes result to stack
     */
    public void sub() {
        operands.push(this.firstTerm - this.secondTerm) ;
    }

    /**
     * Divides firstTerm and secondTerm and pushes result to stack
     */
    public void divide() {
        operands.push(this.firstTerm / this.secondTerm) ;
    }

    /**
     * Derivative method that uses the firstTerm as the coefficient and secondTerm as exponent
     */
    public void derivative() {
       deriv = true ;
       int coefficient = firstTerm * secondTerm ;
       int exponent = secondTerm - 1 ;
       operands.push(exponent) ;
       operands.push(coefficient) ;
    }

    /**
     * Multiplies firstTerm and secondTerm and pushes result to stack
     */
    public void mult() {
        operands.push( this.firstTerm * this.secondTerm) ;
    }

    // Setters

    /**
     * Setter for firstTerm
     * @param term New term to be applied
     */
    public void setFirstTerm(int term) {
        firstTerm = term ;
    }

    /**
     * Setter for secondTerm
     * @param term New term to be applied
     */
    public void setSecondTerm(int term) {
        secondTerm = term ;
    }


    /**
     * Setter for total
     * @param term New term to be applied
     */
    public void setTotal(int term) {
        total = term ;
    }

    /**
     * Setter for stack.
     * @param stack New stack to be applied
     */
    public void setStack(Stack<Integer> stack) {
        this.operands = stack ;
    }

    /**
     * Setter for all instance variables
     * @param firstTerm term 1, an int
     * @param secondTerm  term 2, an int
     * @param total the total of the stack
     * @param stack the stack to be used to compute
     */
    public void setAll(int firstTerm, int secondTerm, int total, Stack<Integer> stack) {
        this.firstTerm = firstTerm ;
        this.secondTerm = secondTerm ;
        this.total = total ;
        this.operands = stack ;
    }

    // Getters

    /**
     * Returns the firstTerm
     * @return firstTerm
     */
    public int getFirstTerm() {
        return firstTerm ;
    }

    /**
     * Returns the secondTerm
     * @return secondTerm
     */
    public int getSecondTerm() {
        return secondTerm ;
    }


    /**
     * Returns the total
     * @return total of stack
     */
    public int getTotal() {
        return total ;
    }

    /**
     * Returns the stack
     * @return stack
     */
    public Stack getStack() {
        return operands ;
    }


    @Override
    public String evaluate(String expression) {
        String[] tokens;
        String postFix;
        char currentChar;

        postFix = convert(expression);
        if(postFix.contains("ERROR")){
            return postFix;
        }
        tokens = postFix.split("\\s+");
        for(String token : tokens) {
            currentChar = token.charAt(0);
            if (Character.isDigit(currentChar)) {
                int operand = Integer.parseInt(token);
                this.pushToStack(operand);
            }
            else if (isOperator(currentChar)) {
                this.secondTerm = this.popFromStack();
                this.firstTerm = this.popFromStack();
                calcTerms(currentChar);

            } else {
                return "Invalid Character Detected!";
            }
        }

        this.total = this.popFromStack();
        if (this.operands.empty()) {
            return this.total + "";
        }
        else if (deriv) {
            return this.total + "X^" + operands.pop() ;
        }

        else {
            return "NaN";
            // Stack should be empty
        }
    }

    /**
     * Helper method for evaluating terms of expression
     * @param currentChar Math operator to process
     */
    private void calcTerms(char currentChar) {
        switch (currentChar){
            case '+':
                this.add();
                break;
            case '-':
                this.sub();
                break;
            case '*':
                this.mult();
                break;
            case '/':
                this.divide();
                break;
            case 'X' :
            case 'x' :
                this.derivative();

                break ;
        }
    }

    /**
     * Converts infix math expressions to postFix
     * @param expression The math expression with infix operators
     * @return String with a space after every character in postfix format
     */
    public static String convert(String expression){
        Stack<Character> operators = new Stack<Character>();
        StringBuilder postFix = new StringBuilder();
        Scanner scan = new Scanner(expression);

        String token;
        while ((token = scan.findInLine("[\\p{L}\\p{N}]+|[-+/\\*Xx()]")) != null){
            char firstChar = token.charAt(0);
            if (firstChar == 'x' || firstChar == 'X') {
                processOperator(operators, postFix, firstChar);
            }
            else if (Character.isJavaIdentifierStart(firstChar) || Character.isDigit(firstChar)){
                postFix.append(token);
                postFix.append(' ');
            }
            else if(isOperator(firstChar)){
                processOperator(operators, postFix, firstChar);
            }
            else{
                return "ERROR: Unsupported Operator";
            }
        }
        while(!operators.empty()){
            char operator = operators.pop();
            if(operator == '('){
                return "ERROR: Unmatched parentheses!";
            }
            postFix.append(operator);
            postFix.append(' ');
        }
        return postFix.toString();
    }

    /**
     * Helper method pushes operators of higher precedence into a stack or pops and appends to a string lesser operators
     * @param operators Character stack with math operators
     * @param postFix StringBuilder object with operands and operators
     * @param operator Math operator to process
     */
    private static void processOperator(Stack<Character> operators, StringBuilder postFix, char operator){
        if(operators.empty() || operator == '('){
            operators.push(operator);
        } else{
            char topOperator = operators.peek();
            if(precedence(operator) > precedence(topOperator)){
                operators.push(operator);
            } else{
                while(!operators.empty() && precedence(operator) <= precedence(topOperator)){
                    operators.pop();
                    if(topOperator == '('){
                        break;
                    }
                    postFix.append(topOperator);
                    postFix.append(' ');
                    if(!operators.empty()){
                        topOperator = operators.peek();
                    }
                }
                if(operator != ')'){
                    operators.push(operator);
                }
            }
        }
    }

    /**
     * Helper method to a character is a math operator: + - * /
     * @param check Operator character to check
     * @return True if char is found in valid operator list
     */
    private static boolean isOperator(char check){
        return OPERATORS.indexOf(check) != -1;
    }

    /**
     * Helper method to gets precedence value of operator: [1] for (+ -), [2] for (* /) and [-1] for parentheses
     * @param operator Math operator to check
     * @return Integer that determines precedence
     */
    private static int precedence(char operator){
        return PRECEDENCE[OPERATORS.indexOf(operator)];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder() ;
        Stack<Integer> temp = new Stack<Integer>() ;
        int move ;
        while (!(operands.empty())) {
            move =  operands.pop() ;
            sb.append(" " + move) ;
            temp.add(move) ;
        }
        while (!(temp.empty())) {
            move = temp.pop() ;
            operands.push(move) ;
        }
        return sb.toString() ;
    }

    @Override
    public boolean equals(Object o) {
        boolean check = true ;
        boolean done = false ;
        int tempInt;
        int move ;
        Stack<Integer> tempStack = new Stack<Integer>() ;

        if (o == null ) {
            return false ;
        }
        if (!(o instanceof CalculatorModel)) {
            return false ;
        }
        CalculatorModel anotherModel = (CalculatorModel) o ;
        // moving our stack to a temp stack
        // I added the boolean done so that the entire program
        // runs and moves the temp stack back to the original before it returns
        while ( (!(operands.empty())) && (!(done))) {
            move =  operands.pop() ;
            // checking for equivalency
            if (move != (int) anotherModel.popFromStack()) {
                check = false ;
                done = true ;
            }
            // adding popped to tempStack
            tempStack.add(move) ;
        }
        // moving tempStack back to original stack
        while (!(tempStack.empty())) {
            move = tempStack.pop() ;
            operands.push(move) ;
        }
        return check ;
    }

}
