package group5.tavCar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.*;

@SuppressWarnings("Duplicates")
public class CarMovementTest {

    @Spy IActuator actuator = new Actuator();
    @Mock ILidarSensor lidarSensor;
    @Mock IRadarSensors radarSensors;

    @InjectMocks CarMovement classUnderTest;  // Arrange.

    @BeforeEach
    public void setUp() throws Exception {
    	MockitoAnnotations.initMocks(this);
    }

    // This method checks if the car moves 55 meters when moveForward() is invoked 11 times
    @Test
    void moveForwardTest1() throws Exception {

        //    move forward 55 meters
        for (int i = 0; i < 11; i++) {
            classUnderTest.moveForward(5);
        }

        // check if the car has moved exactly 55 meters
        assertEquals(55, classUnderTest.whereIs().distance);

    }

    // This method tests if the related boolean gets negated when the car reaches the end of the track
    @Test
    void moveForwardTest2() throws Exception {
        // move forward 95 meters (end of track)
        for (int i = 0; i < 19; i++) {
            classUnderTest.moveForward(5);
        }
        // check if the car has moved exactly 95 meters
        assertEquals(95, classUnderTest.whereIs().distance);

    }

    // Checks if moveForward() throws and exception when called after the car reaches end of the track
    @Test
    void moveForwardTest3() throws Exception {

        // try to call moveForward when the end of the track has been reached
        for (int i = 0; i < 19; i++) {
            classUnderTest.moveForward(5);
        }

        assertThrows(EndOfTrackReachedException.class, () -> classUnderTest.moveForward(5));
    }

    // This method tests if the car stays parked if moveForward() is not invoked
    @Test
    void moveForwardTest4() throws Exception {

        // check if the car hasn't moved
        assertEquals(0, classUnderTest.whereIs().distance);

    }

    @Test
    void leftLaneDetectTest1() throws Exception {
     
    	CarMovement testClass = mock(CarMovement.class);  // Mock.
        
        testClass.leftLaneDetect();  // Act.
        
        when(testClass.leftLaneDetect()).thenThrow(RuntimeException.class);  // Stub.;
        
        verify(testClass, times(1)).leftLaneDetect();  //  Verify.
    }

    @Test
    void leftLaneDetectTest2() throws Exception {
        
        CarMovement testClass = mock(CarMovement.class);  // Mock.
        
        testClass.leftLaneDetect();  // Act.
        
        when(testClass.leftLaneDetect()).thenThrow(RuntimeException.class);  // Stub.;
        
        verify(testClass, times(1)).leftLaneDetect();  //  Verify.
    }

    @Test
    void leftLaneDetectTest3() throws Exception {
        
        CarMovement testClass = mock(CarMovement.class);  // Mock.
        when(testClass.leftLaneDetect()).thenReturn(true);  // Stub.
       
        boolean laneFree = testClass.leftLaneDetect();  // Act.
        verify(testClass, times(1)).leftLaneDetect();  //  Verify.
        // We expect the lane to be free.
        assertEquals(true, laneFree);  // Assert.
    }

    @Test
    void leftLaneDetectTest4() throws Exception {

        CarMovement testClass = mock(CarMovement.class);  // Mock.
        when(testClass.leftLaneDetect()).thenReturn(true);  // Stub.
       
        boolean laneFree = testClass.leftLaneDetect();  // Act.
        verify(testClass, times(1)).leftLaneDetect();  //  Verify.
        // We expect the lane to be free.
        assertEquals(true, laneFree);  // Assert.
    }

    @Test
    void leftLaneDetectTest5() throws Exception {

        CarMovement testClass = mock(CarMovement.class);  // Mock.
        when(testClass.leftLaneDetect()).thenReturn(false);  // Stub.
       
        boolean laneFree = testClass.leftLaneDetect();  // Act.
        verify(testClass, times(1)).leftLaneDetect();  //  Verify.
        // We expect the lane to be taken.
        assertEquals(false, laneFree);  // Assert.
    }

    @Test
    void leftLaneDetectTest6() throws Exception {

        CarMovement testClass = mock(CarMovement.class);  // Mock.
        when(testClass.leftLaneDetect()).thenReturn(false);  // Stub.
       
        boolean laneFree = testClass.leftLaneDetect();  // Act.
        verify(testClass, times(1)).leftLaneDetect();  //  Verify.
        // We expect the lane to be taken.
        assertEquals(false, laneFree);  // Assert.
    }

