package com.example.mad_group_project;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void substract_isCorrect() {
        assertEquals(3, 5 - 2);
    }

    @Test
    public void multiplication() {
        assertEquals(4, 2 * 2);
    }

    @Test
    public void division() {
        assertEquals(4, 8 / 2);
    }

}