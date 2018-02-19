package group5.tavCar;

public interface IActuator {
    void moveForward(int distance);
    void setWheelAngle(int angle);

    int getDistance();
    int getLanePosition();
}
