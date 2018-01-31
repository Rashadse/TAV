package sample;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class CarMovementTest {

    private static CarMovement classUnderTest;

    // this methods initialises the instance of the class to be tested
    @BeforeAll
    static void setUp() throws Exception {
        classUnderTest = new CarMovement();
    }

    @Test
    void moveForwardTest() throws Exception {

        classUnderTest.moveForward(classUnderTest.distance);

    boolean actual = classUnderTest.streetEndNotReached;

    assertTrue(actual, "boolean is not true");
    assertEquals(5, classUnderTest.distanceMoved);

    }

    @Test
    void leftLaneDetect() throws Exception {
    }

    @Test
    void changeLane() throws Exception {
    }

    @Test
    void whereIs() throws Exception {
    }

}