package com.examples.android.examples;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testLogicEquality() throws Exception{
        assertTrue(true);
    }

    @Test
    public void testNumberInequality() throws Exception{
        assertTrue(1 < 3);
    }

    @Test
    public void testNotEquals() throws Exception{
        assertNotEquals(5+4, 2/3);
    }

    

}