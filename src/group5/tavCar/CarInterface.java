package group5.tavCar;

public interface CarInterface {

    void moveForward(int distance) throws EndOfTrackReachedException;

    boolean leftLaneDetect(int[] query_1, int[] query_2);

    int changeLane();

    int[] whereIs();
   
}
