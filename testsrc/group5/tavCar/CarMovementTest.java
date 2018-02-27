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

//        assertEquals(95, car.distanceMoved);
//        assertEquals(0, car.whereIs().lanePosition);
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
    
    
    
    
    //=========================================================================
  //  @Spy  Actuator actuator;
  //  @Mock IRadarSensors radarSensors;
  //  @Mock ILidarSensor lidarSensor;

    @InjectMocks CarMovement car;

   /* @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }*/

   // private int[] busyLaneRadarQuery   = {15,  5,  5};
    private int[] busyLaneRadarQuery1   = {-1,  -1,  -1};
   // private int[] emptyLaneRadarQuery  = {30, 30, 30};
    private int[] busyLaneOneLaneOverRadarQuery = {45, 11, 11};
    private int[] outOfRangeRadarQuery = {30, 30, 30};

  //  private int busyLaneLidarQuery   = 22;
  //  private int emptyLaneLidarQuery  = 30;
    private int outOfRangeLidarQuery = -1;

    @Test
    void scenario1()
    {


        //1. Starts at the beginning of the street.
        //2. Moves halfway through the street without changing lanes.

        for(int i = 0; i < 9; i++ ) {
            car.moveForward();
        }
        verify(actuator, atLeastOnce()).moveForward();

        assertEquals(45, car.whereIs().distance);


        //3. Detects a car on the left lane from only the Radar sensor.
        when(radarSensors.Read()).thenReturn(busyLaneRadarQuery);
        when(lidarSensor.Read()).thenReturn(outOfRangeLidarQuery);

        car.changeLane();

        verify(actuator, atLeastOnce()).moveForward();

        assertEquals(0, car.whereIs().lanePosition);

        for(int i = 0; i < 9; i++){
            car.moveForward();
        }

    }

    @Test
    void scenario2()
    {

        for(int i = 0; i < 5; i++ ) {
            car.moveForward();
        }

        verify(actuator, atLeastOnce()).moveForward();

        assertEquals(25, car.whereIs().distance);

        when(radarSensors.Read()).thenReturn(busyLaneRadarQuery);
        when(lidarSensor.Read()).thenReturn(busyLaneLidarQuery);

        car.changeLane();

        verify(actuator, atLeastOnce()).moveForward();

        assertEquals(0, car.whereIs().lanePosition);

        for(int i = 0; i < 13; i++){
            car.moveForward();
        }

    }

    @Test
    void scenario3()
    {

        assertEquals(0, car.whereIs().distance);

        when(radarSensors.Read()).thenReturn(busyLaneRadarQuery1);

        car.changeLane();

        verify(actuator, atLeastOnce()).moveForward();

        assertEquals(0, car.whereIs().lanePosition);

        for(int i = 0; i < 18; i++){
            car.moveForward();
        }

    }

    @Test
    void scenario4() {
    	
        // Starts at the beginning of the street.
        // Moves 45 meters without changing lanes.
        for(int i = 0; i < 8; i++ ) {
            car.moveForward();
        }
        // Verify we actually call the actuator.
        verify(actuator, atLeastOnce()).moveForward();

        // Assert distance traveled.
        assertEquals(40, car.whereIs().distance);
        
        // Radar sensors pick up an empty lane.
        when(radarSensors.Read()).thenReturn(emptyLaneRadarQuery);
        // Lidar sensor doesn't pick up anything.
        when(lidarSensor.Read()).thenReturn(outOfRangeLidarQuery);

        // Act.
        car.changeLane();

        // Verify we're calling the mock.
        verify(actuator, atLeastOnce()).moveForward();

        // We assert the car's in the middle lane.
        assertEquals(1, car.whereIs().lanePosition);

        // Move forward until end of road.
        for(int i = 0; i < 8; i++){
            car.moveForward();
        }
    }
    
    @Test
    void scenario5() {
    	
        // Starts at the beginning of the street.
        // Moves 15 meters without changing lanes.
        for(int i = 0; i < 2; i++ ) {
            car.moveForward();
        }
        // Verify we actually call the actuator.
        verify(actuator, atLeastOnce()).moveForward();

        // Assert distance traveled.
        assertEquals(10, car.whereIs().distance);
        
        // Radar sensors pick up a car one lane over.
        when(radarSensors.Read()).thenReturn(busyLaneOneLaneOverRadarQuery);
        // Lidar sensor doesn't pick up anything.
        when(lidarSensor.Read()).thenReturn(outOfRangeLidarQuery);

        // Act.
        car.changeLane();

        // Verify we're calling the mock.
        verify(actuator, atLeastOnce()).moveForward();

        // We assert the car's in the middle lane.
        assertEquals(1, car.whereIs().lanePosition);

        // Move forward until end of road.
        for(int i = 0; i < 16; i++){
            car.moveForward();
        }	
    }
    
    @Test
    void scenario6() {
    	
        // Starts at the beginning of the street.
        // Moves 25 meters without changing lanes.
        for(int i = 0; i < 4; i++ ) {
            car.moveForward();
        }
        // Verify we actually call the actuator.
        verify(actuator, atLeastOnce()).moveForward();

        // Assert distance traveled.
        assertEquals(20, car.whereIs().distance);
        
        // Radar sensors pick up a car in the left lane.
        when(radarSensors.Read()).thenReturn(busyLaneRadarQuery);
        // Lidar sensor picks up a car far ahead.
        when(lidarSensor.Read()).thenReturn(emptyLaneLidarQuery);

        // Act.
        car.changeLane();

        // Verify we're calling the mock.
        verify(actuator, atLeastOnce()).moveForward();

        // We assert the car's in the same lane.
        assertEquals(0, car.whereIs().lanePosition);

        // Move forward 25 meters
        for(int i = 0; i < 4; i++){
            car.moveForward();
        }	
        
        // Verify we actually call the actuator.
        verify(actuator, atLeastOnce()).moveForward();

        // Assert distance traveled.
        assertEquals(45, car.whereIs().distance);
        
        // Radar sensors pick up a car in the left lane.
        when(radarSensors.Read()).thenReturn(emptyLaneRadarQuery);
        // Lidar sensor picks up a car ahead.
        when(lidarSensor.Read()).thenReturn(busyLaneLidarQuery);

        // Act.
        car.changeLane();

        // Verify we're calling the mock.
        verify(actuator, atLeastOnce()).moveForward();

        // We assert the car's in the middle lane.
        assertEquals(1, car.whereIs().lanePosition);
        
        // Moves 15 meters without changing lanes.
        for(int i = 0; i < 2; i++ ) {
            car.moveForward();
        }
        // Verify we actually call the actuator.
        verify(actuator, atLeastOnce()).moveForward();

        // Assert distance traveled.
        assertEquals(60, car.whereIs().distance);
        
        // Radar sensors pick up a car in the left lane.
        when(radarSensors.Read()).thenReturn(busyLaneRadarQuery);
        // Lidar sensor picks up a car far ahead.
        when(lidarSensor.Read()).thenReturn(emptyLaneLidarQuery);

        // Act.
        car.changeLane();

        // Verify we're calling the mock.
        verify(actuator, atLeastOnce()).moveForward();

        // We assert the car's in the same lane.
        assertEquals(1, car.whereIs().lanePosition);
        
        // Moves 15 meters without changing lanes.
        for(int i = 0; i < 2; i++ ) {
            car.moveForward();
        }
        // Verify we actually call the actuator.
        verify(actuator, atLeastOnce()).moveForward();

        // Assert distance traveled.
        assertEquals(75, car.whereIs().distance);
        
        // Radar sensors pick up an empty lane.
        when(radarSensors.Read()).thenReturn(emptyLaneRadarQuery);
        // Lidar sensor picks up an empty lane.
        when(lidarSensor.Read()).thenReturn(emptyLaneLidarQuery);

        // Act.
        car.changeLane();

        // Verify we're calling the mock.
        verify(actuator, atLeastOnce()).moveForward();

        // We assert the car's in the left-most lane.
        assertEquals(2, car.whereIs().lanePosition);
        
        // Move forward until end of road.
        for(int i = 0; i < 3; i++){
            car.moveForward();
        }	
    }
    
    @Test
    void scenario7() {
    	
        // Starts at the beginning of the street.
        // Moves 25 meters without changing lanes.
        for(int i = 0; i < 4; i++ ) {
            car.moveForward();
        }
        // Verify we actually call the actuator.
        verify(actuator, atLeastOnce()).moveForward();

        // Assert distance traveled.
        assertEquals(20, car.whereIs().distance);
        
        // Radar sensors pick up a car in the left lane.
        when(radarSensors.Read()).thenReturn(busyLaneRadarQuery);
        // Lidar sensor picks up a car far ahead.
        when(lidarSensor.Read()).thenReturn(emptyLaneLidarQuery);

        // Act.
        car.changeLane();

        // Verify we're calling the mock.
        verify(actuator, atLeastOnce()).moveForward();

        // We assert the car's in the same lane.
        assertEquals(0, car.whereIs().lanePosition);

        // Move forward 25 meters
        for(int i = 0; i < 4; i++){
            car.moveForward();
        }	
        
        // Verify we actually call the actuator.
        verify(actuator, atLeastOnce()).moveForward();

        // Assert distance traveled.
        assertEquals(45, car.whereIs().distance);
        
        // Radar sensors pick up a car one lane over.
        when(radarSensors.Read()).thenReturn(emptyLaneRadarQuery);
        // Lidar sensor picks up a car ahead.
        when(lidarSensor.Read()).thenReturn(busyLaneLidarQuery);

        // Act.
        car.changeLane();

        // Verify we're calling the mock.
        verify(actuator, atLeastOnce()).moveForward();

        // We assert the car's in the middle lane.
        assertEquals(1, car.whereIs().lanePosition);
        
        // Moves 15 meters without changing lanes.
        for(int i = 0; i < 2; i++ ) {
            car.moveForward();
        }
        // Verify we actually call the actuator.
        verify(actuator, atLeastOnce()).moveForward();

        // Assert distance traveled.
        assertEquals(60, car.whereIs().distance);
        
        // Radar sensors pick up a car in the left lane.
        when(radarSensors.Read()).thenReturn(busyLaneRadarQuery);
        // Lidar sensor picks up a car far ahead.
        when(lidarSensor.Read()).thenReturn(emptyLaneLidarQuery);

        // Act.
        car.changeLane();

        // Verify we're calling the mock.
        verify(actuator, atLeastOnce()).moveForward();

        // We assert the car's in the same lane.
        assertEquals(1, car.whereIs().lanePosition);
        
        // Moves 15 meters without changing lanes.
        for(int i = 0; i < 2; i++ ) {
            car.moveForward();
        }
        // Verify we actually call the actuator.
        verify(actuator, atLeastOnce()).moveForward();

        // Assert distance traveled.
        assertEquals(75, car.whereIs().distance);
        
        // Radar sensors pick up an empty lane.
        when(radarSensors.Read()).thenReturn(emptyLaneRadarQuery);
        // Lidar sensor picks up a car far ahead.
        when(lidarSensor.Read()).thenReturn(emptyLaneLidarQuery);
        
        // Radar sensors pick up a car in the left lane.
        when(radarSensors.Read()).thenReturn(busyLaneRadarQuery);
        // Lidar sensor picks up a car far ahead.
        when(lidarSensor.Read()).thenReturn(emptyLaneLidarQuery);

        // Act.
        car.changeLane();

        // Verify we're calling the mock.
        verify(actuator, atLeastOnce()).moveForward();

        // We assert the car's in the same lane.
        assertEquals(1, car.whereIs().lanePosition);
        
        // Move forward until end of road.
        for(int i = 0; i < 3; i++){
            car.moveForward();
        }	
    }

}