    int[] busyLaneQuery = {15, 5, 5, 22};
    int[] emptyLaneQuery = {30, 30, 30, 30};

    private int[] busyLaneRadarQuery  = {15,  5,  5};
    private int[] emptyLaneRadarQuery = {30, 30, 30};

    private int busyLaneLidarQuery  = 22;
    private int emptyLaneLidarQuery = 30;

    @Test
    void changeLaneTest1() {

        int originalPosition = classUnderTest.whereIs().distance;

        when(radarSensors.Read()).thenReturn(emptyLaneRadarQuery);
        when(lidarSensor.Read()).thenReturn(emptyLaneLidarQuery);

        int returnCode = classUnderTest.changeLane();

        // We expect the car to move forward, turn left and return an success code
        assertEquals(0, returnCode);
        assertEquals(originalPosition + 5, classUnderTest.whereIs().distance);
        assertEquals(1, classUnderTest.whereIs().lanePosition);

    }

    @Test
    void changeLaneTest2() {

        int originalPosition = classUnderTest.whereIs().distance;

        when(radarSensors.Read()).thenReturn(busyLaneRadarQuery);
        when(lidarSensor.Read()).thenReturn(busyLaneLidarQuery);

        int returnCode = classUnderTest.changeLane();

        // We expect the car to move forward and return an error code
        assertEquals(-1, returnCode);
        assertEquals(originalPosition + 5, classUnderTest.whereIs().distance);

    }

    @Test
    void changeLaneTest3() throws Exception {
        classUnderTest.moveForward(95);
        int returnCode = classUnderTest.changeLane();

        // We expect the car not to move at all and return an error code
        assertEquals(-1, returnCode);

        assertEquals(95, classUnderTest.whereIs().distance);
        assertEquals(0, classUnderTest.whereIs().lanePosition);

    }

    @Test
    void changeLaneTest4() throws Exception {
        //classUnderTest.moveForward(95);

        when(radarSensors.Read()).thenReturn(busyLaneRadarQuery);
        when(lidarSensor.Read()).thenReturn(busyLaneLidarQuery);

        when(actuator.getDistance()).thenReturn(95);

        int returnCode;
        returnCode = classUnderTest.changeLane();

        // We expect the car not to move at all and return an error code
        assertEquals(-1, returnCode);
    }

    @Test
    void changeLaneTest5() throws Exception {
        when(radarSensors.Read()).thenReturn(busyLaneRadarQuery);
        when(lidarSensor.Read()).thenReturn(busyLaneLidarQuery);

        classUnderTest.whereIs().lanePosition = 3;

        int returnCode = classUnderTest.changeLane();

        assertEquals(-1, returnCode);
    }

    @Test
    void changeLaneTest6() throws Exception {
        classUnderTest.whereIs().lanePosition = 3;
        classUnderTest.moveForward(95);

        int returnCode = classUnderTest.changeLane();

        assertEquals(-1, returnCode);
    }

    // Test to check the initial longitudinal position of the car
    @Test
    void whereIsTest1() throws Exception {


        classUnderTest.moveForward(0);
        int actual_distanceMoved = 0;

        assertEquals(actual_distanceMoved, classUnderTest.whereIs().distance);
    }

    // Test to check the initial latitudinal position of the car
    @Test
    void whereIsTest2() throws Exception {

        int actual = 0;

        assertEquals(actual, classUnderTest.whereIs().lanePosition);
    }

    // Test to check the the longitudinal position of the car
    @Test
    void whereIsTest3() throws Exception {


        classUnderTest.moveForward(90);
        int actual_distanceMoved = 90;

        assertEquals(actual_distanceMoved, classUnderTest.whereIs().distance);
    }

    // test to check the latitudinal position of the car after changeLane() has been invoked twice.
    @Test
    void whereIsTest4() throws Exception {


        //emptyLaneQuery, emptyLaneQuery
        when(radarSensors.Read()).thenReturn(emptyLaneRadarQuery);
        when(lidarSensor.Read()).thenReturn(emptyLaneLidarQuery);

        classUnderTest.changeLane();
        classUnderTest.changeLane();

        int actual_lanePosition = 2;

        assertEquals(actual_lanePosition, classUnderTest.whereIs().lanePosition);
    }
}
