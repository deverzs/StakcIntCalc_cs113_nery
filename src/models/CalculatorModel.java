package models;

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
    public static final String OPERATORS = "+-*/()";
    public static final int[] PRECEDENCE = {1, 1, 2, 2, -1, -1};
    private Stack<Integer> operands;
    private int firstTerm ;
    private int secondTerm ;
    private int total ;

    /**
     * Default constructor for CalculatorModel. All instance variables are set to default.
     */
    public  CalculatorModel() {
        firstTerm = 0 ;
        secondTerm = 0 ;
        total = 0 ;
        operands = new Stack<Integer>();
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
    public int popFronStack() {
        return   operands.pop() ;
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
    public void setSecondTermTerm(int term) {
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
     * @param subTotal  the subTotal of the stack
     * @param total the total of the stack
     * @param stack the stack to be used to compute
     */
    public void setAll(int firstTerm, int secondTerm, int subTotal, int total, Stack<Integer> stack) {
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
        String postFix = null;
        int subTotal;
        char currentChar;
        try {
            postFix = convert(expression);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        tokens = postFix.split("\\s+");
        for(String token : tokens) {
            currentChar = token.charAt(0);
            if (Character.isDigit(currentChar)) {
                int operand = Integer.parseInt(token);
                this.pushToStack(operand);
            } else if (isOperator(currentChar)) {
                this.secondTerm = this.popFronStack();
                this.firstTerm = this.popFronStack();
                calcTerms(currentChar);
            } else {
                // Invalid char so throw exception
            }
        }
        this.total = this.popFronStack();
        if (this.operands.empty()) {
            return this.total + "";
        } else {
            return "NaN";
            // Stack should be empty
        }
    }

    private void calcTerms(char currentChar) {
        System.out.println("Current OP: " + currentChar);
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
        }
    }

    public static String convert(String expression) throws Exception {
        Stack<Character> operators = new Stack<Character>();
        StringBuilder postFix = new StringBuilder();
        Scanner scan = new Scanner(expression);

        String token;
        while ((token = scan.findInLine("[\\p{L}\\p{N}]+|[-+/\\*()]")) != null){
            char firstChar = token.charAt(0);
            if(Character.isJavaIdentifierStart(firstChar) || Character.isDigit(firstChar)){
                postFix.append(token);
                postFix.append(' ');
            } else if(isOperator(firstChar)){
                //process operator
                processOperator(operators, postFix, firstChar);
            } else{
                throw new Exception("Unsupported Operator");
            }
        }
        while(!operators.empty()){
            char operator = operators.pop();
            if(operator == '('){
                throw new Exception("Extra parentheses");
            }
            postFix.append(operator);
            postFix.append(' ');
        }
        return postFix.toString();
    }

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

    public static boolean isOperator(char check){
        return OPERATORS.indexOf(check) != -1;
    }

    public static int precedence(char operator){
        return PRECEDENCE[OPERATORS.indexOf(operator)];
    }

    @Override
    public String toString() {
        //System.out.println("In the toString now");
        StringBuilder sb = new StringBuilder() ;
        Stack<Integer> temp = new Stack<Integer>() ;
        int move ;
        while (!(operands.empty())) {
            move =  operands.pop() ;
            sb.append(" " + move) ;
            temp.add(move) ;
           //System.out.println("String now is: " + sb);
        }
        while (!(temp.empty())) {
            move = temp.pop() ;
            operands.push(move) ;
            //System.out.println("Stack now has" + move);
        }
        return sb.toString() ;


    }
}
