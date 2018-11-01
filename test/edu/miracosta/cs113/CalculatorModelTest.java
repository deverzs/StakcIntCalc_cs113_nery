package edu.miracosta.cs113;

import models.CalculatorModel;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;
import static org.junit.Assert.*;


public class CalculatorModelTest {

    @Test
    public void testConvert(){
        CalculatorModel test = new CalculatorModel();
        String expected = "5 2 + "; // space after every operand and operator
        String result = null;
        try {
            result  = CalculatorModel.convert("5 + 2");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals("Expected result with postfix", expected, result);
    }
}
