package group5.tavCar;

public class CarMovement implements CarInterface {

    private IRadarSensors radarSensors;
    private ILidarSensor lidarSensor;
    private IActuator actuator;

    public int distance;
    public int lanePosition;

    public CarMovement(IRadarSensors radarSensors,
                       ILidarSensor lidarSensor,
                       IActuator actuator)
    {
        this.radarSensors = radarSensors;
        this.lidarSensor = lidarSensor;

        this.actuator = actuator;
    }

    public void moveForward() {
        actuator.moveForward();
        updatePosition();
    }

    @Override
    public void moveForward(int distance) throws EndOfTrackReachedException {
        actuator.moveForward(distance);
    }


    /**
     * @return true if the lane is free, false otherwise
     */
    @Override
    public boolean leftLaneDetect() {

        /**
         *  Hacky way to adapt the existing algorithm to the new way of reading sensors
         */

        int[] sensorQuery_1 = new int[4];
        int[] sensorQuery_2 = new int[4];

        System.arraycopy(radarSensors.Read(), 0, sensorQuery_1, 0, 3);
        sensorQuery_1[3] = lidarSensor.Read();

        System.arraycopy(radarSensors.Read(), 0, sensorQuery_2, 0, 3);
        sensorQuery_2[3] = lidarSensor.Read();

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
    public int changeLane() {
        if (whereIs().distance >= 95) {
            //The car is at end of street so do nothing
            return -1;
        }

        if (leftLaneDetect() && whereIs().lanePosition < 3) {
            actuator.setWheelAngle(-1);
            moveForward();
            actuator.setWheelAngle(0);
            return 0;
        }

        moveForward();
        updatePosition();
        return -1;
    }

    @Override
    public CarPosition whereIs() {

        // distanceMoved is the longitudinal position
        // lanePosition is the Latitudinal position

        CarPosition p = new CarPosition();

        p.distance = actuator.getDistance();
        p.lanePosition = actuator.getLanePosition();

        return p;
    }

    private void updatePosition(){
        CarPosition p = whereIs();

        this.distance = p.distance;
        this.lanePosition = p.lanePosition;
    }

}
