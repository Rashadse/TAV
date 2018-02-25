package group5.tavCar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

class CarMovementTest {

    private static CarMovement classUnderTest;

    // this methods initializes the instance of the class to be tested
    @BeforeEach
    void setUp() throws Exception {
        classUnderTest = new CarMovement();  // Arrange.
    }


    // This method checks if the car moves 55 meters when moveForward() is invoked 11 times
    @Test
    void moveForwardTest1() throws Exception {

        //    move forward 55 meters
        for (int i = 0; i < 11; i++) {
            classUnderTest.moveForward(classUnderTest.distance);
        }

        boolean actual = classUnderTest.streetEndNotReached;

        // check if the car has not reached the end of the track
        assertTrue(actual, "boolean is not true");
        // check if the car has moved exactly 55 meters
        assertEquals(55, classUnderTest.distanceMoved);

    }

    // This method tests if the related boolean gets negated when the car reaches the end of the track
    @Test
    void moveForwardTest2() throws Exception {
        // move forward 95 meters (end of track)
        for (int i = 0; i < 19; i++) {
            classUnderTest.moveForward(classUnderTest.distance);
        }
        boolean actual = classUnderTest.streetEndNotReached;

        // check if the car has moved exactly 95 meters
        assertEquals(95, classUnderTest.distanceMoved);

        // check if the car has reached the end of the track
        assertFalse(actual, "car has not reached the end of the track");
        //System.out.println(actual);
    }

    // Checks if moveForward() throws and exception when called after the car reaches end of the track
    @Test
    void moveForwardTest3() throws Exception {

        try {
            // try to call moveForward when the end of the track has been reached
            for (int i = 0; i < 25; i++) {
                classUnderTest.moveForward(classUnderTest.distance);
            }

        } catch (RuntimeException e) {
            assertEquals("the car has reached the end of the track", e.getMessage());
        }
    }

    // This method tests if the car stays parked if moveForward() is not invoked
    @Test
    void moveForwardTest4() throws Exception {

        boolean actual = classUnderTest.streetEndNotReached;

        // check if the car has not reached the end of the track
        assertTrue(actual, "boolean is not true");
        // check if the car hasn't moved
        assertEquals(0, classUnderTest.distanceMoved);

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
        int[] Q1 = {45, 55, 55, 60};
        int[] Q2 = {47, 51, 51, 60};
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
        int[] Q1 = {45, 10, 10, 30};
        int[] Q2 = {45, 10, 10, 31};

        boolean laneFree = classUnderTest.leftLaneDetect(Q1, Q2);  // Act.
        // We expect the lane to be free.
        assertEquals(true, laneFree);  // Assert.
    }

    @Test
    void leftLaneDetectTest4() throws Exception {
        //Test case 6.
        int[] Q1 = {22, 24, 25, 45};
        int[] Q2 = {25, 25, 25, 45};

        boolean laneFree = classUnderTest.leftLaneDetect(Q1, Q2);  // Act.
        // We expect the lane to be free.
        assertEquals(true, laneFree);  // Assert.
    }

    @Test
    void leftLaneDetectTest5() throws Exception {
        // Test case 7.
        int[] Q1 = {25, 10, 9, 25};
        int[] Q2 = {25, 11, 9, 25};

        boolean laneFree = classUnderTest.leftLaneDetect(Q1, Q2);  // Act.
        // We expect the lane to be busy.
        assertEquals(false, laneFree);  // Assert.
    }

    @Test
    void leftLaneDetectTest6() throws Exception {
        //Test case 8.
        int[] Q1 = {15, 5, 5, 22};
        int[] Q2 = {15, 5, 6, 22};

        boolean laneFree = classUnderTest.leftLaneDetect(Q1, Q2);  // Act.
        // We expect the lane to be busy.
        assertEquals(false, laneFree);  // Assert.
    }

    int[] busyLaneQuery = {15, 5, 5, 22};
    int[] emptyLaneQuery = {30, 30, 30, 30};

    @Test
    void changeLaneTest1() {

        int originalPosition = classUnderTest.distanceMoved;
        int returnCode = classUnderTest.changeLane(emptyLaneQuery, emptyLaneQuery);

        // We expect the car to move forward, turn left and return an success code
        assertEquals(0, returnCode);
        assertEquals(originalPosition + 5, classUnderTest.distanceMoved);
        assertEquals(1, classUnderTest.lanePosition);

    }

    @Test
    void changeLaneTest2() {

        int originalPosition = classUnderTest.distanceMoved;
        int returnCode = classUnderTest.changeLane(busyLaneQuery, busyLaneQuery);

        // We expect the car to move forward and return an error code
        assertEquals(-1, returnCode);
        assertEquals(originalPosition + 5, classUnderTest.distanceMoved);

    }

    @Test
    void changeLaneTest3() throws Exception {
        classUnderTest.moveForward(95);
        int returnCode = classUnderTest.changeLane(emptyLaneQuery, emptyLaneQuery);

        // We expect the car not to move at all and return an error code
        assertEquals(-1, returnCode);

        assertEquals(95, classUnderTest.distanceMoved);
        assertEquals(0, classUnderTest.lanePosition);

    }

    @Test
    void changeLaneTest4() throws Exception {
        classUnderTest.moveForward(95);
        int returnCode;
        returnCode = classUnderTest.changeLane(busyLaneQuery, busyLaneQuery);

        // We expect the car not to move at all and return an error code
        assertEquals(-1, returnCode);

        assertEquals(95, classUnderTest.distanceMoved);
        assertEquals(0, classUnderTest.lanePosition);
    }

    @Test
    void changeLaneTest5() throws Exception {
        classUnderTest.lanePosition = 3;

        int returnCode = classUnderTest.changeLane(emptyLaneQuery, emptyLaneQuery);

        assertEquals(-1, returnCode);
    }

    @Test
    void changeLaneTest6() throws Exception {
        classUnderTest.lanePosition = 3;
        classUnderTest.moveForward(95);

        int returnCode = classUnderTest.changeLane(emptyLaneQuery, emptyLaneQuery);

        assertEquals(-1, returnCode);
    }

    // Test to check the initial longitudinal position of the car
    @Test
    void whereIsTest1() throws Exception {


        classUnderTest.moveForward(0);
        int actual_distanceMoved = 0;

        assertEquals(actual_distanceMoved, classUnderTest.whereIs()[0]);
    }

    // Test to check the initial latitudinal position of the car
    @Test
    void whereIsTest2() throws Exception {

        int actual_lanePosition = 0;

        assertEquals(actual_lanePosition, classUnderTest.whereIs()[1]);
    }

    // Test to check the the longitudinal position of the car
    @Test
    void whereIsTest3() throws Exception {


        classUnderTest.moveForward(90);
        int actual_distanceMoved = 90;

        assertEquals(actual_distanceMoved, classUnderTest.whereIs()[0]);
    }

    // test to check the latitudinal position of the car after changeLane() has been invoked twice.
    @Test
    void whereIsTest4() throws Exception {


        classUnderTest.changeLane(emptyLaneQuery, emptyLaneQuery);
        classUnderTest.changeLane(emptyLaneQuery, emptyLaneQuery);

        int actual_lanePosition = 2;


        assertEquals(actual_lanePosition, classUnderTest.whereIs()[1]);
    }
}
