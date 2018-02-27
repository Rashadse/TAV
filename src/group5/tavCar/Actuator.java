package group5.tavCar;

public class Actuator implements IActuator{
    private int lanePosition;
    private int distance;
    private int wheelAngle;

    public Actuator(){
        distance = 0;
        wheelAngle = 0;
        lanePosition = 0;
    }

    @Override
    public void moveForward(int distance) {
        if(this.distance >= 95)
        {
            throw new EndOfTrackReachedException("Uh oh");
        }

        if(wheelAngle == -1)
        {
            lanePosition++;
        }

        this.distance += distance;
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
        return distance;
    }

    @Override
    public int getLanePosition() {
        return lanePosition;
    }
}
