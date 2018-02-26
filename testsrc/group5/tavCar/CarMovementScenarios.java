package group5.tavCar;

import org.junit.jupiter.api.*;
import org.mockito.*;

import static org.mockito.Mockito.*;


public class CarMovementScenarios {

    @Spy  IActuator actuator;
    @Mock IRadarSensors radarSensors;
    @Mock ILidarSensor lidarSensor;

    @InjectMocks CarMovement car;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    private int[] busyLaneRadarQuery  = {15,  5,  5};
    private int[] emptyLaneRadarQuery = {30, 30, 30};

    private int busyLaneLidarQuery  = 22;
    private int emptyLaneLidarQuery = 30;

    @Test
    void scenario1()
    {
        when(radarSensors.Read()).thenReturn(busyLaneRadarQuery);
        when(lidarSensor.Read()).thenReturn(busyLaneLidarQuery);

        car.moveForward();

        verify(actuator, atLeastOnce()).moveForward();

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
