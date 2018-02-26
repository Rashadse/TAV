package group5.tavCar;

public class CarMovement implements CarInterface {

    // default final value of 5 to be used each time the car moves
    final int distance = 5;
    // this boolean will be changed to false when the car reaches the end of the street
    boolean streetEndNotReached = true;

    int distanceMoved = 0;

    private IRadarSensors radarSensors;
    private ILidarSensor lidarSensor;
    private IActuator actuator;

    public CarMovement(IRadarSensors radarSensors,
                       ILidarSensor lidarSensor,
                       IActuator actuator)
    {
        this.radarSensors = radarSensors;
        this.lidarSensor = lidarSensor;

        this.actuator = actuator;
    }

    // this variable shows where lane the car is in,
    //and it is initialised to 0, as the car starts at the right most lane.
    int lanePosition = 0;

    public void moveForward() {
        moveForward(5);
    }

    @Override
    public void moveForward(int distance) throws EndOfTrackReachedException {

        if (distanceMoved + distance <= 95) {
            distanceMoved += distance;
            actuator.moveForward(distance);
        } else {
            throw new EndOfTrackReachedException("The car has reached the end of the track.");
        }

        if (distanceMoved >= 95) {
            streetEndNotReached = false;
        }
    }


    /**
     * @return true if the lane is free, false otherwise
     */
    @Override
    public boolean leftLaneDetect(int[] sensorQuery_1, int[] sensorQuery_2) {

        /**
         * These integer arrays hold the results from querying all sensors twice: 
         * R1, R2 and R3, L If either doesn't contain exactly 4 items, throw an
         * error. Otherwise, check all the values in the arrays.
         */

        // Control boolean where return value is stored.
        boolean laneFree = false;
        // Counter to track how many sensors detect a free lane in both queries.
        int sensorCounter = 0;

        if (!((sensorQuery_1.length == 4) && (sensorQuery_2.length == 4))) {
            // We're not interested in covering this exception for now.
            throw new RuntimeException("Sensor readings incorrect.");

        } else {
            // Loop through both arrays.
            for (int i = 0; i < 4; i++) {
                // Same sensor in both queries works properly.
                if ((sensorQuery_1[i] <= 50) && (sensorQuery_2[i] <= 50)) {
                    // Same sensor detects an obstacle in both queries.
                    if (((sensorQuery_1[1] < 10) && (sensorQuery_2[1] < 10))
                    		|| ((sensorQuery_1[2] < 10) && (sensorQuery_2[2] < 10))) {
                    	// Check if the sensor detecting is either R2, or R3.
	                    // Lane is busy.
	                    laneFree = false;
	                    // Exit loop at this point to avoid incrementing sensorCounter.
	                    break;
	                    
                    } else {
                        // Lane free, keep track of how many sensors confirm this.
                        sensorCounter++;
                    }

                } else {
                    // One or more sensors work incorrectly in one or both queries.
                    throw new RuntimeException("Obstacles out of range");
                }
            }
        }
        // Two or more sensors detect a free lane in both queries.
        if (sensorCounter >= 2) {
            // Lane is free.
            laneFree = true;
        }
        // Return the status of the lane.
        return laneFree;

    }

    @Override
    public int changeLane(){
        return changeLane(new int[]{10,10,10,10}, new int[]{10,10,10,10});
    }


    public int changeLane(int[] sensorQuery_1, int[] sensorQuery_2) {
        if (!this.streetEndNotReached) {
            //The car is at end of street so do nothing
            return -1;
        }

        if (leftLaneDetect(sensorQuery_1, sensorQuery_2) && lanePosition < 3) {
            lanePosition++;
            actuator.setWheelAngle(-1);
            moveForward();
            actuator.setWheelAngle(0);
            return 0;
        }

        moveForward();
        return -1;
    }

    @Override
    public int[] whereIs() {
        // distanceMoved is the longitudinal position
        // lanePosition is the Latitudinal position
        return new int[]{this.distanceMoved, this.lanePosition};

    }

}
