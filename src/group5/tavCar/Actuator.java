package group5.tavCar;

public class Actuator implements IActuator{
    private int lanePosition;
    private int distanceMoved;
    private int wheelAngle;

    public Actuator(){
        distanceMoved = 0;
        wheelAngle = 0;
        lanePosition = 0;
    }

    @Override
    public void moveForward(int distance) {
        if(this.distanceMoved >= 95)
        {
            throw new EndOfTrackReachedException("Uh oh");
        }

        if(wheelAngle == -1)
        {
            lanePosition++;
        }

        this.distanceMoved += distance;
    }

    public void moveForward(){
        moveForward(5);
    }

    @Override
    public void setWheelAngle(int angle) {
        this.wheelAngle = angle;
    }

    @Override
    public int getDistance() {
        return distanceMoved;
    }

    @Override
    public int getLanePosition() {
        return lanePosition;
    }
}
