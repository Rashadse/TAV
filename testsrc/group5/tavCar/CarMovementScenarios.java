package group5.tavCar;

import org.junit.jupiter.api.*;
import org.mockito.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CarMovementScenarios {

    @Spy  Actuator actuator;
    @Mock IRadarSensors radarSensors;
    @Mock ILidarSensor lidarSensor;

    @InjectMocks CarMovement car;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    private int[] busyLaneRadarQuery   = {15,  5,  5};
    private int[] busyLaneRadarQuery1   = {-1,  -1,  -1};
    private int[] emptyLaneRadarQuery  = {30, 30, 30};
    private int[] busyLaneOneLaneOverRadarQuery = {45, 11, 11};
    private int[] outOfRangeRadarQuery = {30, 30, 30};

    private int busyLaneLidarQuery   = 22;
    private int emptyLaneLidarQuery  = 30;
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
        
        // Radar sensors pick up a car in the left lane.
        when(radarSensors.Read()).thenReturn(emptyLaneRadarQuery);
        // Lidar sensor picks up a car far ahead.
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

}
