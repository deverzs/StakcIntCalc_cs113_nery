package edu.miracosta.cs113;

import models.CalculatorModel;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.*;

public class CalculatorModelTest {
    public static final String[] INFIX_EXPRESSIONS = {"4 * 7", "4 * ( 7 + 2 )", "( 4 * 7 ) - 20", "3 + ( ( 4 * 7 ) / 2)"};
    public static final String[] POSTFIX_RESULTS = {"4 7 * ", "4 7 2 + * ", "4 7 * 20 - ", "3 4 7 * 2 / + "};// Space after every char
    public static final String[] ANSWERS = {"28", "36", "8", "17"};
    private CalculatorModel model ;

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

    @Test
    public void testPopEmpty() {
        model = new CalculatorModel() ;
        assertEquals("Test failed. Stack should be empty", -1, model.popFromStack());
    }

    @Test
    public void testPushEmpty() {
        model = new CalculatorModel() ;
        model.pushToStack(5);
        assertEquals("Test failed. Did not add to stack", 5, model.popFromStack());
    }

    private void setUpForMany() {
        model = new CalculatorModel() ;
        model.pushToStack(5);
        model.pushToStack(10);
        model.pushToStack(15);
        model.pushToStack(4);
        model.pushToStack(8);
    }

    @Test
    public void testPushandPopMany() {
        setUpForMany();
        assertEquals("Test failed. Should have popped the top", 8,  model.popFromStack());
        assertEquals("Test failed. Should have popped the top", 4, model.popFromStack());
        assertEquals("Test failed. Should have popped the top", 15, model.popFromStack());
        assertEquals("Test failed. Should have popped the top", 10, model.popFromStack());
        assertEquals("Test failed. Should have popped the top", 5, model.popFromStack());
    }

    /**
     * This helper methods is used by methods that test calculations. This populates firstTerm and secondTerm
     * @param x firstTerm
     * @param y secondTerm
     */
    private void setUpCalculations(int x, int y) {
        model.setFirstTerm(x) ;
        model.setSecondTerm(y) ;
    }

    @Test
    public void testAdd() {
        model = new CalculatorModel() ;

        setUpCalculations(0, 6);
        model.add();
        assertEquals("Test failed to add zero and integer", 6, model.popFromStack());

        setUpCalculations(6, 0);
        model.add();
        assertEquals("Test failed to add integer and zero", 6, model.popFromStack());

        setUpCalculations(0, 0);
        model.add();
        assertEquals("Test failed to add zero and zero", 0, model.popFromStack());

        setUpCalculations(1100, 6);
        model.add();
        assertEquals("Test failed to add big integer and integer", 1106, model.popFromStack());

        setUpCalculations(6, 1100);
        model.add();
        assertEquals("Test failed to add integer and big integer", 1106, model.popFromStack());

    }

    @Test
    public void testSubtract() {
        model = new CalculatorModel() ;

        setUpCalculations(0, 6);
        model.sub();
        assertEquals("Test failed to subtract zero and integer", -6, model.popFromStack());

        setUpCalculations(6, 0);
        model.sub();
        assertEquals("Test failed to subtract integer and zero", 6, model.popFromStack());

        setUpCalculations(0, 0);
        model.sub();
        assertEquals("Test failed to subtract zero and zero", 0, model.popFromStack());

        setUpCalculations(1100, 6);
        model.sub();
        assertEquals("Test failed to subtract big integer and integer", 1094, model.popFromStack());

        setUpCalculations(6, 1100);
        model.sub();
        assertEquals("Test failed to subtract integer and big integer", -1094, model.popFromStack());

    }

    @Test
    public void testMultiply() {
        model = new CalculatorModel() ;

        setUpCalculations(0, 6);
        model.mult();
        assertEquals("Test failed to multiply zero and integer", 0, model.popFromStack());

        setUpCalculations(6, 0);
        model.mult();
        assertEquals("Test failed to multiply integer and zero", 0, model.popFromStack());

        setUpCalculations(0, 0);
        model.mult();
        assertEquals("Test failed to multiply zero and zero", 0, model.popFromStack());

        setUpCalculations(1100, 6);
        model.mult();
        assertEquals("Test failed to multiply big integer and integer", 6600, model.popFromStack());

        setUpCalculations(6, 1100);
        model.mult();
        assertEquals("Test failed to multiply integer and big integer", 6600, model.popFromStack());

    }

