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

    @Override
    public String evaluate(String expression) {
        return "NaN";
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
}
