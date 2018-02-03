package sample;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class CarMovementTest {

    private static CarMovement classUnderTest;

    // this methods initializes the instance of the class to be tested
    @BeforeAll
    static void setUp() throws Exception {
        classUnderTest = new CarMovement();  // Arrange.
    }

    @Test
    void moveForwardTest() throws Exception {

        classUnderTest.moveForward(classUnderTest.distance);

    boolean actual = classUnderTest.streetEndNotReached;

    assertTrue(actual, "boolean is not true");
    assertEquals(5, classUnderTest.distanceMoved);

    }

    @Test
    void leftLaneDetectTest() throws Exception {
    	
    	int[] Q1 = {50,35,20,20};
    	int[] Q2 = {30,35,20,20};
    	
    	boolean laneFree = classUnderTest.leftLaneDetect(Q1, Q2);  // Act.
    	
    	assertEquals(true, laneFree);  // Assert.
    }

    @Test
    void changeLane() throws Exception {
    }

    @Test
    void whereIs() throws Exception {
    }

}