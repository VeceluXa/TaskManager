package com.bsuir.taskmanager;

import org.junit.Test;

import static org.junit.Assert.*;

import android.provider.ContactsContract;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        Database database = new Database();
        assertEquals(database.addition(2, 2), 2 + 2);
    }
}