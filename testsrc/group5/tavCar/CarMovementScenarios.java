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
    private int[] emptyLaneRadarQuery  = {30, 30, 30};
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

        for(int i = 0; i < 7; i++){
            car.moveForward();
        }

    }

    @Test
    void scenario2()
    {

    }

    @Test
    void scenario3()
    {

    }

    @Test
    void scenario4()
    {

    }


}
