import static org.junit.Assert.*;
import org.junit.*;

import doubly.DoublyLinkedWebPage;

public class DoublyLinkedWebPageTest {

    /*
     * This is a Java Test Class, which uses the JUnit package to create
     * and run tests. You do NOT have to submit this class.
     * 
     * You can fill in these tests in order to evaluate your code. Writing tests
     * is a crucial skill to have as a developer.
     */

    @Test
    public void testAddRecent() {

        // This is a completed test, which you can use to test your addRecent() code

        DoublyLinkedWebPage test = new DoublyLinkedWebPage();

        // First we add three nodes to the list
        // A <=> B <=> C <-- current
        test.addRecent("A");
        test.addRecent("B");
        test.addRecent("C");

        // We'll then check they got inserted in that order
        assertEquals(test.getCurrent().website, "C");
        test.moveLeft(); // Move current to 'B'
        assertEquals(test.getCurrent().website, "B");
        test.moveLeft(); // Move current to 'A'
        assertEquals(test.getCurrent().website, "A");
        test.moveLeft(); // Try to move current to the left of A, which fails because it is null
        assertEquals(test.getCurrent().website, "A");

        // Current is at A, so insert D after
        test.addRecent("D");
        // The list now: A <=> D <-- current
        assertEquals(test.getCurrent().website, "D");
        test.moveLeft(); // Move current back to A
        assertEquals(test.getCurrent().website, "A");

        test.addRecent("E"); // A <=> E <-- current
        test.addRecent("F"); // A <=> E <=> F <-- current
        test.moveRight(); // A <=> E <=> F <-- current
        assertEquals(test.getCurrent().website, "F");
        test.moveLeft(); // Move current to E
        assertEquals(test.getCurrent().website, "E");
        test.moveLeft(); // Move current to A
        assertEquals(test.getCurrent().website, "A");

    }

    @Test
    public void testNumberSites() {
        DoublyLinkedWebPage test = new DoublyLinkedWebPage();

        assertEquals(test.numberSites(), 0);

        test.addRecent("A");
        test.addRecent("B");
        test.addRecent("C");

        test.moveLeft();

        assertEquals(test.numberSites(), 3);
    }

}