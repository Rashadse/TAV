package group5.tavCar;

public interface CarInterface {

    void moveForward(int distance) throws EndOfTrackReachedException;

    boolean leftLaneDetect();

    int changeLane();

    CarPosition whereIs();
   
}
