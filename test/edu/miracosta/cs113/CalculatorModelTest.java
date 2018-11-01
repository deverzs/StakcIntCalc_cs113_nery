package edu.miracosta.cs113;

import models.CalculatorModel;
import org.junit.Test;
import static org.junit.Assert.*;

public class CalculatorModelTest {
    public static final String[] INFIX_EXPRESSIONS = {"4 * 7", "4 * ( 7 + 2 )", "( 4 * 7 ) - 20", "3 + ( ( 4 * 7 ) / 2)"};
    public static final String[] POSTFIX_RESULTS = {"4 7 * ", "4 7 2 + * ", "4 7 * 20 - ", "3 4 7 * 2 / + "};// Space after every char
    public static final String[] ANSWERS = {"28", "36", "8", "17"};

    @Test
    public void testConvert(){
        for(int i = 0; i < INFIX_EXPRESSIONS.length; i++){
            assertEquals("Unexpected postfix result", POSTFIX_RESULTS[i], CalculatorModel.convert(INFIX_EXPRESSIONS[i]));
        }
    }

    @Test
    public void testEvaluate(){
        CalculatorModel test = new CalculatorModel();
        // Testing basic arithmetic
        for(int i = 0; i < INFIX_EXPRESSIONS.length; i++){
            assertEquals("Unexpected answer", ANSWERS[i], test.evaluate(INFIX_EXPRESSIONS[i]));
        }
    }
}
