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
    void whereIs() throws Exception {

        int[] actual = classUnderTest.whereIs();
        System.out.println(actual[0]);
    }

    @Test
    void moveForwardTest() throws Exception {

        boolean actual = classUnderTest.streetEndNotReached;

        // check if the car has not reached the end of the track
        assertTrue(actual, "boolean is not true");
        // check if the car hasn't moved
        assertEquals(0, classUnderTest.distanceMoved);

    }

    @Test
    void moveForwardTest2() throws Exception {

        // move forward 5 meters
         classUnderTest.moveForward(classUnderTest.distance);

        boolean actual = classUnderTest.streetEndNotReached;

        // check if the car has not reached the end of the track
        assertTrue(actual, "boolean is not true");
        // check if the car has moved exactly 5 meters
        assertEquals(5, classUnderTest.distanceMoved);
    }

    @Test
    void moveForwardTest3() throws Exception {
     //    move forward 55 meters
        for (int i = 0; i < 11; i++) {
            classUnderTest.moveForward(classUnderTest.distance);
        }

        boolean actual = classUnderTest.streetEndNotReached;

        // check if the car has not reached the end of the track
        assertTrue(actual, "boolean is not true");
        // check if the car has moved exactly 55 meters
        assertEquals(60, classUnderTest.distanceMoved);

    }

    @Test
    void moveForwardTest4() throws Exception {
        // move forward 35 meters
        for (int i = 0; i < 7; i++) {
            classUnderTest.moveForward(classUnderTest.distance);
        }
        boolean actual = classUnderTest.streetEndNotReached;

        // check if the car has moved exactly 35 meters
        assertEquals(95, classUnderTest.distanceMoved);

        // check if the car has reached the end of the track
        assertFalse(actual, "boolean is not false");
    }

    @Test
    void leftLaneDetectTest1() throws Exception {
        // Test case 1.
        int[] Q1 = {55, 52, 52, 52};
        int[] Q2 = {55, 52, 52, 52};
        // Check the exception type, out of range expected.
        try {
            classUnderTest.leftLaneDetect(Q1, Q2);  // Act.
            fail("Expected a RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertEquals("Obstacles out of range", e.getMessage()); // Assert.
        }
    }

    @Test
    void leftLaneDetectTest2() throws Exception {
        //Test case 3.
        int[] Q1 = {25, 55, 25, 55};
        int[] Q2 = {20, 51, 51, 51};
        // Check the exception type, out of range expected.
        try {
            classUnderTest.leftLaneDetect(Q1, Q2);  // Act.
            fail("Expected a RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertEquals("Obstacles out of range", e.getMessage()); // Assert.
        }
    }

    @Test
    void leftLaneDetectTest3() throws Exception {
        // Test case 5.
        int[] Q1 = {30, 30, 30, 30};
        int[] Q2 = {35, 35, 35, 35};

        boolean laneFree = classUnderTest.leftLaneDetect(Q1, Q2);  // Act.
        // We expect the lane to be free.
        assertEquals(true, laneFree);  // Assert.
    }

    @Test
    void leftLaneDetectTest4() throws Exception {
        //Test case 6.
        int[] Q1 = {22, 20, 25, 22};
        int[] Q2 = {25, 25, 25, 25};

        boolean laneFree = classUnderTest.leftLaneDetect(Q1, Q2);  // Act.
        // We expect the lane to be free.
        assertEquals(true, laneFree);  // Assert.
    }

    @Test
    void leftLaneDetectTest5() throws Exception {
        // Test case 7.
        int[] Q1 = {25, 10, 25, 25};
        int[] Q2 = {25, 15, 25, 25};

        boolean laneFree = classUnderTest.leftLaneDetect(Q1, Q2);  // Act.
        // We expect the lane to be busy.
        assertEquals(false, laneFree);  // Assert.
    }

    @Test
    void leftLaneDetectTest6() throws Exception {
        //Test case 8.
        int[] Q1 = {15, 15, 22, 22};
        int[] Q2 = {15, 10, 22, 22};

        boolean laneFree = classUnderTest.leftLaneDetect(Q1, Q2);  // Act.
        // We expect the lane to be busy.
        assertEquals(false, laneFree);  // Assert.
    }

    @Test
    void changeLane() throws Exception {
    }


}