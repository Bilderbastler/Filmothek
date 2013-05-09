/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author florianneumeister
 */
public class FilmothekModelTest {

    public FilmothekModelTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of createUser method, of class FilmothekModel.
     */
    @Ignore
    @Test
    public void testCreateUser() {
        System.out.println("createUser");
        UserBean user = null;
        FilmothekModel instance = new FilmothekModel();
        instance.createUser(user);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUser method, of class FilmothekModel.
     */
    @Test
    public void testGetUser_String() {
        System.out.println("getUser");
        String email = "florian.neumeister@haw-hamburg.de";
        FilmothekModel instance = new FilmothekModel();
        UserBean expResult = new UserBean();
        expResult.setName("Florian");
        UserBean result = instance.getUser(email);
        assertEquals("Namen sind nicht gleich", expResult.getName(), result.getName());
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getUser method, of class FilmothekModel.
     */
    @Ignore
    @Test
    public void testGetUser_int() {
        System.out.println("getUser");
        int id = 0;
        FilmothekModel instance = new FilmothekModel();
        UserBean expResult = null;
        UserBean result = instance.getUser(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}