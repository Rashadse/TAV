package sample;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CarMovementTest {
    @Test
    void moveForward() throws Exception {

    CarMovement c = new CarMovement();
    boolean actual = c.streetEndNotReached;

    assertTrue(actual);
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