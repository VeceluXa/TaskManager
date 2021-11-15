package com.bsuir.taskmanager;

import static org.junit.Assert.assertEquals;

import junit.framework.TestCase;

public class DatabaseTest extends TestCase {
    /**
     * First Unit Test
     */
    public void testAddition() {
        Database database = new Database();
        assertEquals(database.addition(2, 2), 2 + 2);
    }
}