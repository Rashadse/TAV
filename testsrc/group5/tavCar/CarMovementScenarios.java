package group5.tavCar;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.mockito.*;

public class CarMovementScenarios {


    @Spy  IActuator actuator;
    @Mock IRadarSensors radarSensors;
    @Mock ILidarSensor lidarSensor;

    @BeforeAll
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void scenario1()
    {

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