    @Test
    public void testDivide() {
        model = new CalculatorModel() ;

        setUpCalculations(0, 6);
        model.divide();
        assertEquals("Test failed to divide zero and integer", 0, model.popFromStack());

        setUpCalculations(1100, 6);
        model.divide();
        assertEquals("Test failed to divide big integer and integer", 183, model.popFromStack());

        setUpCalculations(6, 1100);
        model.divide();
        assertEquals("Test failed to divide integer and big integer", 0, model.popFromStack());

    }

    @Test
    public void testDerivative() {
        String answer ;
        String problem  ;

        model = new CalculatorModel() ;
        problem = "6 X 1" ;
        answer = model.evaluate(problem) ;
        assertEquals("Test failed to derive 6 x^ 1", "6",answer);

        problem = "6 X 7" ;
        answer = model.evaluate(problem) ;
        assertEquals("Test failed to derive 6 x^ 7", "42 X^6",answer);

        problem = "6 X 0" ;
        answer = model.evaluate(problem) ;
        assertEquals("Test failed to derive 6 x^ 0", "0",answer);

        problem = "233 X 67" ;
        answer = model.evaluate(problem) ;
        assertEquals("Test failed to derive 233 x^ 67", "15611 X^66",answer);

    }

    // Test setters and getters

    @Test
    public  void testSetAndGetFirstTerm() {
        model = new CalculatorModel() ;
        model.setFirstTerm(10);
        assertEquals("Test failed to set first term", 10, model.getFirstTerm());

    }

    @Test
    public  void testSetAndGetSecondTerm() {
        model = new CalculatorModel() ;
        model.setSecondTerm(15);
        assertEquals("Test failed to set second term", 15, model.getSecondTerm());

    }

    @Test
    public  void testSetAndGetTotal() {
        model = new CalculatorModel() ;
        model.setTotal(19);
        assertEquals("Test failed to set total", 19, model.getTotal());

    }

    @Test
    public  void testSetAndGetStack() {
        model = new CalculatorModel() ;
        Stack temp = new Stack<Integer>() ;
        temp.push(9) ;
        model.setStack(temp);
        assertEquals(temp, model.getStack()) ;
    }

    @Test
    public  void testSetAll() {
        model = new CalculatorModel() ;
        Stack temp = new Stack<Integer>() ;
        temp.push(9) ;
        model.setAll(10, 19, 29, temp);

        assertEquals("Test failed to set second term", 19, model.getSecondTerm());
        assertEquals("Test failed to set first term", 10, model.getFirstTerm());
        assertEquals("Test failed to set total", 29, model.getTotal());
        assertEquals(temp, model.getStack()) ;

    }

    @Test
    public void testToString() {
        setUpForMany();
        String actual = " 8 4 15 10 5";
        assertEquals("Test failed toString.", actual, model.toString());
    }

    @Test
    public void testEqualsTrue() {
        CalculatorModel test1, test2 ;
        test1 = new CalculatorModel() ;
        test2 = new CalculatorModel() ;
        test1.pushToStack(5);
        test1.pushToStack(10);
        test2.pushToStack(5);
        test2.pushToStack(10);
        assertEquals("Expected and actual should be TRUE for equals", test1, test2);

    }


    @Test
    public void testEqualsFalse() {
        CalculatorModel test1, test2 ;
        test1 = new CalculatorModel() ;
        test2 = new CalculatorModel() ;
        test1.pushToStack(5);
        test1.pushToStack(10);
        test2.pushToStack(9);
        test2.pushToStack(10);
        assertNotEquals("Expected and actual should be FALSE for equals", test1, test2);

    }


    @Test
    public void testDefaultConstructor() {
        CalculatorModel test = new CalculatorModel();

        assertEquals("Expected and actual first terms DON'T match", 0, test.getFirstTerm());
        assertEquals("Expected and actual second terms DON'T match", 0, test.getSecondTerm());
    }

    @Test
    public void testCopyConstructor() {
        CalculatorModel copy = new CalculatorModel(5, 10) ;
        CalculatorModel original = new CalculatorModel(copy);

        assertEquals("First Term in 'copy' does not match 'original'",
                original.getFirstTerm(), copy.getFirstTerm());
        assertEquals("Second Term in 'copy' does not match 'original'",
                original.getSecondTerm(), copy.getSecondTerm());
        assertTrue("Reference is not a unique memory address (shallow copy)",
                original != copy);
    }
}
